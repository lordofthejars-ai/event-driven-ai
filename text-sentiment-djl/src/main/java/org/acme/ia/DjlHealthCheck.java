package org.acme.ia;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.Startup;

import ai.djl.util.Progress;
import jakarta.inject.Singleton;

@Singleton
public class DjlHealthCheck implements Progress {

    private int currentPercent;
    private long progress;
    private boolean finished = false;
    private long max = 1;

    @ConfigProperty(name="model.name")
    String modelName;

    @Startup
    public HealthCheck checkIfModelIsAvailable() {
        return new HealthCheck() {

            @Override
            public HealthCheckResponse call() {
                if (finished) {
                    return HealthCheckResponse.up("Model %s".formatted(modelName));
                } else {
                    return HealthCheckResponse.builder()
                            .name("Model %s".formatted(modelName))
                            .withData("percentage", currentPercent)
                            .down()
                            .build();
                }
            }
        };
    }

    @Override
    public void reset(String message, long max, String trailingMessage) {
        currentPercent = 0;
        progress = 0;
        finished = false;
    }

    @Override
    public void start(long initialProgress) {
        update(initialProgress);
    }

    @Override
    public void end() {
        update(max - 1);
        this.finished = true;
    }

    @Override
    public void increment(long increment) {
        update(progress + increment);
    }

    @Override
    public void update(long progress, String message) {
        this.progress = progress;

        int percent = (int) ((progress + 1) * 100 / max);
        percent = Math.min(percent, 100);
        if (percent == currentPercent && percent > 0) {
            // no need to refresh
            return;
        }

        currentPercent = percent;
    }
    
}

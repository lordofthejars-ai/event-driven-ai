FROM registry.access.redhat.com/ubi8/openjdk-21:1.18

ENV LANGUAGE='en_US:en'

ARG TARGETPLATFORM

# add DJL stuff respecting the target platform
COPY --chown=185:0 ./.djl.ai/cache/ /home/jboss/.djl.ai/cache/
COPY --chown=185:0 ./.djl.ai/$TARGETPLATFORM/pytorch/ /home/jboss/.djl.ai/pytorch/
COPY --chown=185:0 ./.djl.ai/$TARGETPLATFORM/tokenizers/ /home/jboss/.djl.ai/tokenizers/

# We make four distinct layers so if there are application changes the library layers can be re-used
COPY --chown=185 target/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 target/quarkus-app/*.jar /deployments/
COPY --chown=185 target/quarkus-app/app/ /deployments/app/
COPY --chown=185 target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]

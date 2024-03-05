package org.acme;

import org.acme.ia.SentimentService;
import org.acme.model.Rating;
import org.acme.model.Review;
import org.acme.model.Star;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import ai.djl.translate.TranslateException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Kafka Processor
 */
@ApplicationScoped
public class RatingProcessor {
    
    @Inject
    SentimentService sentimentService;

    @Incoming("review")
    @Outgoing("rating")
    public Uni<Rating> process(Review review) throws TranslateException {
        return Uni.createFrom()
            .item(sentimentService.analyze(review.review))
            .map(sentiment -> new Rating(review.name, Star.from(sentiment)));
    }

}

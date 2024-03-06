package org.acme;

import org.acme.ia.SentimentService;
import org.acme.model.Rating;
import org.acme.model.Review;
import org.acme.model.Star;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import ai.djl.translate.TranslateException;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * Kafka Processor
 */
@Singleton
public class RatingProcessor {
    
    @Inject
    SentimentService sentimentService;

    @Incoming("review")
    @Outgoing("rating")
    public Uni<Rating> process(Review review) throws TranslateException {
        return Uni.createFrom()
            .item(sentimentService.analyze(review.text()))
            .map(sentiment -> new Rating(review.productId(), Star.from(sentiment)));
    }

}

package org.acme;

import org.acme.model.Product;
import org.acme.model.Rating;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RatingProcessor {
    
    @Inject
    private Logger logger;

    @Incoming("rating")
    public void addRating(Rating rating) {

        logger.info("New rating processed %s".formatted(rating));

        Product product = Product.findById(new ObjectId(rating.productId()));
        product.addRating(rating.stars());
        product.update();
    }

}
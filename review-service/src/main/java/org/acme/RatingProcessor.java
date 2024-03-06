package org.acme;

import org.acme.model.Product;
import org.acme.model.Rating;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

// This should be done by Debezium

@ApplicationScoped
public class RatingProcessor {
    
    @Incoming("rating")
    public void addRating(Rating rating) {
        Product product = Product.findById(new ObjectId(rating.productId()));
        product.addRating(rating.stars());
        product.update();
    }

}

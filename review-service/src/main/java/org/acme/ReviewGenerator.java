package org.acme;

import org.acme.model.Feedback;
import org.acme.model.Product;
import org.acme.model.Review;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReviewGenerator {

    public Review generate() {
        // Find a random product
        final Product product = Product.getRandomProduct();

        // Find a random feedback
        final Feedback feedback = Feedback.getRandomFeedback();

        // Creates the review
        return new Review(product.id.toHexString(), feedback.text);
    }

}

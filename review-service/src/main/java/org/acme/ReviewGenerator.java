package org.acme;

import java.util.List;
import java.util.Random;

import org.acme.model.Product;
import org.acme.model.Review;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReviewGenerator {

    // Tested comments to give from 1 to 5 stars
    private List<String> comments = List.of(
            "I like a lot. I love it",
            "It's fine",
            "it's normal",
            "not good not bad",
            "it's horrible");

    // List of inserted comments
    private List<Product> products;

    @PostConstruct
    public void init() {
        products = Product.listAll();
    }

    private static Random random = new Random();

    public Review generate() {

        // Find a random product
        final Product product = products.get(random.nextInt(products.size()));

        // Find a random comment
        final String review = comments.get(random.nextInt(comments.size()));

        // Creates the review
        return new Review(product.name, review);
    }

}

package org.acme;

import java.util.List;

import org.acme.model.Product;
import org.acme.model.Review;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/review")
public class ReviewResource {

    @Channel("review")
    Emitter<Review> emit;

    @Inject
    ReviewGenerator reviewGenerator;

    @GET
    @Path("/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public Review generate() {

        // Generates a random comment for a random product
        Review review = reviewGenerator.generate();

        // MongoDB update
        Product product = Product.find("name", review.name).firstResult();
        product.addComment(review.review);
        product.update();

        // Debezium should do this
        emit.send(review);

        return review;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> products() {
        return Product.listAll();
    }
}

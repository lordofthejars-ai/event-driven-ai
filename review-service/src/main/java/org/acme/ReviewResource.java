package org.acme;

import org.acme.model.Product;
import org.acme.model.Review;
import org.bson.types.ObjectId;
import org.acme.model.Comment;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import io.quarkus.runtime.configuration.ConfigUtils;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    @Inject
    private Logger logger;

    @GET
    @Path("/generate")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Review generate() {

        // Generates a random comment for a random product
        Review review = reviewGenerator.generate();

        logger.info("Generated review %s".formatted(review));

        // MongoDB update
        Product product = Product.findById(new ObjectId(review.productId()));
        product.update();

        Comment comment = new Comment();
        comment.productId = product.id;
        comment.text = review.text();
        comment.persist();

        if (ConfigUtils.getProfiles().contains("dev")) {
            logger.info("Dev Mode configured and review is sent directly to Kafka");
            emit.send(review);
        }

        return review;
    }

}

package org.acme.model;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ReviewDeserializer extends ObjectMapperDeserializer<Review> {
    public ReviewDeserializer() {
        super(Review.class);
    }
}
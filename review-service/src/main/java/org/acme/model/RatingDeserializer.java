package org.acme.model;


import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class RatingDeserializer extends ObjectMapperDeserializer<Rating> {
    public RatingDeserializer() {
        super(Rating.class);
    }
    
}

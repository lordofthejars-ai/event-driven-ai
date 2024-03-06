package org.acme.model;

import java.util.List;
import java.util.stream.StreamSupport;

import org.acme.MongoUtils;
import org.bson.Document;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "feedback")
public class Feedback extends PanacheMongoEntity{
    
    public String text;

    public static Feedback getRandomFeedback() {
        return MongoUtils.getRandomDocument(Feedback.mongoCollection(), Feedback.class);
    }
    
}

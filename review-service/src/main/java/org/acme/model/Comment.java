package org.acme.model;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "comment")
public class Comment extends PanacheMongoEntity {

    public ObjectId productId;
    public String text;

}

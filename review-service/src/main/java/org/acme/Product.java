package org.acme;

import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "product")
public class Product extends PanacheMongoEntity {
    
    public String name;
    public double price;
    public List<String> comments;

}

package org.acme.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

import org.bson.Document;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "product")
public class ProductWithComments extends Product {
    
    public List<String> comments;

    public static List<ProductWithComments> listAll() {
        var aggIter = mongoCollection().aggregate(Arrays.asList(
            new Document("$lookup", 
                new Document("from", "comment")
                        .append("localField", "_id")
                        .append("foreignField", "productId")
                        .append("as", "comments")), 
            new Document("$project", 
                new Document("_id", 1L)
                        .append("name", 1L)
                        .append("price", 1L)
                        .append("comments", "$comments.text")
                        .append("oneStar", 1L)
                        .append("twoStars", 1L)
                        .append("threeStars", 1L)
                        .append("fourStars", 1L)
                        .append("fiveStars", 1L))
            ),ProductWithComments.class);
        return StreamSupport.stream(aggIter.spliterator(), false).toList();
    }

}

package org.acme;

import java.util.List;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.panache.common.exception.PanacheQueryException;

public class MongoUtils {

    public static <T extends PanacheMongoEntityBase> List<T> getRandomDocuments(MongoCollection<T> collection, int count, Class<T> clazz) {
        var aggIter = collection.aggregate(
            List.of(new Document("$sample",new Document("size",count))),
            clazz
        );
        return StreamSupport
            .stream(aggIter.spliterator(), false)
            .toList();
    }
    
    public static <T extends PanacheMongoEntityBase> T getRandomDocument(MongoCollection<T> collection, Class<T> clazz) {
        return getRandomDocuments(collection, 1, clazz)
                .stream()
                .findFirst()
                .orElseThrow(() -> new PanacheQueryException("error: no document was found in collection "+collection.getNamespace()));    
    }

}

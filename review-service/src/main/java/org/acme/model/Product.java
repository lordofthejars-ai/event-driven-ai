package org.acme.model;

import org.acme.MongoUtils;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "product")
public class Product extends PanacheMongoEntity {
    
    public String name;
    public double price;

    public int fiveStars;
    public int fourStars;
    public int threeStars;
    public int twoStars;
    public int oneStar;

    public void addRating(Star star) {
        switch(star) {
            case ONE_STAR -> this.oneStar++;
            case TWO_STARS -> this.twoStars++;
            case THREE_STARS -> this.threeStars++;
            case FOUR_STARS -> this.fourStars++;
            case FIVE_STARS -> this.fiveStars++;
        }
    }

    public static Product getRandomProduct() {
        return MongoUtils.getRandomDocument(mongoCollection(), Product.class);
    }

}

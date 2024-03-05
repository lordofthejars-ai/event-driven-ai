package org.acme.model;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "product")
public class Product extends PanacheMongoEntity {
    
    public String name;
    public double price;
    public List<String> comments = new ArrayList<>();

    public int fiveStars;
    public int fourStars;
    public int threeStars;
    public int twoStars;
    public int oneStar;

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public void addRating(Star star) {
        switch(star) {
            case ONE_STAR: this.increaseOneStar();
            case TWO_STARS: this.increaseTwoStars();
            case THREE_STARS: this.increaseThreeStars();
            case FOUR_STARS: this.increaseFourStars();
            case FIVE_STARS: this.increaseFiveStars();
        }
    }

    public void increaseFiveStars() {
        this.fiveStars++;
    }

    public void increaseFourStars() {
        this.fourStars++;
    }

    public void increaseThreeStars() {
        this.threeStars++;
    }

    public void increaseTwoStars() {
        this.twoStars++;
    }

    public void increaseOneStar() {
        this.oneStar++;
    }

}

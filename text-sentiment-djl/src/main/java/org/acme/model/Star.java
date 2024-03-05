package org.acme.model;


public enum Star {
    ONE_STAR, TWO_STARS, THREE_STARS, FOUR_STARS, FIVE_STARS;

    public static Star from(String sentiment) {
        return switch (sentiment) {
            case "5 stars" -> FIVE_STARS;
            case "4 stars" -> FOUR_STARS;
            case "3 stars" -> THREE_STARS;
            case "2 stars" -> TWO_STARS;
            case "1 star" -> ONE_STAR;
            default -> ONE_STAR;
        };
    }

}

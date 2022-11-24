package com.example.tonghop_linh;

public class Game {
    String category,game,image,price;

    public Game() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Game(String category, String game, String image, String price) {
        this.category = category;
        this.game = game;
        this.image = image;
        this.price = price;


    }
}

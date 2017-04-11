package com.example.mikkel.mandatory.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mikkel on 07-04-2017.
 */

public class Dish {
    @SerializedName("Id")
    private int id;
    @SerializedName("Energy")
    private int energy;
    @SerializedName("Fat")
    private float fat;
    @SerializedName("Alcohol")
    private int alcohol;
    @SerializedName("Carbohydrates")
    private float carbohydrates;
    @SerializedName("Protein")
    private float protein;
    @SerializedName("Weight")
    private int weight;
    @SerializedName("Price")
    private int price;
    @SerializedName("Title")
    private String title;
    @SerializedName("PictureUrl")
    private String pictureUrl;

    public Dish(int id, int energy, float fat, int alcohol, float carbohydrates, float protein, int weight, int price, String title, String pictureUrl) {
        this.id = id;
        this.energy = energy;
        this.fat = fat;
        this.alcohol = alcohol;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.weight = weight;
        this.price = price;
        this.title = title;
        this.pictureUrl = pictureUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public int getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}

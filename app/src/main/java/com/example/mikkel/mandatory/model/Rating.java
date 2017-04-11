package com.example.mikkel.mandatory.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Mikkel on 07-04-2017.
 */

public class Rating {
    @SerializedName("Id")
    private int id;
    @SerializedName("Datum")
    private Date date;
    @SerializedName("CustomerId")
    private int customerId;
    @SerializedName("DishId")
    private int dishId;
    @SerializedName("Rate")
    private int rate;

    public Rating(int id, Date date, int customerId, int dishId, int rate) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.dishId = dishId;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}

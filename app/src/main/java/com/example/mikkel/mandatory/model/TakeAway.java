package com.example.mikkel.mandatory.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Mikkel on 07-04-2017.
 */

public class TakeAway {
    @SerializedName("Id")
    private int id;
    @SerializedName("CustomerId")
    private int customerId;
    @SerializedName("DishId")
    private int dishId;
    @SerializedName("Howmany")
    private int howmany;
    @SerializedName("OrderDate")
    private Date orderDate;
    @SerializedName("PickupDateTime")
    private Date pickupDateTime;

    public TakeAway(int id, int customerId, int dishId, int howmany, Date orderDate, Date pickupDateTime) {
        this.id = id;
        this.customerId = customerId;
        this.dishId = dishId;
        this.howmany = howmany;
        this.orderDate = orderDate;
        this.pickupDateTime = pickupDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getHowmany() {
        return howmany;
    }

    public void setHowmany(int howmany) {
        this.howmany = howmany;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(Date pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }
}

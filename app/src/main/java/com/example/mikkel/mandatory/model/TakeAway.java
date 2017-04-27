package com.example.mikkel.mandatory.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private String orderDate;
    @SerializedName("PickupDateTime")
    private String pickupDateTime;

    public TakeAway(int id, int customerId, int dishId, int howmany, String orderDate, String pickupDateTime) {
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

    public String getOrderDate() {
        String[] splited = orderDate.split("\\(|\\+");
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        String dateString = formatter.format(new Date(Long.parseLong(splited[1])));
        return dateString;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPickupDateTime() {
        String[] splited = pickupDateTime.split("\\(|\\+");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dateString = formatter.format(new Date(Long.parseLong(splited[1])));
        return dateString;
    }

    public void setPickupDateTime(String pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }
}

package com.example.mikkel.mandatory.model;

/**
 * Created by Mikkel on 07-04-2017.
 */

import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("Id")
    private int id;
    @SerializedName("Email")
    private String email;
    @SerializedName("Firstname")
    private String firstname;
    @SerializedName("Lastname")
    private String lastname;
    @SerializedName("Password")
    private String password;
    @SerializedName("PictureUrl")
    private String pictureUrl;

    public Customer(int id, String email, String firstname, String lastname, String password, String pictureUrl) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.pictureUrl = pictureUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}

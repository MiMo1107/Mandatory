package com.example.mikkel.mandatory.rest;

/**
 * Created by Mikkel on 07-04-2017.
 */

import com.example.mikkel.mandatory.model.Customer;
import com.example.mikkel.mandatory.model.Dish;
import com.example.mikkel.mandatory.model.TakeAway;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface {
    @GET("customers/{email}/{password}")
    Call<Customer> getCustomer(@Path("email") String email, @Path("password") String password);

    @GET("customers")
    Call<List<Customer>> getAllCustomer();

    @GET("dishes")
    Call<List<Dish>> getAllDishes();

    @GET("customers/{id}")
    Call<Customer> getDish(@Path("id") int id);

    @POST("customers")
    Call<Void> createCustomer(@Body Customer customer);

    @GET("customers")
    Call<Void> getConnection();

    @GET("takeaways")
    Call<List<TakeAway>> getAllTakeaways();

}

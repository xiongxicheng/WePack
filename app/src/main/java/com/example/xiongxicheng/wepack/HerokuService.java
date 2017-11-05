package com.example.xiongxicheng.wepack;

/**
 * Created by xiongxicheng on 10/30/2017.
 */

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Field;


public interface HerokuService {
    @FormUrlEncoded
    @POST("users")
    Call<User> createUser(@Field("username") String username,
                          @Field("password") String password,
                          @Field("email") String email);

    @FormUrlEncoded
    @POST("trips")
    Call<Trip> create(@Body Trip trip);

    @FormUrlEncoded
    @GET("users/{username}")
    Call<User> get(@Path("username") String username);

    @FormUrlEncoded
    @POST("users/validate")
    Call<User> validate(@Field("username") String username,
                        @Field("password") String password);

    @FormUrlEncoded
    @POST("trips/trip/item")
    Call<Trip> addItem(@Field("item") String item,
                       @Field("_id") String id,
                        @Field("username") String username);

    @FormUrlEncoded
    @POST("trips/trip/followers")
    Call<Trip> addFollwersToTrip(@Field("username") String uername,
                                 @Field("_id") String id,
                                 @Field("ownerusername") String ownerusername
                                 );
}

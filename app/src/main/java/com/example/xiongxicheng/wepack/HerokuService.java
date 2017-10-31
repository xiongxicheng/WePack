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
    Call<User> createUser(@Field("email") String email,
                          @Field("password") String password,
                          @Field("username") String username);

    @FormUrlEncoded
    @POST("trips/new")
    Call<Trip> create(@Body Trip trip);

    @FormUrlEncoded
    @GET("users/{username}")
    Call<User> get(@Path("username") String username);
}

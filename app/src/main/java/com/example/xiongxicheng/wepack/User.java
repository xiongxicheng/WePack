package com.example.xiongxicheng.wepack;

/**
 * Created by xiongxicheng on 10/30/2017.
 */
import com.google.gson.annotations.SerializedName;
import java.util.List;
import com.google.gson.annotations.Expose;

public class User {

    @SerializedName("_id")
    @Expose
    String _id;

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("alwayspackItems")
    @Expose
    List<String> alwaypackItems;

    @SerializedName("followingTrips")
    @Expose
    List<String> followingTrip;

    @SerializedName("ownedTrips")
    @Expose
    List<Trip> ownedTrips;

    @SerializedName("__v")
    @Expose
    int __v;

    public User(String email, String password) {
        this.email = email;
        this.username = email;
        this.password = password;
    }
}

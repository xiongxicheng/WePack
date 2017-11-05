package com.example.xiongxicheng.wepack;

/**
 * Created by xiongxicheng on 10/30/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Trip {

    @SerializedName("_id")
    @Expose
    String _id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("owner")
    @Expose
    String owner;

    @SerializedName("__v")
    @Expose
    int __v;

    @SerializedName("follwers")
    @Expose
    List<String> followers;

    @SerializedName("items")
    @Expose
    List<String> items;

    @SerializedName("Created_date")
    @Expose
    Date date;

    public Trip(String userName, String tripName){
        this.name = tripName;
        this.owner = userName;
    }

}

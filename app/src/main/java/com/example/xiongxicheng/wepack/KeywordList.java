package com.example.xiongxicheng.wepack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiongxicheng on 10/14/2017.
 */

public class KeywordList {

    @SerializedName("_id")
    @Expose
    String _id;

    @SerializedName("keyword")
    @Expose
    String keyword;

    @SerializedName("items")
    @Expose
    List<String> items;

    @SerializedName("__v")
    @Expose
    int __v;

    public KeywordList(){};

    public void addItems(String item) {
        items.add(item);
    }

    public void deleteItems(String item) {
        items.remove(item);
    }

}
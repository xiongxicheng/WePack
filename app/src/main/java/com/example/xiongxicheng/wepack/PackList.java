package com.example.xiongxicheng.wepack;

import java.util.List;

/**
 * Created by xiongxicheng on 10/14/2017.
 */

public class PackList {
    List<String> items;
    TripInfo tripInfo;
    String listName;
    public PackList(TripInfo tripInfo){
        this.tripInfo = tripInfo;
    }

    public List<String> getList() {
        return this.items;
    }

    public void addItems(String item) {
        items.add(item);
    }

    public void deleteItems(String item) {
        items.remove(item);
    }

}

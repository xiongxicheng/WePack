package com.example.xiongxicheng.wepack;

import java.util.List;

/**
 * Created by xiongxicheng on 10/14/2017.
 */

public class PackList {
    List<String> items;
    TripInfo tripInfo;
    public PackList(TripInfo tripInfo){
        this.tripInfo = tripInfo;
    }

    public List<String> getItems() {
        return items;
    }

    public void addItems(String item) {
        items.add(item);
    }

    public void deleteItems(String item) {
        items.remove(item);
    }
}

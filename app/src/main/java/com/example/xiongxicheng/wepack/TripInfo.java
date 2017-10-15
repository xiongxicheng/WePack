package com.example.xiongxicheng.wepack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by xiongxicheng on 10/14/2017.
 */

public class TripInfo {

    public String destination;
    public Date startDate;
    public Date endDate;
    public List<String> templateList;

    public TripInfo(String destination, Date startDate, Date endDate){
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.templateList = new ArrayList<>();
        this.templateList.addAll(Arrays.asList("Umbrella or raincoat","Pajama"));
    }

}

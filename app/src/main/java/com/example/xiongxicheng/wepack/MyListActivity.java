package com.example.xiongxicheng.wepack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.*;

import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listViewMyList;
    private ArrayAdapter<String> myAdapter;

    private static User currentUser;
    private static Trip selectedTrip;
    private static List<Trip> ownedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        currentUser = MainActivity.getCurrentUser();
        ownedList = currentUser.ownedTrips;

        listViewMyList = (ListView) findViewById(R.id.listViewMyList);
        listViewMyList.setSelector(R.drawable.listselector);
        myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listViewMyList.setAdapter(myAdapter);
        for(int i=0;i<ownedList.size();i++){
            myAdapter.add(ownedList.get(i).name);
        }
        listViewMyList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position,long id){
        selectedTrip = ownedList.get(position);
        finish();
        startActivity(new Intent(this,EditListActivity.class));
    }
    public static Trip getSelectedTrip(){
        return selectedTrip;
    }
}

package com.example.xiongxicheng.wepack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listViewMyList;
    private ArrayAdapter<String> myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        String[] arr = new String[]{"Weekend Camping Trip", "Thanksgiving", "Ski Trip"};
        listViewMyList = (ListView) findViewById(R.id.listViewMyList);
        listViewMyList.setSelector(R.drawable.listselector);
        myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listViewMyList.setAdapter(myAdapter);
        for(int i=0;i<arr.length;i++){
            myAdapter.add(arr[i]);
        }
        listViewMyList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position,long id){
        String listName = parent.getItemAtPosition(position).toString();
        finish();
        startActivity(new Intent(this,EditListActivity.class));
    }
}

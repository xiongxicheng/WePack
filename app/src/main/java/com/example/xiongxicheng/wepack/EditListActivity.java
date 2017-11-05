package com.example.xiongxicheng.wepack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.*;
import android.widget.AdapterView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditListActivity extends AppCompatActivity implements View.OnClickListener{


//    private DatabaseReference databaseReference;
//    private FirebaseDatabase database;
//    private DataSnapshot dataSnapshot;
    private ListView listViewPackList;
    private ArrayAdapter<String> myAdapter;

    private Button buttonLogout;
    private Button buttonShare;
    private Button buttonAdd;

    private EditText editTextAdd;
    private EditText editTextShare;

    private static Trip currentTrip;
    private static List<String> items;
    private static User owner;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://wepack4261.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    final HerokuService service = retrofit.create(HerokuService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonShare= (Button)findViewById(R.id.buttonShare);

        editTextAdd = (EditText) findViewById(R.id.editTextAdd);
        editTextShare = (EditText) findViewById(R.id.editTextShare);

        buttonLogout.setOnClickListener(this);
        //buttonAdd.setOnClickListener(this);

        currentTrip = MyListActivity.getSelectedTrip();
        owner = MainActivity.getCurrentUser();

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Call<Trip> call = service.addItem(editTextAdd.getText().toString().trim(),currentTrip._id,currentTrip.owner);
                call.enqueue(new Callback<Trip>() {
                    @Override
                    public void onResponse(Call<Trip> call, Response<Trip> response) {
                        myAdapter.add(editTextAdd.getText().toString().trim());
                    }

                    @Override
                    public void onFailure(Call<Trip> call, Throwable t) {

                    }
                });
            }

        });

        buttonShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Call<Trip> call = service.addFollwersToTrip(editTextShare.getText().toString().trim(),currentTrip._id,currentTrip.owner);
                call.enqueue(new Callback<Trip>() {
                    @Override
                    public void onResponse(Call<Trip> call, Response<Trip> response) {
                        Toast.makeText(EditListActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Trip> call, Throwable t) {

                    }
                });
            }

        });


        items = MyListActivity.getSelectedTrip().items;
        listViewPackList = (ListView) findViewById(R.id.listViewPackList);
        listViewPackList.setSelector(R.drawable.listselector);
        myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listViewPackList.setAdapter(myAdapter);
        for(int i=0;i<items.size();i++){
            myAdapter.add(items.get(i));
        }

    }

    @Override
    public void onClick(View view){
//        if(view == buttonAdd){
//            String item = editTextAdd.getText().toString().trim();
//            myAdapter.add(item);
//
//        }
        if(view == buttonLogout){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
//        if(view == buttonShare){
//            //Share activity here
//            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
//            return;
//        }


    }
}

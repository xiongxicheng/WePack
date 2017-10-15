package com.example.xiongxicheng.wepack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

public class CreateTripActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDestination;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private Button buttonCreateList;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        buttonCreateList = (Button) findViewById(R.id.buttonCreateList);
        editTextDestination = (EditText) findViewById(R.id.editTextDestination);
        editTextStartDate = (EditText) findViewById(R.id.editTextStartDate);
        editTextEndDate = (EditText) findViewById(R.id.editTextEndDate);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        buttonCreateList.setOnClickListener(this);
    }

    private void saveTripInfo(){
        String destination = editTextDestination.getText().toString().trim();
        String startDateString = editTextStartDate.getText().toString().trim();
        Date startDate = new Date(Integer.parseInt(startDateString.split("-")[0]),
                Integer.parseInt(startDateString.split("-")[1]),
                Integer.parseInt(startDateString.split("-")[2]));
        String endDateString = editTextStartDate.getText().toString().trim();
        Date endDate = new Date(Integer.parseInt(endDateString.split("-")[0]),
                Integer.parseInt(endDateString.split("-")[1]),
                Integer.parseInt(endDateString.split("-")[2]));
        TripInfo tripInfo = new TripInfo(destination, startDate, endDate);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(tripInfo);

    }

//    private List<String> getTemplateList(TripInfo tripInfo){
//        PackList packTemplate =
//    }

    @Override
    public void onClick(View view){
        if(view==buttonCreateList){
            saveTripInfo();
            finish();
            startActivity(new Intent(this, EditListActivity.class));
        }
    }
}

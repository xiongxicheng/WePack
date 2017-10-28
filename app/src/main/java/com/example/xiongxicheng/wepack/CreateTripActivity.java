package com.example.xiongxicheng.wepack;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateTripActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText editTextDestination;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private Button buttonCreateList;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Spinner spinnerTripType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        buttonCreateList = (Button) findViewById(R.id.buttonCreateList);
        editTextDestination = (EditText) findViewById(R.id.editTextDestination);
        editTextStartDate = (EditText) findViewById(R.id.editTextStartDate);
        editTextEndDate = (EditText) findViewById(R.id.editTextEndDate);
        spinnerTripType = (Spinner) findViewById(R.id.spinnerTripType);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        buttonCreateList.setOnClickListener(this);

        String[] types = {"Ski", "Camping", "Beach", "None"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTripType.setAdapter(typeAdapter);
        spinnerTripType.setOnItemSelectedListener(this);
        editTextStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment dialogFragment = new DatePickerDialogStart();
                dialogFragment.show(getFragmentManager(),"Start");
            }
        });
        editTextEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment dialogFragment = new DatePickerDialogEnd();
                dialogFragment.show(getFragmentManager(),"End");
            }
        });
    }

    public static class DatePickerDialogStart extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialogStart = new DatePickerDialog(getActivity(),this,year,month,day);
            return datePickerDialogStart;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            EditText editText = (EditText) getActivity().findViewById(R.id.editTextStartDate);
            editText.setText(month+"-"+day+"-"+year);
        }
    }
    public static class DatePickerDialogEnd extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialogEnd = new DatePickerDialog(getActivity(),this,year,month,day);
            return datePickerDialogEnd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            EditText editText = (EditText) getActivity().findViewById(R.id.editTextEndDate);
            editText.setText(month+"-"+day+"-"+year);
        }
    }


    private void saveTripInfo(){
        String destination = editTextDestination.getText().toString().trim();
        String startDateString = editTextStartDate.getText().toString().trim();
        Date startDate = new Date(Integer.parseInt(startDateString.split("-")[2]),
                Integer.parseInt(startDateString.split("-")[0]),
                Integer.parseInt(startDateString.split("-")[1]));
        String endDateString = editTextStartDate.getText().toString().trim();
        Date endDate = new Date(Integer.parseInt(endDateString.split("-")[2]),
                Integer.parseInt(endDateString.split("-")[0]),
                Integer.parseInt(endDateString.split("-")[1]));
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View v,int position, long id){

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0){

    }
}

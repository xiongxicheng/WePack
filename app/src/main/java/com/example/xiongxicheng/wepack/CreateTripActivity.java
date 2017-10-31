package com.example.xiongxicheng.wepack;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.ConnectionResult;

public class CreateTripActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, OnConnectionFailedListener{

    public double lat=0.0;
    public double lng=0.0;
    public String destinationString;
    //protected GeoDataClient mGeoDataClient;
    private GoogleApiClient mGoogleApiClient;
    private TextView textViewDestination;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private Button buttonCreateList;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Spinner spinnerTripType;

    private PlaceAutocompleteFragment placeAutocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        buttonCreateList = (Button) findViewById(R.id.buttonCreateList);
        textViewDestination = (TextView) findViewById(R.id.textViewDestination);
        editTextStartDate = (EditText) findViewById(R.id.editTextStartDate);
        editTextEndDate = (EditText) findViewById(R.id.editTextEndDate);
        spinnerTripType = (Spinner) findViewById(R.id.spinnerTripType);
        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        buttonCreateList.setOnClickListener(this);

        //mGeoDataClient = Places.getGeoDataClient(this,null);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //get info
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
                destinationString = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                //handle error
            }
        });


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
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialogStart = new DatePickerDialog(getActivity(),this,year,month,day);
            return datePickerDialogStart;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            EditText editText = (EditText) getActivity().findViewById(R.id.editTextStartDate);
            editText.setText((1+month)+"-"+day+"-"+year);
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
            editText.setText((1+month)+"-"+day+"-"+year);
        }
    }


    private void saveTripInfo(){
        String destination = textViewDestination.getText().toString().trim();
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
        //Toast.makeText(this,Double.toString(lat)+","+Double.toString(lng),Toast.LENGTH_SHORT).show();
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){

    }
}

package com.example.xiongxicheng.wepack;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.ConnectionResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.Set;

public class CreateTripActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, OnConnectionFailedListener{

    public double lat=0.0;
    public double lng=0.0;
    public String destinationString;
    //protected GeoDataClient mGeoDataClient;
    private GoogleApiClient mGoogleApiClient;
    private TextView textViewDestination;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private EditText editTextListName;
    private Button buttonCreateList;
    private Spinner spinnerTripType;
    private static User currentUser;

    private HashMap<String, KeywordList> hm;

    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private Trip currentTrip;

    private static List<KeywordList> keywordLists;
    private static String selectedKeyword;
    private static List<String> typeItems;
    final String[] keywords = {"none","skiing","hiking"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        buttonCreateList = (Button) findViewById(R.id.buttonCreateList);
        textViewDestination = (TextView) findViewById(R.id.textViewDestination);
        editTextStartDate = (EditText) findViewById(R.id.editTextStartDate);
        editTextListName = (EditText) findViewById(R.id.editTextListName);
        editTextEndDate = (EditText) findViewById(R.id.editTextEndDate);
        spinnerTripType = (Spinner) findViewById(R.id.spinnerTripType);
        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        currentUser = MainActivity.getCurrentUser();
        hm = new HashMap<>();
        typeItems = new ArrayList<String>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wepack4261.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final HerokuService service = retrofit.create(HerokuService.class);
        Call<List<KeywordList>> call = service.getAllKeywordLists();
        call.enqueue(new Callback<List<KeywordList>>() {
            @Override
            public void onResponse(Call<List<KeywordList>> call, Response<List<KeywordList>> response) {
                keywordLists = response.body();
//                KeywordList k= new KeywordList();
//                k.addItems("coke");
                hm.put("none",new KeywordList());
                for(KeywordList list:keywordLists){
                    hm.put(list.keyword,list);
                }

            }

            @Override
            public void onFailure(Call<List<KeywordList>> call, Throwable t) {

            }
        });

        buttonCreateList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editTextListName.setText("haha");
                finish();
                startActivity(new Intent(CreateTripActivity.this, EditListActivity.class));
            }

        });

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

        //String[] keywords = new String[hm.size()+1];
        //keywords[0] = "None";
        //int i=0;
        //keywords[0] = templ.get(0);
//        for(String keyword: templ){
//            keywords[i++] = keyword;
//        }

        ArrayAdapter<String> keywordsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,keywords);
        spinnerTripType.setAdapter(keywordsAdapter);
        keywordsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedKeyword = keywords[position];
                KeywordList list = hm.get("skiing");
                typeItems = list.items;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                Call<Trip> call = service.createTrip(MainActivity.getCurrentUser().username,editTextListName.getText().toString().trim(),selectedKeyword);
                call.enqueue(new Callback<Trip>() {
                    @Override
                    public void onResponse(Call<Trip> call, Response<Trip> response) {
                        Trip trip = response.body();
                        MyListActivity.setSelectedTrip(trip);
                    }

                    @Override
                    public void onFailure(Call<Trip> call, Throwable t) {

                    }
                });

                //currentTrip = MyListActivity.getSelectedTrip();
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

    }

//    private List<String> getTemplateList(TripInfo tripInfo){
//        PackList packTemplate =
//    }

    @Override
    public void onClick(View view){
//        if(view == buttonCreateList){
//            finish();
//            startActivity(new Intent(this, EditListActivity.class));
//        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){

    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){
        //typeItems = hm.get(keywords[position]).items;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parentView){

    }

    public static List<String> getTypeItems(){
        return typeItems;
    }
}

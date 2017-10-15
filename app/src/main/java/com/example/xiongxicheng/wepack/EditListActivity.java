package com.example.xiongxicheng.wepack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

public class EditListActivity extends AppCompatActivity implements View.OnClickListener{

//    private FirebaseAuth firebaseAuth;
//    private DatabaseReference databaseReference;
//    private FirebaseDatabase database;
//    private DataSnapshot dataSnapshot;
//    private ListView listViewPackList;
//    @Override
    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private Button buttonShare;
    private Button buttonSave;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonShare= (Button)findViewById(R.id.buttonShare);
        buttonLogout.setOnClickListener(this);
        buttonShare.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

//  Listview not working
//        String[] arr = new String[]{"as", "ad", "af"};
//        listViewPackList = (ListView) findViewById(R.id.listViewPackList);
//        listViewPackList.setAdapter(new ArrayAdapter<String>(this,R.layout.activity_edit_list, arr));

//        final List<PackList> packLists = new ArrayList<>();
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        databaseReference.child("tripInfo").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //List<String> template = (List<String>) dataSnapshot.child("templateList").getValue();
//                //String[] templateArr = new String[template.size()];
//                //templateArr = template.toArray(templateArr);
//                String[] templateArr = new String[]{"a","b","c"};
//                listViewPackList = (ListView) findViewById(R.id.listViewPackList);
//                ArrayAdapter<String> adapter= new ArrayAdapter<String>(EditListActivity.this, R.layout.activity_edit_list, templateArr);
//                listViewPackList.setAdapter(adapter);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



    }

    @Override
    public void onClick(View view){
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(view == buttonShare){
            //Share activity here
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
            return;
        }
        if(view == buttonSave){
            //save activity here
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
            return;
        }

    }
}

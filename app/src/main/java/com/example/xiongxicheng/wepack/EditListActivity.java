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
import com.google.firebase.database.ValueEventListener;

import java.util.*;
import android.widget.AdapterView;
public class EditListActivity extends AppCompatActivity implements View.OnClickListener{


//    private DatabaseReference databaseReference;
//    private FirebaseDatabase database;
//    private DataSnapshot dataSnapshot;
    private ListView listViewPackList;
    private ArrayAdapter<String> myAdapter;

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private Button buttonShare;
    private Button buttonSave;
    private Button buttonAdd;

    private EditText editTextAdd;
    private EditText editTextShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonShare= (Button)findViewById(R.id.buttonShare);

        editTextAdd = (EditText) findViewById(R.id.editTextAdd);
        editTextShare = (EditText) findViewById(R.id.editTextShare);

        buttonLogout.setOnClickListener(this);
        buttonShare.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);

//  Listview not working
        String[] arr = new String[]{"pajama", "raincoat", "flash light"};
        listViewPackList = (ListView) findViewById(R.id.listViewPackList);
        listViewPackList.setSelector(R.drawable.listselector);
        myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listViewPackList.setAdapter(myAdapter);
        for(int i=0;i<arr.length;i++){
            myAdapter.add(arr[i]);
        }

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
        if(view == buttonAdd){
            String item = editTextAdd.getText().toString().trim();
            myAdapter.add(item);
        }
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

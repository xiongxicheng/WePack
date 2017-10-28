package com.example.xiongxicheng.wepack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;

    private Button buttonLogout;
    private Button buttonCreate;
//    private Button buttonViewInvitation;
    private Button buttonLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonCreate = (Button) findViewById(R.id.buttonCreate);
        //buttonViewInvitation = (Button) findViewById(R.id.buttonViewInvitation);
        buttonLists = (Button) findViewById(R.id.buttonLists);

        buttonLogout.setOnClickListener(this);
        buttonCreate.setOnClickListener(this);
        buttonLists.setOnClickListener(this);
        //buttonViewInvitation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(view==buttonCreate){
            //create list activity
            finish();
            startActivity(new Intent(this, CreateTripActivity.class));
        }
        if(view==buttonLists){
            //view my lists
            finish();
            startActivity(new Intent(this, MyListActivity.class));
        }
//        if(view==buttonViewInvitation){
//            //view invitation
//            finish();
//            startActivity(new Intent(this, ViewInvitationActivity.class));
//        }
    }
}

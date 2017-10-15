package com.example.xiongxicheng.wepack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewInvitationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonShowInvitation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invitation);
        buttonShowInvitation = (Button)findViewById(R.id.buttonShowInvitation);
        buttonShowInvitation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == buttonShowInvitation){
            finish();
            startActivity(new Intent(this,EditListActivity.class));
        }
    }

}

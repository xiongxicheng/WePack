package com.example.xiongxicheng.wepack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignup = (TextView) findViewById(R.id.textViewSignup);

        //buttonSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wepack4261.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final HerokuService service = retrofit.create(HerokuService.class);
        buttonSignin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Call<User> call = service.validate(editTextEmail.getText().toString(),editTextPassword.getText().toString());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        //check if already registered
                        if (response.isSuccessful()){
                            User currentUser = response.body();
                            MainActivity.setCurrentUser(currentUser);
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"Wrong username or password",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }

        });
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void onClick(View view){
        if(view==buttonSignin){
            userLogin();
        }
        if(view==textViewSignup){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

}

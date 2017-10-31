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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignup;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

//        firebaseAuth = FirebaseAuth.getInstance();
//        if(firebaseAuth.getCurrentUser()!=null){
//            //Home activity here
//            finish();
//            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wepack4261.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final HerokuService service = retrofit.create(HerokuService.class);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        //final User user = new User(editTextEmail.getText().toString(),editTextPassword.getText().toString());

        textViewSignin.setOnClickListener(this);

        buttonSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                User user = new User("chenchen","chenchen");
                Call<User> call = service.createUser("chenchen","chenchen","chenchen");
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        //check if already registered
                        User newUser = response.body();
                        editTextEmail.setText("register");
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }

        });

    }
    private void regiterUser(){
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
        progressDialog.setMessage("Registering...");
        //progressDialog.show();

//        firebaseAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            //Home activity here
//                            finish();
//                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//
//                            Toast.makeText(MainActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(MainActivity.this,"Fail to register",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

    }

    @Override
    public void onClick(View view){
//        if(view==buttonSignup){
//            startActivity(new Intent(this,HomeActivity.class));
//        }
        if(view==textViewSignin){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}

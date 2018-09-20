package com.example.samin.paitientmanagement.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samin.paitientmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

/**
 * Created by Samin on 02-02-2017.
 */

public class login_activity  extends AppCompatActivity implements View.OnClickListener{

    public EditText et_email, et_pass;
    private Button loginButton,signup_button, forget_password_button;
    private ProgressDialog PGD;
    private FirebaseAuth firebaseAuth;
    private TextView forget_pass,new_user,guestUser;
    private TextInputLayout password_Field;
    private Boolean flag=false;
    private String email,pass;
    public static Activity login_Activity;


    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
       // Log.d("LOGGED", "LOGIN Application onCreate: ");


        //Static variable to finish this Activity on Sign up Complete

        PGD = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            //User Already logged In
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            i.putExtra("user_type",getIntent().getStringExtra("user_type"));
            this.finish();
            startActivity(i);
            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        login_Activity = this;

        guestUser = findViewById(R.id.guest_login);
        et_email = (EditText) findViewById(R.id.input_Email);
        et_pass =(EditText) findViewById(R.id.input_Password);
        loginButton = (Button) findViewById(R.id.login_button);
        forget_pass = (TextView)findViewById(R.id.forget_password);

        new_user = (TextView)findViewById(R.id.new_user_Text_view);
        signup_button = (Button) findViewById(R.id.signup_button);
        forget_password_button = (Button) findViewById(R.id.forget_password_button);

        password_Field = (TextInputLayout)findViewById(R.id.input_layout_password);

        forget_pass.setOnClickListener(this);
        forget_password_button.setOnClickListener(this);

        //On click listener for login button
        loginButton.setOnClickListener(this);

        guestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = "saminali500@gmail.com";
                pass = "123456";
                PGD.setMessage("Please Wait...");
                PGD.show();
                firebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        if(task.isSuccessful()){
                            // getProviders() will return size 1. if email ID is available.

                            if (task.getResult().getProviders().size() > 0) {
                                validation_completed(email,pass);
                                return;
                            }
                            Toast.makeText(login_activity.this, "Email Not Registered  !!", Toast.LENGTH_SHORT).show();
                            PGD.dismiss();
                        }
                        else{
                            PGD.dismiss();
                            Toast.makeText(login_activity.this, " "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //Change the Fonts of email and Password Test
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Bold.ttf");
        et_email.setTypeface(type);
        et_pass.setTypeface(type);

    }

    @Override
    public void onClick(View v) {
        if(v == loginButton)
        {
            userLogin();
            //Log.d("LOGGED", "loginButton: " + v);
        }
        if(v == forget_pass)
        {
            forget_password_call();
        }
    }

    private void forget_password_call() {
        {
            flag=true;
            //Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            signup_button.setVisibility(View.GONE);
            password_Field.setVisibility(View.GONE);
            new_user.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            forget_password_button.setVisibility(View.VISIBLE);

            forget_password_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email = et_email.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplication(), "Enter your registered Email Id", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //progressBar.setVisibility(View.VISIBLE);
                        PGD.setMessage("Please Wait...");
                        PGD.show();

                        firebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                if(task.isSuccessful()){
                                    // getProviders() will return size 1. if email ID is available.

                                    if (task.getResult().getProviders().size() > 0) {
                                        // Toast.makeText(Create_account.this, "Email : registered " + task.getResult().getProviders(), Toast.LENGTH_SHORT).show();
                                        //PGD.dismiss();
                                        //Toast.makeText(login_activity.this, " ID already registered  !! " , Toast.LENGTH_SHORT).show();
                                        //return;
                                        call_forgetpassword(email);
                                        return;
                                    }
                                    Toast.makeText(login_activity.this, "Email Not Registered  !!", Toast.LENGTH_SHORT).show();
                                    PGD.dismiss();
                                }
                                else{
                                    PGD.dismiss();
                                    Toast.makeText(login_activity.this, " "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });


        }
    }
    private void call_forgetpassword(String email)
    {

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login_activity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    onBackPressed();
                                }
                            }, 1000);

                        } else {
                            Toast.makeText(login_activity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            PGD.dismiss();
                            return;
                        }
                        //progressBar.setVisibility(View.GONE);
                        PGD.dismiss();

                    }
                });
    }

    private void userLogin() {
        email = et_email.getText().toString().trim();
        pass = et_pass.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //Email is Empty
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            //stop the function for Executing further
            return;
        }
        if(TextUtils.isEmpty(pass)){
            //Password is Empty
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            //stop the function for Executing further
            return;
        }
        if(pass.length()< 6)
        {
            Toast.makeText(login_activity.this, "Password Should Minimum 6", Toast.LENGTH_SHORT).show();
            return;
        }

        PGD.setMessage("Please Wait...");
        PGD.show();


        firebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if(task.isSuccessful()){
                    // getProviders() will return size 1. if email ID is available.

                    if (task.getResult().getProviders().size() > 0) {
                        validation_completed(email,pass);
                        return;
                    }
                    Toast.makeText(login_activity.this, "Email Not Registered  !!", Toast.LENGTH_SHORT).show();
                    PGD.dismiss();
                }
                else{
                    PGD.dismiss();
                    Toast.makeText(login_activity.this, " "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void validation_completed(String email,String pass) {


        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        PGD.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            //Create_account.create_Activity.finish();
                            PGD.dismiss();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast.makeText(login_activity.this, "Password Mismatched !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signup_form(View view) {
        Intent i = new Intent(this,Create_account.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if(flag)
        {
            signup_button.setVisibility(View.VISIBLE);
            password_Field.setVisibility(View.VISIBLE);
            new_user.setVisibility(View.VISIBLE);
            forget_password_button.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            flag=false;
            return;
        }

        super.onBackPressed();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        String s = getIntent().getStringExtra("Current_Email");
//        et_email.setText(s);
//    }
}

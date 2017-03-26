package com.example.samin.paitientmanagement.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.samin.paitientmanagement.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by Samin on 02-02-2017.
 */

public class Create_account extends AppCompatActivity implements View.OnClickListener{
    private EditText Name,Email,Password;
    private Button signup;
    private ProgressDialog PGD;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    public String UserID,email,pass;
    private Firebase mRoofRef,userRef;
    RadioButton RB_doctor,RB_patient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        PGD = new ProgressDialog(this);


        //FireBase Details




        //Name = (EditText) findViewById(R.id.etsignup_name);
        Email = (EditText) findViewById(R.id.etsignup_email);
        Password = (EditText) findViewById(R.id.etsignup_password);
        signup = (Button) findViewById(R.id.makeappointment_button);

        RB_doctor = (RadioButton)findViewById(R.id.rb_Doctor);
        RB_patient = (RadioButton)findViewById(R.id.rb_Patient);

        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == signup)
        {
            registerUser();
        }
    }

    private void registerUser() {
        email = Email.getText().toString().trim();
        pass = Password.getText().toString().trim();

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
        if(pass.length() < 6)
        {
            Toast.makeText(this, "Password Should be minimum of 6 character", Toast.LENGTH_SHORT).show();
            //stop the function for Executing further
            return;
        }
        if(!RB_doctor.isChecked() && !RB_patient.isChecked())
        {
            Toast.makeText(this, "Select User type ! ", Toast.LENGTH_SHORT).show();
            return;
        }

        PGD.setMessage("Registering User...");
        PGD.show();



        firebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if(task.isSuccessful()){
                    // getProviders() will return size 1. if email ID is available.

                    if (task.getResult().getProviders().size() > 0) {
                       // Toast.makeText(Create_account.this, "Email : registered " + task.getResult().getProviders(), Toast.LENGTH_SHORT).show();
                        PGD.dismiss();
                        Toast.makeText(Create_account.this, " ID already registered  !! " , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //Toast.makeText(Create_account.this, "Email : Blank " + task.getResult().getProviders(), Toast.LENGTH_SHORT).show();
                     email_validated(email,pass);
                }
                else{
                    PGD.dismiss();
                    Toast.makeText(Create_account.this, " "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });



    }



    private void email_validated(String email,String pass)
    {

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Create_account.this, "Register Successfully. ", Toast.LENGTH_LONG).show();



                            user = firebaseAuth.getCurrentUser();

                            UserID=user.getEmail().replace("@","").replace(".","");
                            //mRoofRef= new Firebase("https://patient-management-11e26.firebaseio.com/"+"User_Details/"+UserID);
                            mRoofRef= new Firebase("https://patient-management-11e26.firebaseio.com/");
                            userRef = mRoofRef.child("User_Details").child(UserID);



                            Firebase childRef_name = userRef.child("Name");
                            childRef_name.setValue("Null");

                            Firebase childRef_phone = userRef.child("Phone");
                            childRef_phone.setValue("Null");

                            Firebase childRef_address = userRef.child("Address");
                            childRef_address.setValue("Null");

                            Firebase childRef_age = userRef.child("Age");
                            childRef_age.setValue("Null");

                            Firebase childRef_height = userRef.child("Height");
                            childRef_height.setValue("Null");

                            Firebase childRef_weight = userRef.child("Weight");
                            childRef_weight.setValue("Null");

                            Firebase childRef_bloodgroup = userRef.child("Bloodgroup");
                            childRef_bloodgroup.setValue("Null");

                            Firebase childRef_image_url = userRef.child("Image_URL");
                            childRef_image_url.setValue("Null");

                            if(RB_doctor.isChecked())
                            {
                                userRef.child("User_Type").setValue("Doctor");
                            }
                            if(RB_patient.isChecked())
                            {
                                userRef.child("User_Type").setValue("Patient");
                            }

                            PGD.dismiss();

                            callLogin();


                        } else {
                            Toast.makeText(Create_account.this, "Could Not Register, Try Again.", Toast.LENGTH_SHORT).show();
                            PGD.dismiss();
                        }
                    }
                });


    }

    private void callLogin() {
        Intent i= new Intent(this,login_activity.class);
        login_activity.login_Activity.finish();
        finish();
        startActivity(i);

    }
}

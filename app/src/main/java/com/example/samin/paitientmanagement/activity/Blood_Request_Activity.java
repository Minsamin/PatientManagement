package com.example.samin.paitientmanagement.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.samin.paitientmanagement.R;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Samin on 08-03-2017.
 */

public class Blood_Request_Activity extends AppCompatActivity {
    TextView request_name,request_address,request_phone;
    Button blood_request;
    private FirebaseAuth firebaseAuth;
    public String UserID;
    Spinner blood_group;
    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_request);

        firebaseAuth = FirebaseAuth.getInstance();

        request_name= (TextView)findViewById(R.id.et_blood_request_name);
        request_address= (TextView)findViewById(R.id.et_blood_request_address);
        request_phone= (TextView)findViewById(R.id.et_blood_request_phone);
        blood_request = (Button)findViewById(R.id.button_blood_request);
        blood_group = (Spinner)findViewById(R.id.spinner_blood_request);
       // request_email = (TextView) findViewById(R.id.et_blood_request_email);

        blood_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = request_name.getText().toString().trim();
                String getPhone = request_phone.getText().toString().trim();
                String getAddress = request_address.getText().toString().trim();
                String getBlood_Group = blood_group.getSelectedItem().toString();
                //String getEmail = request_email.getText().toString().trim();

                if(TextUtils.isEmpty(getName)) {
                    Toast.makeText(getApplicationContext(), "Name Require", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(getPhone)) {
                    Toast.makeText(getApplicationContext(), "Phone No Require", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(getAddress)) {
                    Toast.makeText(getApplicationContext(), "Address Require", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(TextUtils.isEmpty(getEmail)) {
//                    Toast.makeText(getApplicationContext(), "Email Require", Toast.LENGTH_SHORT).show();
//                    return;
//                }


                FirebaseUser user = firebaseAuth.getCurrentUser();
                UserID=user.getEmail().replace("@","").replace(".","");
                Firebase mRoofRef = new Firebase("https://patient-management-11e26.firebaseio.com/");
                Firebase userRef = mRoofRef.child("Blood_Request").push();
                userRef.child("Person_Name").setValue(getName);
                userRef.child("Person_Phone").setValue(getPhone);
                userRef.child("Person_Address").setValue(getAddress);
                userRef.child("Person_Email").setValue(UserID);
                userRef.child("Person_Request_blood_group").setValue(getBlood_Group);

                Toast.makeText(getApplicationContext(), "Request Registered", Toast.LENGTH_SHORT).show();

                request_name.setText("");
                request_phone.setText("");
                request_address.setText("");
            }
        });

    }
}

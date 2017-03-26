package com.example.samin.paitientmanagement.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.activity.Blood_Request_Activity;
import com.example.samin.paitientmanagement.activity.Show_Appointments;
import com.example.samin.paitientmanagement.activity.Show_blood_request;


public class BloodBankFragment extends Fragment {
    Button show_requests, make_request,blood_banks;

    public BloodBankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blood_donate_layout, container, false);
        show_requests= (Button)v.findViewById(R.id.button_show_Blood_Requests);
        make_request= (Button)v.findViewById(R.id.button_Blood_Requests);
        blood_banks= (Button)v.findViewById(R.id.button_Blood_banks);


        //Make Blood Request
        make_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Blood_Request_Activity.class);
                getContext().startActivity(i);
            }
        });



        //Show Blood Request
        show_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Show_blood_request.class);
                getContext().startActivity(i);

            }
        });


        //Show Blood Banks
        blood_banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Not Implemented Yet", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}

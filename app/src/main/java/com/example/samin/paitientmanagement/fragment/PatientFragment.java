package com.example.samin.paitientmanagement.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.activity.Show_Appointments;

import org.w3c.dom.Text;

public class PatientFragment extends Fragment {

    private TextView your_appointment_ref;
    Context ctx;

    public PatientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx=ctx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_paitient, container, false);

        your_appointment_ref = (TextView) v.findViewById(R.id.your_appointment);

        your_appointment_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), Show_Appointments.class);
                getContext().startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

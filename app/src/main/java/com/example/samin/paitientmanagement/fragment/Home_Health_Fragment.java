package com.example.samin.paitientmanagement.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;

public class Home_Health_Fragment extends Fragment {
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;
    TextView website_link;


    public Home_Health_Fragment() {
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
        View v= inflater.inflate(R.layout.home_health_layout, container, false);

        imageView1 =(ImageView)v.findViewById(R.id.home_health_1);
        imageView2 =(ImageView)v.findViewById(R.id.home_health_2);
        imageView3 =(ImageView)v.findViewById(R.id.home_health_3);
        imageView4 =(ImageView)v.findViewById(R.id.home_health_4);
        imageView5 =(ImageView)v.findViewById(R.id.home_health_5);
        imageView6 =(ImageView)v.findViewById(R.id.home_health_6);
        imageView7 =(ImageView)v.findViewById(R.id.home_health_7);
        website_link = (TextView)v.findViewById(R.id.home_health_website_link);


        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/patient-management-11e26.appspot.com/o/Inhouse_Treatment%2Fhome_health_1.PNG?alt=media&token=03886cf2-96a0-4cd5-8f48-d6ca728e3fa3")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView1);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/patient-management-11e26.appspot.com/o/Inhouse_Treatment%2Fhome_health_2.PNG?alt=media&token=08033d96-9b08-48c8-884b-42309ad3d670")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView2);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/patient-management-11e26.appspot.com/o/Inhouse_Treatment%2Fhome_health_3.PNG?alt=media&token=13656f77-9f99-40d1-904f-b587fe08054a")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView3);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/patient-management-11e26.appspot.com/o/Inhouse_Treatment%2Fhome_health_4.PNG?alt=media&token=03418c91-7b41-470a-b401-eaa6232acf7e")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView4);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/patient-management-11e26.appspot.com/o/Inhouse_Treatment%2Fhome_health_5.PNG?alt=media&token=cbcfcf43-f3ae-4fe4-bbcb-1d47f732c9f2")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView5);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/patient-management-11e26.appspot.com/o/Inhouse_Treatment%2Fhome_health_6.PNG?alt=media&token=0e5f8259-79b0-42a3-b852-664dbc2ce085")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView6);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/patient-management-11e26.appspot.com/o/Inhouse_Treatment%2Fhome_health_7.PNG?alt=media&token=d416bed5-3666-499a-93e1-34296b4055d8")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView7);


        website_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.tribecacare.com/"));
                startActivity(i);
            }
        });


        return v;
    }


}

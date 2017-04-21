package com.example.samin.paitientmanagement.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.samin.paitientmanagement.R;

/**
 * Created by Samin on 19-04-2017.
 */

public class Post_Health_Tips extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post_health_tips);

        toolbar = (Toolbar) findViewById(R.id.show_appointment_appBarLayout);
        toolbar.setTitle("Health Tips");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

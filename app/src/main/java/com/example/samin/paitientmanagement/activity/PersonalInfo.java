package com.example.samin.paitientmanagement.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PersonalInfo extends AppCompatActivity
{
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    ImageView imageView;
    TextView  tx_email,tx_phone,personal_info_text,tv_make_appo,tx_specialization,tx_chamber,tx_experience,tx_fees,tx_timing;
    String intent_name,intent_phone,intent_email,tx_image;
    Button appointment_button;
    private EditText your_name,your_phone,your_appoint_date,your_appoint_reason;
    private FirebaseAuth firebaseAuth;
    public String UserID;
    SimpleDateFormat current_date_format, current_time_format;
    CoordinatorLayout coordinatorLayout;
    Calendar myCal = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dpl = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCal.set(Calendar.YEAR, year);
            myCal.set(Calendar.MONTH, monthOfYear);
            myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            your_appoint_date.setText("" + dayOfMonth + " / " + (monthOfYear + 1) + " / " + year );
            your_appoint_reason.requestFocus();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        current_date_format = new SimpleDateFormat( "dd/MM/yyyy" );
        current_time_format = new SimpleDateFormat("h:mm a");

        your_name=(EditText)findViewById(R.id.appoint_Your_name);
        your_phone = (EditText)findViewById(R.id.appoint_Phone);
        your_appoint_date = (EditText)findViewById(R.id.appoint_Visit_Date);
        your_appoint_reason = (EditText)findViewById(R.id.appoint_Visit_reason);
        your_appoint_date.setText(current_date_format.format( new Date()));


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_personal_info);

        appointment_button= (Button) findViewById(R.id.makeappointment_button);
        personal_info_text= (TextView) findViewById(R.id.personal_info_txt);
        tv_make_appo= (TextView) findViewById(R.id.tv_make_appo);

        imageView = (ImageView) findViewById(R.id.image_id);

        //imageView.setImageResource(getIntent().getIntExtra("image_id",00));

        tx_email =(TextView)findViewById(R.id.d_doctor_email2);
        tx_phone=(TextView)findViewById(R.id.d_doctor_phone2);
        tx_chamber=(TextView)findViewById(R.id.d_doctor_chamber2);
        tx_experience=(TextView)findViewById(R.id.d_doctor_experience2);
        tx_specialization=(TextView)findViewById(R.id.d_doctor_specialization2);
        tx_timing=(TextView)findViewById(R.id.d_doctor_timing2);
        tx_fees=(TextView)findViewById(R.id.d_doctor_fees2);


        your_appoint_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //Log.d("LOGGED", "onFocusChange: ");
                if(hasFocus) {
                    DatePickerDialog dpd = new DatePickerDialog(PersonalInfo.this,
                            dpl, myCal.get(Calendar.YEAR), myCal
                            .get(Calendar.MONTH), myCal
                            .get(Calendar.DAY_OF_MONTH));
                    dpd.show();
                }
            }
        });

        //tx_email.setText("Email : "+getIntent().getStringExtra("email"));
        tx_email.setText(getIntent().getStringExtra("email"));


        tx_phone.setText(getIntent().getStringExtra("phone"));
        tx_chamber.setText(getIntent().getStringExtra("chamber"));
        tx_experience.setText(getIntent().getStringExtra("experience").concat(" Years"));
        tx_specialization.setText(getIntent().getStringExtra("specialization"));
        tx_timing.setText(getIntent().getStringExtra("timing").replace(",","\n"));
        tx_fees.setText(getIntent().getStringExtra("fees"));


        //Fetch Intent data for appointment Field
        intent_name = getIntent().getStringExtra("name");
        intent_phone = getIntent().getStringExtra("phone");
        intent_email = getIntent().getStringExtra("email");
        tx_image = getIntent().getStringExtra("image_id");
       // imageView.setImageURI(Uri.parse(tx_image));
//        Picasso.with(getApplicationContext())
//                .load(tx_image)
//                .into(imageView);

        Glide.with(getApplicationContext())
                .load(tx_image)
                .thumbnail(0.1f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


        Typeface txt =Typeface.createFromAsset(getAssets(),"fonts/Roboto-LightItalic.ttf");
        Typeface txt2 =Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-BoldItalic.ttf");

        tx_email.setTypeface(txt);
        tx_phone.setTypeface(txt);
        tx_specialization.setTypeface(txt);
        tx_experience.setTypeface(txt);
        tx_chamber.setTypeface(txt);
        tx_timing.setTypeface(txt);
        tx_fees.setTypeface(txt);

        personal_info_text.setTypeface(txt2);
        tv_make_appo.setTypeface(txt2);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("name"));

        //dynamicToolbarColor();
        toolbarTextAppearance();

        }



    private void toolbarTextAppearance() {
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-LightItalic.ttf");
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(font);
        collapsingToolbarLayout.setExpandedTitleTypeface(font);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void make_appointment(View view) {
        String getName = your_name.getText().toString().trim();
        String getPhone = your_phone.getText().toString().trim();
        String getDate = your_appoint_date.getText().toString().trim();
        String getReason = your_appoint_reason.getText().toString().trim();

        if(TextUtils.isEmpty(getName)) {
            Toast.makeText(this, "Name Require", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getPhone)) {
            Toast.makeText(this, "Phone No Require", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getDate)) {
            Toast.makeText(this, "Date Require", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getReason)) {
            Toast.makeText(this, "Reason Require", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserID=user.getEmail().replace("@","").replace(".","");
        //Firebase mRoofRef = new Firebase("https://patient-management-11e26.firebaseio.com/");

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("User_Appointment").child(UserID).push();
        //Firebase userRef = mRoofRef.child("User_Appointment").child(UserID).push();
        mRootRef.child("Patient_Name").setValue(getName);
        mRootRef.child("Patient_Phone").setValue(getPhone);
        mRootRef.child("Appointment_Date").setValue(getDate);
        mRootRef.child("Appointment_Reason").setValue(getReason);
        mRootRef.child("Appointment_Doctor_Name").setValue(intent_name.trim());
        mRootRef.child("Appointment_Doctor_Email").setValue(intent_email.trim());
        mRootRef.child("Appointment_Doctor_phone").setValue(intent_phone.trim());
        mRootRef.child("Creation_Time").setValue(current_time_format.format( new Date()));
        mRootRef.child("Creation_Date").setValue(current_date_format.format( new Date()));
        mRootRef.child("Request_Status").setValue("Pending");
        mRootRef.child("Patient_Email").setValue(MainActivity.LoggedIn_User_Email);
        mRootRef.child("Approval_Tracking").setValue(intent_email.trim()+" + "+ current_time_format.format( new Date()) + " + " + current_date_format.format( new Date()));


        DatabaseReference mRootRef2 = FirebaseDatabase.getInstance().getReference().child("Doctor_Appointment").child(intent_email.trim().replace("@","").replace(".","")).push();
        //Firebase mRoofRef2 = new Firebase("https://patient-management-11e26.firebaseio.com/");
        //Firebase userRef2 = mRoofRef2.child("Doctor_Appointment").child(intent_email.trim().replace("@","").replace(".","")).push();
        mRootRef2.child("Patient_Name").setValue(getName);
        mRootRef2.child("Patient_Phone").setValue(getPhone);
        mRootRef2.child("Appointment_Date").setValue(getDate);
        mRootRef2.child("Appointment_Reason").setValue(getReason);
//        userRef2.child("Appointment_Doctor_Name").setValue(intent_name.trim());
        mRootRef2.child("Appointment_Doctor_Email").setValue(intent_email.trim());
//        userRef2.child("Appointment_Doctor_phone").setValue(intent_email.trim());
        mRootRef2.child("Creation_Time").setValue(current_time_format.format( new Date()));
        mRootRef2.child("Creation_Date").setValue(current_date_format.format( new Date()));
        mRootRef2.child("Request_Status").setValue("Pending");
        mRootRef2.child("Patient_Email").setValue(MainActivity.LoggedIn_User_Email);
        mRootRef2.child("Approval_Tracking").setValue(intent_email.trim()+" + "+ current_time_format.format( new Date()) + " + " + current_date_format.format( new Date()));



        Toast.makeText(this, "Appointment Registered", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Email was: " + MainActivity.LoggedIn_User_Email, Toast.LENGTH_SHORT).show();

        your_name.setText("");
        your_phone.setText("");
        your_appoint_date.setText( current_date_format.format( new Date()));
        your_appoint_reason.setText("");
        //view.scrollBy(10,10);


    }
}
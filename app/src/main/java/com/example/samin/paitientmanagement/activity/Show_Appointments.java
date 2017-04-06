package com.example.samin.paitientmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.fragment.PathologyLabsFragment;
import com.example.samin.paitientmanagement.other.Show_appointment_data_item;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;

public class Show_Appointments extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    public String UserID;
    DatabaseReference myRef,myRef2;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder> mFirebaseAdapter;
    //Boolean flag=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_appointment_layout);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.show_appointment_appBarLayout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Your Appointments");
        }
       // Log.d("LOGGED", "----------------------- START NEW --------------------------");

        //Send a Query to database
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //Log.d("LOGGED ", " USER " + user.toString());

//        if(MainActivity.app_user_type=="Patient")
//        {}
//        if(MainActivity.LoggedIn_User_Email=="")
//        {}


        UserID = user.getEmail().replace("@", "").replace(".", "");
        myRef = FirebaseDatabase.getInstance().getReference("User_Appointment").child(UserID);
        myRef2 = FirebaseDatabase.getInstance().getReference("Doctor_Appointment").child(MainActivity.LoggedIn_User_Email.replace("@", "").replace(".", ""));

        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.show_appointment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Log.d("LOGGED", "ADAPTER 1st " + adapter);
        Toast.makeText(this, "Wait ! Fetching data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Log.d("LOGGED", "IN onStart ");

        if (MainActivity.app_user_type.equals("Patient"))
        {
            mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder>(Show_appointment_data_item.class, R.layout.show_appointment_single_item, MyViewHolder.class, myRef) {
                //            @Override
//            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                Log.d("LOGGED", " onStart-Called 4m inside " );
//
//                ViewGroup view = (ViewGroup)
//                        LayoutInflater.from(parent.getContext())
//                                .inflate(R.layout.show_appointment_single_item, parent, false);
//                return super.onCreateViewHolder(view, viewType);
//            }
                public void populateViewHolder(final MyViewHolder viewHolder, Show_appointment_data_item model, final int position) {
                    //flag = true;


                    //if(MainActivity.app_user_type.equals("Patient")) {
                    viewHolder.setDoctor_URL(model.getAppointment_Doctor_Email());
                    viewHolder.setAppointment_Doctor_Name(model.getAppointment_Doctor_Name());
                    viewHolder.setAppointment_Doctor_Email(model.getAppointment_Doctor_Email());
                    viewHolder.setAppointment_Doctor_Phone(model.getAppointment_Doctor_phone());
                    viewHolder.setAppointment_Patient_Name(model.getPatient_Name());
                    viewHolder.setAppointment_Patient_Phone(model.getPatient_Phone());
                    viewHolder.setAppointment_Date(model.getAppointment_Date());
                    viewHolder.setAppointment_Reason(model.getAppointment_Reason());

                    //}

//                else if (MainActivity.app_user_type.equals("Doctor"))
//                {
//                    String my_Appointment = model.getAppointment_Doctor_Email();
//                    if(my_Appointment.equals(MainActivity.LoggedIn_User_Email))
//                    {
//
//                    }
//
//                }
                    //Log.d("LOGGED", "populateViewHolder: Called ");
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(final View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Show_Appointments.this);
                            builder.setMessage("Do you want to Delete the Appointment ?").setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int selectedItems = position;
                                            mFirebaseAdapter.getRef(selectedItems).removeValue();
                                            mFirebaseAdapter.notifyItemRemoved(selectedItems);
                                            recyclerView.invalidate();
                                            onStart();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.setTitle("Confirm");
                            dialog.show();
                        }
                    });


                }
            };
    }

        else if (MainActivity.app_user_type.equals("Doctor"))
        {
            mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder>(Show_appointment_data_item.class, R.layout.show_appointment_single_item, MyViewHolder.class, myRef2) {
                //            @Override
//            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                Log.d("LOGGED", " onStart-Called 4m inside " );
//
//                ViewGroup view = (ViewGroup)
//                        LayoutInflater.from(parent.getContext())
//                                .inflate(R.layout.show_appointment_single_item, parent, false);
//                return super.onCreateViewHolder(view, viewType);
//            }
                public void populateViewHolder(final MyViewHolder viewHolder, Show_appointment_data_item model, final int position) {
                    //flag = true;


                    //if(MainActivity.app_user_type.equals("Patient")) {
                    viewHolder.setDoctor_URL(model.getAppointment_Doctor_Email());
                    //viewHolder.setAppointment_Doctor_Name(model.getAppointment_Doctor_Name());
                    viewHolder.setAppointment_Doctor_Email(model.getAppointment_Doctor_Email());
                    //viewHolder.setAppointment_Doctor_Phone(model.getAppointment_Doctor_phone());
                    viewHolder.setAppointment_Patient_Name(model.getPatient_Name());
                    viewHolder.setAppointment_Patient_Phone(model.getPatient_Phone());
                    viewHolder.setAppointment_Date(model.getAppointment_Date());
                    viewHolder.setAppointment_Reason(model.getAppointment_Reason());

                    //}

//                else if (MainActivity.app_user_type.equals("Doctor"))
//                {
//                    String my_Appointment = model.getAppointment_Doctor_Email();
//                    if(my_Appointment.equals(MainActivity.LoggedIn_User_Email))
//                    {
//
//                    }
//
//                }
                    //Log.d("LOGGED", "populateViewHolder: Called ");
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(final View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Show_Appointments.this);
                            builder.setMessage("Do you want to Delete the Appointment ?").setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int selectedItems = position;
                                            mFirebaseAdapter.getRef(selectedItems).removeValue();
                                            mFirebaseAdapter.notifyItemRemoved(selectedItems);
                                            recyclerView.invalidate();
                                            onStart();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.setTitle("Confirm");
                            dialog.show();
                        }
                    });


                }
            };
        }

        //Log.d("LOGGED", "Setting Adapter ");
        recyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_appointment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            case R.id.action_refresh:
                recyclerView.invalidate();
                onStart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //View Holder For Recycler View
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView post_doctor_name, post_doctor_email, post_doctor_phone, post_patient_name, post_patient_phone, post_appointment_date, post_appointment_reason;
        private final ImageView post_doctor_image;
        View mView;
        Firebase mRoofRef;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            post_doctor_name = (TextView) mView.findViewById(R.id.fetch_doctor_name);
            post_doctor_email = (TextView) mView.findViewById(R.id.fetch_doctor_email);
            post_doctor_phone = (TextView) mView.findViewById(R.id.fetch_doctor_phone);
            post_patient_name = (TextView) mView.findViewById(R.id.fetch_patient_name);
            post_patient_phone = (TextView) mView.findViewById(R.id.fetch_patient_phone);
            post_appointment_date = (TextView) mView.findViewById(R.id.fetch_Appointment_date);
            post_appointment_reason = (TextView) mView.findViewById(R.id.fetch_Appointment_reason);
            post_doctor_image = (ImageView) mView.findViewById(R.id.show_appointment_doctor_image);
        }

        private void setAppointment_Doctor_Name(String title) {
            post_doctor_name.setText(title);
            Log.d("LOGGED", "Doctor_Name: " + title);
        }

        private void setAppointment_Doctor_Phone(String title) {
            post_doctor_phone.setText(title);

        }

        private void setAppointment_Doctor_Email(String title) {
            post_doctor_email.setText(title);
            Log.d("LOGGED", "Doctor_Email: " + title);
        }

        private void setAppointment_Patient_Name(String title) {
            post_patient_name.setText(title);
        }

        private void setAppointment_Patient_Phone(String title) {
            post_patient_phone.setText(title);
        }

        private void setAppointment_Date(String title) {
            post_appointment_date.setText(title);
        }

        private void setAppointment_Reason(String title) {
            post_appointment_reason.setText(title);
        }

        private void setDoctor_URL(String url) {

            if (url != null) {
                String UserID = url.replace("@", "").replace(".", "");
                mRoofRef = new Firebase("https://patient-management-11e26.firebaseio.com/Doctor_Detais").child(UserID);
                mRoofRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, String> map = dataSnapshot.getValue(Map.class);
                        String retrieve_url = map.get("Image_URL");
//                        Picasso.with(itemView.getContext())
//                                .load(retrieve_url)
//                                .placeholder(R.drawable.loading)
//                                .into(post_doctor_image);

                        Glide.with(itemView.getContext()).load(retrieve_url)
                                .crossFade()
                                .placeholder(R.drawable.loading)
                                .thumbnail(0.1f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(post_doctor_image);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        //Nothing
                    }
                });
            } else {
                //Log.d("LOGGED", "I GOT THE URL " + url);
            }
        }
    }
}

package com.example.samin.paitientmanagement.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.activity.MainActivity;
import com.example.samin.paitientmanagement.activity.PatientProfile;
import com.example.samin.paitientmanagement.activity.Show_Appointments;
import com.example.samin.paitientmanagement.other.CircleTransform;
import com.example.samin.paitientmanagement.other.Show_appointment_data_item;
import com.example.samin.paitientmanagement.other.Show_notification_data_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Show_notification_data_item, NotificationFragment.MyViewHolder> mFirebaseAdapter;
    ProgressBar progressBar;
    DatabaseReference mRootRef;
    ImageView noDataAvailableImage;

    public NotificationFragment() {
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

        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        noDataAvailableImage = (ImageView) v.findViewById(R.id.no_data_available_image);

        String UserID = MainActivity.LoggedIn_User_Email.replace("@", "").replace(".", "");
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar3);


        mRootRef = FirebaseDatabase.getInstance().getReference("User_Notification").child(UserID);
        Log.d("LOGGED", "mRootRef " + mRootRef);
        //Recycler View
        recyclerView = (RecyclerView) v.findViewById(R.id.show_notification_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Log.d("LOGGED", "ADAPTER 1st " + adapter);
        Toast.makeText(getContext(), "Wait ! Fetching data...", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(ProgressBar.VISIBLE);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Log.d("LOGGED", "IN onStart ");

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_notification_data_item, MyViewHolder>(Show_notification_data_item.class, R.layout.fragment_notification_single_item, NotificationFragment.MyViewHolder.class, mRootRef) {
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, Show_notification_data_item model, int position) {
                //progressBar.setVisibility(ProgressBar.INVISIBLE);
                Log.d("LOGGED", "populateViewHolder Called ");
                viewHolder.set_notification_image(model.getNotification_Image());
                viewHolder.set_notification_date(model.getNotification_Date());
                viewHolder.set_notification_time(model.getNotification_Time());
                viewHolder.set_notification_text(model.getNotification_Text());
            }

        };
        //Log.d("LOGGED", "Setting Adapter ");
        recyclerView.setAdapter(mFirebaseAdapter);


        mRootRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                //Log.d("LOGGED", "I Called Yahoo ");
                if (dataSnapshot.hasChildren()) {
                    //Log.d("LOGGED", "Boolean value true: ");
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    noDataAvailableImage.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Data Unavailable", Toast.LENGTH_SHORT).show();
                    //Log.d("LOGGED", "Boolean value false: ");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        recyclerView.setAdapter(mFirebaseAdapter);
//
//        mRootRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
//            @Override
//            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
//                //Log.d("LOGGED", "I Called Yahoo ");
//                if(dataSnapshot.hasChildren())
//                {
//                    //Log.d("LOGGED", "Boolean value true: ");
//                    progressBar.setVisibility(ProgressBar.INVISIBLE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                }
//                else {
//                    progressBar.setVisibility(ProgressBar.INVISIBLE);
//                    recyclerView.setVisibility(View.INVISIBLE);
//                    noDataAvailableImage.setVisibility(View.VISIBLE);
//                    Toast.makeText(getContext(), "Data Unavailable", Toast.LENGTH_SHORT).show();
//                    //Log.d("LOGGED", "Boolean value false: ");
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.show_appointment_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
//                // if this doesn't work as desired, another possibility is to call `finish()` here.
//                this.onBackPressed();
//                return true;
//            case R.id.action_refresh:
//                recyclerView.invalidate();
//                onStart();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    //View Holder For Recycler View
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView post_date,post_time,post_text;
        private final ImageView post_notification_image;
        View mView;
        //Firebase mRoofRef;
        RecyclerView.ViewHolder vv;

        public MyViewHolder(final View itemView) {

            super(itemView);
            Log.d("LOGGED", "ViewHolder Called: ");
            mView = itemView;
            post_date = (TextView) mView.findViewById(R.id.notification_date);
            post_time = (TextView) mView.findViewById(R.id.notification_time);
            post_text = (TextView) mView.findViewById(R.id.notification_text);
            post_notification_image = (ImageView) mView.findViewById(R.id.notification_image);

        }

        private void set_notification_date(String title) {
            post_date.setText(title);
            //Log.d("LOGGED", "Doctor_Name: " + title);
        }

        private void set_notification_time(String title) {
            post_time.setText(title);
            //Log.d("LOGGED", "Doctor_Name: " + title);
        }

        private void set_notification_text(String title) {
            post_text.setText(title);
            //Log.d("LOGGED", "Doctor_Name: " + title);
        }


        private void set_notification_image(String title) {
            if(!title.equals("Null")) {
                Glide.with(itemView.getContext())
                        .load(title)
                        .thumbnail(0.5f)
                        .crossFade()
                        .placeholder(R.drawable.default_avatar_large)
                        .bitmapTransform(new CircleTransform(itemView.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(post_notification_image);
            }
            else
            {
                Glide.with(itemView.getContext())
                        .load(R.drawable.default_avatar_large)
                        .thumbnail(0.5f)
                        .crossFade()
                        .bitmapTransform(new CircleTransform(itemView.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(post_notification_image);
            }


            }
        }


    }


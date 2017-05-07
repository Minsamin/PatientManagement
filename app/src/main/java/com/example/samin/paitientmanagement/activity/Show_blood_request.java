package com.example.samin.paitientmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.other.Show_appointment_data_item;
import com.example.samin.paitientmanagement.other.Show_blood_request_data_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class Show_blood_request extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    public String UserID;
    DatabaseReference myRef;
    private RecyclerView recyclerView;


    private FirebaseRecyclerAdapter<Show_blood_request_data_item, Blood_request_DetailsViewHolder> mFirebaseAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_blood_requests_layout);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.show_blood_request_appBarLayout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color=#FFFFFF>" + "Requests" + "</font>"));
        }
       // Log.d("LOGGED", "----------------------- START NEW --------------------------");

        //Send a Query to database
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //Log.d("LOGGED ", " USER " + user.toString());
        UserID = user.getEmail().replace("@", "").replace(".", "");
        myRef = FirebaseDatabase.getInstance().getReference("Blood_Request");
        myRef.keepSynced(true);

        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.show_blood_request_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Log.d("LOGGED", "ADAPTER 1st " + adapter);
        Toast.makeText(this, "Wait ! Fetching data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
       // Log.d("LOGGED", "IN onStart ");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_blood_request_data_item, Blood_request_DetailsViewHolder>(Show_blood_request_data_item.class, R.layout.show_blood_request_single_item, Blood_request_DetailsViewHolder.class, myRef) {

            public void populateViewHolder(final Blood_request_DetailsViewHolder viewHolder, Show_blood_request_data_item model, final int position) {
                viewHolder.set_Blood_requester_name(model.getPerson_Name());
                viewHolder.set_Blood_requester_phone(model.getPerson_Phone());
                viewHolder.set_Blood_requester_address(model.getPerson_Address());
                viewHolder.set_Blood_requester_blood_group(model.getPerson_Request_blood_group());
                viewHolder.set_Blood_requester_email(model.getPerson_Email());
                //Log.d("LOGGED", "populateViewHolder: Called ");
//                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(final View v) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Show_blood_request.this);
//                        builder.setMessage("Do you want to Delete the Appointment ?").setCancelable(false)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        int selectedItems = position;
//                                        mFirebaseAdapter.getRef(selectedItems).removeValue();
//                                        mFirebaseAdapter.notifyItemRemoved(selectedItems);
//                                        recyclerView.invalidate();
//                                        onStart();
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        AlertDialog dialog = builder.create();
//                        dialog.setTitle("Confirm");
//                        dialog.show();
//                    }
//                });
            }
        };

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
    public static class Blood_request_DetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, email, phone, blood_group,address;
        View mView;

        public Blood_request_DetailsViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            name = (TextView) mView.findViewById(R.id.fetch_blood_requester_name);
            email = (TextView) mView.findViewById(R.id.fetch_blood_requester_email);
            phone = (TextView) mView.findViewById(R.id.fetch_blood_requester_phone);
            blood_group = (TextView) mView.findViewById(R.id.fetch_blood_group);
            address = (TextView) mView.findViewById(R.id.fetch_blood_requester_address);


        }

        private void set_Blood_requester_name(String title) {
            name.setText(title);
        }

        private void set_Blood_requester_email(String title) {
            email.setText(title);
        }

        private void set_Blood_requester_phone(String title) {
            phone.setText(title);
        }

        private void set_Blood_requester_blood_group(String title) {
            blood_group.setText(title);
        }

        private void set_Blood_requester_address(String title) {
            address.setText(title);
        }



    }
}

package com.example.samin.paitientmanagement.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.fragment.PathologyLabsFragment;
import com.example.samin.paitientmanagement.other.Show_pathology_data_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Samin on 03-05-2017.
 */

public class Blood_Banks_Page extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<Show_pathology_data_item, Blood_Banks_Page.BloodBankDetailsViewHolder> mFirebaseAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_banks_layout);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Blood_Banks");
        myRef.keepSynced(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.show_blood_banks_appBarLayout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color=#FFFFFF>" + "Blood Banks" + "</font>"));
        }

        recyclerView = (RecyclerView)findViewById(R.id.show_blood_banks_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Toast.makeText(getApplicationContext(), "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onStart() {
        super.onStart();
        //Log.d("LOGGED", "IN onStart ");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_pathology_data_item, Blood_Banks_Page.BloodBankDetailsViewHolder>(Show_pathology_data_item.class, R.layout.show_blood_banks_single_item, Blood_Banks_Page.BloodBankDetailsViewHolder.class, myRef) {




            public void populateViewHolder(final Blood_Banks_Page.BloodBankDetailsViewHolder viewHolder, Show_pathology_data_item model, final int position) {
                viewHolder.Blood_Bank_Name(model.getName());
                viewHolder.Blood_Bank_Phone(model.getPhone());
                viewHolder.Blood_Bank_Address(model.getAddress());
                viewHolder.Blood_Bank_Website(model.getWebsite());
                viewHolder.Blood_Bank_Direction(model.getDirection());


//                //OnClick Item
//                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
//
////                    @Override
////                    public void onClick(final View v) {
////
////                        DatabaseReference ref = mFirebaseAdapter.getRef(position);
////                        ref.addValueEventListener(new ValueEventListener() {
////                            @Override
////                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
////                               // Intent intent = new Intent(getContext(), PersonalInfo.class);
////
////                                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
////                                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );
////
////                                String retrieve_website = map.get("Website");
////                                String retrieve_direction = map.get("Direction");
////
////
////
//////                                intent.putExtra("image_id",retrieve_url);
//////                                intent.putExtra("email",retrieve_Email);
//////                                intent.putExtra("name",retrieve_name);
//////                                intent.putExtra("phone",retrieve_phone);
//////                                intent.putExtra("spec",retrieve_Qualification);
//////                                getContext().startActivity(intent);
////                            }
////
////                            @Override
////                            public void onCancelled(DatabaseError databaseError) {
////                            }
////                        });
////                    }
                //  });


            }
        };


        recyclerView.setAdapter(mFirebaseAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
//            case R.id.action_refresh:
//                recyclerView.invalidate();
//                onStart();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    //View Holder For Recycler View
    public static class BloodBankDetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView bloodbank_name, bloodbank_address,  bloodbank_phone;
        // private final TextView  pathology_website, pathology_direction,
        private final LinearLayout website,direction;
        View mView;

        public BloodBankDetailsViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            bloodbank_name = (TextView) mView.findViewById(R.id.fetch_blood_bank_name);
            bloodbank_address = (TextView) mView.findViewById(R.id.fetch_blood_bank_address);
//            pathology_website = (TextView) mView.findViewById(R.id.fetch_pathology_website);
//            pathology_direction = (TextView) mView.findViewById(R.id.fetch_pathology_direction);
            bloodbank_phone = (TextView) mView.findViewById(R.id.fetch_blood_bank_phone);
            website = (LinearLayout) mView.findViewById(R.id.fetch_blood_bank_website_linearlayout);
            direction = (LinearLayout) mView.findViewById(R.id.fetch_blood_bank_direction_linearlayout);

        }

        private void Blood_Bank_Name(String title) {
            bloodbank_name.setText(title);
        }

        private void Blood_Bank_Phone(String title) {
            bloodbank_phone.setText(title);
        }

        private void Blood_Bank_Address( String title) {
            bloodbank_address.setText(title);

        }

        private void Blood_Bank_Website(final String title) {
                website.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!title.equals("Null")) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(title));
                            mView.getContext().startActivity(i);
                            //Toast.makeText(mView.getContext(), "Website:  " + title, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(mView.getContext(), "Website Not Available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        }

        private void Blood_Bank_Direction(final String title) {
            direction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(title));
                    mView.getContext().startActivity(i);
                    // Toast.makeText(mView.getContext(), "Direction :  " + title, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}

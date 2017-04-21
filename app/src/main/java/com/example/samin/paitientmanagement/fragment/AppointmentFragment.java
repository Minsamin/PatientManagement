package com.example.samin.paitientmanagement.fragment;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.activity.PersonalInfo;
import com.example.samin.paitientmanagement.other.CircleTransform;
import com.example.samin.paitientmanagement.other.DoctorDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;

import static com.example.samin.paitientmanagement.activity.MainActivity.MainActivityLoaded;

public class AppointmentFragment extends Fragment {

    private Spinner spinner;
    TextView txt;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<DoctorDetails, DoctorDetailsViewHolder> mFirebaseAdapter;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;
    //Boolean flag = true;

    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Doctor_Detais");
        //progressBar = new ProgressBar(getContext());
//        if(flag)
//            Toast.makeText(getContext(), "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();
//        flag=false;
        Log.d("LOGGED", "onCreate Called : ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_appointment, container, false);
        //Log.d("Fragment","onCreateView Called");
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar2);

        //Recycler View
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        //mLinearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLinearLayoutManager);
        Log.d("LOGGED", "onCreateView Called : ");

        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        txt=(TextView)v.findViewById(R.id.tv_department);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        txt.setTypeface(font);



       spinner = (Spinner) v.findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            String firstItem = String.valueOf(spinner.getSelectedItem());
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (!firstItem.equals(spinner.getSelectedItem()))
                {
                    Toast.makeText(parent.getContext(),"Doctors in " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                    firstItem = String.valueOf(spinner.getSelectedItem());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(ProgressBar.VISIBLE);
        if(MainActivityLoaded.equals("1"))
        {
            Toast.makeText(getContext(), "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();
            MainActivityLoaded = "0";
        }
        Log.d("LOGGED", "Will Start Calling populateViewHolder : ");
        //Log.d("LOGGED", "IN onStart ");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<DoctorDetails, DoctorDetailsViewHolder>(DoctorDetails.class, R.layout.card_doctor_layout, DoctorDetailsViewHolder.class, myRef) {

            public void populateViewHolder(final DoctorDetailsViewHolder viewHolder, DoctorDetails model, final int position) {
                //Log.d("LOGGED", "populateViewHolder Called: ");
                progressBar.setVisibility(ProgressBar.INVISIBLE);

                if (!model.getName().equals("Null")) {
                    viewHolder.Doctor_Name(model.getName());
                    viewHolder.Doctor_Chamber(model.getChamber());
                    viewHolder.Doctor_Image_URL(model.getImage_Url());
                    viewHolder.Doctor_Specialization(model.getSpecialization());
            }


                //OnClick Item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                Intent intent = new Intent(getContext(), PersonalInfo.class);

                                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );

                                String retrieve_name = map.get("Name");
                                String retrieve_phone = map.get("Phone");
                                String retrieve_Email = map.get("Email");
                                String retrieve_url = map.get("Image_URL");
                                String retrieve_Specialization = map.get("Specialization");
                                String retrieve_Experience = map.get("Experience");
                                String retrieve_Chamber = map.get("Chamber");
                                String retrieve_Timing = map.get("Timing");
                                String retrieve_Fees = map.get("Fees");

                                intent.putExtra("image_id",retrieve_url);
                                intent.putExtra("email",retrieve_Email);
                                intent.putExtra("name",retrieve_name);
                                intent.putExtra("phone",retrieve_phone);
                                intent.putExtra("specialization",retrieve_Specialization);
                                intent.putExtra("experience",retrieve_Experience);
                                intent.putExtra("chamber",retrieve_Chamber);
                                intent.putExtra("timing",retrieve_Timing);
                                intent.putExtra("fees",retrieve_Fees);
                                getContext().startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                });
            }

        };




//        //Log.d("LOGGED", "setAdapter Called: ");
        recyclerView.setAdapter(mFirebaseAdapter);

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
    public static class DoctorDetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView doctor_name, doctor_chamber, doctor_specialization;
        private final ImageView doctor_image;

        public DoctorDetailsViewHolder(final View itemView) {
            super(itemView);
            Log.d("LOGGED", "View Holder Called: ");
            doctor_name = (TextView) itemView.findViewById(R.id.appointment_doctor_name);
            doctor_chamber = (TextView) itemView.findViewById(R.id.appointment_doctor_chamber);
            doctor_image = (ImageView) itemView.findViewById(R.id.appointment_doctor_image);
            doctor_specialization = (TextView) itemView.findViewById(R.id.appointment_doctor_spec);
        }


        private void Doctor_Name(String title) {
           // Log.d("LOGGED", "Setting Name: ");
            doctor_name.setText(title);
        }

        private void Doctor_Chamber(String title) {
            doctor_chamber.setText(title);
        }

        private void Doctor_Specialization(String title) {
            doctor_specialization.setText(title);
        }

        private void Doctor_Image_URL(String url) {

            if (!url.equals("Null")) {
//                Picasso.with(itemView.getContext())
//                        .load(url)
//                        .placeholder(R.drawable.loading)
//                        .into(doctor_image);
                Log.d("LOGGED", "Setting Image: " +url);

                Glide.with(itemView.getContext())
                        .load(url)
                        .crossFade()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.loading)
                        .bitmapTransform(new CircleTransform(itemView.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(doctor_image);
           }

            else {
                //Log.d("LOGGED", "I GOT THE URL " + url);
                Glide.with(itemView.getContext())
                        .load(R.drawable.invalid_person_image)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(itemView.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(doctor_image);
            }
        }
    }
}


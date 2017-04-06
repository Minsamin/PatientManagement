package com.example.samin.paitientmanagement.fragment;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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

public class AppointmentFragment extends Fragment {

    private Spinner spinner;
    TextView txt;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<DoctorDetails, DoctorDetailsViewHolder> mFirebaseAdapter;

    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Doctor_Detais");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_appointment, container, false);
        //Log.d("Fragment","onCreateView Called");

        //Recycler View
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txt=(TextView)v.findViewById(R.id.tv_department);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        txt.setTypeface(font);

        Toast.makeText(getContext(), "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();

       spinner = (Spinner) v.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            String firstItem = String.valueOf(spinner.getSelectedItem());
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (firstItem.equals(String.valueOf(spinner.getSelectedItem())))
                {
                    // ToDo when first item is selected
                } else
                {
                    Toast.makeText(parent.getContext(),
                            "Doctors in " + parent.getItemAtPosition(position).toString(),
                            Toast.LENGTH_SHORT).show();
                    // Todo when item is selected by the user
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
        //Log.d("LOGGED", "IN onStart ");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<DoctorDetails, DoctorDetailsViewHolder>(DoctorDetails.class, R.layout.card_doctor_layout, DoctorDetailsViewHolder.class, myRef) {

            public void populateViewHolder(final DoctorDetailsViewHolder viewHolder, DoctorDetails model, final int position) {
                viewHolder.Doctor_Name(model.getName());
                viewHolder.Doctor_Chamber(model.getChamber());
                viewHolder.Doctor_Image_URL(model.getImage_Url());
                viewHolder.Doctor_Specialization(model.getSpecialization());


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
            doctor_name = (TextView) itemView.findViewById(R.id.appointment_doctor_name);
            doctor_chamber = (TextView) itemView.findViewById(R.id.appointment_doctor_chamber);
            doctor_image = (ImageView) itemView.findViewById(R.id.appointment_doctor_image);
            doctor_specialization = (TextView) itemView.findViewById(R.id.appointment_doctor_spec);
        }

        private void Doctor_Name(String title) {
            doctor_name.setText(title);
        }

        private void Doctor_Chamber(String title) {
            doctor_chamber.setText(title);
        }

        private void Doctor_Specialization(String title) {
            doctor_specialization.setText(title);
        }

        private void Doctor_Image_URL(String url) {

            if (url != null) {
//                Picasso.with(itemView.getContext())
//                        .load(url)
//                        .placeholder(R.drawable.loading)
//                        .into(doctor_image);

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
                        .placeholder(R.drawable.loading)
                        .bitmapTransform(new CircleTransform(itemView.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(doctor_image);
            }
        }
    }
}


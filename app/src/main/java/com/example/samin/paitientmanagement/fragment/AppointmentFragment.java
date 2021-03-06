package com.example.samin.paitientmanagement.fragment;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.activity.ChatActivity;
import com.example.samin.paitientmanagement.activity.MainActivity;
import com.example.samin.paitientmanagement.activity.PaitientManagement;
import com.example.samin.paitientmanagement.activity.PersonalInfo;
import com.example.samin.paitientmanagement.other.CircleTransform;
import com.example.samin.paitientmanagement.other.DoctorDetails;
import com.example.samin.paitientmanagement.other.UserDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AppointmentFragment extends Fragment {

    private Spinner spinner;
    TextView txt;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    public FirebaseRecyclerAdapter<DoctorDetails, DoctorDetailsViewHolder> mFirebaseAdapter;
    public FirebaseRecyclerAdapter<UserDetails, DoctorDetailsViewHolder> mFirebaseAdapter_User;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;
    LinearLayout spinner_layout;
    static String Doctor_Category ="All";

    Intent intent;
    //Boolean flag = true;
    //static String test;

    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Log.d("LOGGED","onCreateView Called");

//        if(PaitientManagement.app_user_type.equals("Doctor"))
//        {
//            Log.d("LOGGED", "putting test = Doctor: ");
//            test = "Doctor";
//        }
//        else if (PaitientManagement.app_user_type.equals("Patient"))
//        {
//            Log.d("LOGGED", "putting test = Patient: ");
//            test = "Patient";
//        }
//
//        Log.d("LOGGED", "so,,, test = : " + test);
//
//        if(test.equals("Doctor"))
//        {
//            myRef = firebaseDatabase.getReference("User_Details");
//        }
//        else
//        {
//            myRef = firebaseDatabase.getReference("Doctor_Detais");
//        }



        if(MainActivity.app_user_type.equals("Doctor"))
        {
            myRef = firebaseDatabase.getReference("User_Details");
            Log.d("LOGGED", "I AM Doctor Called: " +myRef.toString());

            //test = "Doctor";

           // myRef.orderByChild("User_Type").startAt("Patient");
            Log.d("LOGGED","My_REF : " + myRef.orderByChild("User_Type").startAt("Patient").toString());
        }
        else
        {
            myRef = firebaseDatabase.getReference("Doctor_Detais");
           // Log.d("LOGGED","My_REF : " + myRef);
           // test = "Patients";
            Log.d("LOGGED", "I AM Patient Called: " +myRef.toString());
        }


        myRef.keepSynced(true);
        Toast.makeText(getContext(), "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();






        //Toast.makeText(getContext(), "Testing: " + (MainActivity.app_user_type), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "Testing Static: " + test, Toast.LENGTH_SHORT).show();
        Log.d("LOGGED", "Testing: " + (MainActivity.app_user_type));
        Log.d("LOGGED", "Testing + PaitientManagement: " + (PaitientManagement.app_user_type));
        //Log.d("LOGGED", "Testing Static: " + test);


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



        //Log.d("LOGGED", "onCreateView Called : ");

        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        txt=(TextView)v.findViewById(R.id.tv_department);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        txt.setTypeface(font);



         spinner = (Spinner) v.findViewById(R.id.spinner);
        spinner_layout =(LinearLayout) v.findViewById(R.id.appointmentPage_spinnerLayout);
        //spinner.setPrompt("Select your favorite Planet!");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            String firstItem = String.valueOf(spinner.getSelectedItem());
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (!firstItem.equals(spinner.getSelectedItem()))
                {
                    Toast.makeText(parent.getContext(),parent.getItemAtPosition(position).toString() + " Doctors", Toast.LENGTH_SHORT).show();
                    firstItem = String.valueOf(spinner.getSelectedItem());

                    myRef = firebaseDatabase.getReference("Doctor_Detais");
                    //myRef.orderByChild("Category").startAt(parent.getItemAtPosition(position).toString());

                    Doctor_Category = parent.getItemAtPosition(position).toString();

                   // Log.d("LOGGED", "onItemSelected Called: " +myRef.orderByChild("Category").startAt(parent.getItemAtPosition(position).toString()));
                    //Log.d("LOGGED", "onItemSelected REFERENCE: " +myRef.toString());
                    recyclerView.invalidate();
                    onStart();
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
//        if(MainActivityLoaded.equals("1"))
//        {
//            //Toast.makeText(getContext(), "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();
//            MainActivityLoaded = "0";
//        }
        //Log.d("LOGGED", "Will Start Calling populateViewHolder : ");
        //Log.d("LOGGED", "IN onStart ");

 if(MainActivity.app_user_type.equals("Patient") || PaitientManagement.app_user_type.equals("Patient"))
 //if(test.equals("Patient"))
 {
     //Log.d("LOGGED","app_user_type : " + MainActivity.app_user_type);

     mFirebaseAdapter = new FirebaseRecyclerAdapter<DoctorDetails, DoctorDetailsViewHolder>(DoctorDetails.class, R.layout.card_doctor_layout, DoctorDetailsViewHolder.class, myRef) {

         public void populateViewHolder(final DoctorDetailsViewHolder viewHolder, DoctorDetails model, final int position) {
             //Log.d("LOGGED", "populateViewHolder Called: ");
             //Log.d("LOGGED", "populateViewHolder Called: " +myRef.toString());

             progressBar.setVisibility(ProgressBar.INVISIBLE);

             if (!model.getName().equals("Null") && (model.getCategory().equals(Doctor_Category)) || Doctor_Category.equals("All") ) {
                 Log.d("LOGGED", "Doctors in : " +Doctor_Category);

                 if(model.getName().equals("Null"))
                 {
                     viewHolder.Layout_hide();
                     return;
                 }
                 else
                 {
                     viewHolder.Doctor_Name(model.getName());
                 }
                 viewHolder.Doctor_Chamber(model.getChamber());
                 viewHolder.Doctor_Image_URL(model.getImage_Url());
                 viewHolder.Doctor_Specialization(model.getSpecialization());

             }
             else
             {
                 viewHolder.Layout_hide();
             }


             //OnClick Item
             viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                 @Override
                 public void onClick(final View v) {

                     DatabaseReference ref = mFirebaseAdapter.getRef(position);
                     ref.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                             //GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                             //Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );

                             String retrieve_name = dataSnapshot.child("Name").getValue(String.class);
                             String retrieve_phone = dataSnapshot.child("Phone").getValue(String.class);
                             String retrieve_Email = dataSnapshot.child("Email").getValue(String.class);
                             String retrieve_url = dataSnapshot.child("Image_URL").getValue(String.class);
                             String retrieve_Specialization = dataSnapshot.child("Specialization").getValue(String.class);
                             String retrieve_Experience = dataSnapshot.child("Experience").getValue(String.class);
                             String retrieve_Chamber = dataSnapshot.child("Chamber").getValue(String.class);
                             String retrieve_Timing = dataSnapshot.child("Timing").getValue(String.class);
                             String retrieve_Fees = dataSnapshot.child("Fees").getValue(String.class);


                             if(MainActivity.Fragment_Title.equals("Doctors"))
                             {
                                 // Toast.makeText(getContext(), "Doctor Appointment Fragment", Toast.LENGTH_SHORT).show();
                                 intent = new Intent(getContext(), PersonalInfo.class);
                                 intent.putExtra("image_id",retrieve_url);
                                 intent.putExtra("email",retrieve_Email);
                                 intent.putExtra("name",retrieve_name);
                                 intent.putExtra("phone",retrieve_phone);
                                 intent.putExtra("specialization",retrieve_Specialization);
                                 intent.putExtra("experience",retrieve_Experience);
                                 intent.putExtra("chamber",retrieve_Chamber);
                                 intent.putExtra("timing",retrieve_Timing);
                                 intent.putExtra("fees",retrieve_Fees);
                             }
                             else if(MainActivity.Fragment_Title.equals("Chat"))
                             {
                                 intent = new Intent(getContext(), ChatActivity.class);
                                 intent.putExtra("image_id",retrieve_url);
                                 intent.putExtra("email",retrieve_Email);
                                 intent.putExtra("name",retrieve_name);


                             }



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

 else if(MainActivity.app_user_type.equals("Doctor") || PaitientManagement.app_user_type.equals("Doctor"))
 //else if(test.equals("Doctor"))
 {
     //spinner.setVisibility(View.GONE);
     spinner_layout.setVisibility(View.GONE);
     //Log.d(" LOGGED ","app_user_type : " + MainActivity.app_user_type);
     mFirebaseAdapter_User = new FirebaseRecyclerAdapter<UserDetails, DoctorDetailsViewHolder>(UserDetails.class, R.layout.card_doctor_layout, DoctorDetailsViewHolder.class,
             myRef.orderByChild("Name_Present").startAt("YES").endAt("YES")) {

         public void populateViewHolder(final DoctorDetailsViewHolder viewHolder, UserDetails model, final int position) {
             //Log.d("LOGGED", "populateViewHolder Called: ");
             progressBar.setVisibility(ProgressBar.INVISIBLE);
             //Log.d("LOGGED","populateViewHolder : " + MainActivity.app_user_type);

             if (!model.getName().equals("Null")) {
                 viewHolder.Doctor_Name(model.getName());
                 Log.d("LOGGED", "Patient Name : " +model.getName());
                 viewHolder.Doctor_Chamber(model.getAddress());
                 viewHolder.Doctor_Image_URL(model.getImage_URL());
                 viewHolder.Doctor_Specialization(model.getPhone());
             }
             else
             {
                 //Log.d("LOGGED","mFirebaseAdapter_User.getItem(position) : " + viewHolder.getAdapterPosition());
                 //Log.d("LOGGED","getItemViewType : " + viewHolder.getItemViewType());

             }


             //OnClick Item
             viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                 @Override
                 public void onClick(final View v) {

                     DatabaseReference ref = mFirebaseAdapter_User.getRef(position);
                     ref.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                             //GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                             //Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );

                             String retrieve_name = dataSnapshot.child("Name").getValue(String.class);
                             String retrieve_phone = dataSnapshot.child("Phone").getValue(String.class);
                             String retrieve_Email = dataSnapshot.child("Email").getValue(String.class);
                             String retrieve_url = dataSnapshot.child("Image_URL").getValue(String.class);

                                 intent = new Intent(getContext(), ChatActivity.class);
                                 intent.putExtra("image_id",retrieve_url);
                                 intent.putExtra("email",retrieve_Email);
                                 intent.putExtra("name",retrieve_name);


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
     //Log.d("LOGGED","setAdapter : " + MainActivity.app_user_type);
     recyclerView.setAdapter(mFirebaseAdapter_User);
 }
    }

    //View Holder For Recycler View
    public static class DoctorDetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView doctor_name, doctor_chamber, doctor_specialization;
        private final ImageView doctor_image;
        private final LinearLayout layout;
        final LinearLayout.LayoutParams params;

        public DoctorDetailsViewHolder(final View itemView) {
            super(itemView);
           // Log.d("LOGGED", "View Holder Called: ");
            doctor_name = (TextView) itemView.findViewById(R.id.appointment_doctor_name);
            doctor_chamber = (TextView) itemView.findViewById(R.id.appointment_doctor_chamber);
            doctor_image = (ImageView) itemView.findViewById(R.id.appointment_doctor_image);
            doctor_specialization = (TextView) itemView.findViewById(R.id.appointment_doctor_spec);
            layout = (LinearLayout)itemView.findViewById(R.id.show_appointment_single_item);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); // This will need to Customize
        }

        private void Doctor_Name(String title) {
           // Log.d("LOGGED", "Setting Name: ");
            doctor_name.setText(title);
        }

        private void Layout_hide() {
            params.height = 0; // we set the Height to 0 and set this parameter to a particular layout...
            //itemView.setLayoutParams(params);
            layout.setLayoutParams(params);

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
               // Log.d("LOGGED", "Setting Image: " +url);

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


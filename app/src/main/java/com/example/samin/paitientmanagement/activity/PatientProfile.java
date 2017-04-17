package com.example.samin.paitientmanagement.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.other.CircleTransform;
import com.example.samin.paitientmanagement.other.Show_appointment_data_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PatientProfile extends AppCompatActivity {

    TextView tv_name,tv_email,tv_phone,tv_age,tv_height,tv_address,tv_bloodgroup,tv_weight;
    Button chat;
    ImageView patient_image;
    public String UserID;
    DatabaseReference myRef;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    //private LayoutInflater mLayoutInflater;
   // private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_profile);
        tv_name = (TextView)findViewById(R.id.patient_profile_name);
        tv_email = (TextView)findViewById(R.id.patient_profile_email);
        tv_phone = (TextView)findViewById(R.id.patient_profile_phone);
        tv_address = (TextView)findViewById(R.id.patient_profile_address);
        tv_age = (TextView)findViewById(R.id.patient_profile_age);
        tv_height = (TextView)findViewById(R.id.patient_profile_height);
        tv_weight = (TextView)findViewById(R.id.patient_profile_weight);
        tv_bloodgroup = (TextView)findViewById(R.id.patient_profile_blood_group);
        chat = (Button) findViewById(R.id.patient_profile_chat);
        patient_image = (ImageView) findViewById(R.id.patient_profile_image);
        recyclerView = (RecyclerView)findViewById(R.id.patient_profile_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);


        UserID = getIntent().getStringExtra("Email").replace("@", "").replace(".", "");
        myRef = FirebaseDatabase.getInstance().getReference("User_Appointment").child(UserID);


        Log.d("LOGGED ", " myREF " + myRef.toString());


        tv_name.setText(getIntent().getStringExtra("Name"));
        tv_address.setText(getIntent().getStringExtra("Address"));
        tv_phone.setText("Ph: ".concat(getIntent().getStringExtra("Phone")));
        tv_age.setText(getIntent().getStringExtra("Age").concat(" Years"));
        tv_height.setText(getIntent().getStringExtra("Height").concat(" cm"));
        tv_weight.setText(getIntent().getStringExtra("Weight").concat(" Kg"));
        tv_bloodgroup.setText(getIntent().getStringExtra("Bloodgroup"));
        tv_email.setText(getIntent().getStringExtra("Email"));
        Log.d("LOGGED", "Image value :  " + getIntent().getStringExtra("Image"));
        String check_image_validity = getIntent().getStringExtra("Image");

        if(check_image_validity == null)
        {
            Glide.with(PatientProfile.this)
                    .load(R.drawable.invalid_person_image)
                    .crossFade()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.loading)
                    .bitmapTransform(new CircleTransform(PatientProfile.this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(patient_image);
        }
        else
        {
            Glide.with(PatientProfile.this)
                    .load(getIntent().getStringExtra("Image"))
                    .crossFade()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.loading)
                    .bitmapTransform(new CircleTransform(PatientProfile.this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(patient_image);
        }


            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PatientProfile.this, "Not Implemented Yet !!", Toast.LENGTH_SHORT).show();
                }
            });

        progressBar.setVisibility(ProgressBar.VISIBLE);
        Log.d("LOGGED ", " On Create Complete  ");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LOGGED ", " Before mFirebaseAdapter  ");

        FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder> mFirebaseAdapter =
                new FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder>(Show_appointment_data_item.class, R.layout.timeline_view, MyViewHolder.class, myRef)
                {
                    public void populateViewHolder(final MyViewHolder viewHolder, Show_appointment_data_item model, final int position) {
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Log.d("LOGGED ", " I am in populateViewHolder  ");
                        //flag = true;
                        //                viewHolder.setDoctor_URL(model.getAppointment_Doctor_Email());
                        viewHolder.setAppointment_Doctor_Name(model.getAppointment_Doctor_Name());
                        //                viewHolder.setAppointment_Doctor_Email(model.getAppointment_Doctor_Email());
                viewHolder.setAppointment_Doctor_Phone(model.getAppointment_Doctor_phone());
//                viewHolder.setAppointment_Patient_Name(model.getPatient_Name());
//                viewHolder.setAppointment_Patient_Phone(model.getPatient_Phone());
                viewHolder.setAppointment_Date(model.getAppointment_Date());
                viewHolder.setAppointment_Reason(model.getAppointment_Reason());

                //Log.d("LOGGED", "populateViewHolder: Called ");
//                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(final View v) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(PatientProfile.this);
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

        Log.d("LOGGED ", " Setting Adapter  ");
        recyclerView.setAdapter(mFirebaseAdapter);
    }



    private static class MyViewHolder extends RecyclerView.ViewHolder {
        //private final TextView post_doctor_name, post_doctor_email, post_doctor_phone, post_patient_name, post_patient_phone, post_appointment_date, post_appointment_reason;
        //private final ImageView post_doctor_image;
        View mView;
        //Firebase mRoofRef;
        private final TextView post_doctor_name,post_appointment_date,post_doctor_phone,post_appointment_reason;

//        public MyViewHolder(View itemView, int viewType) {
//            super(itemView);
//
//           // ButterKnife.bind(this, itemView);
//            mTimelineView.initLine(viewType);
//        }


        public MyViewHolder(final View itemView) {
            super(itemView);
            Log.d("LOGGED ", " I am in MyViewHolder  ");
            mView = itemView;
            post_doctor_name = (TextView)mView.findViewById(R.id.text_timeline_doctor_name);
            post_appointment_date = (TextView)mView.findViewById(R.id.text_timeline_date);
            post_doctor_phone = (TextView)mView.findViewById(R.id.text_timeline_doctor_phone);
            post_appointment_reason = (TextView)mView.findViewById(R.id.text_timeline_doctor_visit_reason);

        }

        private void setAppointment_Doctor_Name(String title) {
            //Log.d("LOGGED ", " Setting setAppointment_Doctor_Name  ");
            post_doctor_name.setText(title);
            //Log.d("LOGGED", "Doctor_Name: " + title);
        }

        private void setAppointment_Doctor_Phone(String title) {
            post_doctor_phone.setText("( Ph: "+ title + " )");

        }
//
//        private void setAppointment_Doctor_Email(String title) {
//            post_doctor_email.setText(title);
//            //Log.d("LOGGED", "Doctor_Email: " + title);
//        }
//
//        private void setAppointment_Patient_Name(String title) {
//            post_patient_name.setText(title);
//        }
//
//        private void setAppointment_Patient_Phone(String title) {
//            post_patient_phone.setText(title);
//        }
//
//
        private void setAppointment_Date(String title) {
            post_appointment_date.setText(title);
        }
//
        private void setAppointment_Reason(String title) {
            post_appointment_reason.setText(title);
        }
//
//        private void setDoctor_URL(String url) {
//
//            if (url != null) {
//                String UserID = url.replace("@", "").replace(".", "");
//                mRoofRef = new Firebase("https://patient-management-11e26.firebaseio.com/Doctor_Detais").child(UserID);
//                mRoofRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Map<String, String> map = dataSnapshot.getValue(Map.class);
//                        String retrieve_url = map.get("Image_URL");
////                        Picasso.with(itemView.getContext())
////                                .load(retrieve_url)
////                                .placeholder(R.drawable.loading)
////                                .into(post_doctor_image);
//
//                        Glide.with(itemView.getContext()).load(retrieve_url)
//                                .crossFade()
//                                .placeholder(R.drawable.loading)
//                                .thumbnail(0.1f)
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(post_doctor_image);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//                        //Nothing
//                    }
//                });
//            } else {
//                //Log.d("LOGGED", "I GOT THE URL " + url);
//            }
//        }
    }
}

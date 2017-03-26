package com.example.samin.paitientmanagement.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.activity.PersonalInfo;
import com.example.samin.paitientmanagement.activity.Show_Appointments;
import com.example.samin.paitientmanagement.other.CircleTransform;
import com.example.samin.paitientmanagement.other.DoctorDetails;
import com.example.samin.paitientmanagement.other.Show_appointment_data_item;
import com.example.samin.paitientmanagement.other.Show_pathology_data_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class PathologyLabsFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<Show_pathology_data_item, PathologyDetailsViewHolder> mFirebaseAdapter;

    public PathologyLabsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Pathology_Labs");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pathology_layout, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.show_pathology_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Toast.makeText(getContext(), "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();

        return v;
    }




    @Override
    public void onStart() {
        super.onStart();
        //Log.d("LOGGED", "IN onStart ");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_pathology_data_item, PathologyDetailsViewHolder>(Show_pathology_data_item.class, R.layout.show_pathology_single_item, PathologyDetailsViewHolder.class, myRef) {




            public void populateViewHolder(final PathologyDetailsViewHolder viewHolder, Show_pathology_data_item model, final int position) {
                viewHolder.Pathology_Name(model.getName());
                viewHolder.Pathology_Phone(model.getPhone());
                viewHolder.Pathology_Address(model.getAddress());
                viewHolder.Pathology_Website(model.getWebsite());
                viewHolder.Pathology_Direction(model.getDirection());


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



    //View Holder For Recycler View
    public static class PathologyDetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView pathology_name, pathology_address,  pathology_phone;
       // private final TextView  pathology_website, pathology_direction,
        private final LinearLayout website,direction;
        View mView;

        public PathologyDetailsViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            pathology_name = (TextView) mView.findViewById(R.id.fetch_pathology_name);
            pathology_address = (TextView) mView.findViewById(R.id.fetch_pathology_address);
//            pathology_website = (TextView) mView.findViewById(R.id.fetch_pathology_website);
//            pathology_direction = (TextView) mView.findViewById(R.id.fetch_pathology_direction);
            pathology_phone = (TextView) mView.findViewById(R.id.fetch_pathology_phone);
            website = (LinearLayout) mView.findViewById(R.id.fetch_pathology_website_linearlayout);
            direction = (LinearLayout) mView.findViewById(R.id.fetch_pathology_direction_linearlayout);

        }

        private void Pathology_Name(String title) {
            pathology_name.setText(title);
        }

        private void Pathology_Phone(String title) {
            pathology_phone.setText(title);
        }

        private void Pathology_Address( String title) {
            pathology_address.setText(title);

        }

         private void Pathology_Website(final String title) {
             website.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent i = new Intent(Intent.ACTION_VIEW);
                     i.setData(Uri.parse(title));
                     mView.getContext().startActivity(i);
                     //Toast.makeText(mView.getContext(), "Website:  " + title, Toast.LENGTH_SHORT).show();
                 }
             });
        }

         private void Pathology_Direction(final String title) {
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

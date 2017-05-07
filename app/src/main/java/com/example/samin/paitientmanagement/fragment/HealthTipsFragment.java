package com.example.samin.paitientmanagement.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.other.Show_health_tips_data_item;
import com.example.samin.paitientmanagement.other.Show_pathology_data_item;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;


public class HealthTipsFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private static FirebaseRecyclerAdapter<Show_health_tips_data_item, HealthTipsFragment.HealthTipsDetailsViewHolder> mFirebaseAdapter;
    private FirebaseAuth firebaseAuth;
    private static String userID;
    private LinearLayoutManager mLinearLayoutManager;


    public HealthTipsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Health_Tips");
        myRef.keepSynced(true);
        userID = firebaseAuth.getCurrentUser().getEmail().replace("@","").replace(".","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_health_tips_layout, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.show_health_tips_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLinearLayoutManager.setStackFromEnd(true);
        mLinearLayoutManager.setReverseLayout(true);


        //recyclerView.setItemAnimator(animation);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
       // mLinearLayoutManager.
        Toast.makeText(getContext(), "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        //Log.d("LOGGED", "IN onStart ");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_health_tips_data_item, HealthTipsFragment.HealthTipsDetailsViewHolder>(Show_health_tips_data_item.class, R.layout.show_health_tips_single_item, HealthTipsFragment.HealthTipsDetailsViewHolder.class, myRef)
        {



//            @Override
//            protected void onChildChanged(ChangeEventListener.EventType type, int index, int oldIndex) {
//
//
//                Log.d("LOGGED", "My onChildChanged ");
//                Log.d("LOGGED", "My onChildChanged " + index);
//                Log.d("LOGGED", "My onChildChanged " + oldIndex);
//                //recyclerView.scrollToPosition(index);
//
//
//                super.onChildChanged(type, index, oldIndex);
//            }

//            @Override
//            public Show_health_tips_data_item getItem(int position) {
//                Log.d("LOGGED", "IN My getItem " + position);
//                return super.getItem(position);
//
//            }

//            @Override
//            public HealthTipsDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_health_tips_single_item, parent, false);
//                final HealthTipsDetailsViewHolder holder = new HealthTipsDetailsViewHolder(itemView);
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int position = holder.getAdapterPosition();
//                        Log.d("LOGGED", "IN My Position " + position);
//                    }
//                });
//                return new HealthTipsDetailsViewHolder(itemView);
//            }




            public void populateViewHolder(final HealthTipsFragment.HealthTipsDetailsViewHolder viewHolder,final Show_health_tips_data_item model, final int position) {
                //Log.d("LOGGED", "IN populateViewHolder ");

                viewHolder.Thank_counter(model.getThank_counter(),mFirebaseAdapter.getRef(position));
                viewHolder.Post_about(model.getPost_about());
                viewHolder.Post_date(model.getPost_date());
                viewHolder.Post_title(model.getPost_title());
                viewHolder.Posted_by(model.getPosted_by());
                viewHolder.Post_image(model.getPost_image());
                viewHolder.Doctor_email(model.getDoctor_email());
                viewHolder.Thank_Person(model.getThank_person(),mFirebaseAdapter.getRef(position));
                //my_position = position;
               }

            @Override
            protected void onDataChanged() {
                super.onDataChanged();

                Log.d("LOGGED", "IN onDataChanged ");
                //mLinearLayoutManager.scrollToPosition(my_position);
                //recyclerView.scrollToPosition(my_position);

            }
        };








        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                super.onItemRangeInserted(positionStart, itemCount);
//                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
//                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
//                Log.d("LOGGED", "friendlyMessageCount " + friendlyMessageCount);
//                Log.d("LOGGED", "lastVisiblePosition " + lastVisiblePosition);
//                // If the recycler view is initially being loaded or the
//                // user is at the bottom of the list, scroll to the bottom
//                // of the list to show the newly added message.
//                if (lastVisiblePosition == -1 ||
//                        (positionStart >= (friendlyMessageCount - 1) &&
//                                lastVisiblePosition == (positionStart - 1))) {
//                    //recyclerView.scrollToPosition(positionStart);
//                    recyclerView.smoothScrollToPosition(my_position);
//                    //Log.d("logged", "scrollToPosition: " + my_position);
//                }
//                recyclerView.smoothScrollToPosition(my_position);
//
//            }
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d("LOGGED", "onChanged " );
                //recyclerView.smoothScrollToPosition(my_position);

            }
        });
       // Log.d("LOGGED", "IN setAdapter ");
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mFirebaseAdapter);


        //recyclerView.setAnimation

    }




    //View Holder For Recycler View
    public static class HealthTipsDetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView  Post_about, Post_date, Post_title, Posted_by, Thank_counter,post_consult,post_share,doctor_qualification,doctor_address;
        private final ImageView Post_image,post_thank_image,doctor_image;
        // private final TextView  pathology_website, pathology_direction,
       // private final LinearLayout website,direction;
        View mView;

        public HealthTipsDetailsViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            //Log.d("LOGGED", "IN ViewHolder ");
            //Doctor_email = (TextView) mView.findViewById(R.id.health_tips_post_doctor_name);
            Thank_counter = (TextView) mView.findViewById(R.id.health_tips_post_thank_counter);
            Post_about = (TextView) mView.findViewById(R.id.health_tips_post_about);
            Post_date = (TextView) mView.findViewById(R.id.health_tips_post_date);
            Post_title = (TextView) mView.findViewById(R.id.health_tips_post_title);
            Posted_by = (TextView) mView.findViewById(R.id.health_tips_post_doctor_name);

            post_consult=(TextView)mView.findViewById(R.id.health_tips_post_consult_button);
            post_share=(TextView)mView.findViewById(R.id.health_tips_post_share_button);

            doctor_qualification=(TextView)mView.findViewById(R.id.health_tips_post_doctor_qualification);
            doctor_address=(TextView)mView.findViewById(R.id.health_tips_post_doctor_address);

            Post_image = (ImageView) mView.findViewById(R.id.health_tips_post_image);
            post_thank_image=(ImageView)mView.findViewById(R.id.health_tips_post_thnak_image);
            doctor_image=(ImageView)mView.findViewById(R.id.health_tips_post_doctor_image);




            post_consult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mView.getContext(), "Not Implemented Yet", Toast.LENGTH_SHORT).show();
                }
            });
            post_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mView.getContext(), "Not Implemented Yet", Toast.LENGTH_SHORT).show();
                }
            });

//            post_thank_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Log.d("TAGGED", "onClick: " + post_thank_image.getImageAlpha());
////                    Log.d("TAGGED", "onClick: " + post_thank_image.getId());
////                    Log.d("TAGGED", "onClick: " + R.drawable.ic_thank);
//                   Toast.makeText(mView.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
////                    Log.d("TAGGED", "onClick: " + post_thank_image.getContentDescription());
////                    if(post_thank_image.getContentDescription().equals("not_liked"))
////                    {
////                        Glide.with(itemView.getContext()).load(R.drawable.ic_thank_selected)
////                                .crossFade()
////                                .diskCacheStrategy(DiskCacheStrategy.ALL)
////                                .into(post_thank_image);
////                        post_thank_image.setContentDescription("liked");
////                        return;
////                    }
////                    if(post_thank_image.getContentDescription().equals("liked"))
////                    {
////                        Glide.with(itemView.getContext()).load(R.drawable.ic_thank)
////                                .crossFade()
////                                .diskCacheStrategy(DiskCacheStrategy.ALL)
////                                .into(post_thank_image);
////                        post_thank_image.setContentDescription("not_liked");
////                    }
//
//                }
//            });

        }

        private void Thank_counter(String title, final DatabaseReference ref) {

            //Log.d("TAGGED", "title: " + title);
            //Log.d("LOGGED", "IN Thank_counter ");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String complete_thank_person = dataSnapshot.child("Thank_person").getValue(String.class);

                    int counter = 0;
                    //Log.d("TAGGED", "length: " + complete_thank_person.length());

                    for( int i=0; i<complete_thank_person.length(); i++ )
                    {
                        //Log.d("TAGGED", "Character AT: " + complete_thank_person.charAt(i) + "\n");
                        if(complete_thank_person.charAt(i) == ',')
                            counter++;
                            //Log.d("TAGGED", "counter: " + counter);
                        }

                    Thank_counter.setText((String.valueOf(counter)).concat(" Thanks"));



//                    if(complete_thank_person.contains(userID))
//                    {
//                        String temp_complete_thank_person = complete_thank_person.replace(userID+",","");
//                        ref.child("Thank_counter").setValue(temp_complete_thank_person);
//                    }
//                    else
//                    {
//                        ref.child("Thank_counter").setValue(complete_thank_person.concat(userID+","));
//                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





        }
        private void Thank_Person(String title, final DatabaseReference ref) {
           // Thank_counter.setText(title.concat(" Thanks"));
            //Log.d("TAGGED", "TITLE: " + title);
            //Log.d("TAGGED", "USER ID: " + userID);
            //Log.d("TAGGED", "Position: " + position);
           // Log.d("TAGGED", "REF: " + ref.toString());
            //Log.d("LOGGED", "IN Thank_Person ");

            if(title.contains(userID))
            {
                Glide.with(itemView.getContext()).load(R.drawable.ic_thank_selected)
                                .crossFade()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(post_thank_image);
            }
            else
            {
                Glide.with(itemView.getContext()).load(R.drawable.ic_thank)
                        .crossFade()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(post_thank_image);
            }

            post_thank_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      //my_position = getAdapterPosition();
                    //Log.d("LOGGED", "MY getAdapterPosition " + my_position);
                   // DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("ff");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Log.d("LOGGED", "IN post_thank_image.setOnClickListener ");
                            String complete_thank_person = dataSnapshot.child("Thank_person").getValue(String.class);
                            if(complete_thank_person.contains(userID))
                            {
                                String temp_complete_thank_person = complete_thank_person.replace(userID+",","");
                                ref.child("Thank_person").setValue(temp_complete_thank_person);
                            }
                            else
                            {
                                ref.child("Thank_person").setValue(complete_thank_person.concat(userID+","));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }


        private void Post_about(String title) {
            Post_about.setText(title);
            Typeface txt2 =Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/RopaSans-Regular.ttf");

            Post_about.setTypeface(txt2);
        }

        private void Post_date(String title) {
            Post_date.setText(title);
        }

        private void Post_title( String title) {
            Post_title.setText(title);
            Typeface txt2 =Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/Graduate-Regular.ttf");
            Post_title.setTypeface(txt2);

        }


        private void Posted_by( String title) {
            Posted_by.setText(title);
            //Typeface txt2 =Typeface.createFromAsset(get,"fonts/RobotoCondensed-BoldItalic.ttf");
            Typeface txt2 =Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/RobotoCondensed-BoldItalic.ttf");

            Posted_by.setTypeface(txt2);
        }


       private void Post_image( String title) {
           Glide.with(itemView.getContext()).load(title)
                   .crossFade()
                   .diskCacheStrategy(DiskCacheStrategy.ALL)
                   .into(Post_image);
        }


        private void Doctor_email( String title) {

           if(!title.equals("null"))
           {
               DatabaseReference mRootref = FirebaseDatabase.getInstance().getReference().child("Doctor_Detais").child(title);
               mRootref.keepSynced(true);

               mRootref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                    String doctor_image_url = dataSnapshot.child("Image_URL").getValue(String.class);
                    String doctor_specialization = dataSnapshot.child("Specialization").getValue(String.class);
                    String doctor_chamber = dataSnapshot.child("Chamber").getValue(String.class);

                       doctor_address.setText(doctor_chamber);
                       doctor_qualification.setText(doctor_specialization.concat(","));

                       Typeface txt2 =Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/Roboto-Regular.ttf");

                       doctor_address.setTypeface(txt2);
                       doctor_qualification.setTypeface(txt2);

                       if(!doctor_image_url.equals("null"))
                       {
                           Glide.with(itemView.getContext()).load(doctor_image_url)
                                   .crossFade()
                                   .diskCacheStrategy(DiskCacheStrategy.ALL)
                                   .into(doctor_image);
                       }

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
           }

        }




    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFirebaseAdapter != null) {
            mFirebaseAdapter.cleanup();
            mFirebaseAdapter = null;
        }
    }
}

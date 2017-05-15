//package com.example.samin.paitientmanagement.fragment;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.samin.paitientmanagement.R;
//import com.example.samin.paitientmanagement.activity.MainActivity;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ChatFragment extends Fragment {
//
////    private RecyclerView recyclerView;
////    private FirebaseRecyclerAdapter<Show_notification_data_item, ChatFragment.MyViewHolder> mFirebaseAdapter;
////    ProgressBar progressBar;
////    DatabaseReference mRootRef;
////    ImageView noDataAvailableImage;
//    LinearLayout layout;
//    RelativeLayout layout_2;
//    ImageView sendButton;
//    EditText messageArea;
//    ScrollView scrollView;
//    DatabaseReference reference1, reference2;
//    int width;
//
//    public ChatFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View v = inflater.inflate(R.layout.fragment_chat, container, false);
////        noDataAvailableImage = (ImageView) v.findViewById(R.id.no_data_available_image);
////
////        String UserID = MainActivity.LoggedIn_User_Email.replace("@", "").replace(".", "");
////        progressBar = (ProgressBar) v.findViewById(R.id.progressBar3);
////
////
////        mRootRef = FirebaseDatabase.getInstance().getReference("User_Notification").child(UserID);
////        Log.d("LOGGED", "mRootRef " + mRootRef);
////        //Recycler View
////        recyclerView = (RecyclerView) v.findViewById(R.id.show_notification_recycler_view);
////        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////
////        //Log.d("LOGGED", "ADAPTER 1st " + adapter);
////        Toast.makeText(getContext(), "Wait ! Fetching data...", Toast.LENGTH_SHORT).show();
////        progressBar.setVisibility(ProgressBar.VISIBLE);
//
//        layout = (LinearLayout)v.findViewById(R.id.layout1);
//        layout_2 = (RelativeLayout)v.findViewById(R.id.layout2);
//        sendButton = (ImageView)v.findViewById(R.id.sendButton);
//        messageArea = (EditText)v.findViewById(R.id.messageArea);
//        scrollView = (ScrollView)v.findViewById(R.id.myscrollView);
//
//        //Firebase.setAndroidContext(this);
//        reference1 = FirebaseDatabase.getInstance().getReference().child("Chat1");
//        reference2 = FirebaseDatabase.getInstance().getReference().child("Chat2");
//
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String messageText = messageArea.getText().toString().trim();
//
//                if(!messageText.equals("")){
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("message", messageText);
//                    map.put("user", MainActivity.LoggedIn_User_Email);
//                    reference1.push().setValue(map);
//                    reference2.push().setValue(map);
//                    messageArea.setText("");
//                }
//            }
//        });
//
//        reference1.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String message = dataSnapshot.child("message").getValue(String.class);
//                String userName = dataSnapshot.child("user").getValue(String.class);
//                //Map map = dataSnapshot.getValue(Map.class);
//                //String message = map.get("message").toString();
//                //String userName = map.get("user").toString();
//
//                if(userName.equals(MainActivity.LoggedIn_User_Email)){
//                    addMessageBox("You:-\n" + message, 1);
//                }
//                else{
//                    addMessageBox("Chandrima" + ":-\n" + message, 2);
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//
//
//        return v;
//    }
//    public void addMessageBox(String message, int type){
//        TextView textView = new TextView(getContext().getApplicationContext());
//        textView.setText(message);
//        width = layout.getWidth();
//        //textView.setPadding(5,0,5,0);
//
//        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp2.weight = 1.0f;
//
//
//
//        if(type == 1) {
//            lp2.gravity = Gravity.START;
//            lp2.setMargins(10,0,width/3,0);
//            textView.setBackgroundResource(R.drawable.bubble_out);
//        }
//        else{
//            lp2.gravity = Gravity.END;
//            lp2.setMargins(width/3,0,10,0);
//            textView.setBackgroundResource(R.drawable.bubble_in);
//        }
//        textView.setLayoutParams(lp2);
//        layout.addView(textView);
//        scrollView.fullScroll(View.FOCUS_DOWN);
//
//    }
//
////    @Override
////    public void onStart() {
////        super.onStart();
////        // Log.d("LOGGED", "IN onStart ");
////
////        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_notification_data_item, MyViewHolder>(Show_notification_data_item.class, R.layout.fragment_notification_single_item, ChatFragment.MyViewHolder.class, mRootRef) {
////            @Override
////            protected void populateViewHolder(MyViewHolder viewHolder, Show_notification_data_item model, int position) {
////                //progressBar.setVisibility(ProgressBar.INVISIBLE);
////                Log.d("LOGGED", "populateViewHolder Called ");
////                viewHolder.set_notification_image(model.getNotification_Image());
////                viewHolder.set_notification_date(model.getNotification_Date());
////                viewHolder.set_notification_time(model.getNotification_Time());
////                viewHolder.set_notification_text(model.getNotification_Text());
////            }
////
////        };
////        //Log.d("LOGGED", "Setting Adapter ");
////        recyclerView.setAdapter(mFirebaseAdapter);
////
////
////        mRootRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
////            @Override
////            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
////                //Log.d("LOGGED", "I Called Yahoo ");
////                if (dataSnapshot.hasChildren()) {
////                    //Log.d("LOGGED", "Boolean value true: ");
////                    progressBar.setVisibility(ProgressBar.INVISIBLE);
////                    recyclerView.setVisibility(View.VISIBLE);
////                } else {
////                    progressBar.setVisibility(ProgressBar.INVISIBLE);
////                    recyclerView.setVisibility(View.INVISIBLE);
////                    noDataAvailableImage.setVisibility(View.VISIBLE);
////                    Toast.makeText(getContext(), "Data Unavailable", Toast.LENGTH_SHORT).show();
////                    //Log.d("LOGGED", "Boolean value false: ");
////
////                }
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        });
////        recyclerView.setAdapter(mFirebaseAdapter);
////
////        mRootRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
////            @Override
////            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
////                //Log.d("LOGGED", "I Called Yahoo ");
////                if(dataSnapshot.hasChildren())
////                {
////                    //Log.d("LOGGED", "Boolean value true: ");
////                    progressBar.setVisibility(ProgressBar.INVISIBLE);
////                    recyclerView.setVisibility(View.VISIBLE);
////                }
////                else {
////                    progressBar.setVisibility(ProgressBar.INVISIBLE);
////                    recyclerView.setVisibility(View.INVISIBLE);
////                    noDataAvailableImage.setVisibility(View.VISIBLE);
////                    Toast.makeText(getContext(), "Data Unavailable", Toast.LENGTH_SHORT).show();
////                    //Log.d("LOGGED", "Boolean value false: ");
////
////                }
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        });
////
////    }
////
//////    @Override
//////    public boolean onCreateOptionsMenu(Menu menu) {
//////        // Inflate the menu; this adds items to the action bar if it is present.
//////        getMenuInflater().inflate(R.menu.show_appointment_menu, menu);
//////        return true;
//////    }
//////
//////    @Override
//////    public boolean onOptionsItemSelected(MenuItem item) {
//////        switch (item.getItemId()) {
//////            case android.R.id.home:
//////                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
//////                // if this doesn't work as desired, another possibility is to call `finish()` here.
//////                this.onBackPressed();
//////                return true;
//////            case R.id.action_refresh:
//////                recyclerView.invalidate();
//////                onStart();
//////                return true;
//////            default:
//////                return super.onOptionsItemSelected(item);
//////        }
//////    }
////
////    //View Holder For Recycler View
////    public static class MyViewHolder extends RecyclerView.ViewHolder {
////        private final TextView post_date,post_time,post_text;
////        private final ImageView post_notification_image;
////        View mView;
////        //Firebase mRoofRef;
////        RecyclerView.ViewHolder vv;
////
////        public MyViewHolder(final View itemView) {
////
////            super(itemView);
////            Log.d("LOGGED", "ViewHolder Called: ");
////            mView = itemView;
////            post_date = (TextView) mView.findViewById(R.id.notification_date);
////            post_time = (TextView) mView.findViewById(R.id.notification_time);
////            post_text = (TextView) mView.findViewById(R.id.notification_text);
////            post_notification_image = (ImageView) mView.findViewById(R.id.notification_image);
////
////        }
////        //set_notification_image
////        //set_notification_date
////        //set_notification_time
////        //set_notification_text
////
////
////        private void set_notification_date(String title) {
////            post_date.setText(title);
////            //Log.d("LOGGED", "Doctor_Name: " + title);
////        }
////
////        private void set_notification_time(String title) {
////            post_time.setText(title);
////            //Log.d("LOGGED", "Doctor_Name: " + title);
////        }
////
////        private void set_notification_text(String title) {
////            post_text.setText(title);
////            //Log.d("LOGGED", "Doctor_Name: " + title);
////        }
////
////
////        private void set_notification_image(String title) {
////                Glide.with(itemView.getContext())
////                        .load(title)
////                        .thumbnail(0.5f)
////                        .crossFade()
////                        .placeholder(R.drawable.loading)
////                        .bitmapTransform(new CircleTransform(itemView.getContext()))
////                        .diskCacheStrategy(DiskCacheStrategy.ALL)
////                        .into(post_notification_image);
////
////
////            }
////        }
//
//
//    }
//

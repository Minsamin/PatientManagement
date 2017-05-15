package com.example.samin.paitientmanagement.activity;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.other.Show_chat_data_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static java.security.AccessController.getContext;


public class ChatActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef,myRef2;
    private FirebaseRecyclerAdapter<Show_chat_data_item, ChatActivity.ChatDetailsViewHolder> mFirebaseAdapter;
    public LinearLayoutManager mLinearLayoutManager;
    static String Sender_Name;
    ImageView attach_icon,send_icon,no_data_available_image;
    EditText message_area;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;
    ProgressBar progressBar;
    public static final int READ_EXTERNAL_STORAGE = 0;
    Uri mImageUri = Uri.EMPTY;
    TextView no_chat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        String USER_ID = MainActivity.LoggedIn_User_Email.replace("@","").replace(".","");
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("Chat").child(USER_ID).child(getIntent().getStringExtra("email").replace("@","").replace(".",""));
        myRef.keepSynced(true);

        //reference1 = FirebaseDatabase.getInstance().getReference().child("Chat").child(USER_ID).child(getIntent().getStringExtra("email").replace("@","").replace(".",""));
        myRef2 = FirebaseDatabase.getInstance().getReference().child("Chat").child(getIntent().getStringExtra("email").replace("@","").replace(".","")).child(USER_ID);
        myRef2.keepSynced(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.fragment_chat_appBarLayout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color=#FFFFFF>" + getIntent().getStringExtra("name") + "</font>"));
        }
        Sender_Name = getIntent().getStringExtra("name");

        recyclerView = (RecyclerView)findViewById(R.id.fragment_chat_recycler_view);

        attach_icon = (ImageView)findViewById(R.id.attachButton);
        send_icon = (ImageView)findViewById(R.id.sendButton);
        no_data_available_image = (ImageView)findViewById(R.id.no_data_available_image);
        message_area = (EditText)findViewById(R.id.messageArea);
        mProgressDialog = new ProgressDialog(this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        no_chat = (TextView)findViewById(R.id.no_chat_text);


        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());

        mLinearLayoutManager.setStackFromEnd(true);

        //mLinearLayoutManager.setReverseLayout(true);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        send_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = message_area.getText().toString().trim();

                if(!messageText.equals("")){
                    ArrayMap<String, String> map = new ArrayMap<>();
                    map.put("message", messageText);
                    map.put("sender", MainActivity.LoggedIn_User_Email);
                    myRef.push().setValue(map);
                    myRef2.push().setValue(map);
                    message_area.setText("");
                    recyclerView.postDelayed(new Runnable() {
                        @Override public void run()
                        {
                            // Toast.makeText(ChatActivity.this, "Executing", Toast.LENGTH_SHORT).show();
                            //recyclerView.scrollTo(0,recyclerView.getScrollY()+20);
                            //Log.d("LOGGED", " findLastCompletelyVisibleItemPosition " + (mLinearLayoutManager.findLastCompletelyVisibleItemPosition() -1));
                            //Log.d("LOGGED", " getItemCount " + (mLinearLayoutManager.getItemCount()));
                            //Log.d("LOGGED", " getAdapter + getItemCount " + (recyclerView.getAdapter().getItemCount()-1));
                            //recyclerView.scrollToPosition(mLinearLayoutManager.getItemCount()+1);
                            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount()-1);

                        }
                    }, 500);
                }
            }
        });

        attach_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(ChatActivity.this, "Call for Permission", Toast.LENGTH_SHORT).show();
//                    //requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
//                    ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
//                } else {
//
//                    callgalary();
//                }
                if (ContextCompat.checkSelfPermission(ChatActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ChatActivity.this, "Call for Permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(ChatActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                } else {

                    callgalary();
                }
            }
        });




    }



    @Override
    public void onStart() {
        super.onStart();
        //Log.d("LOGGED", "IN onStart ");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_chat_data_item, ChatActivity.ChatDetailsViewHolder>(Show_chat_data_item.class, R.layout.show_chat_single_item, ChatActivity.ChatDetailsViewHolder.class, myRef) {


            public void populateViewHolder(final ChatActivity.ChatDetailsViewHolder viewHolder, Show_chat_data_item model, final int position) {
                viewHolder.getSender(model.getSender());
                viewHolder.getMessage(model.getMessage());


                //OnClick Item
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        final DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.keepSynced(true);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String retrieve_image_url = dataSnapshot.child("message").getValue(String.class);
                                if(retrieve_image_url.startsWith("https"))
                                {
                                    //Toast.makeText(ChatActivity.this, "URL : " + retrieve_image_url, Toast.LENGTH_SHORT).show();
                                    Intent intent = (new Intent(ChatActivity.this,EnlargeImageView.class));
                                    intent.putExtra("url",retrieve_image_url);
                                    startActivity(intent);
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                  });

            }
        };

        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mFirebaseAdapter);

        myRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                //Log.d("LOGGED", "I Called Yahoo ");
                if (dataSnapshot.hasChildren())
                {
                    //Log.d("LOGGED", "Boolean value true: ");
                    progressBar.setVisibility(ProgressBar.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    no_data_available_image.setVisibility(View.GONE);
                    no_chat.setVisibility(View.GONE);




                    recyclerView.postDelayed(new Runnable() {
                        @Override public void run()
                        {
                            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                        }
                    }, 500);




                    recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v,
                                                   int left, int top, int right, int bottom,
                                                   int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            if (bottom < oldBottom) {
                                recyclerView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                                    }
                                }, 100);
                            }
                        }
                    });
                }


                else {
                    progressBar.setVisibility(ProgressBar.GONE);
                    recyclerView.setVisibility(View.GONE);
                    no_data_available_image.setVisibility(View.VISIBLE);
                    no_chat.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "Chat Unavailable", Toast.LENGTH_SHORT).show();
                    //Log.d("LOGGED", "Boolean value false: ");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
    public static class ChatDetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView message, sender;
        private final ImageView chat_image_incoming,chat_image_outgoing;
        View mView;
        final LinearLayout.LayoutParams params,text_params;
        LinearLayout layout;


        public ChatDetailsViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            message = (TextView) mView.findViewById(R.id.fetch_chat_messgae);
            sender = (TextView) mView.findViewById(R.id.fetch_chat_sender);
            chat_image_incoming = (ImageView) mView.findViewById(R.id.chat_uploaded_image_incoming);
            chat_image_outgoing = (ImageView) mView.findViewById(R.id.chat_uploaded_image_outgoing);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            text_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layout = (LinearLayout) mView.findViewById(R.id.chat_linear_layout);



        }

        private void getSender(String title) {


            if(title.equals(MainActivity.LoggedIn_User_Email))
            {
                //int width = mView.getWidth();

                // params.gravity = Gravity.END;
                // params.setLayoutDirection(Gravity.END);
                // params.weight = 1.0f;
                //layout.setHorizontalGravity(Gravity.END);
                //layout.setGravity(GravityCompat.END);
                //layout.setGravity(Gravity.END);
                params.setMargins((MainActivity.Device_Width/3),5,10,10);
                text_params.setMargins(15,10,0,5);
                sender.setLayoutParams(text_params);
                mView.setLayoutParams(params);
                mView.setBackgroundResource(R.drawable.shape_outcoming_message);
                sender.setText("YOU");
                chat_image_outgoing.setVisibility(View.VISIBLE);
                chat_image_incoming.setVisibility(View.GONE);
                //Log.d("LOGGED", "getSender: YOU WIDTH" + MainActivity.Device_Width );
            }
            else
            {

                //int width = mView.getWidth();
                //params.gravity = Gravity.START;
                //params.setLayoutDirection(Gravity.START);
                //params.weight = 3.0f;
                params.setMargins(10,0,(MainActivity.Device_Width/3),10);
                sender.setGravity(Gravity.START);
                text_params.setMargins(60,10,0,5);
                sender.setLayoutParams(text_params);


                mView.setLayoutParams(params);
//
                mView.setBackgroundResource(R.drawable.shape_incoming_message);
                sender.setText(Sender_Name);
                chat_image_outgoing.setVisibility(View.GONE);
                chat_image_incoming.setVisibility(View.VISIBLE);
                //Log.d("LOGGED", "getSender: SENDER WIDTH" + MainActivity.Device_Width );
            }



        }

        private void getMessage(String title) {
            // message.setText(title);
            if(!title.startsWith("https"))
            {
                //message.setVisibility(View.VISIBLE);
                if(!sender.getText().equals(Sender_Name))
                {
                    text_params.setMargins(15,10,22,15);
                }
                else
                {
                    text_params.setMargins(65,10,22,15);
                }

                message.setLayoutParams(text_params);
                message.setText(title);
                message.setTextColor(Color.parseColor("#FFFFFF"));
                //Log.d("LOGGED", "getMessage: TEXT" );
                message.setVisibility(View.VISIBLE);
                chat_image_incoming.setVisibility(View.GONE);
                chat_image_outgoing.setVisibility(View.GONE);
            }
            else
            {
                if (chat_image_outgoing.getVisibility()==View.VISIBLE && chat_image_incoming.getVisibility()==View.GONE)
                {
                    chat_image_outgoing.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    Glide.with(mView.getContext())
                            .load(title)
                            .crossFade()
                            .fitCenter()
                            .placeholder(R.drawable.loading)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(chat_image_outgoing);
                }
                else
                {
                    chat_image_incoming.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    Glide.with(mView.getContext())
                            .load(title)
                            .crossFade()
                            .fitCenter()
                            .placeholder(R.drawable.loading)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(chat_image_incoming);
                }
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            mImageUri = data.getData();
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("Chat_Images").child(mImageUri.getLastPathSegment());
            //Log.d("LOGGED", "ImageURI : " +mImageUri);
            //the Progress bar Should be Here
            mProgressDialog.setMessage("Uploading...");
            mProgressDialog.show();

            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
                    //reference1.child("message").setValue(downloadUri.toString());

//                    Glide.with(getApplicationContext())
//                            .load(downloadUri)
//                            .crossFade()
//                            .placeholder(R.drawable.loading)
//                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                            .into(imageView);
                    ArrayMap<String, String> map = new ArrayMap<>();
                    map.put("message", downloadUri.toString());
                    map.put("sender", MainActivity.LoggedIn_User_Email);
                    myRef.push().setValue(map);
                    myRef2.push().setValue(map);

                    //Picasso.with(getContext()).load(downloadUri).into(user_image);
                    //Toast.makeText(getApplicationContext(), "Posted", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
                //Toast.makeText(getContext(), "Call Req Prmssn", Toast.LENGTH_SHORT).show();
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    // Toast.makeText(getContext(), "Inside If", Toast.LENGTH_SHORT).show();
                    callgalary();
                return;
        }
        Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Glide.clear(imageView);
        Glide.get(getApplicationContext()).clearMemory();
    }


    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
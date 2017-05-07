package com.example.samin.paitientmanagement.activity;

import com.example.samin.paitientmanagement.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.Manifest;
import com.example.samin.paitientmanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Samin on 19-04-2017.
 */

public class Post_Health_Tips extends AppCompatActivity {
    private Toolbar toolbar;
     EditText about,title;
     ImageView imageView;
     ImageButton camera_button;
     Button post_button;
    DatabaseReference mRoofref;
    private FirebaseAuth firebaseAuth;
    Uri mImageUri = Uri.EMPTY;
    //private DatabaseReference mdatabaseRef;
    // private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;
    public String UserID;
    public static final int READ_EXTERNAL_STORAGE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post_health_tips);
        firebaseAuth = FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.show_appointment_appBarLayout);
        setSupportActionBar(toolbar);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color=#FFFFFF>" + "Health Tips" + "</font>"));
        }
        final SimpleDateFormat current_date_format = new SimpleDateFormat( "dd/MM/yyyy" );
        final SimpleDateFormat current_time_format = new SimpleDateFormat("h:mm a");
        mRoofref = FirebaseDatabase.getInstance().getReference().child("Health_Tips").push();
        mRoofref.keepSynced(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        about = (EditText) findViewById(R.id.post_health_about);
        title = (EditText) findViewById(R.id.post_health_title);
        imageView = (ImageView)findViewById(R.id.post_health_image_view);
        post_button = (Button)findViewById(R.id.post_health_button);
        camera_button =(ImageButton)findViewById(R.id.post_health_image_button);
        mProgressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserID = user.getEmail().replace("@", "").replace(".", "");

        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if(checkS){}

                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Post_Health_Tips.this, "Call for Permission", Toast.LENGTH_SHORT).show();
                    //requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                } else {

                    callgalary();
                }
            }
        });


        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mAbout = about.getText().toString().trim();
                final String mTitle = title.getText().toString().trim();

                if ((mAbout.isEmpty() || mTitle.isEmpty()))
                {
                    Toast.makeText(getApplicationContext(), "Fill all Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if( mImageUri==Uri.EMPTY)
                {
                    Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                mRoofref.child("Post_about").setValue(mAbout);
                mRoofref.child("Post_title").setValue(mTitle);
                mRoofref.child("Doctor_email").setValue(UserID);
                mRoofref.child("Post_date").setValue(current_date_format.format( new Date()));
                mRoofref.child("Thank_counter").setValue("0");
                mRoofref.child("Thank_person").setValue("");


                 DatabaseReference mRoofref2 = FirebaseDatabase.getInstance().getReference().child("User_Details").child(UserID);
                 mRoofref2.keepSynced(true);
                mRoofref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String Name = dataSnapshot.child("Name").getValue(String.class);
                        mRoofref.child("Posted_by").setValue(Name);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                    StorageReference filePath = FirebaseStorage.getInstance().getReference().child("Health_Tips_Image").child(mImageUri.getLastPathSegment());
                    //Log.d("LOGGED", "ImageURI : " +mImageUri);
                    //the Progress bar Should be Here
                    mProgressDialog.setMessage(" Wait...");
                    mProgressDialog.show();

                    filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
                            mRoofref.child("Post_image").setValue(downloadUri.toString());

                            Glide.with(getApplicationContext())
                                    .load(downloadUri)
                                    .crossFade()
                                    .placeholder(R.drawable.loading)
                                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                    .into(imageView);

                            //Picasso.with(getContext()).load(downloadUri).into(user_image);
                            Toast.makeText(getApplicationContext(), "Posted", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    });


                //mRoofref.child("Post_image").setValue(mTitle);



                Toast.makeText(getApplicationContext(), "Tips Posted.", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            mImageUri = data.getData();
            imageView.setImageURI(mImageUri);
            //StorageReference filePath = mStorage.child("User_Images").child(mImageUri.getLastPathSegment());
//            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("User_Images").child(mImageUri.getLastPathSegment());
//            Log.d("LOGGED", "ImageURI : " +mImageUri);
//            //the Progress bar Should be Here
//            mProgressDialog.setMessage("Wait ! Uploading Image...");
//            mProgressDialog.show();
//
//            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
//                    mRoofref.child("Post_image").setValue(downloadUri.toString());
//
//                    Glide.with(getApplicationContext())
//                            .load(downloadUri)
//                            .crossFade()
//                            .placeholder(R.drawable.loading)
//                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                            .into(imageView);
//
//                    //Picasso.with(getContext()).load(downloadUri).into(user_image);
//                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
//                    mProgressDialog.dismiss();
//                }
//            });
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
        Glide.clear(imageView);
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

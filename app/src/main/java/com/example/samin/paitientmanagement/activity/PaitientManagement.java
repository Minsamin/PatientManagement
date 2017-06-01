package com.example.samin.paitientmanagement.activity;

import android.app.Application;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.other.CircleTransform;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//

/**
 * Created by Samin on 15-02-2017.
 */

public class PaitientManagement extends Application{

    static boolean isInitialized = false;
    //static DatabaseReference mRoofRef;
    FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    public static String app_user_type = "null",LoggedIn_User_Email;
   // static public String UserID;

    @Override
    public void onCreate() {
        super.onCreate();
        //Firebase.setAndroidContext(this);
        //Log.d("LOGGED", "Application onCreate: ");
       // Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
        try {
            if (!isInitialized) {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                isInitialized = true;
            } else {
                Log.d("LOGGED", "Already Initialized");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        //user = firebaseAuth.getCurrentUser();
        //LoggedIn_User_Email = user.getEmail();
        //UserID = LoggedIn_User_Email.replace("@", "").replace(".", "");

//        mRoofRef = FirebaseDatabase.getInstance().getReference().child("User_Details").child(UserID);
//        mRoofRef.keepSynced(true);
//        mRoofRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
//            @Override
//            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
//                String retrieve_type = dataSnapshot.child("User_Type").getValue(String.class);
//                if (retrieve_type.equals("Doctor")) {
//                    //app_user_type = "Doctor";
//                    set_app_user_type("Doctor");
//
//                }
//                if (retrieve_type.equals("Patient")) {
//                    //app_user_type = "Patient";
//                    set_app_user_type("Patient");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                //Log.w("TAGGED", "loadPost:onCancelled", databaseError.toException());
//            }
//        });
    }

    public void set_app_user_type(String type, String email)
    {
        if(type.equals("Doctor"))
        {
            app_user_type = "Doctor";
            LoggedIn_User_Email = email;
        }
        else if (type.equals("Patient"))
        {
            app_user_type = "Patient";
            LoggedIn_User_Email = email;
        }
    }

}

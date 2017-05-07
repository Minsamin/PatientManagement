package com.example.samin.paitientmanagement.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.samin.paitientmanagement.BuildConfig;
import com.example.samin.paitientmanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class SettingsFragment extends Fragment {
    CardView changelog,checkForUpdate;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_settings, container, false);
        changelog = (CardView) v.findViewById(R.id.change_log);
        checkForUpdate = (CardView) v.findViewById(R.id.check_for_update);



        changelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
              View view = layoutInflater.inflate(R.layout.changelog_fragment_dialogstandard, null);


               new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle)
                      .setTitle(R.string.changelog_Header)
                      .setView(view)
                      .setPositiveButton
                              (R.string.about_ok,
                              new DialogInterface.OnClickListener()
                                {
                                  public void onClick(DialogInterface dialog, int whichButton)
                                  {
                                      dialog.dismiss();
                                  }
                                }
                              )
                      .show();
            }
        });


        checkForUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Version
                final String version = BuildConfig.VERSION_NAME;
                //Firebase myRef;
                //myRef = new Firebase("https://patient-management-11e26.firebaseio.com/Update_APK");
                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("Update_APK");


                //Toast.makeText(getContext(), "Got Name !" +ss, Toast.LENGTH_LONG).show();
                mRootRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Map<String, String> map = dataSnapshot.getValue(Map.class);
                        String retrieve_url = dataSnapshot.child("Download_URL").getValue(String.class);
                        String retrieve_version = dataSnapshot.child("Version").getValue(String.class);
                        if(version.equals(retrieve_version))
                        {
                            Toast.makeText(getContext(), "You have Latest Version ! " + retrieve_version, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Update Available ! " + retrieve_version, Toast.LENGTH_SHORT).show();
                            //new Intent(Intent.ACTION_VIEW, Uri.parse(retrieve_url));
                            //String url = "http://www.example.com";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(retrieve_url));
                            startActivity(i);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


//                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                        Map<String, String> map = dataSnapshot.getValue(Map.class);
//                        String retrieve_url = map.get("Download_URL");
//                        String retrieve_version = map.get("Version");
//                        if(version.equals(retrieve_version))
//                        {
//                            Toast.makeText(getContext(), "You have Latest Version ! " + retrieve_version, Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(getContext(), "Update Available ! " + retrieve_version, Toast.LENGTH_SHORT).show();
//                            //new Intent(Intent.ACTION_VIEW, Uri.parse(retrieve_url));
//                            //String url = "http://www.example.com";
//                            Intent i = new Intent(Intent.ACTION_VIEW);
//                            i.setData(Uri.parse(retrieve_url));
//                            startActivity(i);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
            }
        });

        return v;
    }


}

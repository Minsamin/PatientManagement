package com.example.samin.paitientmanagement.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment_Doctor extends Fragment {

    private EditText doctor_name, doctor_phone, doctor_chamber, doctor_Specialization,doctor_Experience,doctor_Fees,doctor_timing;
    private Button doctor_save_button;
    private ImageView doctor_image, change_image;
    private Firebase mRoofRef,mRoofRef_doctor;
    private FirebaseAuth firebaseAuth;
    private Uri mImageUri = null;
    private DatabaseReference mdatabaseRef;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;
    public String UserID;
    EditText sun_am, sun_pm, mon_am, mon_pm, tues_am, tues_pm, wed_am, wed_pm, thu_am, thu_pm, fri_am, fri_pm, sat_am, sat_pm;
    CheckBox sun, mon, tues, wed, thu, fri, sat;
    Context context;

    public static final int READ_EXTERNAL_STORAGE = 0;
    //final ArrayList seletedItems=new ArrayList();

    public ProfileFragment_Doctor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        context = getActivity();
        Log.d("LOGGED", "onCreate: context " + context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_profile_doctor, container, false);


        doctor_name = (EditText) v.findViewById(R.id.profile_edit_name);
        doctor_phone = (EditText) v.findViewById(R.id.profile_edit_phone);
        doctor_chamber = (EditText) v.findViewById(R.id.profile_edit_address);
        doctor_Experience = (EditText) v.findViewById(R.id.profile_edit_experience);
        doctor_Fees = (EditText) v.findViewById(R.id.profile_edit_fee);
        doctor_Specialization = (EditText) v.findViewById(R.id.profile_edit_specialization);
        doctor_timing = (EditText) v.findViewById(R.id.profile_edit_timing);

        doctor_image = (ImageView) v.findViewById(R.id.profile_edit_image);
        change_image = (ImageView) v.findViewById(R.id.change_user_image);





        doctor_timing.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    //Toast.makeText(getContext(), " Has Focused", Toast.LENGTH_SHORT).show();

                            //final CharSequence[] items = {"Mon", "Tues", "Wed", "Thus", "Fri", "Sat", "Sun"};
                            // arraylist to keep the selected items
                            //seletedItems=new ArrayList();

                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            final View dialogView = inflater.inflate(R.layout.timing_dailog, null);
                            dialog.setView(dialogView);

                            sun=(CheckBox)dialogView.findViewById(R.id.checkBox_sunday);
                            sun_am = (EditText)dialogView.findViewById(R.id.editText_sunday_am);
                            sun_pm = (EditText)dialogView.findViewById(R.id.editText_sunday_pm);

                            mon=(CheckBox)dialogView.findViewById(R.id.checkBox_monday);
                            mon_am = (EditText)dialogView.findViewById(R.id.editText_monday_am);
                            mon_pm = (EditText)dialogView.findViewById(R.id.editText_monday_pm);

                            tues=(CheckBox)dialogView.findViewById(R.id.checkBox_tuesday);
                            tues_am = (EditText)dialogView.findViewById(R.id.editText_tuesday_am);
                            tues_pm = (EditText)dialogView.findViewById(R.id.editText_tuesday_pm);

                            wed=(CheckBox)dialogView.findViewById(R.id.checkBox_wednesday);
                            wed_am = (EditText)dialogView.findViewById(R.id.editText_wednesday_am);
                            wed_pm = (EditText)dialogView.findViewById(R.id.editText_wednesday_pm);

                            thu=(CheckBox)dialogView.findViewById(R.id.checkBox_thursday);
                            thu_am = (EditText)dialogView.findViewById(R.id.editText_thursday_am);
                            thu_pm = (EditText)dialogView.findViewById(R.id.editText_thursday_pm);

                            fri=(CheckBox)dialogView.findViewById(R.id.checkBox_friday);
                            fri_am = (EditText)dialogView.findViewById(R.id.editText_friday_am);
                            fri_pm = (EditText)dialogView.findViewById(R.id.editText_friday_pm);

                            sat=(CheckBox)dialogView.findViewById(R.id.checkBox_saturday);
                            sat_am = (EditText)dialogView.findViewById(R.id.editText_saturday_am);
                            sat_pm = (EditText)dialogView.findViewById(R.id.editText_saturday_pm);





                    //Retrieve TextBox Data and implemented it to Alert DialogBox

                   String doctor_time= doctor_timing.getText().toString().trim();
                    if(!doctor_time.isEmpty())
                    {
                        String trim_brackets = doctor_time.replace("[","").replace("]","").replace(", ",",");
                        String myArray[] = trim_brackets.split(",");

                        int i=0;
                        for(String s : myArray)
                        {
                            if(s !=null && s.length() > 0)
                            {
                                Log.d("LOGGED", "MY ARRAY " + myArray[i]);


                                //for Sunday
                                if(myArray[i].contains("Sunday"))
                                {
                                    sun.setChecked(true);
                                    if(myArray[i].matches("Sunday .*-"))
                                    {
                                        String temp = myArray[i].replace("Sunday","").replace("-","");
                                        sun_am.setText(temp);
                                    }
                                    else if(myArray[i].matches("Sunday -.*"))
                                    {
                                        String temp = myArray[i].replace("Sunday","").replace("-","");
                                        sun_pm.setText(temp);
                                    }
                                    else if (myArray[i].matches("Sunday .*-.*"))
                                    {
                                        String temp = myArray[i].replace("Sunday ","").replace("-"," ");
                                        String[] temp2 = temp.split(" ");
                                        sun_am.setText(temp2[0]);
                                        sun_pm.setText(temp2[1]);
                                    }
                                }


                                //for Monday
                                if(myArray[i].contains("Monday"))
                                {
                                    mon.setChecked(true);
                                    if(myArray[i].matches("Monday .*-"))
                                    {
                                        String temp = myArray[i].replace("Monday","").replace("-","");
                                        mon_am.setText(temp);
                                    }
                                    else if(myArray[i].matches("Monday -.*"))
                                    {
                                        String temp = myArray[i].replace("Monday","").replace("-","");
                                        mon_pm.setText(temp);
                                    }
                                    else if (myArray[i].matches("Monday .*-.*"))
                                    {
                                        String temp = myArray[i].replace("Monday ","").replace("-"," ");
                                        String[] temp2 = temp.split(" ");
                                        mon_am.setText(temp2[0]);
                                        mon_pm.setText(temp2[1]);
                                    }
                                }

                                //for Tuesday
                                if(myArray[i].contains("Tuesday"))
                                {
                                    tues.setChecked(true);
                                    if(myArray[i].matches("Tuesday .*-"))
                                    {
                                        String temp = myArray[i].replace("Tuesday","").replace("-","");
                                        tues_am.setText(temp);
                                    }
                                    else if(myArray[i].matches("Tuesday -.*"))
                                    {
                                        String temp = myArray[i].replace("Tuesday","").replace("-","");
                                        tues_pm.setText(temp);
                                    }
                                    else if (myArray[i].matches("Tuesday .*-.*"))
                                    {
                                        String temp = myArray[i].replace("Tuesday ","").replace("-"," ");
                                        String[] temp2 = temp.split(" ");
                                        tues_am.setText(temp2[0]);
                                        tues_pm.setText(temp2[1]);
                                    }
                                }

                                //for Tuesday
                                if(myArray[i].contains("Wednesday"))
                                {
                                    wed.setChecked(true);
                                    if(myArray[i].matches("Wednesday .*-"))
                                    {
                                        String temp = myArray[i].replace("Wednesday","").replace("-","");
                                        wed_am.setText(temp);
                                    }
                                    else if(myArray[i].matches("Wednesday -.*"))
                                    {
                                        String temp = myArray[i].replace("Wednesday","").replace("-","");
                                        wed_pm.setText(temp);
                                    }
                                    else if (myArray[i].matches("Wednesday .*-.*"))
                                    {
                                        String temp = myArray[i].replace("Wednesday ","").replace("-"," ");
                                        String[] temp2 = temp.split(" ");
                                        wed_am.setText(temp2[0]);
                                        wed_pm.setText(temp2[1]);
                                    }
                                }

                                //for Thursday
                                if(myArray[i].contains("Thursday"))
                                {
                                    thu.setChecked(true);
                                    if(myArray[i].matches("Thursday .*-"))
                                    {
                                        String temp = myArray[i].replace("Thursday","").replace("-","");
                                        thu_am.setText(temp);
                                    }
                                    else if(myArray[i].matches("Thursday -.*"))
                                    {
                                        String temp = myArray[i].replace("Thursday","").replace("-","");
                                        thu_pm.setText(temp);
                                    }
                                    else if (myArray[i].matches("Thursday .*-.*"))
                                    {
                                        String temp = myArray[i].replace("Thursday ","").replace("-"," ");
                                        String[] temp2 = temp.split(" ");
                                        thu_am.setText(temp2[0]);
                                        thu_pm.setText(temp2[1]);
                                    }
                                }

                                //for Friday
                                if(myArray[i].contains("Friday"))
                                {
                                    fri.setChecked(true);
                                    if(myArray[i].matches("Friday .*-"))
                                    {
                                        String temp = myArray[i].replace("Friday","").replace("-","");
                                        fri_am.setText(temp);
                                    }
                                    else if(myArray[i].matches("Friday -.*"))
                                    {
                                        String temp = myArray[i].replace("Friday","").replace("-","");
                                        fri_pm.setText(temp);
                                    }
                                    else if (myArray[i].matches("Friday .*-.*"))
                                    {
                                        String temp = myArray[i].replace("Friday ","").replace("-"," ");
                                        String[] temp2 = temp.split(" ");
                                        fri_am.setText(temp2[0]);
                                        fri_pm.setText(temp2[1]);
                                    }
                                }

                                //for Saturday
                                if(myArray[i].contains("Saturday"))
                                {
                                    sat.setChecked(true);
                                    if(myArray[i].matches("Saturday .*-"))
                                    {
                                        String temp = myArray[i].replace("Saturday","").replace("-","");
                                        sat_am.setText(temp);
                                    }
                                    else if(myArray[i].matches("Saturday -.*"))
                                    {
                                        String temp = myArray[i].replace("Saturday","").replace("-","");
                                        sat_pm.setText(temp);
                                    }
                                    else if (myArray[i].matches("Saturday .*-.*"))
                                    {
                                        String temp = myArray[i].replace("Saturday ","").replace("-"," ");
                                        String[] temp2 = temp.split(" ");
                                        sat_am.setText(temp2[0]);
                                        sat_pm.setText(temp2[1]);
                                    }
                                }


                                i++;
                            }
                        }
                    }

                           // final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);


                                    //dialog.setTitle("Select Your Timing");
//                                    .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
//                                            if (isChecked) {
//                                                // If the user checked the item, add it to the selected items
//                                                if (!seletedItems.contains(items[indexSelected])) {
//                                                    seletedItems.add(items[indexSelected]);
//                                                }
//                                            }
//                                            if (!isChecked) {
//                                                seletedItems.remove(items[indexSelected]);
//                                            }
//                                        }
//                                    })
                                        //dialog.

                                     dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
//                                            String allTime="";
//                                            //  Your code when user clicked on OK
//                                            //  You can write the code  to save the selected item here
//                                            // Toast.makeText(getContext(), " "+seletedItems, Toast.LENGTH_SHORT).show();
//                                           // String times = String.valueOf(seletedItems).replace("[","").replace("]","");
//
//                                            //doctor_timing.setText(times);
//                                           // seletedItems.clear();
//                                            if(sun.isChecked()) {
//                                                if (!sun_am.getText().toString().trim().equals("") || !sun_pm.getText().toString().trim().equals("")) {
//                                                    allTime += "Sunday " + sun_am.getText().toString().trim() + "-" + sun_pm.getText().toString().trim() + " ; ";
//                                                } else
//                                                {
//                                                    Toast.makeText(getContext(), "Fill Times Field on Checked Days", Toast.LENGTH_SHORT).show();
//                                                return;
//                                            }
//                                            }
//                                            if(mon.isChecked())
//                                            {
//                                                if(!mon_am.getText().toString().trim().equals("") || !mon_pm.getText().toString().trim().equals(""))
//                                                {
//                                                    allTime+="Monday "+mon_am.getText().toString().trim() +"-" +mon_pm.getText().toString().trim()+" ; ";
//                                                }
//                                                else {
//                                                    Toast.makeText(getContext(), "Fill Times Field on Checked Days", Toast.LENGTH_SHORT).show();
//                                                    return;
//                                                }
//                                            }
//                                            doctor_timing.setText(allTime);
                                        }
                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Your code when user clicked on Cancel
                                        }
                                    });//.create();
                                //dialog.setCancelable(false);
                            //dialog.show();

                    final AlertDialog dialog1 = dialog.create();
                    dialog1.show();

                    dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Boolean wantToCloseDialog = false;
                            //Do stuff, possibly set wantToCloseDialog to true then...
                            String inputs[]=new String[7];
                            //  Your code when user clicked on OK
                            //  You can write the code  to save the selected item here
                            // Toast.makeText(getContext(), " "+seletedItems, Toast.LENGTH_SHORT).show();
                            // String times = String.valueOf(seletedItems).replace("[","").replace("]","");

                            //doctor_timing.setText(times);
                            // seletedItems.clear();


                            if(sun.isChecked()) {
                                if (!sun_am.getText().toString().trim().equals("") || !sun_pm.getText().toString().trim().equals(""))
                                {
                                    inputs[0] = "Sunday " + sun_am.getText().toString().trim() + "-" + sun_pm.getText().toString().trim() ;
                                    wantToCloseDialog = true;
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Fill Times Field on Checked Days", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }


                            if(mon.isChecked())
                            {
                                if(!mon_am.getText().toString().trim().equals("") || !mon_pm.getText().toString().trim().equals(""))
                                {
                                    inputs[1] = "Monday "+mon_am.getText().toString().trim() +"-" +mon_pm.getText().toString().trim();
                                    wantToCloseDialog = true;
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Fill Times Field on Checked Days", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            if(tues.isChecked())
                            {
                                if(!tues_am.getText().toString().trim().equals("") || !tues_pm.getText().toString().trim().equals(""))
                                {
                                    inputs[2] = "Tuesday "+tues_am.getText().toString().trim() +"-" +tues_pm.getText().toString().trim();
                                    wantToCloseDialog = true;
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Fill Times Field on Checked Days", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }


                            if(wed.isChecked())
                            {
                                if(!wed_am.getText().toString().trim().equals("") || !wed_pm.getText().toString().trim().equals(""))
                                {
                                    inputs[3] = "Wednesday "+wed_am.getText().toString().trim() +"-" +wed_pm.getText().toString().trim();
                                    wantToCloseDialog = true;
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Fill Times Field on Checked Days", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }


                            if(thu.isChecked())
                            {
                                if(!thu_am.getText().toString().trim().equals("") || !thu_pm.getText().toString().trim().equals(""))
                                {
                                    inputs[4] = "Thursday "+thu_am.getText().toString().trim() +"-" +thu_pm.getText().toString().trim();
                                    wantToCloseDialog = true;
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Fill Times Field on Checked Days", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            if(fri.isChecked())
                            {
                                if(!fri_am.getText().toString().trim().equals("") || !fri_pm.getText().toString().trim().equals(""))
                                {
                                    inputs[5] = "Friday "+fri_am.getText().toString().trim() +"-" +fri_pm.getText().toString().trim();
                                    wantToCloseDialog = true;
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Fill Times Field on Checked Days", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            if(sat.isChecked())
                            {

                                if(!sat_am.getText().toString().trim().equals("") || !sat_pm.getText().toString().trim().equals(""))
                                {
                                    inputs[6] = "Saturday "+sat_am.getText().toString().trim() +"-" +sat_pm.getText().toString().trim();
                                    wantToCloseDialog = true;
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Fill Times Field on Checked Days", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            List<String> list = new ArrayList<String>();

                            for(String s : inputs)
                            {
                                if(s !=null && s.length() > 0)
                                {
                                    list.add(s);
                                }
                            }

                            doctor_timing.setText(String.valueOf(list));
                            if(wantToCloseDialog)
                                dialog1.dismiss();
                            //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                        }
                    });
                }
            }

        });

            //For image
            mProgressDialog = new ProgressDialog(getContext());

            // change_image.setOnClickListener(this);
            change_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // if(checkS){}

                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getContext(), "Give Read Permission", Toast.LENGTH_SHORT).show();
                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                    } else {

                        callgalary();
                    }
                }
            });

            doctor_save_button = (Button) v.findViewById(R.id.profile_save_button);
            mdatabaseRef = FirebaseDatabase.getInstance().getReference();

            FirebaseUser user = firebaseAuth.getCurrentUser();
            UserID = user.getEmail().replace("@", "").replace(".", "");
            mRoofRef = new Firebase("https://patient-management-11e26.firebaseio.com/").child("Doctor_Detais").child(UserID);

            //For Update UserDetails Database Table for Doctors.
            mRoofRef_doctor = new Firebase("https://patient-management-11e26.firebaseio.com/").child("User_Details").child(UserID);

            mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://patient-management-11e26.appspot.com/");

            mRoofRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Map<String, String> map = dataSnapshot.getValue(Map.class);

                    String retrieve_name = map.get("Name");
                    String retrieve_phone = map.get("Phone");
                    String retrieve_chamber = map.get("Chamber");
                    String retrieve_Specialization = map.get("Specialization");
                    String retrieve_Experience = map.get("Experience");
                    String retrieve_Fees = map.get("Fees");
                    String retrieve_timing = map.get("Timing");
                    String retrieve_url = map.get("Image_URL");

                    Log.d("LOGGED", "onDataChange: v.context " + v.getContext());

                    if (retrieve_name.matches("Null"))
                        doctor_name.setText("");
                    else
                        doctor_name.setText(retrieve_name);


                    if (retrieve_phone.matches("Null"))
                        doctor_phone.setText("");
                    else
                        doctor_phone.setText(retrieve_phone);


                    if (retrieve_chamber.matches("Null"))
                        doctor_chamber.setText("");
                    else
                        doctor_chamber.setText(retrieve_chamber);


                    if (retrieve_Specialization.matches("Null"))
                        doctor_Specialization.setText("");
                    else
                        doctor_Specialization.setText(retrieve_Specialization);


                    if (retrieve_Experience.matches("Null"))
                        doctor_Experience.setText("");
                    else
                        doctor_Experience.setText(retrieve_Experience);


                    if (retrieve_Fees.matches("Null"))
                        doctor_Fees.setText("");
                    else
                        doctor_Fees.setText(retrieve_Fees);


                    if (retrieve_timing.matches("Null"))
                        doctor_timing.setText("");
                    else
                        doctor_timing.setText(retrieve_timing);


                    if (retrieve_url.matches("Null"))
                        Picasso.with(context).load(R.drawable.invalid_person_image).into(doctor_image);

                        //Glide Gives Context Errors :(
//                        Glide.with(context)
//                                .load(R.drawable.invalid_person_image)
//                                .crossFade()
//                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                                .into(user_image);
                    else
                        Picasso.with(getContext()).load(retrieve_url).into(doctor_image);

                    //Glide Gives Context Errors :(
//                        Glide.with(v.getContext())
//                                .load(retrieve_url)
//                                .crossFade()
//                                .placeholder(R.drawable.loading)
//                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                                .into(user_image);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            doctor_save_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String mName = doctor_name.getText().toString().trim();
                    final String mPhone = doctor_phone.getText().toString().trim();
                    final String mChamber = doctor_chamber.getText().toString().trim();
                    final String mSpecialization = doctor_Specialization.getText().toString().trim();
                    final String mExperience = doctor_Experience.getText().toString().trim();
                    final String mFees = doctor_Fees.getText().toString().trim();
                    final String mTiming = doctor_timing.getText().toString().trim().replace("[","").replace("]","");
                    if ((mName.isEmpty() || mPhone.isEmpty() || mChamber.isEmpty() || mSpecialization.isEmpty() || mExperience.isEmpty() || mFees.isEmpty() || mTiming.isEmpty())) {
                        Toast.makeText(getContext(), "Fill all Field", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mRoofRef.child("Name").setValue(mName);

                    mRoofRef.child("Phone").setValue(mPhone);

                    mRoofRef.child("Chamber").setValue(mChamber);

                    mRoofRef.child("Specialization").setValue(mSpecialization);

                    mRoofRef.child("Experience").setValue(mExperience);

                    mRoofRef.child("Fees").setValue(mFees);



                    mRoofRef.child("Timing").setValue(mTiming);

                    Toast.makeText(getActivity(), "Information Updated ", Toast.LENGTH_SHORT).show();



                    mRoofRef_doctor.child("Name").setValue(mName);

                }
            });


            return v;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            mImageUri = data.getData();
            doctor_image.setImageURI(mImageUri);
            StorageReference filePath = mStorage.child("Doctor_Images").child(mImageUri.getLastPathSegment());

            //the Progress bar Should be Here
            mProgressDialog.setMessage("Uploading Image....");
            mProgressDialog.show();

            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     Uri downloadUri = taskSnapshot.getDownloadUrl();
                    mRoofRef.child("Image_URL").setValue(downloadUri.toString());


                    //mRoofRef_doctor = new Firebase("https://patient-management-11e26.firebaseio.com/").child("User_Details").child(UserID);
                    mRoofRef_doctor.child("Image_URL").setValue(downloadUri.toString());

                    Glide.with(getContext())
                                .load(downloadUri)
                                .crossFade()
                                .placeholder(R.drawable.loading)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .into(doctor_image);

                    //Picasso.with(getContext()).load(downloadUri).into(user_image);
                    Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), "...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.clear(doctor_image);
        Glide.get(getActivity()).clearMemory();
    }


    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }
}

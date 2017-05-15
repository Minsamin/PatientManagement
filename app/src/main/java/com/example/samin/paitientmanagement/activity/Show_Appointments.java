package com.example.samin.paitientmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.transition.Visibility;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Show_Appointments extends AppCompatActivity {

    //public FirebaseAuth firebaseAuth;
    public String UserID,Patient_Email_ID;
    DatabaseReference myRef,myRef2,myRef3,myRef4,root_Ref;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder> mFirebaseAdapter;
    //Boolean flag=false;
    ProgressBar progressBar;
    ImageView noDataAvailableImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_appointment_layout);
        noDataAvailableImage = (ImageView)findViewById(R.id.no_data_available_image);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.show_appointment_appBarLayout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setTitle("Your Appointments");
            actionBar.setTitle(Html.fromHtml("<font color=#FFFFFF>" + "Your Appointments" + "</font>"));
           // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
       // Log.d("LOGGED", "----------------------- START NEW --------------------------");

        //Send a Query to database
        //firebaseAuth = FirebaseAuth.getInstance();
       // FirebaseUser user = firebaseAuth.getCurrentUser();
        //Log.d("LOGGED ", " USER " + user.toString());

        root_Ref = FirebaseDatabase.getInstance().getReference();

        progressBar = (ProgressBar)findViewById(R.id.progressBar3);

        UserID = MainActivity.LoggedIn_User_Email.replace("@", "").replace(".", "");
        myRef = FirebaseDatabase.getInstance().getReference("User_Appointment").child(UserID);
        myRef2 = FirebaseDatabase.getInstance().getReference("Doctor_Appointment").child(MainActivity.LoggedIn_User_Email.replace("@", "").replace(".", ""));
        myRef.keepSynced(true);
        myRef2.keepSynced(true);
        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.show_appointment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Log.d("LOGGED", "ADAPTER 1st " + adapter);
        Toast.makeText(this, "Wait ! Fetching data...", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }







    @Override
    protected void onStart() {
        super.onStart();
        // Log.d("LOGGED", "IN onStart ");

        if (MainActivity.app_user_type.equals("Patient"))
        {
            mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder>(Show_appointment_data_item.class, R.layout.show_appointment_single_item, MyViewHolder.class, myRef) {
                //            @Override
//            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                Log.d("LOGGED", " onStart-Called 4m inside " );
//
//                ViewGroup view = (ViewGroup)
//                        LayoutInflater.from(parent.getContext())
//                                .inflate(R.layout.show_appointment_single_item, parent, false);
//                return super.onCreateViewHolder(view, viewType);
//            }
                public void populateViewHolder(final MyViewHolder viewHolder, Show_appointment_data_item model, final int position) {
                    //progressBar.setVisibility(ProgressBar.INVISIBLE);
                    //flag = true;
                    viewHolder.setDoctor_URL(model.getAppointment_Doctor_Email());
                    viewHolder.setAppointment_Doctor_Name(model.getAppointment_Doctor_Name());
                    viewHolder.setAppointment_Doctor_Email(model.getAppointment_Doctor_Email());
                    viewHolder.setAppointment_Doctor_Phone(model.getAppointment_Doctor_phone());
                    viewHolder.setAppointment_Patient_Name(model.getPatient_Name());
                    viewHolder.setAppointment_Patient_Phone(model.getPatient_Phone());
                    viewHolder.setAppointment_Date(model.getAppointment_Date());
                    viewHolder.setAppointment_Reason(model.getAppointment_Reason());
                    viewHolder.setAppointment_Request_Status(model.getRequest_Status());


                    //Log.d("LOGGED", "populateViewHolder: Called ");
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(final View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Show_Appointments.this);
                            builder.setMessage("Do you want to Delete the Appointment ?").setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mFirebaseAdapter.getRef(position).removeValue();
                                            mFirebaseAdapter.notifyItemRemoved(position);
                                            recyclerView.invalidate();
                                            onStart();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.setTitle("Confirm");
                            dialog.show();
                        }
                    });


                }
            };
            //Log.d("LOGGED", "Setting Adapter ");
            recyclerView.setAdapter(mFirebaseAdapter);


            myRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    //Log.d("LOGGED", "I Called Yahoo ");
                    if(dataSnapshot.hasChildren())
                    {
                        //Log.d("LOGGED", "Boolean value true: ");
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    else {
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        noDataAvailableImage.setVisibility(View.VISIBLE);
                        Toast.makeText(Show_Appointments.this, "Data Unavailable", Toast.LENGTH_SHORT).show();
                        //Log.d("LOGGED", "Boolean value false: ");

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }






        else if (MainActivity.app_user_type.equals("Doctor"))
        {
            mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder>(Show_appointment_data_item.class, R.layout.show_appointment_single_item_doctor, MyViewHolder.class, myRef2) {


                @Override
                public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                    //Log.d("LOGGED", "onCreateViewHolder: Called ");
                    return super.onCreateViewHolder(parent, viewType);


                }

                public void populateViewHolder(final MyViewHolder viewHolder, Show_appointment_data_item model, final int position) {
                    //flag = true;

                    //progressBar.setVisibility(ProgressBar.INVISIBLE);

                    viewHolder.setAppointment_Patient_Name(model.getPatient_Name());
                    viewHolder.setAppointment_Patient_Phone(model.getPatient_Phone());
                    viewHolder.setAppointment_Date(model.getAppointment_Date());
                    viewHolder.setAppointment_Reason(model.getAppointment_Reason());
                    final String PATIENT_EMAIL = model.getPatient_Email();
                    //final String DOCTOR_EMAIL = model.getAppointment_Doctor_Email();
                    viewHolder.setPatient_Image(model.getPatient_Email());
                    viewHolder.setAppointment_Request_Status_doctor(model.getRequest_Status());


                    //Log.d("LOGGED", "populateViewHolder: Called ");
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(final View v) {
                                final CharSequence[] options = { "Show Patient Profile", "Approve Appointment","Send a Message","Delete Appointment"};

                                AlertDialog.Builder builder = new AlertDialog.Builder(Show_Appointments.this);
                                builder.setTitle("Manage Appointment !");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int item) {
                                        if (options[item].equals("Show Patient Profile")) {
                                            //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            //File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                                            //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                                            //startActivityForResult(intent, 1);

                                            myRef3 = FirebaseDatabase.getInstance().getReference().child("User_Details").child(PATIENT_EMAIL.replace("@", "").replace(".", ""));
                                            myRef3.keepSynced(true);
                                            myRef3.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                @Override
                                                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                    Intent intent = new Intent(Show_Appointments.this, PatientProfile.class);

                                                    String retrieve_Address = dataSnapshot.child("Address").getValue(String.class);
                                                    String retrieve_name = dataSnapshot.child("Name").getValue(String.class);
                                                    String retrieve_Phone = dataSnapshot.child("Phone").getValue(String.class);
                                                    String retrieve_Age = dataSnapshot.child("Age").getValue(String.class);
                                                    String retrieve_Height = dataSnapshot.child("Height").getValue(String.class);
                                                    String retrieve_Weight = dataSnapshot.child("Weight").getValue(String.class);
                                                    String retrieve_Bloodgroup = dataSnapshot.child("Bloodgroup").getValue(String.class);
                                                    String retrieve_Image = dataSnapshot.child("Image_URL").getValue(String.class);

                                                    intent.putExtra("Name", retrieve_name);
                                                    intent.putExtra("Address", retrieve_Address);
                                                    intent.putExtra("Phone", retrieve_Phone);
                                                    intent.putExtra("Age", retrieve_Age);
                                                    intent.putExtra("Height", retrieve_Height);
                                                    intent.putExtra("Weight", retrieve_Weight);
                                                    intent.putExtra("Bloodgroup", retrieve_Bloodgroup);
                                                    intent.putExtra("Image", retrieve_Image);
                                                    intent.putExtra("Email", PATIENT_EMAIL);

                                                    startActivity(intent);

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                }
                                            });
                                            // }
                                            // @Override
                                            //  public void onCancelled(DatabaseError databaseError) {
                                            //  }
                                            //  });
                                        }
                                        else if (options[item].equals("Approve Appointment"))
                                        {
                                            //Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            //startActivityForResult(intent, 2);
                                            // myRef3 = FirebaseDatabase.getInstance().getReference().child("User_Appointment").child(PATIENT_EMAIL.replace("@","").replace(".",""));
                                            //mFirebaseAdapter.getRef(position)
                                            //Log.d("LOGGED", "Doctor Database Reference " + mFirebaseAdapter.getRef(position));

                                            myRef3 = mFirebaseAdapter.getRef(position);
                                            myRef3.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                @Override
                                                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                    final String Doctors_Request_Status =dataSnapshot.child("Request_Status").getValue(String.class);
                                                    final String Patient_EmailID = dataSnapshot.child("Patient_Email").getValue(String.class).replace("@", "").replace(".", "");
                                                    final String Doctors_Approval_Tracking = dataSnapshot.child("Approval_Tracking").getValue(String.class);
                                                    Patient_Email_ID = dataSnapshot.child("Patient_Email").getValue(String.class);

                                                    if(Doctors_Request_Status.equals("Pending"))
                                                    {
                                                        Toast.makeText(Show_Appointments.this, "Appointment Approved", Toast.LENGTH_SHORT).show();
                                                        myRef3.child("Request_Status").setValue("Approve");
                                                        call2nd( Patient_EmailID, Doctors_Approval_Tracking);


                                                    }

                                                    else {
                                                        Toast.makeText(Show_Appointments.this, "Already Approved", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        else if (options[item].equals("Send a Message"))
                                        {
                                            //Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            //startActivityForResult(intent, 2);
                                            Toast.makeText(Show_Appointments.this, "Will Implement Later", Toast.LENGTH_SHORT).show();


                                        }
                                        else if (options[item].equals("Delete Appointment")) {
                                            Toast.makeText(Show_Appointments.this, "Delete Appointment", Toast.LENGTH_SHORT).show();
                                            //dialog.dismiss();
                                            mFirebaseAdapter.getRef(position).removeValue();
                                            mFirebaseAdapter.notifyItemRemoved(position);
                                            recyclerView.invalidate();
                                            onStart();
                                        }
                                    }
                                });
                                builder.show();
                        }
                    });
                }
            };
            //Log.d("LOGGED", "Setting Adapter ");
            recyclerView.setAdapter(mFirebaseAdapter);

            myRef2.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    //Log.d("LOGGED", "I Called Yahoo ");
                    if(dataSnapshot.hasChildren())
                    {
                        //Log.d("LOGGED", "Boolean value true: ");
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    else {
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        noDataAvailableImage.setVisibility(View.VISIBLE);
                        Toast.makeText(Show_Appointments.this, "Data Unavailable", Toast.LENGTH_SHORT).show();
                        //Log.d("LOGGED", "Boolean value false: ");

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }




    public void call2nd(final String Patient_EmailID, final String Doctors_Approval_Tracking)
    {
        myRef4= root_Ref.child("User_Appointment").child(Patient_EmailID);
        myRef4.keepSynced(true);

        myRef4.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (final com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("Approval_Tracking").getValue().equals(Doctors_Approval_Tracking))
                    {
                        final DatabaseReference snapReference = snapshot.getRef();
                        snapReference.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                //final String Approval_Tracking = map.get("Approval_Tracking");
                                //Log.e("REFERENCE " ,""+snapReference);
                                snapReference.child("Request_Status").setValue("Approved");

                                SimpleDateFormat current_date_format = new SimpleDateFormat( "dd/MM/yyyy" );
                                SimpleDateFormat current_time_format = new SimpleDateFormat("h:mm a");

                                String Doctor_Email = snapshot.child("Appointment_Doctor_Email").getValue().toString();

                                final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("User_Notification").child(Patient_EmailID).push();
                                mRootRef.child("Notification_Date").setValue(current_date_format.format( new Date()));
                                mRootRef.child("Notification_Time").setValue(current_time_format.format( new Date()));
                                mRootRef.child("Notification_To").setValue(Patient_Email_ID);
                                mRootRef.child("Doctor_Email").setValue(Doctor_Email);


                                DatabaseReference mRootRef2 = FirebaseDatabase.getInstance().getReference().child("Doctor_Detais").child(Doctor_Email.replace("@", "").replace(".", ""));
                                mRootRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String Doctor_Image = dataSnapshot.child("Image_URL").getValue(String.class);
                                        String Doctor_Name = dataSnapshot.child("Name").getValue(String.class);
                                        mRootRef.child("Notification_Image").setValue(Doctor_Image);
                                        mRootRef.child("Notification_Text").setValue("Your Appointment is Approved by " + Doctor_Name);
                                        send_notification();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });




                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void send_notification()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);


                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic MmRkOGI3ODAtYzcwNi00ZmRhLWFiYjItMzZhMTdiNzY1YTBl");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"d0bb9a8d-ae7a-4eef-b4f1-4b7b40992263\","

                                + "\"filters\": [{\"field\": \"tag\"," +
                                " \"key\": \"User_ID\"," +
                                " \"relation\": \"=\", " +
                                "\"value\": \"" + Patient_Email_ID + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"headings\": {\"en\": \"Your Appointment is Approved\"},"
                                //+ "\"big_picture\": \"" + R.drawable.logo_pms + "\","
                                + "\"large_icon\": \"" + R.drawable.logo_pms + "\","
                                + "\"android_led_color\": \"#3949AB\","
                                + "\"android_accent_color\": \"#3949AB\","

                                + "\"android_background_layout\": " +
                               // "{\"image\": \"http://3.bp.blogspot.com/-5GNtI62kFFw/U4DK7M_fNkI/AAAAAAAAADA/nvF-d_CfBsg/s1600/wp917d5eab_06.png\","
                                 "{\"headings_color\": \"9C27B0\"," +
                                "\"contents_color\": \"00695C\"},"

                                + "\"contents\": {\"en\": \"Check your Appointment Section for Further Details.\"}"
                                + "}";

                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_appointment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            case R.id.action_refresh:
                recyclerView.invalidate();
                onStart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }









    //View Holder For Recycler View
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView post_doctor_name, post_doctor_email, post_doctor_phone, post_patient_name, post_patient_phone, post_appointment_date, post_appointment_reason;
        private final ImageView post_doctor_image,post_appointment_status,post_appointment_status_doctor;
        View mView;
        //Firebase mRoofRef;
        RecyclerView.ViewHolder vv;

        public MyViewHolder(final View itemView) {

            super(itemView);
            Log.d("LOGGED", "ViewHolder Called: " );
            mView = itemView;
            post_doctor_name = (TextView) mView.findViewById(R.id.fetch_doctor_name);
            post_doctor_email = (TextView) mView.findViewById(R.id.fetch_doctor_email);
            post_doctor_phone = (TextView) mView.findViewById(R.id.fetch_doctor_phone);
            post_patient_name = (TextView) mView.findViewById(R.id.fetch_patient_name);
            post_patient_phone = (TextView) mView.findViewById(R.id.fetch_patient_phone);
            post_appointment_date = (TextView) mView.findViewById(R.id.fetch_Appointment_date);
            post_appointment_reason = (TextView) mView.findViewById(R.id.fetch_Appointment_reason);
            post_doctor_image = (ImageView) mView.findViewById(R.id.show_appointment_doctor_image);
            post_appointment_status = (ImageView) mView.findViewById(R.id.fetch_doctor_approval);
            post_appointment_status_doctor = (ImageView) mView.findViewById(R.id.show_appointment_appointment_status);

        }



        private void setAppointment_Doctor_Name(String title) {
            post_doctor_name.setText(title);
            //Log.d("LOGGED", "Doctor_Name: " + title);
        }

        private void setAppointment_Doctor_Phone(String title) {
            post_doctor_phone.setText(title);

        }

        private void setAppointment_Doctor_Email(String title) {
            post_doctor_email.setText(title);
            //Log.d("LOGGED", "Doctor_Email: " + title);
        }

        private void setAppointment_Patient_Name(String title) {
            post_patient_name.setText(title);
        }

        private void setAppointment_Patient_Phone(String title) {
            post_patient_phone.setText(title);
        }

        private void setAppointment_Request_Status(String title) {
            if(title.equals("Pending"))
            {
                Glide.with(itemView.getContext()).load(R.drawable.pending_icon)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(post_appointment_status);
            }
            else
            {
                Glide.with(itemView.getContext()).load(R.drawable.approve_icon)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(post_appointment_status);
            }
        }

        private void setAppointment_Date(String title) {
            post_appointment_date.setText(title);
        }

        private void setAppointment_Reason(String title) {
            post_appointment_reason.setText(title);
        }

        private void setDoctor_URL(String url) {

            if (url != null) {
                String UserID = url.replace("@", "").replace(".", "");
                //mRoofRef = new Firebase("https://patient-management-11e26.firebaseio.com/Doctor_Detais").child(UserID);
                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("Doctor_Detais").child(UserID);
                mRootRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        //Map<String, String> map = dataSnapshot.getValue(Map.class);

                        String retrieve_url = dataSnapshot.child("Image_URL").getValue(String.class);
//                        Picasso.with(itemView.getContext())
//                                .load(retrieve_url)
//                                .placeholder(R.drawable.loading)
//                                .into(post_doctor_image);

                        Glide.with(itemView.getContext())
                                .load(retrieve_url)
                                .crossFade()
                                .placeholder(R.drawable.loading)
                                .thumbnail(0.1f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(post_doctor_image);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//                mRootRef.addValueEventListener(new ValueEventListener() {
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
            }
        }

        private void setPatient_Image(String url) {

            if (url != null) {
                String UserID = url.replace("@", "").replace(".", "");

                //mRoofRef = FirebaseDatabase.getInstance().getReference().child("User_Details").child(UserID);
                //mRoofRef = new Firebase("https://patient-management-11e26.firebaseio.com/User_Details").child(UserID);
                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("User_Details").child(UserID);
                mRootRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        //Map<String, String> map = dataSnapshot.getValue(Map.class);
                        String retrieve_url = dataSnapshot.child("Image_URL").getValue(String.class);
//                        Picasso.with(itemView.getContext())
//                                .load(retrieve_url)
//                                .placeholder(R.drawable.loading)
//                                .into(post_doctor_image);

                        Glide.with(itemView.getContext())
                                .load(retrieve_url)
                                .crossFade()
                                .placeholder(R.drawable.invalid_person_image)
                                .thumbnail(0.5f)
                                .bitmapTransform(new CircleTransform(itemView.getContext()))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(post_doctor_image);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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
//                        Glide.with(itemView.getContext())
//                                .load(retrieve_url)
//                                .crossFade()
//                                .placeholder(R.drawable.invalid_person_image)
//                                .thumbnail(0.5f)
//                                .bitmapTransform(new CircleTransform(itemView.getContext()))
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(post_doctor_image);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//                        //Nothing
//                    }
//                });
            } else {
                //Log.d("LOGGED", "I GOT THE URL " + url);
            }
        }
        private void setAppointment_Request_Status_doctor(String title) {

            if(title.equals("Pending"))
            {
                Glide.with(itemView.getContext()).load(R.drawable.pending_icon)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(post_appointment_status_doctor);
            }
            else
            {
                Glide.with(itemView.getContext()).load(R.drawable.approve_icon)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(post_appointment_status_doctor);
            }
        }
    }
}

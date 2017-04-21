package com.example.samin.paitientmanagement.fragment;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.activity.MainActivity;
//import com.example.samin.paitientmanagement.other.TransformerAdapter;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    Button notification;
    //private Handler mHandler;

    TextView heading1,heading2,tv_1,tv_2,tv_3,tv_4,tv_5,tv_6;
    private SliderLayout mDemoSlider;
    Button get_started_button;
    //private DrawerLayout drawer;
    public HomeFragment() {
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
        View v;
//    if(MainActivity.user_designation=="Doctor")
//         v = inflater.inflate(R.layout.fragment_notification, container, false);
//        else
         v = inflater.inflate(R.layout.fragment_home, container, false);
        //notification= (Button)v.findViewById(R.id.notification_test);

//        heading1=(TextView)v.findViewById(R.id.tv_heading1);
//        heading2=(TextView)v.findViewById(R.id.tv_heading2);
//        tv_1=(TextView)v.findViewById(R.id.tv_text1);
//        tv_2=(TextView)v.findViewById(R.id.tv_text2);
//        tv_3=(TextView)v.findViewById(R.id.tv_text3);
//        tv_4=(TextView)v.findViewById(R.id.tv_text4);
//        tv_5=(TextView)v.findViewById(R.id.tv_text5);
//        tv_6=(TextView)v.findViewById(R.id.tv_text6);
//
//        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-BoldItalic.ttf");
//        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Light.ttf");
//        heading1.setTypeface(font);
//        heading2.setTypeface(font);
//        tv_1.setTypeface(font2);
//        tv_2.setTypeface(font2);
//        tv_3.setTypeface(font2);
//        tv_4.setTypeface(font2);
//        tv_5.setTypeface(font2);
//        tv_6.setTypeface(font2);

        mDemoSlider = (SliderLayout)v.findViewById(R.id.slider);
        //drawer = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        // getActivity().findViewById(R.id.drawer_layout);

//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");


        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Online Medical",R.drawable.banner_1);
        file_maps.put("Doctors",R.drawable.banner_2);
        file_maps.put("Blood Donation",R.drawable.banner_3);
        //file_maps.put("Game of Thrones", R.drawable.invalid_person_image);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);  //Setting Transition
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        get_started_button =(Button)v.findViewById(R.id.get_started_button);
        get_started_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.app_user_type.equals("Doctor") || MainActivity.app_user_type.equals("Patient"))
                    Toast.makeText(getContext(), "Swipe from Left-Edge to right !", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Wait...", Toast.LENGTH_SHORT).show();


                //drawer.openDrawer(GravityCompat.START);
                //(MainActivity)getActivity().openDrawer();
            }
        });


        //if(MainActivity.app_user_type.equals("Doctor") || MainActivity.app_user_type.equals("Patient"))
        //{
            //drawer.openDrawer(drawer);
       // }

//        ListView l = (ListView)v.findViewById(R.id.transformers);
//        l.setAdapter(new TransformerAdapter(getContext()));
//        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
//                Toast.makeText(getContext(), ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });







//        notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendNotification();
//            }
//        });

//        txt = (TextView)v.findViewById(R.id.home_txt1);
//        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
//        txt.setTypeface(font);
        return v;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
       // Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}


//    private void sendNotification()
//    {
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                int SDK_INT = android.os.Build.VERSION.SDK_INT;
//                if (SDK_INT > 8) {
//                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                            .permitAll().build();
//                    StrictMode.setThreadPolicy(policy);
//                    String send_email;
//                    if (MainActivity.LoggedIn_User_Email.equals("user3@gmail.com")) {
//                        send_email = "doctor3@gmail.com";
//                    } else {
//                        send_email = "user3@gmail.com";
//                    }
//
//                    try {
//                        String jsonResponse;
//
//                        URL url = new URL("https://onesignal.com/api/v1/notifications");
//                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                        con.setUseCaches(false);
//                        con.setDoOutput(true);
//                        con.setDoInput(true);
//
//                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                        con.setRequestProperty("Authorization", "Basic MmRkOGI3ODAtYzcwNi00ZmRhLWFiYjItMzZhMTdiNzY1YTBl");
//                        con.setRequestMethod("POST");
//
//                        String strJsonBody = "{"
//                                + "\"app_id\": \"d0bb9a8d-ae7a-4eef-b4f1-4b7b40992263\","
//
//                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"
//
//                                + "\"data\": {\"foo\": \"bar\"},"
//                                + "\"contents\": {\"en\": \"English Message\"}"
//                                + "}";
//
//
//                        System.out.println("strJsonBody:\n" + strJsonBody);
//
//                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
//                        con.setFixedLengthStreamingMode(sendBytes.length);
//
//                        OutputStream outputStream = con.getOutputStream();
//                        outputStream.write(sendBytes);
//
//                        int httpResponse = con.getResponseCode();
//                        System.out.println("httpResponse: " + httpResponse);
//
//                        if (httpResponse >= HttpURLConnection.HTTP_OK
//                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
//                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
//                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
//                            scanner.close();
//                        } else {
//                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
//                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
//                            scanner.close();
//                        }
//                        System.out.println("jsonResponse:\n" + jsonResponse);
//
//                    } catch (Throwable t) {
//                        t.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
}

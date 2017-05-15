package com.example.samin.paitientmanagement.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.samin.paitientmanagement.R;

import java.io.File;

/**
 * Created by Samin on 16-05-2017.
 */

public class EnlargeImageView extends AppCompatActivity {
    ImageView image;
    Button download;
    String url;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.enlarge_image_layout);
        image = (ImageView)findViewById(R.id.fetch_enlarge_image);
        download = (Button)findViewById(R.id.image_download_button);
        progressBar = (ProgressBar)findViewById(R.id.enlarge_image_progress_bar);
        progressBar.setVisibility(ProgressBar.VISIBLE);




        url =  getIntent().getStringExtra("url");

        Glide.with(getApplicationContext())
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        })
                .crossFade()
                .fitCenter()
                .into(image);

        download.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //Sender_Name = getIntent().getStringExtra("name");
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
    }
}

//                DownloadManager mManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                DownloadManager.Request mRqRequest = new DownloadManager.Request(
//                        Uri.parse(url));
//                //final File destinationDir = new File(Environment.getExternalStorageDirectory(), getPackageName());
//               // if (!destinationDir.exists()) {
//                  //  destinationDir.mkdir(); // Don't forget to make the directory if it's not there
//                //}
//                mRqRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download");
//                mRqRequest.setDescription("Downloading Image");
//
//                //File destinationFile = new File (destinationDir, url);
//               // mRqRequest.setDestinationUri(Uri.fromFile(destinationFile));
//                //mRqRequest.setDestinationUri(Uri.parse("/sdcard/Download"));
//                mManager.enqueue(mRqRequest);
//            }
//        });


   // }
//}

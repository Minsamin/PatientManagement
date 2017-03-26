package com.example.samin.paitientmanagement.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.BuildConfig;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.other.CircleTransform;

/**
 * Created by samin on 11/4/2016.
 */
public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener{
    TextView version,mailus,github;
    ImageView img_samin,img_nag,img_huduk,img_sudipta;
    TextView fb_sam,g_sam,t_sam;
    TextView fb_nag,g_nag,t_nag;
    TextView fb_huduk,g_huduk,t_huduk;
    TextView fb_sudipta,g_sudipta,t_sudipta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.about_us_layout);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));


        version=(TextView) findViewById(R.id.show_version_about_us_page);
        mailus=(TextView) findViewById(R.id.mail_us_about_us);
        github=(TextView) findViewById(R.id.git_hub);

        img_samin=(ImageView)findViewById(R.id.about_image_samin);
        img_huduk=(ImageView)findViewById(R.id.about_image_huduk);
        img_nag=(ImageView)findViewById(R.id.about_image_nag);
        img_sudipta=(ImageView)findViewById(R.id.about_image_sudipta);

        //Social Media - Samin
        fb_sam = (TextView)findViewById(R.id.tv_facebook_samin);
        g_sam = (TextView)findViewById(R.id.tv_google_plus_samin);
        t_sam = (TextView)findViewById(R.id.tv_twitter_samin);

        //Social Media - Nag
        fb_nag = (TextView)findViewById(R.id.tv_facebook_nag);
        g_nag = (TextView)findViewById(R.id.tv_google_plus_nag);
        t_nag = (TextView)findViewById(R.id.tv_twitter_nag);

        //Social Media - Huduk
        fb_huduk = (TextView)findViewById(R.id.tv_facebook_huduk);
        g_huduk = (TextView)findViewById(R.id.tv_google_plus_huduk);
        t_huduk = (TextView)findViewById(R.id.tv_twitter_huduk);

        //Social Media - Sudipta
        fb_sudipta = (TextView)findViewById(R.id.tv_facebook_sudipta);
        g_sudipta = (TextView)findViewById(R.id.tv_google_plus_sudipta);
        t_sudipta = (TextView)findViewById(R.id.tv_twitter_sudipta);


        //OnClick Listeners
        fb_sam.setOnClickListener(this);
        g_sam.setOnClickListener(this);
        t_sam.setOnClickListener(this);

        fb_nag.setOnClickListener(this);
        g_nag.setOnClickListener(this);
        t_nag.setOnClickListener(this);

        fb_huduk.setOnClickListener(this);
        g_huduk.setOnClickListener(this);
        t_huduk.setOnClickListener(this);

        fb_sudipta.setOnClickListener(this);
        g_sudipta.setOnClickListener(this);
        t_sudipta.setOnClickListener(this);





        version.setText("Version: " + BuildConfig.VERSION_NAME +"");

        mailus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "saminali500@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from PatientManagement  App");
                intent.putExtra(Intent.EXTRA_TEXT, "Hi, ");
                startActivity(intent);
            }
        });



        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String git="https://github.com/Minsamin/PatientManagement";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(git));
//                startActivity(i);
                Toast.makeText(AboutUsActivity.this, "Disabled Link for Security Reason..", Toast.LENGTH_SHORT).show();
            }
        });


        Glide.with(AboutUsActivity.this)
                .load(R.drawable.samin_image)
                .crossFade()
                .thumbnail(0.5f)
                .placeholder(R.drawable.invalid_person_image)
                .bitmapTransform(new CircleTransform(AboutUsActivity.this))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img_samin);

         Glide.with(AboutUsActivity.this)
                .load(R.drawable.huduk_image)
                .crossFade()
                .thumbnail(0.5f)
                .placeholder(R.drawable.invalid_person_image)
                .bitmapTransform(new CircleTransform(AboutUsActivity.this))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img_huduk);

         Glide.with(AboutUsActivity.this)
                .load(R.drawable.invalid_person_image)
                .crossFade()
                .thumbnail(0.5f)
                .placeholder(R.drawable.invalid_person_image)
                .bitmapTransform(new CircleTransform(AboutUsActivity.this))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img_nag);

         Glide.with(AboutUsActivity.this)
                .load(R.drawable.invalid_person_image)
                .crossFade()
                .thumbnail(0.5f)
                .placeholder(R.drawable.invalid_person_image)
                .bitmapTransform(new CircleTransform(AboutUsActivity.this))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img_sudipta);



    }

    @Override
    public void onClick(View v) {
        //SAM
        if (v.equals(g_sam)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://plus.google.com/u/0/+SaminAliMondal"));
            startActivity(i);
            return;
        }
        if (v.equals(fb_sam)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.facebook.com/XJsam"));
            startActivity(i);
            return;

        }
        if (v.equals(t_sam)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/SAMIN500"));
            startActivity(i);
            return;

        }

        //NAG
        if (v.equals(g_nag)) {
            Toast.makeText(this, "Not Available ! ", Toast.LENGTH_SHORT).show();
            return;

        }
        if (v.equals(fb_nag)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.facebook.com/profile.php?id=100001333209563"));
            startActivity(i);
            return;

        }
        if (v.equals(t_nag)) {
            Toast.makeText(this, "Not Available ! ", Toast.LENGTH_SHORT).show();
            return;

        }

        //HUDUK

        if (v.equals(g_huduk)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://plus.google.com/u/0/+SnehasishHuduk2012"));
            startActivity(i);
            return;

        }
        if (v.equals(fb_huduk)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.facebook.com/snehasish.huduk"));
            startActivity(i);
            return;

        }
        if (v.equals(t_huduk)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/snehasish_huduk"));
            startActivity(i);
            return;

        }

        //SUDIPTA

        if (v.equals(g_sudipta)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://plus.google.com/u/0/118352442763165149285"));
            startActivity(i);
            return;

        }
        if (v.equals(fb_sudipta)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.facebook.com/sudiptahaldercse"));
            startActivity(i);
            return;

        }
        if (v.equals(t_sudipta)) {
            Toast.makeText(this, "Not Available ! ", Toast.LENGTH_SHORT).show();

        }
    }
}

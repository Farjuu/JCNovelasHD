package com.jcnovelashd.novelasgratis.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jcnovelashd.novelasgratis.R;
import com.jcnovelashd.novelasgratis.utility.Utility;

import java.util.function.Consumer;

public class LinksFiltroActivity extends AppCompatActivity {
    Button Lbtn_continue_play;
    RadioButton Lhd_btn;
    RadioButton Lhd_hls1;
    RadioButton Lfhd_btn;
    LinearLayout Lln_select;
    ProgressBar LprogressBar;
    TextView text1;
    TextView text2;

    TextView LtextView;
    Ads ads;
    Intent intent;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links_filtro);
        Lbtn_continue_play = findViewById(R.id.Lbtn_continue_play);
        Lhd_btn = findViewById(R.id.Lhd_btn);
        Lhd_hls1 = findViewById(R.id.Lhd_hls1);
        Lfhd_btn = findViewById(R.id.Lfhd_btn);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        Lln_select = findViewById(R.id.Lln_select);
        LprogressBar = findViewById(R.id.LprogressBar3);
        LtextView = findViewById(R.id.Ltxt_loading);

        if (getIntent().hasExtra("Url")) {
            url = getIntent().getStringExtra("Url");
        } else {
            Toast.makeText(this, "No se puede reproducir este enlace", Toast.LENGTH_SHORT).show();
        }

        Lbtn_continue_play.setOnClickListener(view -> {

            String appPackageName = "com.jcplayerhd.reproductorhd";
            String[] fnmae = url.split("/");
            String fileNameWithoutExtn;

            try {
                fileNameWithoutExtn = fnmae[fnmae.length - 1].substring(0, fnmae[fnmae.length - 1].lastIndexOf('.'));

            } catch (Exception e) {
                fileNameWithoutExtn = fnmae[fnmae.length - 1];
            }
            try {
                // We found the activity now start the activity
                intent = new Intent(Intent.ACTION_SEND);
                intent.setPackage("com.jcplayerhd.reproductorhd");
                intent.putExtra("isFromApp", true);
                intent.putExtra("Name", fileNameWithoutExtn);
                intent.putExtra("ad_count", 1);
                intent.setClassName("com.jcplayerhd.reproductorhd", "com.jcplayerhd.reproductorhd.activity.ContinueActivity");
                intent.putExtra("Url", url);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Log.v("PruebaLinks", "******" + url);


            } catch (Exception e) {
                Log.e("stream", e.toString());
                try {
                    Toast.makeText(this, "try is held", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

                } catch (android.content.ActivityNotFoundException anfe) {
                    Toast.makeText(this, "catch is held", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }


            if (isAppAvailable(this, appPackageName)) {
                startActivity(intent);
                finish();
            } else {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("MAX PLAYER");
//                builder.setMessage(getResources().getString(R.string.maxplayerdescription));
//                builder.setPositiveButton(R.string.reproducir, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User clicked OK button
//                        Intent intent2;
//                        // Bring user to the market or let them choose an app?
//                        intent2 = new Intent(Intent.ACTION_VIEW);
//                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent2.setData(Uri.parse("market://details?id=" + "com.jcplayerhd.reproductorhd"));
//                        startActivity(intent2);
//                    }
//                });
//                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
                String finalUrl = url;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

              Utility.InstallExternalDialog(this,
                      a -> {
                          try {
                              Log.e("stream", "pakage > " + appPackageName + " > " + finalUrl);
                              LinksFiltroActivity.this.startActivity(intent);
                             // Toast.makeText(LinksFiltroActivity.this, "try block", Toast.LENGTH_SHORT).show();

                          } catch (Exception e) {
                              Log.e("stream", e.toString());
                           //   Toast.makeText(LinksFiltroActivity.this, "catch block", Toast.LENGTH_SHORT).show();
                              try {
                                  LinksFiltroActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                           //       Toast.makeText(LinksFiltroActivity.this, "catch try block", Toast.LENGTH_SHORT).show();
                              } catch (android.content.ActivityNotFoundException anfe) {
                                  LinksFiltroActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                               //   Toast.makeText(LinksFiltroActivity.this, "catch catch block", Toast.LENGTH_SHORT).show();
                              }
                          }
                       //  LinksFiltroActivity.this.finish();
                      }, t -> {
                          // LinksFiltroActivity.this.finish();
                        });
               // finish();

            }

        });


        Handler handler = new Handler();

        handler.postDelayed(() -> text2.setVisibility(View.VISIBLE), 2000);

        handler.postDelayed(() -> text1.setVisibility(View.VISIBLE), 3000);

        handler.postDelayed(() -> LtextView.setVisibility(View.VISIBLE), 4000);
        YoYo.with(Techniques.Tada)
                .

                        duration(3000)
                .

                        repeat(5)
                .

                        playOn(LtextView);

        YoYo.with(Techniques.Bounce)
                .

                        duration(3000)
                .

                        repeat(5)
                .

                        playOn(text1);

        handler.postDelayed(() ->

        {
            Lbtn_continue_play.setVisibility(View.VISIBLE);
            LprogressBar.setVisibility(View.GONE);
            LtextView.setVisibility(View.GONE);
            text1.setVisibility(View.GONE);
            text2.setVisibility(View.GONE);

        }, 5000);


        ads = new Ads();

        FrameLayout nativeAdContainer = findViewById(R.id.native_ad_container);
        ads.loadNativeAd(LinksFiltroActivity.this, nativeAdContainer);


    }


    public static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();
    }


}
package com.jcnovelashd.novelasgratis.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jcnovelashd.novelasgratis.R;

public class SelectQualityActivity extends AppCompatActivity {

    Button btn_continue_play;
    RadioButton hd_btn;
    RadioButton hd_hls1;
    RadioButton fhd_btn;
    LinearLayout ln_select;
    ProgressBar progressBar;
    TextView textView;
    Ads ads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quality);
        btn_continue_play = findViewById(R.id.btn_continue_play);
        hd_btn = findViewById(R.id.hd_btn);
        hd_hls1 = findViewById(R.id.hd_hls1);
        fhd_btn = findViewById(R.id.fhd_btn);
        ln_select = findViewById(R.id.ln_select);
        progressBar = findViewById(R.id.progressBar3);
        textView = findViewById(R.id.txt_loading);
        btn_continue_play.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtras(getIntent());
            startActivity(intent);
            finish();
        });

        Handler handler = new Handler();

        handler.postDelayed(() -> hd_hls1.setVisibility(View.VISIBLE),2000);

        handler.postDelayed(() -> hd_btn.setVisibility(View.VISIBLE),3000);

        handler.postDelayed(() -> fhd_btn.setVisibility(View.VISIBLE),4000);


        handler.postDelayed(() -> {
            btn_continue_play.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        },5000);



        ads = new Ads();

        FrameLayout nativeAdContainer = findViewById(R.id.native_ad_container);
        ads.loadNativeAd(SelectQualityActivity.this, nativeAdContainer);


    }
}
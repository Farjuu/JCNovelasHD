package com.jcnovelashd.novelasgratis.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> testDeviceIds = new ArrayList<>();
        testDeviceIds.add("2DA7DB775C18AAC2BFC1B37E91A72E08");

        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        new getValuesAsyncTaskRunner(this).execute("");

     /*Intent intent = new Intent(this, MainActivity.class);
     intent.putExtras(getIntent());
       startActivity(intent);
      finish();*/
    }
}

package com.jcnovelashd.novelasgratis.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.jcnovelashd.novelasgratis.R;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class CodeActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    String CodigoGenerado;
    Button btn_codigo;
    Ads ads;
    //    final M3UParser parser = new M3UParser();
    InputStream is;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private boolean isGenerated = false;
    PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prf = new PrefManager(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_code);
        editText = findViewById(R.id.editTextCodigoEntrada);
        textView = findViewById(R.id.textViewCodigo);
        btn_codigo = findViewById(R.id.btn_codigo);
        CodigoGenerado = getRandomString();
        textView.setText(CodigoGenerado);
//        checkPermission();
//        context.getString(R.string.admob_banner_id) = getString(R.string.admob_banner_id);
//        context.getString(R.string.admob_interstitial_id) = getString(R.string.admob_interstitial_id);


        ads = new Ads();

        FrameLayout nativeAdContainer = findViewById(R.id.native_ad_container);
        ads.loadNativeAd(CodeActivity.this, nativeAdContainer);

        if (checkPermission()) {
            if (NetworkUtils.isConnected(CodeActivity.this)) {
//                splashScreen();
//                checkLicense();

//                new _getFromFreeList().execute("http://aver.tv/tv.m3u", "prueba");
            } else {
                Toast.makeText(CodeActivity.this, "Problemas en la conexion", Toast.LENGTH_SHORT).show();
            }
        } else {
            requestPermission();
        }
        btn_codigo.setOnClickListener(v -> {
            if(!isGenerated){
                editText.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                CodigoGenerado = getRandomString();
                textView.setText(CodigoGenerado);
                btn_codigo.setText("Continuar");
                isGenerated =true;
            }else{
                splashScreen();
            }
        });


    }

    private void splashScreen() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
        if (editText.getText().toString().equals(CodigoGenerado)) {
//            showInterstitialAdsToActivityNoCounter(CodeActivity.this, MainActivity.class);
            prf.setString("PASSCODE","true");
            Intent intent = new Intent(getApplicationContext(), SelectQualityActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else {
            CodigoGenerado = getRandomString();
            textView.setText(CodigoGenerado);
            editText.setText("");
            Toast.makeText(this, "Codigo incorrecto. Intentelo de nuevo", Toast.LENGTH_SHORT).show();

        }
//            }
//
//        }, SPLASH_DURATION);
    }

    @Override
    public void onBackPressed() {
        // set the flag to true so the next activity won't start up
//        mIsBackButtonPressed = true;
        super.onBackPressed();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(CodeActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getApplicationContext(), getString(R.string.allow_permission_text), Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(CodeActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(CodeActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
                File dir = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));
                if (!dir.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    dir.mkdir();
                }
                File dirFile = new File(dir.getPath() + "/" + "default.m3u");
                if (!dirFile.exists()) {
                    try {
                        String inputLine = null;
                        OutputStreamWriter myOutWriter = new FileWriter(dirFile);
                        myOutWriter.write(inputLine + "\n");
                        myOutWriter.flush();
                        myOutWriter.close();
                    } catch (Exception e) {
                        //Log.d("Google", "FILE NOT CREATED" + e.getMessage());
                    }
                }
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
        }
    }



    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(6);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString()
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(6);
        for(int i = 0; i< 6; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

}

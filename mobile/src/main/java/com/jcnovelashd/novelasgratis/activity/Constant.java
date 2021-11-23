package com.jcnovelashd.novelasgratis.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


import com.jcnovelashd.novelasgratis.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import static android.content.Context.UI_MODE_SERVICE;

public class Constant implements Serializable {

    public static String APP_COMPLEMENTS_URL = "https://complements.xplayerhd.com/complements.json?";

//    public static String FILES_API = "https://app.peliculashdlive.com/wp-content/themes/uploadhd/iptvNewVip/files/files.json";

    public static String APP_BACKGROUND = "https://i.imgur.com/9W6VzUP.png";

    public static String MY_DEFAULT_LIST_PREF = "MY_DEFAULT_FIRE_PLAYER_PREF";

    private static final long serialVersionUID = 1L;

//    public static String internalAppKey = "rtt&3acTwW*MyF9D7FWf@X_w6$5W&a_V";

//    public static String apiKey = "9TV=V6HZb!vb@v37?Td32zBv%gSS@nFq";

//    public static String url = BuildConfig.My_api + "api.php";

    public static final int DOWNLOADS_COUNT_LIMIT = 5;

    public static boolean IS_VIP_USER = false;

    public static int AD_COUNT = 0;
    public static int AD_COUNT_SHOW = 2;

    public static boolean IS_FROM_BS_APP;
    public static boolean IS_FROM_APP;

//    public static int VIDEO_ADS_LIMIT = 0;
//    public static int BANNER_ADS_LIMIT = 0;

    public static String APP_SEG = "";

//    public static boolean IS_PASSED_TIME = false;

    public static boolean isBanner = true, isInterstitial = true, isRewarded = true, isPremium = true;
//    public static String adMobBannerId, adMobInterstitialId, adMobRewardedId, adMobPublisherId, adMobNativeId;

    @SuppressLint("QueryPermissionsNeeded")
    public static boolean isPackageExisted(String targetPackage, PackageManager pack){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = pack;
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }


    public static boolean isNotBot(Activity activity){
        return isPackageExisted("com.whatsapp", activity.getPackageManager()) || isPackageExisted("com.whatsapp.w4b", activity.getPackageManager());
    }

    public static boolean isNotBotLicence(Activity activity){
        return isPackageExisted("com.whatsapp", activity.getPackageManager()) || isPackageExisted("com.whatsapp.w4b", activity.getPackageManager()) ||
                isPackageExisted("com.facebook.katana", activity.getPackageManager()) || isPackageExisted("com.facebook.lite", activity.getPackageManager())
                || isPackageExisted("com.facebook.orca", activity.getPackageManager()) || isPackageExisted("com.facebook.mlite", activity.getPackageManager()) == true;
    }

    public static boolean isSmartTV(Activity activity){
        UiModeManager uiModeManager;
        uiModeManager = (UiModeManager) activity.getSystemService(UI_MODE_SERVICE);
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
            return true;
        }
        return false;
    }

//    public static String appFolder(Activity activity){
//        String md5 = SecureRequestTokensGenerator.md5(activity.getString(R.string.app_name) + activity.getString(R.string.app_id));
//        return activity.getString(R.string.app_name) + " " + md5;
//    }

    public static void appNotInstalledDownload(final Activity activity, final String appName, final String packageName) {
        createDownloadDialogo(activity, appName, packageName).show();
//        String text = activity.getString(R.string.download_msg, appName);
//        new AlertDialog.Builder(activity)
//                .setTitle(activity.getString(R.string.important_text))
//                .setMessage(text)
//                .setPositiveButton(activity.getString(R.string.download), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        try {
//                            activity.startActivity(new Intent(Intent.ACTION_VIEW,
//                                    Uri.parse("market://details?id="
//                                            + packageName)));
//                        } catch (android.content.ActivityNotFoundException anfe) {
//                            activity.startActivity(new Intent(
//                                    Intent.ACTION_VIEW,
//                                    Uri.parse("http://play.google.com/store/apps/details?id="
//                                            + packageName)));
//                        }
//                    }
//                })
//                .setNegativeButton(activity.getString(R.string.cancel_btn_text), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//                    }
//                })
//                .show();
    }

    public static AlertDialog createDownloadDialogo(Activity activity, final String appName, final String packageName) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.download_dialog, null);

        builder.setView(v);

        final AlertDialog dialog = builder.create();

        LinearLayout adViewBannerAdd, adViewBannerAdd2;
        adViewBannerAdd = v.findViewById(R.id.adViewBannerDelete);

//        if (Constant.BANNER_ADS_LIMIT == 0) {
//            BannersAdsFacebook.ShowBannerAds(activity, adViewBannerAdd);
//        } else {}

        TextView txt_title = v.findViewById(R.id.txt_title_rate);
        txt_title.setText(activity.getString(R.string.download_msg, appName));

        Button btn_continue;
        btn_continue = (Button) v.findViewById(R.id.btn_continue_play);
        btn_continue.setText(activity.getString(R.string.download));
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id="
                                    + packageName)));
                    dialog.dismiss();
                } catch (android.content.ActivityNotFoundException anfe) {
                    activity.startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + packageName)));
                    dialog.dismiss();
                }
            }
        });

        Button btn_close;
        btn_close = (Button) v.findViewById(R.id.btn_cancel_play);
        btn_close.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        return dialog;
    }

    public static String appFolder(Activity activity){
        String md5 = SecureRequestTokensGenerator.md5(activity.getString(R.string.app_name) + activity.getString(R.string.app_id) + activity.getString(R.string.app_name));
        return activity.getString(R.string.app_name) + " " + md5;
    }




    public static void moveFile(Activity activity, String inputPath, String inputFile, String outputPath) {
        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();

            // delete the original file
            //noinspection ResultOfMethodCallIgnored
            new File(inputPath + inputFile).delete();

        } catch (Exception fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }

    }



}

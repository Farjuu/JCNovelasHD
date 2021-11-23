package com.jcnovelashd.novelasgratis.utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.view.ContextThemeWrapper;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jcnovelashd.novelasgratis.R;

import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class Utility {

    private static MaterialAlertDialogBuilder dialogBuilder;

    public static class ProgressBarHandler {
        private final ProgressBar mProgressBar;
        private final Context mContext;
        private String loadingText;
        private final TextView textview;
        private final ViewGroup layout;
        private final RelativeLayout rl;

        public ProgressBarHandler(Context context) {

            mContext = context;

            loadingText = context.getString(R.string.loading);


            layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();

            mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
            mProgressBar.setIndeterminate(true);


            textview = new TextView(context, null);
            textview.setText(loadingText);
            textview.setTextColor(context.getResources().getColor(R.color.white));


            RelativeLayout.LayoutParams params = new
                    RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

            RelativeLayout.LayoutParams paramstext = new
                    RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


            rl = new RelativeLayout(context);
            paramstext.setMargins(0, 200, 0, 0);

            textview.setLayoutParams(paramstext);
            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            rl.setGravity(Gravity.CENTER);
            rl.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            rl.addView(mProgressBar);
            rl.addView(textview);


            layout.addView(rl, params);

            hide();
        }

        public void show() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        public void hide() {
            mProgressBar.setVisibility(View.INVISIBLE);

        }

        public void destroy() {
            try {
                mProgressBar.setVisibility(View.INVISIBLE);
            } catch (Exception ignored) {
            }

            rl.removeAllViews();

            try {

                ViewGroup parent = (ViewGroup) layout.getParent();
                parent.removeView(layout);

            } catch (Exception ignored) {
            }


        }

        public void message(String text) {
            if (text != null) {

                loadingText = text;
                textview.setText(loadingText);

            }
        }
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

    public static void openTelegram(Context context, String telegramID, String name) {
        final String appName = "org.telegram.messenger";
        if (name == null) name = "App";

        if (isAppAvailable(context, appName)) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=" + telegramID));
            context.startActivity(intent);

        } else {

            Toast.makeText(context, name + " is not Installed", Toast.LENGTH_SHORT).show();


            try {
                Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                telegramIntent.setData(Uri.parse("https://www.telegram.me/" + telegramID));
                context.startActivity(telegramIntent);
            } catch (Exception e) {
                /* show error message */
            }

        }
    }

    static public abstract class Promise {

        public void reject() {

        }

        public void resolve() {

        }
    }


   static public void InstallExternalDialog(Context context, Consumer callback, Consumer failback) {

        Context _context = new ContextThemeWrapper(context, R.style.AlertDialogTheme);
        dialogBuilder = new MaterialAlertDialogBuilder(_context);
        dialogBuilder.setTitle(R.string.dialog_player_install_title);
        dialogBuilder.setMessage(R.string.dialog_player_install_description);

        int resID = _context.getResources().getIdentifier("ic_launcher", "mipmap", _context.getPackageName());
        dialogBuilder.setIcon(resID);
        dialogBuilder.setPositiveButton(R.string.reproducir, (dialog, which) -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    callback.accept(null);
                }
          //  dialog.dismiss();
        });


        dialogBuilder.setNegativeButton(R.string.dialog_player_install_negative, (dialog, which) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                failback.accept(null);
            }//dialog.dismiss();
        });
        dialogBuilder.show();
        //  finish();

    }




}



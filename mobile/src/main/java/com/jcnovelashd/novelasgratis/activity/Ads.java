package com.jcnovelashd.novelasgratis.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.jcnovelashd.novelasgratis.R;

public class Ads {


    public static int statusclick = 3;
    public static int statusclickcount = 0;
    public static String privecy = "http://whatstrackerdns.itamazons.in/whatsweb/whats_web_privacy_policy.html";

    private com.google.android.gms.ads.InterstitialAd interstitialAds;

    public void interstitialload(Context context) {


        MobileAds.initialize(context, initializationStatus -> {
            //add
        });
        interstitialAds = new com.google.android.gms.ads.InterstitialAd(context);
        interstitialAds.setAdUnitId(context.getString(R.string.interstitial_full_screen));
        interstitialAds.loadAd(
                new AdRequest.Builder()
                        .build()
        );
    }

//    public void showInd(final com.cv5whatstools.Adclick adclick) {
//        if ((interstitialAds == null) || (!interstitialAds.isLoaded())) {
//            adclick.onclicl();
//            return;
//        }
//        interstitialAds.setAdListener(new com.google.android.gms.ads.AdListener() {
//            @Override
//            public void onAdClosed() {
//                adclick.onclicl();
//                interstitialAds.loadAd(
//                        new AdRequest.Builder()
//                                .build()
//                );
//            }
//
//            @Override
//            public void onAdLoaded() {
//                //add
//            }
//
//            @Override
//            public void onAdOpened() {
//                //add
//            }
//        });
//        interstitialAds.show();
//
//
//    }


    public static void bannerad(com.google.android.gms.ads.AdView adViews, Context context) {

        MobileAds.initialize(context, initializationStatus -> {
            //add
        });
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adViews.loadAd(adRequest);

    }


    public Ads() {
        //add
    }

    public void loadNativeAd(final Activity context, final FrameLayout frameLayout) {
        refreshAd(context, frameLayout);
    }


    @SuppressWarnings("deprecation")
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {


        com.google.android.gms.ads.formats.MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);


        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));


        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());


        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);

    }


    @SuppressWarnings("deprecation")
    private void refreshAd(final Activity context, final FrameLayout frameLayout) {

        AdLoader.Builder builder = new AdLoader.Builder(context, context.getString(R.string.nativeid));

        builder.forUnifiedNativeAd(unifiedNativeAd -> {


            if (nativeAds != null) {
                nativeAds.destroy();
            }
            nativeAds = unifiedNativeAd;
            @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) context.getLayoutInflater()
                    .inflate(R.layout.adunity, null);
            populateUnifiedNativeAdView(unifiedNativeAd, adView);
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(false)
                .build();

        @SuppressWarnings("deprecation") NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            /**      * @deprecated (when, why, refactoring advice...)      */
            @Deprecated
            public void onAdFailedToLoad(int errorCode) {
            }
        }).build();
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        adLoader.loadAd(adRequest);

    }

    private UnifiedNativeAd nativeAds;


}

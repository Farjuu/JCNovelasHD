package com.jcnovelashd.novelasgratis.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.facebook.ads.Ad;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ironsource.mediationsdk.IronSource;
import com.jcnovelashd.novelasgratis.R;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Funciones {

    //Variable Anuncios Full
    @SuppressWarnings("deprecation")
    private static InterstitialAd Admobinterstitial;
    private static com.facebook.ads.InterstitialAd FacebookinterstitialAd;

    //Variable Anuncios Banner
    private static AdView mAdView =  null;
    private static com.facebook.ads.AdView mAdViewFacebook = null;
    @SuppressLint("StaticFieldLeak")
    private static com.startapp.sdk.ads.banner.Banner mAdViewStarApp = null;

    private static JSONArray jObj = null;
    private static String json = "";

    public Funciones(){}


    //Podiendo el margen abajo para que el anuncio no salga sobre la lista
    public static void SetMarginBottom(View rootView){
        LinearLayout MainLinearLayout = rootView.findViewById(R.id.ln_select);
        RelativeLayout.LayoutParams Linearlayout = new RelativeLayout.LayoutParams(MainLinearLayout.getLayoutParams());
        Linearlayout.setMargins(0, 0, 0, MainActivity.ValueMarginbottom);//left,right,top,bottom
        MainLinearLayout.setLayoutParams(Linearlayout);
    }

    //****[[[INICIO CODIGOS ANUNCIOS BANNER]]]****//
    public static void loadBannerAd(final View rootView, Activity activity) {
        try{
            //****[[[INICIO CODIGOS ANUNCIOS]]]****//
            //*******PARA SABER SI EL ALTERNEDOR ESTA ACTIVADO********//
            if(Contantes.ANUNCIOS_ALTERNADOS){

                Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = CheckCuentasBloqueadas(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR);

                //Este if es para saber si voy a poner el de admob
                if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 0){

                    mAdView = new AdView(activity);
                    mAdView.setAdUnitId(Contantes.ADMOB_BANNER_ID);
                    mAdView.setAdSize(AdSize.BANNER);
                    RelativeLayout layout = rootView.findViewById(R.id.ln_select);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    layout.addView(mAdView, params);
                    mAdView.loadAd(new AdRequest.Builder().build());
                    mAdView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            //Podiendo el margen abajo para que el anuncio no salga sobre la lista
                            //actualizando la vista para ponerle el margen abajo
                            SetMarginBottom(rootView);
                        }
                    });

                    //Cambiando el alternedor para que salga el otro anuncio
                    Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 1;

                    //Notificando al Log de cual ID esta Cargando
                    Log.i("ID_ADS_BANNER_ADMOB", Contantes.ADMOB_BANNER_ID);

                    //Este if es para saber si tengo que poner el de Facebook
                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 1){

                    mAdViewFacebook = new com.facebook.ads.AdView(activity,
                            Contantes.FACEBOOK_BANNER_ID,
                            com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                    RelativeLayout layout = rootView.findViewById(R.id.ln_select);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    layout.addView(mAdViewFacebook, params);
                    com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                        @Override
                        public void onError(Ad ad, com.facebook.ads.AdError adError) {

                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            //Podiendo el margen abajo para que el anuncio no salga sobre la lista
                            //actualizando la vista para ponerle el margen abajo
                            SetMarginBottom(rootView);
                        }
                        @Override
                        public void onAdClicked(Ad ad) {}
                        @Override
                        public void onLoggingImpression(Ad ad) {}
                    };

                    com.facebook.ads.AdView.AdViewLoadConfig loadAdConfig = mAdViewFacebook.buildLoadAdConfig()
                            .withAdListener(adListener)
                            .build();

                    mAdViewFacebook.loadAd(loadAdConfig);

                    //Cambiando el alternedor para que salga el otro anuncio
                    Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 2;

                    //Notificando al Log de cual ID esta Cargando
                    Log.i("ID_ADS_BANNER_FACEBOOK", Contantes.FACEBOOK_BANNER_ID);

                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 2){

                    // Instantiate an AdView view
                    mAdViewStarApp = new com.startapp.sdk.ads.banner.Banner(activity);

                    RelativeLayout layout = rootView.findViewById(R.id.ln_select);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    layout.addView(mAdViewStarApp, params);
                    //Podiendo el margen abajo para que el anuncio no salga sobre la lista
                    //actualizando la vista para ponerle el margen abajo
                    SetMarginBottom(rootView);

                    mAdViewStarApp.loadAd();

                    //Cambiando el alternedor para que salga el otro anuncio
                    Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 0;

                }


                //*******CUANDO EL ALTERNADOR ESTE DESATIVADO QUIERE DECIR QUE SALEN NORMALES********//
            }else {

                if(Contantes.ADMOB_BANNER_ADS){

                    mAdView = new AdView(activity);
                    mAdView.setAdUnitId(Contantes.ADMOB_BANNER_ID);
                    mAdView.setAdSize(AdSize.BANNER);
                    RelativeLayout layout = rootView.findViewById(R.id.ln_select);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    layout.addView(mAdView, params);
                    mAdView.loadAd(new AdRequest.Builder().build());
                    mAdView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            //Podiendo el margen abajo para que el anuncio no salga sobre la lista
                            //actualizando la vista para ponerle el margen abajo
                            SetMarginBottom(rootView);
                        }
                    });

                    //Notificando al Log de cual ID esta Cargando
                    Log.i("ID_ADS_BANNER_ADMOB", Contantes.ADMOB_BANNER_ID);
                }else if(Contantes.FACEBOOK_BANNER_ADS){
                    // Instantiate an AdView view
                    mAdViewFacebook = new com.facebook.ads.AdView(activity,
                            Contantes.FACEBOOK_BANNER_ID,
                            com.facebook.ads.AdSize.BANNER_HEIGHT_50);

                    RelativeLayout layout = rootView.findViewById(R.id.Lln_select);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    layout.addView(mAdViewFacebook, params);
                    com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                        @Override
                        public void onError(Ad ad, com.facebook.ads.AdError adError) {

                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            //Podiendo el margen abajo para que el anuncio no salga sobre la lista
                            //actualizando la vista para ponerle el margen abajo
                            SetMarginBottom(rootView);
                        }
                        @Override
                        public void onAdClicked(Ad ad) {}
                        @Override
                        public void onLoggingImpression(Ad ad) {}
                    };

                    com.facebook.ads.AdView.AdViewLoadConfig loadAdConfig = mAdViewFacebook.buildLoadAdConfig()
                            .withAdListener(adListener)
                            .build();

                    mAdViewFacebook.loadAd(loadAdConfig);

                    //Notificando al Log de cual ID esta Cargando
                    Log.i("ID_ADS_BANNER_FACEBOOK", Contantes.FACEBOOK_BANNER_ID);
                }else if(Contantes.STARTAPP_BANNER_ADS){
                    // Instantiate an AdView view
                    mAdViewStarApp = new com.startapp.sdk.ads.banner.Banner(activity);

                    RelativeLayout layout = rootView.findViewById(R.id.Lln_select);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    layout.addView(mAdViewStarApp, params);
                    //Podiendo el margen abajo para que el anuncio no salga sobre la lista
                    //actualizando la vista para ponerle el margen abajo
                    SetMarginBottom(rootView);

                    mAdViewStarApp.loadAd();

                }

            }


            //****[[[FIN CODIGOS ANUNCIOS]]]****//
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //****[[[FIN CODIGOS ANUNCIOS BANNER]]]****//


    //****[[[INICIO CODIGOS ANUNCIOS INIT]]]****//
    //CARGADOR ADMOB
    public static void INIT_loadFullScreenAd_ADMOB(Activity activity) {
        try{
            if(Admobinterstitial != null){
                if (!Admobinterstitial.isLoaded()) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    Admobinterstitial = new InterstitialAd(activity);
                    Admobinterstitial.setAdUnitId(Contantes.ADMOB_FULLSCREEN_ID);
                    // Begin loading your interstitial.
                    Admobinterstitial.loadAd(adRequest);
                }
            }else {
                AdRequest adRequest = new AdRequest.Builder().build();
                Admobinterstitial = new InterstitialAd(activity);
                Admobinterstitial.setAdUnitId(Contantes.ADMOB_FULLSCREEN_ID);
                // Begin loading your interstitial.
                Admobinterstitial.loadAd(adRequest);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //MOSTRAR ADMOB
    public static void INIT_showFullScreenAd_ADMOB() {
        try{
            if(ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                if (Admobinterstitial.isLoaded()) {
                    Admobinterstitial.show();
                }
            }else {
                Log.d("AppInBackground", "App Is In Background Ad Is Not Going To Show");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //CARGADOR FACEBOOK
    public static void INIT_loadFullScreenAd_FACEBOOK(Activity activity) {
        try{
            if(FacebookinterstitialAd != null){
                if (!FacebookinterstitialAd.isAdLoaded()) {
                    FacebookinterstitialAd = new com.facebook.ads.InterstitialAd(activity,
                            Contantes.FACEBOOK_FULLSCREEN_ID);
                    FacebookinterstitialAd.loadAd();
                }
            }else {
                FacebookinterstitialAd = new com.facebook.ads.InterstitialAd(activity,
                        Contantes.FACEBOOK_FULLSCREEN_ID);
                FacebookinterstitialAd.loadAd();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //MOSTRAR FACEBOOK
    public static void INIT_showFullScreenAd_FACEBOOK() {
        try{
            if(ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                if (FacebookinterstitialAd.isAdLoaded()) {
                    FacebookinterstitialAd.show();
                }
            }else {
                Log.d("AppInBackground", "App Is In Background Ad Is Not Going To Show");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //MOSTRAR STARTAPP
    public static void INIT_showFullScreenAd_STARTAPP(Context context) {
        try{
            if (StartAppAd.showAd(context)) {
                StartAppAd.showAd(context);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //CARGADOR IRONSOURCE
    public static void INIT_loadFullScreenAd_IRONSOURCE(Activity activity) {
        IronSource.loadInterstitial();
    }

    //CARGADOR IRONSOURCE
    public static void INIT_showFullScreenAd_IRONSOURCE(Activity activity) {
        if (IronSource.isInterstitialReady()) {
            IronSource.showInterstitial("");
        }
    }

//    //CARGADOR APPODEAL
//    public static void INIT_showFullScreenAd_APPODEAL(Activity activity) {
//        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
//            Appodeal.show(activity, Appodeal.INTERSTITIAL);
//        }
//    }
//    //****[[[FIN CODIGOS ANUNCIOS INIT]]]****//


    //****[[[INICIO CODIGOS ANUNCIOS]]]****//
    public static void loadFullScreenAd(Activity activity) {

        try{
            //*******PARA SABER SI EL ALTERNEDOR ESTA ACTIVADO********//
            if(Contantes.ANUNCIOS_ALTERNADOS){

                Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = CheckCuentasBloqueadas(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR);

                //Este if es para saber si voy a poner el de admob
                if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 0){
                    if(Admobinterstitial != null) {
                        if (Admobinterstitial.isLoaded()) {
                            return;
                        }
                    }
                    // Create the interstitial.
                    AdRequest adRequest = new AdRequest.Builder().build();
                    Admobinterstitial = new InterstitialAd(activity);
                    Admobinterstitial.setAdUnitId(Contantes.ADMOB_FULLSCREEN_ID);
                    // Begin loading your interstitial.
                    Admobinterstitial.loadAd(adRequest);
                    //Notificando al Log de cual ID esta Cargando
                    Log.i("ID_AnuncioFULL_ADMOB", Contantes.ADMOB_FULLSCREEN_ID);
                    //Este if es para saber si tengo que poner el de Facebook
                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 1){
                    if(FacebookinterstitialAd != null) {
                        if (FacebookinterstitialAd.isAdLoaded()) {
                            return;
                        }
                    }
                    FacebookinterstitialAd = new com.facebook.ads.InterstitialAd(activity,
                            Contantes.FACEBOOK_FULLSCREEN_ID);
                    FacebookinterstitialAd.loadAd();
                    //Notificando al Log de cual ID esta Cargando
                    Log.i("ID_AnuncioFULL_FACEBOOK", Contantes.FACEBOOK_FULLSCREEN_ID);
                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 3){
                    if (IronSource.isInterstitialReady()) {
                        return;
                    }
                    IronSource.loadInterstitial();
                }
                //*******CUANDO EL ALTERNADOR ESTE DESATIVADO QUIERE DECIR QUE SALEN NORMALES********//
            }else {
                if (Contantes.ADMOB_FULLSCREEN_ADS) {
                    if(Admobinterstitial != null) {
                        if (Admobinterstitial.isLoaded()) {
                            return;
                        }
                    }
                    // Create the interstitial.
                    AdRequest adRequest = new AdRequest.Builder().build();
                    Admobinterstitial = new InterstitialAd(activity);
                    Admobinterstitial.setAdUnitId(Contantes.ADMOB_FULLSCREEN_ID);
                    // Begin loading your interstitial.
                    Admobinterstitial.loadAd(adRequest);
                    //Notificando al Log de cual ID esta Cargando
                    Log.i("ID_AnuncioFULL_ADMOB", Contantes.ADMOB_FULLSCREEN_ID);
                }else if (Contantes.FACEBOOK_FULLSCREEN_ADS) {
                    if(FacebookinterstitialAd != null) {
                        if (FacebookinterstitialAd.isAdLoaded()) {
                            return;
                        }
                    }
                    FacebookinterstitialAd = new com.facebook.ads.InterstitialAd(activity,
                            Contantes.FACEBOOK_FULLSCREEN_ID);
                    FacebookinterstitialAd.loadAd();
                    //Notificando al Log de cual ID esta Cargando
                    Log.i("ID_AnuncioFULL_FACEBOOK", Contantes.FACEBOOK_FULLSCREEN_ID);
                }else if (Contantes.IRONSOURCE_FULLSCREEN_ADS) {
                    IronSource.loadInterstitial();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showFullScreenAd(Activity context) {
        try{

            //*******PARA SABER SI EL ALTERNEDOR ESTA ACTIVADO********//
            if(Contantes.ANUNCIOS_ALTERNADOS){

                Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = CheckCuentasBloqueadas(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR);

                //Este if es para saber si voy a poner el de admob
                if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 0){
                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                        if (Admobinterstitial.isLoaded()) {
                            Admobinterstitial.show();
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cambiar el alternador para que aparesca la otra compania
                            Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 1;
                            //Cargando el anuncio otra ves
                            loadFullScreenAd(context);
                        }else {
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cargando el anuncio otra ves
                            loadFullScreenAd(context);
                        }
                    }else {
                        //Subiendo el conteo para que se pueda hacer lo del anuncio
                        Contantes.ConteoIntestitialLocal++;
                    }
                    //Este if es para saber si tengo que poner el de Facebook
                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 1){
                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                        if (FacebookinterstitialAd.isAdLoaded()) {
                            FacebookinterstitialAd.show();
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cambiar el alternador para que aparesca la otra compania
                            Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 2;
                            //Cargando el anuncio otra ves
                            loadFullScreenAd(context);
                        }else {
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cargando el anuncio otra ves
                            loadFullScreenAd(context);
                        }
                    }else {
                        //Subiendo el conteo para que se pueda hacer lo del anuncio
                        Contantes.ConteoIntestitialLocal++;
                    }
                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 2){
                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                        if (StartAppAd.showAd(context)) {
                            StartAppAd.showAd(context);
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cambiar el alternador para que aparesca la otra compania
                            Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 3;
                        }else {
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                        }
                    }else {
                        //Subiendo el conteo para que se pueda hacer lo del anuncio
                        Contantes.ConteoIntestitialLocal++;
                    }
                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 3){
                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                        if (IronSource.isInterstitialReady()) {
                            IronSource.showInterstitial("");
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cambiar el alternador para que aparesca la otra compania
                            Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 4;
                        }else {
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                        }
                    }else {
                        //Subiendo el conteo para que se pueda hacer lo del anuncio
                        Contantes.ConteoIntestitialLocal++;
                    }
                }
//                else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 4){
//                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
//                        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
//                            Appodeal.show(context, Appodeal.INTERSTITIAL);
//                            //Cambiando el conteo local para que no salga siempre el anuncio
//                            Contantes.ConteoIntestitialLocal = 0;
//                            //Cambiar el alternador para que aparesca la otra compania
//                            Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 0;
//                        }else {
//                            //Cambiando el conteo local para que no salga siempre el anuncio
//                            Contantes.ConteoIntestitialLocal = 0;
//                        }
//                    }else {
//                        //Subiendo el conteo para que se pueda hacer lo del anuncio
//                        Contantes.ConteoIntestitialLocal++;
//                    }
//                }
                //*******CUANDO EL ALTERNADOR ESTE DESATIVADO QUIERE DECIR QUE SALEN NORMALES********//
            }else if(Contantes.ADS_ALTERNADOS_POR_HORAS){
                Ads_Full_Por_Hora(context);
            }else {
                if (Contantes.ADMOB_FULLSCREEN_ADS) {
                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                        if (Admobinterstitial.isLoaded()) {
                            Admobinterstitial.show();
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cargando el anuncio otra ves
                            loadFullScreenAd(context);
                        }else {
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cargando el anuncio otra ves
                            loadFullScreenAd(context);
                        }
                    }else {
                        //Subiendo el conteo para que se pueda hacer lo del anuncio
                        Contantes.ConteoIntestitialLocal++;
                    }
                }else if (Contantes.FACEBOOK_FULLSCREEN_ADS) {
                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                        if (FacebookinterstitialAd.isAdLoaded()) {
                            FacebookinterstitialAd.show();
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cargando el anuncio otra ves
                            loadFullScreenAd(context);
                        }else {
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                            //Cargando el anuncio otra ves
                            loadFullScreenAd(context);
                        }
                    }else {
                        //Subiendo el conteo para que se pueda hacer lo del anuncio
                        Contantes.ConteoIntestitialLocal++;
                    }
                }else if (Contantes.STARTAPP_FULLSCREEN_ADS) {
                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                        if (StartAppAd.showAd(context)) {
                            StartAppAd.showAd(context);
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                        }else {
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                        }
                    }else {
                        //Subiendo el conteo para que se pueda hacer lo del anuncio
                        Contantes.ConteoIntestitialLocal++;
                    }
                }else if (Contantes.IRONSOURCE_FULLSCREEN_ADS) {
                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                        if (IronSource.isInterstitialReady()) {
                            IronSource.showInterstitial("");
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                        }else {
                            //Cambiando el conteo local para que no salga siempre el anuncio
                            Contantes.ConteoIntestitialLocal = 0;
                        }
                    }else {
                        //Subiendo el conteo para que se pueda hacer lo del anuncio
                        Contantes.ConteoIntestitialLocal++;
                    }
                }
//                else if (Contantes.APPODEAL_FULLSCREEN_ADS) {
//                    if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
//                        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
//                            Appodeal.show(context, Appodeal.INTERSTITIAL);
//                            //Cambiando el conteo local para que no salga siempre el anuncio
//                            Contantes.ConteoIntestitialLocal = 0;
//                        }else {
//                            //Cambiando el conteo local para que no salga siempre el anuncio
//                            Contantes.ConteoIntestitialLocal = 0;
//                        }
//                    }else {
//                        //Subiendo el conteo para que se pueda hacer lo del anuncio
//                        Contantes.ConteoIntestitialLocal++;
//                    }
//                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Ads_Full_Por_Hora(Activity context){
        if(Funciones.getDate_ADS_POR_HORA() != null){
            if(Funciones.isExpire(Funciones.getDate_ADS_POR_HORA())){
                int Alternacion_Number = Funciones.getAlternacion_Number();

                if(Alternacion_Number == 4){
                    Alternacion_Number = 0;
                }else {
                    Alternacion_Number+=1;
                }
                Funciones.putAlternacion_Number(Alternacion_Number);
                Funciones.putDate_ADS_POR_HORA();

                //Iniciando Los INIT Por Hora
                PutADS_Por_Hora(context);
            }else {
                //Iniciando Los INIT Por Hora
                PutADS_Por_Hora(context);
            }
        }
    }

    public static void PutADS_Por_Hora(Activity context){
        int Alternacion_Number = Funciones.getAlternacion_Number();
        Alternacion_Number = Funciones.CheckCuentasBloqueadas(Alternacion_Number);

        //****[[[INICIO CODIGOS ANUNCIOS INIT]]]****//
        //CARGADOR ADMOB
        if(Alternacion_Number == 0) {
            if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                if (Admobinterstitial.isLoaded()) {
                    Admobinterstitial.show();
                    //Cambiando el conteo local para que no salga siempre el anuncio
                    Contantes.ConteoIntestitialLocal = 0;
                    //Cargando el anuncio otra ves
                    loadFullScreenAd(context);
                }else {
                    //Cambiando el conteo local para que no salga siempre el anuncio
                    Contantes.ConteoIntestitialLocal = 0;
                    //Cargando el anuncio otra ves
                    loadFullScreenAd(context);
                }
            }else {
                //Subiendo el conteo para que se pueda hacer lo del anuncio
                Contantes.ConteoIntestitialLocal++;
            }
        }else if(Alternacion_Number == 1) {
            if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                if (FacebookinterstitialAd.isAdLoaded()) {
                    FacebookinterstitialAd.show();
                    //Cambiando el conteo local para que no salga siempre el anuncio
                    Contantes.ConteoIntestitialLocal = 0;
                    //Cargando el anuncio otra ves
                    loadFullScreenAd(context);
                }else {
                    //Cambiando el conteo local para que no salga siempre el anuncio
                    Contantes.ConteoIntestitialLocal = 0;
                    //Cargando el anuncio otra ves
                    loadFullScreenAd(context);
                }
            }else {
                //Subiendo el conteo para que se pueda hacer lo del anuncio
                Contantes.ConteoIntestitialLocal++;
            }
        }else if(Alternacion_Number == 2) {
            if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                if (StartAppAd.showAd(context)) {
                    StartAppAd.showAd(context);
                    //Cambiando el conteo local para que no salga siempre el anuncio
                    Contantes.ConteoIntestitialLocal = 0;
                }else {
                    //Cambiando el conteo local para que no salga siempre el anuncio
                    Contantes.ConteoIntestitialLocal = 0;
                }
            }else {
                //Subiendo el conteo para que se pueda hacer lo del anuncio
                Contantes.ConteoIntestitialLocal++;
            }
        }else if(Alternacion_Number == 3) {
            if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
                if (IronSource.isInterstitialReady()) {
                    IronSource.showInterstitial("");
                    //Cambiando el conteo local para que no salga siempre el anuncio
                    Contantes.ConteoIntestitialLocal = 0;
                }else {
                    //Cambiando el conteo local para que no salga siempre el anuncio
                    Contantes.ConteoIntestitialLocal = 0;
                }
            }else {
                //Subiendo el conteo para que se pueda hacer lo del anuncio
                Contantes.ConteoIntestitialLocal++;
            }
        }else if(Alternacion_Number == 4) {
//            if(Contantes.ConteoIntestitialLocal == Contantes.ConteoInterstitial ){
//                if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
//                    Appodeal.show(context, Appodeal.INTERSTITIAL);
//                    //Cambiando el conteo local para que no salga siempre el anuncio
//                    Contantes.ConteoIntestitialLocal = 0;
//                }else {
//                    //Cambiando el conteo local para que no salga siempre el anuncio
//                    Contantes.ConteoIntestitialLocal = 0;
//                }
//            }else {
//                //Subiendo el conteo para que se pueda hacer lo del anuncio
//                Contantes.ConteoIntestitialLocal++;
//            }
        }
        //****[[[FIN CODIGOS ANUNCIOS INIT]]]****//
    }

    public static void showInmediatoFullScreenAd(Activity context) {
        try{

            //*******PARA SABER SI EL ALTERNEDOR ESTA ACTIVADO********//
            if(Contantes.ANUNCIOS_ALTERNADOS){

                Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = CheckCuentasBloqueadas(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR);

                //Este if es para saber si voy a poner el de admob
                if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 0){
                    if (Admobinterstitial.isLoaded()) {
                        Admobinterstitial.show();
                        //Cambiar el alternador para que aparesca la otra compania
                        Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 1;
                        //Cargando el anuncio otra ves
                        loadFullScreenAd(context);
                    }else {
                        //Cargando el anuncio otra ves
                        loadFullScreenAd(context);
                    }
                    //Este if es para saber si tengo que poner el de Facebook
                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 1){
                    if (FacebookinterstitialAd.isAdLoaded()) {
                        FacebookinterstitialAd.show();
                        //Cambiar el alternador para que aparesca la otra compania
                        Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 2;
                        //Cargando el anuncio otra ves
                        loadFullScreenAd(context);
                    }else {
                        //Cargando el anuncio otra ves
                        loadFullScreenAd(context);
                    }
                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 2){
                    if (StartAppAd.showAd(context)) {
                        StartAppAd.showAd(context);
                        //Cambiar el alternador para que aparesca la otra compania
                        Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 3;
                    }
                }else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 3){
                    if (IronSource.isInterstitialReady()) {
                        IronSource.showInterstitial("");
                        //Cambiar el alternador para que aparesca la otra compania
                        Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 4;
                    }
                }
//                else if(Contantes.ANUNCIOS_ALTERNADOS_INDICADOR == 4){
//                    if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
//                        Appodeal.show(context, Appodeal.INTERSTITIAL);
//                        //Cambiar el alternador para que aparesca la otra compania
//                        Contantes.ANUNCIOS_ALTERNADOS_INDICADOR = 0;
//                    }
//                }
                //*******CUANDO EL ALTERNADOR ESTE DESATIVADO QUIERE DECIR QUE SALEN NORMALES********//
            }else {
                if (Contantes.ADMOB_FULLSCREEN_ADS) {
                    if (Admobinterstitial.isLoaded()) {
                        Admobinterstitial.show();
                        //Cargando el anuncio otra ves
                        loadFullScreenAd(context);
                    }else {
                        //Cargando el anuncio otra ves
                        loadFullScreenAd(context);
                    }
                }else if (Contantes.FACEBOOK_FULLSCREEN_ADS) {
                    if (FacebookinterstitialAd.isAdLoaded()) {
                        FacebookinterstitialAd.show();
                        //Cargando el anuncio otra ves
                        loadFullScreenAd(context);
                    }else {
                        //Cargando el anuncio otra ves
                        loadFullScreenAd(context);
                    }
                }else if (Contantes.STARTAPP_FULLSCREEN_ADS) {
                    if (StartAppAd.showAd(context)) {
                        StartAppAd.showAd(context);
                    }
                }else if (Contantes.IRONSOURCE_FULLSCREEN_ADS) {
                    if (IronSource.isInterstitialReady()) {
                        IronSource.showInterstitial("");
                    }
                }
//                else if (Contantes.APPODEAL_FULLSCREEN_ADS) {
//                    if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
//                        Appodeal.show(context, Appodeal.INTERSTITIAL);
//                    }
//                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //****[[[FIN CODIGOS ANUNCIOS]]]****//


    public static String getValuesForKeyPreferen(SharedPreferences _Pref, String KEY){

        String Result = null;

        Map<String, ?> allEntries = _Pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            if (KEY.equalsIgnoreCase(entry.getKey())){
                Result = entry.getValue().toString();
            }
        }

        return Result;
    }

    public static boolean isNetworkAvailable(Context ctx) {
        int networkStatePermission = ctx
                .checkCallingOrSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);

        if (networkStatePermission == PackageManager.PERMISSION_GRANTED) {

            ConnectivityManager mConnectivity = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            // Skip if no connection, or background data disabled
            assert mConnectivity != null;
            NetworkInfo info = mConnectivity.getActiveNetworkInfo();
            if (info == null) {
                return false;
            }
            // Only update if WiFi
            int netType = info.getType();
            // int netSubtype = info.getSubtype();
            return ((netType == ConnectivityManager.TYPE_WIFI) || (netType == ConnectivityManager.TYPE_MOBILE)) && info.isConnected();
        } else {
            return true;
        }
    }

    public static JSONArray getJSONFromUrlBlogger(String url) {
        try
        {
            String Soure = getHtml(url);
            String[] items = Soure.split("ECJSON");
            json = items[1];
        } catch(Exception e) {
            e.printStackTrace();
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONArray(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON String
        return jObj;

    }

    public static String getHtml(String url) throws IOException {
        // Build and set timeout values for the request.
        URLConnection connection = (new URL(url)).openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
        connection.setRequestProperty("Accept","*/*");
        connection.connect();

        // Read and store the result line by line then return the entire string.
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder html = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            html.append(line);
        }
        in.close();

        return html.toString();
    }

    public static void IniciarStarApp(Boolean Values, Context _con){
        if(Values){
            if(!Contantes.BLOQUEO_STARTAPP) {
                StartAppSDK.init(_con, Contantes.STARTAPP_APPID, false);
            }
        }
    }

    public static void IniciarIronSource(Boolean Values, Context _con){
        if(Values){
            if(!Contantes.BLOQUEO_IRONSOURCE) {
                IronSource.init((Activity) _con, Contantes.IRONSOURCE_KEYID);
            }
        }
    }

//    public static void IniciarAppoDeal(Boolean Values, Context _con){
//        if(Values){
//            if(!Contantes.BLOQUEO_APPODEAL) {
//                Appodeal.initialize((Activity) _con, Contantes.APPODEAL_KEYID, Appodeal.INTERSTITIAL);
//            }
//        }
//    }

    public static int CheckCuentasBloqueadas(int Alternacion){
        int values = 0;

        if(Alternacion == 0){
            if(Contantes.BLOQUEO_ADMOB){
                values = 1;
            }else{
                values = 0;
            }
        }else if (Alternacion == 1){
            if(Contantes.BLOQUEO_FACEBOOK){
                values = 2;
            }else{
                values = 1;
            }
        }else if (Alternacion == 2){
            if(Contantes.BLOQUEO_STARTAPP){
                values = 3;
            }else{
                values = 2;
            }
        }else if (Alternacion == 3){
            if(Contantes.BLOQUEO_IRONSOURCE){
                values = 4;
            }else{
                values = 3;
            }
        }else if (Alternacion == 4){
            if(Contantes.BLOQUEO_APPODEAL){
                values = 0;
            }else{
                values = 4;
            }
        }

        return values;
    }

    public static void HideAnuncio(){

        /**Poniendo todas las variables de ADS ADMOB**/
        Contantes.ADMOB_INIT_FULLSCREEN_ADS = false;
        Contantes.ADMOB_FULLSCREEN_ADS = false;
        Contantes.ADMOB_BANNER_ADS = false;

        /**Poniendo todas las variables de ADS FACEBOOK**/
        Contantes.FACEBOOK_BANNER_ADS = false;
        Contantes.FACEBOOK_FULLSCREEN_ADS = false;
        Contantes.FACEBOOK_INIT_FULLSCREEN_ADS = false;

        /**Poniendo todas las variables de ADS STARTAPP**/
        Contantes.STARTAPP_BANNER_ADS = false;
        Contantes.STARTAPP_FULLSCREEN_ADS = false;
        Contantes.STARTAPP_INIT_FULLSCREEN_ADS = false;
        Contantes.INICIACION_STARTAPP = false;

        /**Poniendo todas las variables de ADS IRONSOURCE**/
        Contantes.IRONSOURCE_FULLSCREEN_ADS = false;
        Contantes.IRONSOURCE_BANNER_ADS = false;
        Contantes.IRONSOURCE_INIT_FULLSCREEN_ADS = false;
        Contantes.INICIACION_IRONSOURCE = false;

        /**Poniendo todas las variables de ADS APPODEAL**/
        Contantes.APPODEAL_FULLSCREEN_ADS = false;
        Contantes.APPODEAL_BANNER_ADS = false;
        Contantes.APPODEAL_INIT_FULLSCREEN_ADS = false;
        Contantes.INICIACION_APPODEAL = false;

        /**Poniendo todas las variables de CUENTAS_BLOQUEADAS**/
        Contantes.BLOQUEO_ADMOB = false;
        Contantes.BLOQUEO_FACEBOOK = false;
        Contantes.BLOQUEO_STARTAPP = false;
        Contantes.BLOQUEO_IRONSOURCE = false;
        Contantes.BLOQUEO_APPODEAL = false;

        /**Otras variables de ADS**/
        Contantes.ANUNCIOS_ALTERNADOS = false;
        Contantes.ADS_ALTERNADOS_POR_HORAS = false;
        Contantes.Init_ADS_ALTERNADOS_POR_HORAS = false;
    }

    public static boolean isExpire(String date){
        if(date.isEmpty() || date.trim().equals("")){
            return false;
        }else{
            SimpleDateFormat sdf =  new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss a"); // Jan-20-2015 1:30:55 PM
            Date d=null;
            Date d1=null;
            String today=   getToday("MMM-dd-yyyy hh:mm:ss a");
            try {
                //System.out.println("expdate>> "+date);
                //System.out.println("today>> "+today+"\n\n");
                d = sdf.parse(date);
                d1 = sdf.parse(today);
                if(d1.compareTo(d) <0){// not expired
                    return false;
                }else if(d.compareTo(d1)==0){// both date are same
                    if(d.getTime() < d1.getTime()){// not expired
                        return false;
                    }else if(d.getTime() == d1.getTime()){//expired
                        return true;
                    }else{//expired
                        return true;
                    }
                }else{//expired
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static String getToday(String format){
        Date date = new Date();
        return new SimpleDateFormat(format).format(date);
    }


    public static void putDate_ADS_POR_HORA(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, Contantes.HORAS_ADS_ALTERNADO);
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss a").format(cal.getTime());
        SharedPreferences sharedPreferences = MainActivity._Cont.getSharedPreferences("Anuncio_ADS_Hora", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ADS_Hora_Key", timeStamp);
        editor.apply();
    }

    public static String getDate_ADS_POR_HORA(){
        SharedPreferences sharedPreferences = MainActivity._Cont.getSharedPreferences("Anuncio_ADS_Hora", MODE_PRIVATE);
        String text = sharedPreferences.getString("ADS_Hora_Key", null);
        return text;
    }


    public static void putAlternacion_Number(int Values){
        SharedPreferences sharedPreferences = MainActivity._Cont.getSharedPreferences("Alternacion_Number_Pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Alternacion_Number", Values);
        editor.apply();
    }

    public static int getAlternacion_Number(){
        SharedPreferences sharedPreferences = MainActivity._Cont.getSharedPreferences("Alternacion_Number_Pref", MODE_PRIVATE);
        int values = sharedPreferences.getInt("Alternacion_Number", 0);
        return values;
    }
}

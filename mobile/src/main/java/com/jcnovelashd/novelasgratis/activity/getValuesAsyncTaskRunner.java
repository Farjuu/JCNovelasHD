package com.jcnovelashd.novelasgratis.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getValuesAsyncTaskRunner extends AsyncTask<String, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    @SuppressWarnings("deprecation")
    public getValuesAsyncTaskRunner(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params) {
        getConfiguracion(mContext);
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        if(!isFinishing()){
//            launchMainActivity();
        if(mContext != null){
            mContext.startActivity(new Intent(mContext, MainActivity.class));
            ((Activity)mContext).finish();
        }
    }
    public static void getConfiguracion(Context context) {
        try {
            try {
                // Getting JSON string from URL ------ Used JSON Array froam Android
//                JSONArray json = Funciones.getJSONFromUrlBlogger("https://ecappspro.blogspot.com/2019/03/tumusic-todas-apps-configuracion.html");
//                JSONArray json = Funciones.getJSONFromUrlBlogger("https://configuraciomaxplayer.blogspot.com/2020/12/config-diego.html");
//                JSONArray json = Funciones.getJSONFromUrlBlogger("https://configuraciomaxplayer.blogspot.com/2020/12/config.html");
                JSONArray json = Funciones.getJSONFromUrlBlogger(Contantes.URL_MAIN_BLOGGER);
//                JSONArray json = Funciones.getJSONFromUrlBlogger("https://iptvconfigcine.blogspot.com");
                for (int i = 0; i < json.length(); i++) {
                    JSONObject c = json.getJSONObject(i);

                    Log.v("configuracion",c.getString("URLS"));
                    Contantes.BLOGGER_URL = c.getJSONArray("URLS");
                    Contantes.MENU_String = c.getJSONArray("MENU");
//                    Contantes.Movie_URL = c.getString("Movie_URL");
//                    Contantes.Serie_URL = c.getString("Serie_URL");
//                    Contantes.ESTRENO_URL = c.getString("ESTRENO_URL");
//                    Contantes.TELEGRAM_ID = c.getString("TELEGRAM_ID");
//                    Contantes.TELEGRAM_NAME = c.getString("TELEGRAM_NAME");
//                    Contantes.Tv_URL = c.getString("TV_URL");
//                    Contantes.FACEBOOK_INIT_FULLSCREEN_ADS = c.getBoolean("FACEBOOK_INIT_FULLSCREEN_ADS");
//                    Contantes.ADMOB_INIT_FULLSCREEN_ADS = c.getBoolean("ADMOB_INIT_FULLSCREEN_ADS");
//                    Contantes.ADMOB_FULLSCREEN_ADS = c.getBoolean("ADMOB_FULLSCREEN_ADS");
//                    //Jamendo Variables
//                    Contantes.Client_ID = c.getString("Client_ID_APP");
//                    Contantes.JAMENDO_CLIENTE_ID_ALL_ARRAY = c.getString("JAMENDO_CLIENTE_ID_ALL_ARRAY");
//                    Contantes.JAMENDO_KEY_ALEATORIO = c.getBoolean("JAMENDO_KEY_ALEATORIO");
//
//                    //Frearchivo
//                    Contantes.URL_FREEMUSIC_SIN_KEY = c.getString("URL_FREEMUSIC_SIN_KEY");
//
//                    //Lista de dominios para bloquear los anuncios
//                    Contantes.LIST_DOMAIN_BLOCK = c.getString("LIST_DOMAIN_BLOCK");
//
//                    //Para saber si estan activo
//                    Contantes.IsFreeMusicActive = c.getBoolean("IsFreeMusicActive");
//
//                    //CcMixter URL
//                    Contantes.URL_CC_MUSIC_SIN_KEY = c.getString("URL_CC_MUSIC_SIN_KEY");
//
//                    //Para Saber si es la version de prueba
//                    Contantes.VERSION_EN_APROBACION = c.getBoolean("VERSION_EN_APROBACION");
//                    Contantes.VERSION_EN_PRODUCCION = c.getString("VERSION_EN_PRODUCCION");
//                    MainActivity.Version = c.getString("Version");
//
//                    //Para Saber si es la version de prueba
//                    Contantes.VERSION_EN_APROBACION_TUPLAYER = c.getBoolean("VERSION_EN_APROBACION_TUPLAYER");
//                    Contantes.VERSION_EN_PRODUCCION_TUPLAYER = c.getString("VERSION_EN_PRODUCCION_TUPLAYER");
//                    MainActivity.Version_TUPLAYER = c.getString("Version_TUPLAYER");
//
//                    //Otras Variables
//                    Contantes.URL_SEARCH_PAGE_DOWNLOAD = c.getString("URL_SEARCH_PAGE_DOWNLOAD");
//                    Contantes.GO_DOWNLOAD_OPCION2 = c.getBoolean("GO_DOWNLOAD_OPCION2");
//                    Contantes.ConteoInterstitial = Integer.valueOf(c.getString("ConteoInterstitial"));
                    //*******************************************************************************//
                    //********************************************************************************//
                    //********************************************************************************//
                    //Otras Variables De la nueva implementacion
                    //*****STARTAPP******//
//                    Contantes.STARTAPP_FULLSCREEN_ADS = c.getBoolean("STARTAPP_FULLSCREEN_ADS");
//                    Contantes.STARTAPP_BANNER_ADS = c.getBoolean("STARTAPP_BANNER_ADS");
//                    Contantes.STARTAPP_INIT_FULLSCREEN_ADS = c.getBoolean("STARTAPP_INIT_FULLSCREEN_ADS");
//                    Contantes.INICIACION_STARTAPP = c.getBoolean("INICIACION_STARTAPP");
//                    //*****IRONSOURCE******//
//                    Contantes.IRONSOURCE_FULLSCREEN_ADS = c.getBoolean("IRONSOURCE_FULLSCREEN_ADS");
//                    Contantes.IRONSOURCE_BANNER_ADS = c.getBoolean("IRONSOURCE_BANNER_ADS");
//                    Contantes.IRONSOURCE_INIT_FULLSCREEN_ADS = c.getBoolean("IRONSOURCE_INIT_FULLSCREEN_ADS");
//                    Contantes.INICIACION_IRONSOURCE = c.getBoolean("INICIACION_IRONSOURCE");
//                    //*****APPODEAL******//
//                    Contantes.APPODEAL_FULLSCREEN_ADS = c.getBoolean("APPODEAL_FULLSCREEN_ADS");
//                    Contantes.APPODEAL_BANNER_ADS = c.getBoolean("APPODEAL_BANNER_ADS");
//                    Contantes.APPODEAL_INIT_FULLSCREEN_ADS = c.getBoolean("APPODEAL_INIT_FULLSCREEN_ADS");
//                    Contantes.INICIACION_APPODEAL = c.getBoolean("INICIACION_APPODEAL");
//                    //*****CUENTAS_BLOQUEADA******//
//                    Contantes.BLOQUEO_ADMOB = c.getBoolean("BLOQUEO_ADMOB");
//                    Contantes.BLOQUEO_FACEBOOK = c.getBoolean("BLOQUEO_FACEBOOK");
//                    Contantes.BLOQUEO_STARTAPP = c.getBoolean("BLOQUEO_STARTAPP");
//                    Contantes.BLOQUEO_IRONSOURCE = c.getBoolean("BLOQUEO_IRONSOURCE");
//                    Contantes.BLOQUEO_APPODEAL = c.getBoolean("BLOQUEO_APPODEAL");
//                    //*****OTRAS_OPCIONES_ADS******//
//                    Contantes.ADS_ALTERNADOS_POR_HORAS = c.getBoolean("ADS_ALTERNADOS_POR_HORAS");
//                    Contantes.Init_ADS_ALTERNADOS_POR_HORAS = c.getBoolean("Init_ADS_ALTERNADOS_POR_HORAS");
//                    Contantes.HORAS_ADS_ALTERNADO = Integer.valueOf(c.getString("HORAS_ADS_ALTERNADO"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("variablesConf","error"+e.getMessage());
//                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("variablesConf","error"+e.getMessage());
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
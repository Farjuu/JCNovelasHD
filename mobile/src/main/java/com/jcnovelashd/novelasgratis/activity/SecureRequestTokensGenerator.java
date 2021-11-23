package com.jcnovelashd.novelasgratis.activity;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SecureRequestTokensGenerator {
    static final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz12345674890_";
    static final Random rand = new Random();
    final Set<String> identifiers = new HashSet<String>();
    static Constant constants = new Constant();

    public String randomCharPasswordGenerator(){
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(128)+128;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    public static String md5(String s) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(s.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String insertString(String originalString, String stringToBeInserted, int index)
    {
        String newString = "";

        for (int i = 0; i < originalString.length(); i++) {
            newString += originalString.charAt(i);

            if (i == index) {
                newString += stringToBeInserted;
            }
        }

        // return the modified String
        return newString;
    }

    public int rndNumber(int limit){
        int n = rand.nextInt(limit) + 1;
        return n;
    }

    public static String rndChar(int limit){
        String str = "";
        for (int i = 0; i < limit; i++) {
            str += lexicon.charAt(rand.nextInt(lexicon.length()));
//            System.out.println(lexicon.charAt(rand.nextInt(lexicon.length())));
        } // prints 50 random characters from alphabet
        return str;
    }

    public String encodeApiCall(String base64, String token, String hash){
        String part1 = "kp2M0"+rndChar(16);
        String part2 = rndChar(7)+"I1g60wC";

        String s0 = rndChar(12)+base64+ rndChar(3);
        String s1 = insertString(s0, rndChar(9)+token, rndNumber(s0.length()));
        String s2 = insertString(s1, hash+rndChar(4), rndNumber(s1.length()));
        String s3 = insertString(s2, part1, rndNumber(s2.length()));
        String s4 = insertString(s3, part2, rndNumber(s3.length()));
        return s4;
    }

    public static String decodeHash(String json_encode){
        if (json_encode.startsWith("t..")){
            try {
                json_encode = json_encode.replace("t..","");
                StringBuilder str = new StringBuilder(json_encode);
                byte[] decodedBytes = Base64.decode(str.toString().getBytes(), Base64.DEFAULT);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                json_encode = new String(decodedBytes);
//                }
            } catch (Exception e){
//                Log.d("DECODE ERROR", e.toString());
                json_encode = json_encode.replace("t..","");
            }
        } else {
            json_encode = json_encode.replace("u--","");
        }
//        String token = "pTL!{rJSY.@[zd";
        String json_decode = "";
//        String part1_api = "XQzN&PA";
//        String part2_api = "iR#z8";
//        String hash = md5(token+constants.apiKey);
//
//        StringBuilder str = new StringBuilder(json_encode);
//        str.delete(str.indexOf(part1_api) - 3, str.indexOf(part1_api) + part1_api.length());
//
////        Log.d("STR", str.toString());
//
//        StringBuilder str1 = new StringBuilder(str);
//        str1.delete(str1.indexOf(part2_api), str1.indexOf(part2_api) + part2_api.length() + 5);
//
////        Log.d("STR1", str1.toString());
//
//        StringBuilder str2 = new StringBuilder(str1);
//        str2.delete(str2.indexOf(hash), str2.indexOf(hash) + hash.length() + 3);
//
////        Log.d("STR2", str2.toString());
//
//        StringBuilder str3 = new StringBuilder(str2);
//        str3.delete(str3.indexOf(token) - 9, str3.indexOf(token) + token.length());
//
////        Log.d("STR3", str3.toString());
//
//        StringBuilder str4 = new StringBuilder(str3);
//        str4.delete(0, 7);
//
////        Log.d("STR4", str4.toString());
//
//        StringBuilder str5 = new StringBuilder(str4);
//
//        str5.delete(str5.toString().length()-4, str5.toString().length());

//        Log.d("STR5", str5.toString());

//        byte[] decodeValue = Base64.decode(str5.toString().getBytes(), Base64.DEFAULT);

        json_decode = json_encode;

        if (json_decode.startsWith("gsa/")){
            json_decode = json_decode.replace("gsa/","https://storage.googleapis.com/vip-no-copy/");
        }
        else if (json_decode.startsWith("gsavip/")){
            json_decode = json_decode.replace("gsavip/","https://storage.googleapis.com/vip-no-copy/");
        }
        else if (json_decode.startsWith("gsarex/")){
            json_decode = json_decode.replace("gsarex/","https://storage.googleapis.com/stm870-rex/");
        }
        else if (json_decode.startsWith("gsals/")){
            json_decode = json_decode.replace("gsals/","https://storage.googleapis.com/gls-cp-pl/");
        }
        else if (json_decode.startsWith("swp/")){
            json_decode = json_decode.replace("swp/","https://storage.googleapis.com/swp-cdla/");
        }
        else if (json_decode.startsWith("bit/")){
            json_decode = json_decode.replace("bit/","https://storage.googleapis.com/bit-ls665/");
        }
        else if (json_decode.startsWith("xyz/")){
            json_decode = json_decode.replace("xyz/","https://dl.dropboxusercontent.com/s/");
        }
        else if (json_decode.startsWith("bit1/")){
            json_decode = json_decode.replace("bit1/","https://storage.googleapis.com/bit1-ls665/");
        }
        else if (json_decode.startsWith("bi2/")){
            json_decode = json_decode.replace("bi2/","https://storage.googleapis.com/bi2-ls665/");
        }
        else if (json_decode.startsWith("b3t/")){
            json_decode = json_decode.replace("b3t/","https://storage.googleapis.com/b3t-ls665/");
        }
        else if (json_decode.startsWith("4it/")){
            json_decode = json_decode.replace("4it/","https://storage.googleapis.com/4it-ls665/");
        }
        else if (json_decode.startsWith("5bit/")){
            json_decode = json_decode.replace("5bit/","https://storage.googleapis.com/5bit-ls665/");
        }
        else if (json_decode.startsWith("b6it/")){
            json_decode = json_decode.replace("b6it/","https://storage.googleapis.com/b6it-ls665/");
        }
        else if (json_decode.startsWith("bi7t/")) {
            json_decode = json_decode.replace("bi7t/", "https://storage.googleapis.com/bi7t-ls665/");
        }
        else if (json_decode.startsWith("apps/")) {
            json_decode = json_decode.replace("apps/", "https://storage.googleapis.com/"+Constant.APP_SEG+"/");
        }
        else if (json_decode.startsWith("uhd/")) {
            json_decode = decodeUploadHDVideo(json_decode);
        }

        if (json_decode.endsWith(".tar")){
            json_decode = json_decode.replace(".tar",".mkv");
        }
        else if (json_decode.endsWith(".lnk")){
            json_decode = json_decode.replace(".lnk",".mp4");
        }

//        json_decode = String.valueOf(decodedString);

//        Log.d("DECODE 64BIT", json_decode);

        return json_decode;
    }

    public static String decodeUploadHDVideo(String url){
        return url;
    }
}

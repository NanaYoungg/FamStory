package com.hongsam.famstory.controller;

public class SharedManager {

//    private static MainActivity mainActivity;
//
//    private static SharedPreferences pref;
//
//    public static void getInstance(MainActivity main) {
//        if (mainActivity == null) {
//            mainActivity = main;
//            pref = mainActivity.getSharedPreferences(mainActivity.getPackageName(), Activity.MODE_PRIVATE);
//        }
//    }
//
//    private SharedManager() {
//    }
//
//    public static String readString(String key, String defValue) {
//        return pref.getString(key, defValue);
//    }
//
//    public static void writeString(String key, String value) {
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString(key, value);
//        editor.apply();
//    }
//
//    public static int readInteger(String key, int defValue) {
//        return pref.getInt(key, defValue);
//    }
//
//    public static void writeInteger(String key, int value) {
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putInt(key, value);
//        editor.apply();
//    }
//
//    public static boolean readBoolean(String key, boolean defValue) {
//        return pref.getBoolean(key, defValue);
//    }
//
//    public static void writeBoolean(String key, boolean value) {
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putBoolean(key, value);
//        editor.apply();
//    }
//
//    public static void writeObject(String key, Object data) {
//        SharedPreferences.Editor editor = pref.edit();
//
//        Gson gson = new Gson();
//        String json = gson.toJson(data);
//        editor.putString(key, json);
//        editor.commit();
//    }
//
//    public static Object readObject(String key, Class<?> targetClass) {
//        Gson gson = new Gson();
//        String json = pref.getString(key, "");
//
//        if (json.isEmpty()) {
//            return null;
//        } else {
//            Object object = gson.fromJson(json, targetClass);
//            return object;
//        }
//    }
//
//    public static void removePreferences(String key) {
//        SharedPreferences.Editor editor = pref.edit();
//        editor.remove(key);
//        editor.apply();
//    }
//
//
}

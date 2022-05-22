package pepe.pisau.bos.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {
    private static SharedPreferences sharedPreferences;
    private static final String USER_ID = "uidx";
    private static final String COUNTER_ID = "counter";
    private static final String STATUS_LOGIN = "STATUS";

    private static Context mContext;

    Pref(Context ctx){
        mContext = ctx;
        sharedPreferences = mContext.getSharedPreferences("APK", Context.MODE_PRIVATE);
    }

    public static void saveUid(String uid){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(USER_ID, uid);
        edit.apply();
    }

    public static String getUid(){
        return sharedPreferences.getString(USER_ID, " ");
    }

    public static String getUUid(){
        return sharedPreferences.getString(USER_ID, " ");
    }

    public static void saveCounterID(int counter){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(COUNTER_ID, counter);
        edit.apply();
    }

    public static int getCounterId(){
        return sharedPreferences.getInt(COUNTER_ID, 1);
    }

    public static void setStatusLogin(boolean status){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(STATUS_LOGIN, status);
        edit.apply();
    }

    public static boolean cekStatus(){
        return sharedPreferences.getBoolean(STATUS_LOGIN, false);
    }

}

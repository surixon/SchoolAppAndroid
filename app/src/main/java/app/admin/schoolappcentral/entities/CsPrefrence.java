package app.admin.schoolappcentral.entities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rishav on 4/1/18.
 */

public class CsPrefrence {
    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("CS_PREF", Context.MODE_PRIVATE);
    }

    public static String readString(Context context, String key) {
        return getPreferences(context).getString(key, "");
    }

    public static void putString(Context context, String key, String value) {
        getPreferences(context).edit().putString(key, value).apply();
    }
    public static String readUsertype(Context context, String key) {
        return getPreferences(context).getString(key, "");
    }

    public static void putUsertype(Context context, String key, String value) {
        getPreferences(context).edit().putString(key, value).apply();
    }

    public static void putJobid(Context context, String key, String value) {
        getPreferences(context).edit().putString(key, value).apply();
    }

    public static String readJobid(Context context, String key) {
        return getPreferences(context).getString(key, "");
    }
    public static void putemployid(Context context, String key, String value) {
        getPreferences(context).edit().putString(key, value).apply();
    }

    public static String reademployid(Context context, String key) {
        return getPreferences(context).getString(key, "");
    }
    public static void clear(Context context, String key, String value) {
        getPreferences(context).edit().clear();
    }

}

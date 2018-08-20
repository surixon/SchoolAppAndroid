package app.admin.schoolappcentral.healpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import app.admin.schoolappcentral.entities.userprofileview;

/**
 * Created by admin on 03/03/2016.
 */
public class SHAREDPREF
{
    private Context context;
    private SharedPreferences sharedPref;

    public SHAREDPREF(Context con)
    {
        this.context=con;
        sharedPref = con.getSharedPreferences("SMARTSCHOOL", Context.MODE_PRIVATE);
    }

    public void SignOut()
    {
        this.putLoginToken("");
        this.putLoginTokenExpire("");
        this.putdeviceToken("");
        this.putLoginUser(null);
    }

    public String getLoginToken()
    {
        String logintoken=sharedPref.getString("LOGEDINTOKEN", "");
        return logintoken;
    }

    public void putLoginToken(String logintoken)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("LOGEDINTOKEN", logintoken);
        editor.apply();
    }

    public String getLoginTokenExpire()
    {
        String logintoken=sharedPref.getString("LOGEDINTOKENEXP", "");
        return logintoken;
    }

    public void putLoginTokenExpire(String logintoken)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("LOGEDINTOKENEXP", logintoken);
        editor.apply();
    }

    public userprofileview getLoginUser()
    {
        String loginuser=sharedPref.getString("LOGINUSER", "");
        Gson g = new Gson();
        userprofileview userprofileview = g.fromJson(loginuser,userprofileview.class);
        return userprofileview;
    }

    public void putLoginUser(userprofileview loginuser)
    {
        Gson g = new Gson();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("LOGINUSER",g.toJson(loginuser));
        editor.apply();
    }

    public String getdeviceToken()
    {
        String token=sharedPref.getString("DEVICETOKEN", "");
        return token;
    }

    public void putdeviceToken(String token)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("DEVICETOKEN", token);
        editor.apply();
    }
}

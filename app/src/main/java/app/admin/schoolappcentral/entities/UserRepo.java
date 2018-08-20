package app.admin.schoolappcentral.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.LoginResponse;
import app.admin.schoolappcentral.responces.StringResp;
import app.admin.schoolappcentral.responces.UserProfileResponse;

/**
 * Created by admin on 01/11/2017.
 */

public class UserRepo
{
    private Context context;

    public UserRepo(Context context)
    {
        this.context=context;
    }

    public void Login(final dataEventListner listner, String username, String password)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                    .setBodyParameter("action","login")
                    .setBodyParameter("grant_type","guest@123")
                    .setBodyParameter("username", username)
                    .setBodyParameter("password", password)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject jsonObject)
                        {
                            Log.d("LoGINResponse",jsonObject.toString()+"");
                            if (jsonObject != null)
                            {

                                Gson gson = new Gson();
                                LoginResponse responce = gson.fromJson(jsonObject.toString(), LoginResponse.class);
                                listner.onDataEvent(responce, null);
                            }
                            else
                            {
                                listner.onDataEvent(null, e);
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }

    public void RegisterDevice(final dataEventListner listner, String deviceid, String registrationtoken)
    {
        String URL = APPCONSTANTS.API_ENDPOINT+"User/registerdevice";
        try
        {
            Ion.with(context)
                    .load(URL)
                 //   .setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("deviceid", deviceid)
                    .setBodyParameter("notificationtoken", registrationtoken)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject jsonObject)
                        {
                            if (jsonObject != null)
                            {
                                Gson gson = new Gson();
                                StringResp responce = gson.fromJson(jsonObject.toString(), StringResp.class);
                                listner.onDataEvent(responce.getString(), null);
                            }
                            else
                            {
                                listner.onDataEvent(null, e);
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }

    public void GetUserDetail(final dataEventListner listner)
    {
        String URL = APPCONSTANTS.API_ENDPOINT+"User/getuserdetail";
        try
        {
            Ion.with(context)
                    .load(URL)
                    .setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("i","0")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject jsonObject)
                        {
                            if (jsonObject != null)
                            {
                                Gson gson = new Gson();
                                UserProfileResponse responce = gson.fromJson(jsonObject.toString(), UserProfileResponse.class);

                                if(responce.getMessage() != null)
                                {
                                    if(responce.getMessage().equals("Authorization has been denied for this request."))
                                    {
                                        Exception exception = new Exception("INVALIDLOGIN");
                                        listner.onDataEvent(null, exception);
                                    }
                                    else
                                    {
                                        Exception exception = new Exception(responce.getMessage());
                                        listner.onDataEvent(null, exception);
                                    }
                                }
                                else
                                {
                                    listner.onDataEvent(responce.getResponse(), null);
                                }
                            }
                            else
                            {
                                listner.onDataEvent(null, e);
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }

    public void RegisterGuest(final dataEventListner listner, String deviceid)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        Log.e("deviceid",deviceid+"");
        try
        {
            Ion.with(context)
                    .load(URL)
                   // .addHeader("Content-Type", "application/x-www-form-urlencoded")


                    .setBodyParameter("action","registerguest")
                    .setBodyParameter("device_id", deviceid)
                    .setBodyParameter("device_type","A")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()

                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject jsonObject)
                        {
                            if (jsonObject != null)
                            {
                                Log.d("RegisterGuest",jsonObject+"");
                                Gson gson = new Gson();
                                StringResp responce = gson.fromJson(jsonObject.toString(), StringResp.class);
                                listner.onDataEvent(responce.getString(), null);
                            }
                            else
                            {
                                listner.onDataEvent(null, e);
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }

    public void RegisterStudent(final dataEventListner listner, String deviceid)
    {
        String URL = APPCONSTANTS.API_ENDPOINT+"User/registerstudent";
        try
        {
            Ion.with(context)
                    .load(URL)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .setBodyParameter("device_id", deviceid)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject jsonObject)
                        {
                            if (jsonObject != null)
                            {
                                Gson gson = new Gson();
                                StringResp responce = gson.fromJson(jsonObject.toString(), StringResp.class);
                                listner.onDataEvent(responce.getString(), null);
                            }
                            else
                            {
                                listner.onDataEvent(null, e);
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }

    public void RegisterParent(final dataEventListner listner, String deviceid)
    {
        String URL = APPCONSTANTS.API_ENDPOINT+"User/registerparent";
        try
        {
            Ion.with(context)
                    .load(URL)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .setBodyParameter("device_id", deviceid)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject jsonObject)
                        {
                            if (jsonObject != null)
                            {
                                Gson gson = new Gson();
                                StringResp responce = gson.fromJson(jsonObject.toString(), StringResp.class);
                                listner.onDataEvent(responce.getString(), null);
                            }
                            else
                            {
                                listner.onDataEvent(null, e);
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }

    public void validateToken(final dataEventListner listner)
    {
        String URL = APPCONSTANTS.API_ENDPOINT+"User/validatetoken";
        try
        {
            Ion.with(context)
                    .load("GET",URL)
                    .setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result) {
                            if (e == null)
                            {
                                //Log.d("ValidToken",result.getResult());
                                if (result.getHeaders().code() == 200) {
                                    Gson gson = new Gson();
                                    StringResp responce = gson.fromJson(result.getResult(), StringResp.class);

                                    if (responce.getMessage() != null) {
                                        Exception exception = new Exception(responce.getMessage());
                                        listner.onDataEvent(null, exception);
                                    } else {
                                        listner.onDataEvent(responce.getString(), null);
                                    }
                                } else {
                                    Exception exception = new Exception(String.valueOf(result.getHeaders().code()));
                                    listner.onDataEvent(null, exception);
                                }
                            }
                            else
                            {
                                listner.onDataEvent(null, e);
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }
}

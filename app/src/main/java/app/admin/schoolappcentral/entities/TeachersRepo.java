package app.admin.schoolappcentral.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.StringResp;
import app.admin.schoolappcentral.responces.TeachersResponse;

/**
 * Created by admin on 09/02/2017.
 */

public class TeachersRepo
{
    private Context context;

    public TeachersRepo(Context context)
    {
        this.context=context;
    }

    public void getallteachers(final dataEventListner listner)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                  //  .setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("action","getteachers")
                    .setBodyParameter("pageNo","1")
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                         /*   if (result.getHeaders().code() ==  200)
                            {*/
                                Log.d("Getteachers",result.getResult()+"");
                                Gson gson = new Gson();
                                TeachersResponse responce = gson.fromJson(result.getResult(), TeachersResponse.class);

                                if(responce.getMessage() != null)
                                {
                                    Exception exception = new Exception(responce.getMessage());
                                    listner.onDataEvent(null, exception);
                                }
                                else
                                {
                                    listner.onDataEvent(responce.getTeacherArrayList(), null);
                                }
                          /*  }
                            else
                            {
                                Exception exception = new Exception(String.valueOf(result.getHeaders().code()));
                                listner.onDataEvent(null,exception);
                            }*/
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }

    public void subscribetoteacher(final dataEventListner listner,String teacherid)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                  //  .setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("action","subscribeteacher")
                    .setBodyParameter("teacher_id",teacherid)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                          /*  if (result.getHeaders().code() ==  200)
                            {*/
                                Log.d("Subscribeteacher",result.getResult()+"");
                                Gson gson = new Gson();
                                StringResp responce = gson.fromJson(result.getResult(), StringResp.class);
                                listner.onDataEvent(responce.getString(), null);
                         /*   }
                            else
                            {
                                Exception exception = new Exception(String.valueOf(result.getHeaders().code()));
                                listner.onDataEvent(null,exception);
                            }*/
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }

    public void unsubscribetoteacher(final dataEventListner listner,String teacherid)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                    //.setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("action","unsubscribeteacher")
                    .setBodyParameter("teacher_id",teacherid)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                           /* if (result.getHeaders().code() ==  200)
                            {*/
                                Log.d("Unsubscribeteacher",result.getResult()+"");
                                Gson gson = new Gson();
                                StringResp responce = gson.fromJson(result.getResult(), StringResp.class);

                                listner.onDataEvent(responce.getString(), null);
                            /*}
                            else
                            {
                                Exception exception = new Exception(String.valueOf(result.getHeaders().code()));
                                listner.onDataEvent(null,exception);
                            }*/
                        }
                    });
        }
        catch (Exception e)
        {
            listner.onDataEvent(null,e);
        }
    }
}

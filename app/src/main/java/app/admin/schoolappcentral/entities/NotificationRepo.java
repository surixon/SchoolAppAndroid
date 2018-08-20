package app.admin.schoolappcentral.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.MessageResponse;
import app.admin.schoolappcentral.responces.MessageResponseNew;

/**
 * Created by admin on 01/18/2017.
 */

public class NotificationRepo
{
    private Context context;

    public NotificationRepo(Context context)
    {
        this.context=context;
    }

    public void getinboxmessages(final dataEventListner listner)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                   // .setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("action","getnotifications")
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                            Log.d("Getnotifications",result.getResult()+"");
                         /*   if (result.getHeaders().code() ==  200)
                            {*/
                                Gson gson = new Gson();
                                MessageResponseNew responce = gson.fromJson(result.getResult(), MessageResponseNew.class);
                                if(responce.getMessage() != null)
                                {
                                    Exception exception = new Exception(responce.getMessage());
                                    listner.onDataEvent(null, exception);
                                }
                                else
                                {
                                    listner.onDataEvent(responce.getNotificationArrayList(), null);
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

    public void getlatestmessages(final dataEventListner listner)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                    //.setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("action","getlatestnotification")
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                            Log.d("Getlatestnotification",result.getResult()+"");
                           // if (result.getHeaders().code() ==  200)


                                Gson gson = new Gson();
                                MessageResponse responce = gson.fromJson(result.getResult(), MessageResponse.class);
                                if(responce.getMessage() != null)
                                {
                                    Exception exception = new Exception(responce.getMessage());
                                    listner.onDataEvent(null, exception);
                                }
                                else
                                {
                                    listner.onDataEvent(responce.getNotificationArrayList(), null);
                                }

                          /*  else
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

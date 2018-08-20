package app.admin.schoolappcentral.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.EventBean;
import app.admin.schoolappcentral.responces.EventsResponse;

/**
 * Created by admin on 09/02/2017.
 */

public class EventsRepo
{
    private Context context;
    public static EventsResponse responce;

    public EventsRepo(Context context)
    {
        this.context=context;
    }

    public void getallevents(final dataEventListner listner)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                    .setBodyParameter("action","getevents")
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                         /*   if (result.getHeaders().code() ==  200)
                            {*/
                                Log.d("Getevents",result.getResult()+"");
                                Gson gson = new Gson();
                                EventBean responce = gson.fromJson(result.getResult(), EventBean.class);


                                Log.e("mess","m"+responce.Error.Message);

                                Log.e("reponse","reponse"+responce.Response);
                               /* Log.e("reponse","reponse"+responce.Response.get(0).event_id);
                                Log.e("reponse","reponse"+responce.Response.get(0).event_date);
                                Log.e("reponse","reponse"+responce.Response.get(0).event_name);*/







                                 listner.onDataEvent(responce.Response, null);


                                if(responce.Error.Message!= null)
                                {
                                    Exception exception = new Exception(responce.Error.Message);
                                    listner.onDataEvent(null, exception);
                                }
                                else
                                {
                                    listner.onDataEvent(responce.Response, null);
                                }
                       /*     }
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

    public void getlatestevents(final dataEventListner listner)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                    .setBodyParameter("action","getlatestevents")
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                          /*  if (result.getHeaders().code() ==  200)
                            {*/
                                Log.d("Getlatestevents",result.getResult()+"");
                                Gson gson = new Gson();
                                responce = gson.fromJson(result.getResult(), EventsResponse.class);
                                if(!responce.getError().getMessage().equals("success"))
                                {
                                    Exception exception = new Exception(responce.getError().getMessage());
                                    listner.onDataEvent(null, exception);
                                }
                                else
                                {
                                    listner.onDataEvent(responce, null);
                                }
                            //}
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

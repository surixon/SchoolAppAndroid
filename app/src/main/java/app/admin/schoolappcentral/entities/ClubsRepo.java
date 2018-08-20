package app.admin.schoolappcentral.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.ClubsResponse;
import app.admin.schoolappcentral.responces.StringResp;

/**
 * Created by admin on 09/02/2017.
 */

public class ClubsRepo
{
    private Context context;

    public ClubsRepo(Context context)
    {
        this.context=context;
    }

    public void getallclubs(final dataEventListner listner)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                    .setBodyParameter("action","getclubs")
                   // .setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("pageNo","1")
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                        /*    if (result.getHeaders().code() ==  200)
                            {*/
                                Log.d("Getclubs",result.getResult()+"");
                                Gson gson = new Gson();
                                ClubsResponse responce = gson.fromJson(result.getResult(), ClubsResponse.class);
                                if(responce.getMessage() != null)
                                {
                                    Exception exception = new Exception(responce.getMessage());
                                    listner.onDataEvent(null, exception);
                                }
                                else
                                {
                                    listner.onDataEvent(responce.getClubsArrayList(), null);
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

    public void subscribetoclub(final dataEventListner listner,String clubid)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                    .setBodyParameter("action","subscribeclub")
                   // .setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("club_id",clubid)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                          /*  if (result.getHeaders().code() ==  200)
                            {*/
                                Log.d("Subscribeclub",result.getResult()+"");
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

    public void unsubscribetoclub(final dataEventListner listner,String clubid)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                    .setBodyParameter("action","unsubscribeclub")
                    //.setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("club_id",clubid)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                          /*  if (result.getHeaders().code() ==  200)
                            {*/
                                Log.d("Unsubscribeclub",result.getResult()+"");
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
}

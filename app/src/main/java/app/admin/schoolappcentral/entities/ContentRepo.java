package app.admin.schoolappcentral.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.SchoolProfileResponse;

/**
 * Created by admin on 11/02/2017.
 */

public class ContentRepo
{
    private Context context;

    public ContentRepo(Context context)
    {
        this.context=context;
    }

    public void getschoolprofile(final dataEventListner listner)
    {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try
        {
            Ion.with(context)
                    .load(URL)
                    .setBodyParameter("action","getschoolprofile")
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>()
                    {
                        @Override
                        public void onCompleted(Exception e, Response<String> result)
                        {
                            Log.d("GetSchoolProfile",result.getResult()+"");
                         //   if (result.getHeaders().code() ==  200)

                             //   Log.d("GetSchoolProfile",result.getResult()+"");
                                Gson gson = new Gson();
                                SchoolProfileResponse responce = gson.fromJson(result.getResult(), SchoolProfileResponse.class);
                                if(responce.getMessage() != null)
                                {
                                    Exception exception = new Exception(responce.getMessage());
                                    listner.onDataEvent(null, exception);
                                }
                                else
                                {
                                    listner.onDataEvent(responce.getSchoolProfile(), null);
                                }

                         /*   else
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

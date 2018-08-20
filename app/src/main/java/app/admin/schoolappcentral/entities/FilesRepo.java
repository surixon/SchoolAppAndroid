package app.admin.schoolappcentral.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.FilesResponse;
import app.admin.schoolappcentral.responces.FilesResponseNew;

/**
 * Created by admin on 09/02/2017.
 */

public class FilesRepo {
    private Context context;
    public static FilesResponseNew responce;

    public FilesRepo(Context context) {
        this.context = context;
    }

    public void getfiles(final dataEventListner listner) {
        String URL = APPCONSTANTS.API_ENDPOINT;
        try {
            Ion.with(context)
                    .load(URL)
                    .setBodyParameter("action", "getfiles")
                    // .setHeader("Authorization", "bearer "+APPCONSTANTS.LOGINTOKEN)
                    .setBodyParameter("pageNo", "1")
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>() {
                        @Override
                        public void onCompleted(Exception e, Response<String> result) {
                            if (result.getHeaders().code() == 200) {
                                Log.d("Getfiles", result.getResult() + "");
                                Gson gson = new Gson();
                                responce = gson.fromJson(result.getResult(), FilesResponseNew.class);
                                if (!responce.getError().getMessage().equals("success")) {
                                    Exception exception = new Exception(responce.getError().getDisplayMessage());
                                    listner.onDataEvent(null, exception);
                                } else {
                                    listner.onDataEvent(responce.getResponse(), null);
                                }
                            } else {
                                Exception exception = new Exception(String.valueOf(result.getHeaders().code()));
                                listner.onDataEvent(null, exception);
                            }
                        }
                    });
        } catch (Exception e) {
            listner.onDataEvent(null, e);
        }
    }
}

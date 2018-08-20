package app.admin.schoolappcentral.healpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by bhargav on 9/3/2014.
 */
public class ConnectionDetector
{
    private Context _context;
    public Boolean ISCONNECTED=false;

    public ConnectionDetector(Context context)
    {
        this._context = context;
        ISCONNECTED=isConnected();
    }

    private boolean isConnected()
    {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
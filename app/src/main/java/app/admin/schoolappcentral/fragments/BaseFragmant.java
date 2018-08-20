package app.admin.schoolappcentral.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import app.admin.schoolappcentral.R;

/**
 * Created by admin on 01/11/2017.
 */

public abstract class BaseFragmant extends Fragment
{
    private ProgressDialog progress;

    public void showprogressbar(Boolean IS_SHOW)
    {
        if (IS_SHOW)
        {
            if(progress == null)
            {
                progress = new ProgressDialog(getActivity(), R.style.ProgressDialog);
                progress.setMessage("Please wait...");
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
            }
            else
            {
                progress.show();
            }
        }
        else
        {
            if (progress.isShowing())
            {
                progress.dismiss();
            }
        }
    }

    public void showmwssagealert(String message,String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.setButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        alert.show();
    }
}

package app.admin.schoolappcentral;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;

import app.admin.schoolappcentral.entities.CsPrefrence;
import app.admin.schoolappcentral.entities.UserRepo;
import app.admin.schoolappcentral.entities.userprofileview;
import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.ConnectionDetector;
import app.admin.schoolappcentral.healpers.SHAREDPREF;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.LoginResponse;

public class UserSelectionActivity extends AppCompatActivity {
    private ProgressDialog progress;

    public static final int READ_PHONE_STATE = 100;
    public static final int WRITE_EXTERNAL_STORAGE = 101;
    public static final int CALL_PHONE = 102;

    private static String FLAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        ((AppCompatActivity) UserSelectionActivity.this).getSupportActionBar().hide();

        ((TextView) findViewById(R.id.btn_user_guest)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FLAG = "GUEST";
                if (ActivityCompat.checkSelfPermission(UserSelectionActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    //TODO : PERMISSION CHECK
                    if (ActivityCompat.shouldShowRequestPermissionRationale(UserSelectionActivity.this, android.Manifest.permission.READ_PHONE_STATE)) {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserSelectionActivity.this);
                        builder.setTitle("Need Call Permission");
                        builder.setMessage("This app needs permission to make call.It will be charge you as per your carrier service.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                ActivityCompat.requestPermissions(UserSelectionActivity.this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else {
                        ActivityCompat.requestPermissions(UserSelectionActivity.this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);
                    }
                } else {
                    registerguest();
                }

            }
        });
    }

    private void registerguest() {
        FirebaseCrash.log("GUEST REGISTER CLICKED");
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if (connectionDetector.ISCONNECTED) {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

             String  Access_Token= CsPrefrence.readString(this,"fcmtoken");
            final String deviceid = FirebaseInstanceId.getInstance().getToken();
            Log.e("Access_Token", Access_Token + "");

            showprogressbar(true);
            UserRepo usersrepo = new UserRepo(getBaseContext());
            usersrepo.RegisterGuest(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    showprogressbar(false);
                    if (e == null) {
                        loginuser(deviceid, "guest");
                    } else {
                        FirebaseCrash.report(e);

                        if (e.getMessage() != null) {
                            Toast.makeText(UserSelectionActivity.this, R.string.txt_unspecifiederror_message, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UserSelectionActivity.this, R.string.txt_unspecifiederror_message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }, deviceid);
        } else {
            showprogressbar(false);
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message), getApplicationContext().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    private void registerparent() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if (connectionDetector.ISCONNECTED) {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String  Access_Token= CsPrefrence.readString(this,"fcmtoken");
            final String deviceid = Access_Token;

            showprogressbar(true);
            UserRepo usersrepo = new UserRepo(getBaseContext());
            usersrepo.RegisterParent(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    showprogressbar(false);
                    if (e == null) {
                        loginuser(deviceid, "parent");
                    } else {
                        FirebaseCrash.report(e);
                        if (e.getMessage() != null) {
                            Toast.makeText(UserSelectionActivity.this, R.string.txt_unspecifiederror_message, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UserSelectionActivity.this, R.string.txt_unspecifiederror_message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }, deviceid);
        } else {
            showprogressbar(false);
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message), getApplicationContext().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    private void registerstudent() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if (connectionDetector.ISCONNECTED) {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String  Access_Token= CsPrefrence.readString(this,"fcmtoken");
            final String deviceid = Access_Token;

            showprogressbar(true);
            UserRepo usersrepo = new UserRepo(getBaseContext());
            usersrepo.RegisterStudent(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e)
                {
                    showprogressbar(false);
                    if (e == null)
                    {
                        loginuser(deviceid, "student");
                    } else
                    {
                        FirebaseCrash.report(e);
                        if (e.getMessage() != null) {
                            Toast.makeText(UserSelectionActivity.this, R.string.txt_unspecifiederror_message, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UserSelectionActivity.this, R.string.txt_unspecifiederror_message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }, deviceid);
        }
        else
        {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message),getApplicationContext().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    private void loginuser(final String username,final String role)
    {
        ConnectionDetector connectionDetector=new ConnectionDetector(getApplicationContext());
        if(connectionDetector.ISCONNECTED)
        {
            showprogressbar(true);
            UserRepo usersrepo = new UserRepo(getBaseContext());
            usersrepo.Login(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    showprogressbar(false);

                    if (e == null)
                    {
                        LoginResponse resp = (LoginResponse) eventArguments;

                        new SHAREDPREF(getApplicationContext()).putLoginToken(resp.getAccess_token());

                        new SHAREDPREF(getApplicationContext()).putLoginTokenExpire(String.valueOf(resp.getExpires()));

                        APPCONSTANTS.LOGINTOKEN = resp.getAccess_token();
                        // get user detail

                        userprofileview upv = new userprofileview();
                        upv.setName(username);
                        upv.setRole(role);
                        new SHAREDPREF(getApplicationContext()).putLoginUser(upv);

                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    } else
                    {
                        FirebaseCrash.report(e);
                        showmwssagealert(getResources().getString(R.string.txt_unabletologin_message),getApplicationContext().getResources().getString(R.string.txt_unabletologin_title));
                    }
                }
            }, username, "guest@123");
        }
        else
        {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message),getApplicationContext().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    public void showprogressbar(Boolean IS_SHOW)
    {
        if (IS_SHOW)
        {
            if(progress == null)
            {
                progress = new ProgressDialog(UserSelectionActivity.this, R.style.ProgressDialog);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UserSelectionActivity.this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(requestCode == READ_PHONE_STATE && FLAG == "GUEST")
            {
                registerguest();
            }
            if(requestCode == READ_PHONE_STATE && FLAG == "PARENT")
            {
                registerparent();
            }
            if(requestCode == READ_PHONE_STATE && FLAG == "STUDENT")
            {
                registerstudent();
            }
        }
    }
}

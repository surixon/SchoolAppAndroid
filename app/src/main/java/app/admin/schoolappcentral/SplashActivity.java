package app.admin.schoolappcentral;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
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

public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 20;
    public static final int READ_PHONE_STATE = 100;

    private TextView textViewProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ((AppCompatActivity) SplashActivity.this).getSupportActionBar().hide();

        textViewProgress = (TextView) findViewById(R.id.txt_progress);

        APPCONSTANTS.LOGINTOKEN = new SHAREDPREF(getApplicationContext()).getLoginToken();

        if (!APPCONSTANTS.LOGINTOKEN.equals("")) {
            validatetoken();
        } else {
            /*Intent i = new Intent(getBaseContext(), UserSelectionActivity.class);
            startActivity(i);
            finish();*/

            if (ActivityCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //TODO : PERMISSION CHECK
                if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, android.Manifest.permission.READ_PHONE_STATE)) {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SplashActivity.this);
                    builder.setTitle("Need Call Permission");
                    builder.setMessage("This app needs permission to make call.It will be charge you as per your carrier service.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);
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
                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);
                }
            } else {
                registerguest();
            }
        }
    }

   /* @Override
    public void onPermissionsGranted(int requestCode)
    {
        Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }*/

    private void validatetoken() {
        textViewProgress.setText("Validating Token....");
        ConnectionDetector connectionDetector = new ConnectionDetector(SplashActivity.this);
        if (connectionDetector.ISCONNECTED) {
            UserRepo uRepo = new UserRepo(getApplicationContext());
            uRepo.validateToken(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    if (e == null) {
                        String response = (String) eventArguments;
                        Log.e("helllo", response + "");
                        if (response != null) {
                            if (response.equals("SUCCESS")) {
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                userprofileview up = new SHAREDPREF(getApplicationContext()).getLoginUser();
                                loginuser(up.getName(), up.getRole());
                            }
                        }
                    } else {
                        Intent i = new Intent(getBaseContext(), UserSelectionActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
        } else {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message), getResources().getString(R.string.txt_nointernet_title));
        }
    }

    private void loginuser(final String username, final String role) {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if (connectionDetector.ISCONNECTED) {
            UserRepo usersrepo = new UserRepo(getBaseContext());
            usersrepo.Login(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    Log.e("Exception",e+"");

                    if (e == null) {
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

                    } else {
                        FirebaseCrash.report(e);
                        showmwssagealert(getResources().getString(R.string.txt_unabletologin_message), getApplicationContext().getResources().getString(R.string.txt_unabletologin_title));
                    }
                }
            }, username, "guest@123");
        } else {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message), getApplicationContext().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    public void showmwssagealert(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registerguest();
        }
    }

    private void registerguest() {
        FirebaseCrash.log("GUEST REGISTER CLICKED");

        textViewProgress.setText("Registering Your Device....");

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

            String Access_Token= CsPrefrence.readString(this,"fcmtoken");
            Log.e("refreshedToken",Access_Token+"");
             final String deviceid = FirebaseInstanceId.getInstance().getToken();


            /*showprogressbar(true);*/
            UserRepo usersrepo = new UserRepo(getBaseContext());
            usersrepo.RegisterGuest(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e)
                {
//                   Log.e("mmmm",e.getMessage()+"");
                   /* showprogressbar(false);*/
                    if (e == null)
                    {
                        textViewProgress.setText("Validating Device....");
                        Log.d("TokenValid",deviceid+"  l");
                        loginuser(deviceid, "guest");
                    }
                    else
                    {
                        FirebaseCrash.report(e);
                        Log.e("kkkkk",e.getMessage()+"");
                        if (e.getMessage() != null)
                        {
                            Toast.makeText(SplashActivity.this, R.string.txt_unspecifiederror_message, Toast.LENGTH_LONG).show();
                        } else
                        {
                            Toast.makeText(SplashActivity.this, R.string.txt_unspecifiederror_message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }, deviceid);
        }
        else
        {
            /*showprogressbar(false);*/
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message),getApplicationContext().getResources().getString(R.string.txt_nointernet_title));
        }
    }
}

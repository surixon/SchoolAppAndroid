package app.admin.schoolappcentral;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import app.admin.schoolappcentral.entities.ContentRepo;
import app.admin.schoolappcentral.entities.Schoolprofile;
import app.admin.schoolappcentral.fragments.AboutFragment;
import app.admin.schoolappcentral.fragments.ClubsOrganizationsFragment;
import app.admin.schoolappcentral.fragments.DevInfoFragment;
import app.admin.schoolappcentral.fragments.DocumentsFragment;
import app.admin.schoolappcentral.fragments.EventsFragment;
import app.admin.schoolappcentral.fragments.HomeFragment;
import app.admin.schoolappcentral.fragments.LinksFragment;
import app.admin.schoolappcentral.fragments.LoginFragment;
import app.admin.schoolappcentral.fragments.NotificationFragment;
import app.admin.schoolappcentral.fragments.TeacherFragment;
import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.SHAREDPREF;
import app.admin.schoolappcentral.healpers.dataEventListner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    public static final int READ_PHONE_STATE = 100;
    public static final int WRITE_EXTERNAL_STORAGE = 101;
    public static final int CALL_PHONE = 102;

    private Fragment fragment = null;
    private FragmentManager fragmentManager;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FirebaseCrash.log("Main Activity created");

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_camera);

        registerdevice();

        loadschoolprofile();

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getFragmentManager();

        fragment=new HomeFragment();
        loadfragment(false);
    }

    public void setActionBarTitle(String title)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(getApplicationContext().getResources().getString(R.string.app_name));

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            if (getFragmentManager().getBackStackEntryCount() > 0)
            {
                getFragmentManager().popBackStack();
            }
            else
            {
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                {
                    super.onBackPressed();
                    return;
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Press back button again in order to exit", Toast.LENGTH_SHORT).show();
                }

                mBackPressed = System.currentTimeMillis();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
       return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
       /* MenuItem itemsignin = menu.findItem(R.id.action_signin);

        MenuItem itemsignout = menu.findItem(R.id.action_signout);

        if(new SHAREDPREF(getApplicationContext()).getLoginToken().equals(""))
        {
            if (itemsignin != null)
            {
                itemsignin.setVisible(true);
            }
            if (itemsignout != null)
            {
                itemsignout.setVisible(false);
            }
        }
        else
        {
            if (itemsignin != null)
            {
                itemsignin.setVisible(false);
            }
            if (itemsignout != null)
            {
                itemsignout.setVisible(true);
            }
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_signout)
//        {
//            new SHAREDPREF(getApplicationContext()).SignOut();
//            fragment = new HomeFragment();
//            loadfragment(false);
//
//            return true;
//        }
//
//        if (id == R.id.action_signin)
//        {
//            fragment = new LoginFragment();
//            loadfragment(true);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        Boolean ADDTOBACKSTAGE = true;

        int id = item.getItemId();

        if (id == R.id.nav_dashboard)
        {
            // Handle the camera action
            fragment = new HomeFragment();
            ADDTOBACKSTAGE = false;
        }
        else if (id == R.id.nav_notifications)
        {
            fragment = new NotificationFragment();
        }
        else if (id == R.id.nav_events)
        {
            fragment = new EventsFragment();
        }
        else if (id == R.id.nav_teachers)
        {
            fragment = new TeacherFragment();
        }
        else if (id == R.id.nav_schoolorganisation)
        {
            fragment = new ClubsOrganizationsFragment();
        }
        else if (id == R.id.nav_documents)
        {
            fragment = new DocumentsFragment();
        }
        else if (id == R.id.nav_links)
        {
            fragment = new LinksFragment();
        }
        else if (id == R.id.nav_links)
        {

            fragment = new LinksFragment();
        }
        else if (id == R.id.nav_aboutschool)
        {
            fragment = new AboutFragment();
        }
        else if (id == R.id.nav_developer)
        {
            fragment = new DevInfoFragment();
        }
        else if (id == R.id.nav_facebook)
        {
           openlink("FACEBOOK");
            return true;
        }
        else if (id == R.id.nav_twitter)
        {
            openlink("TWITTER");
            return true;
        }
        else if (id == R.id.nav_youtube)
        {
            openlink("YOUTUBE");
            return true;
        }
        else if (id == R.id.nav_instagram)
        {
            openlink("INSTAGRAM");
            return true;
        }
        else if (id == R.id.nav_admin)
        {
            openlink("ADMIN");
            return true;
        }
        else
        {
            fragment = new HomeFragment();
        }

        loadfragment(ADDTOBACKSTAGE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadfragment(Boolean ADDTOBACKSTAGE)
    {
        if(ADDTOBACKSTAGE)
        {
            getFragmentManager().popBackStack();
            fragmentManager.beginTransaction().add(R.id.content_main, fragment).addToBackStack("MAIN").commit();
        }
        else
        {
            getFragmentManager().popBackStack();
            fragmentManager.beginTransaction().add(R.id.content_main, fragment).commit();
        }
    }

    public void loadfragmentfromfragment(Fragment fragment1)
    {
        fragmentManager.beginTransaction().add(R.id.content_main, fragment1).addToBackStack("MAIN").commit();
    }

    private void registerdevice()
    {
        String dt = new SHAREDPREF(getApplicationContext()).getdeviceToken();
        if(1 == 1)
        {
            final String token = FirebaseInstanceId.getInstance().getToken();
            //Log.i("REGISTRATION TOKEN", token);

            APPCONSTANTS.DEVICETOKEN = token;

            FirebaseMessaging.getInstance().subscribeToTopic(MainActivity.this.getResources().getString(R.string.admin_topic_id));
           /* if(token != null)
            {
                TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                String deviceid =mngr.getDeviceId();

                UserRepo uRepo = new UserRepo(getApplicationContext());
                uRepo.RegisterDevice(new dataEventListner()
                {
                    @Override
                    public void onDataEvent(Object eventArguments, Exception e)
                    {
                        if(e == null)
                        {
                            String response = (String)eventArguments;
                            if(response != null) {
                                if (response.equals("SUCCESS")) {
                                    new SHAREDPREF(getApplicationContext()).putdeviceToken(token);
                                }
                            }
                        }
                    }
                },deviceid,token);
            }*/
        }
    }

    private void loadschoolprofile()
    {
        ContentRepo cRepo = new ContentRepo(MainActivity.this);
        cRepo.getschoolprofile(new dataEventListner()
        {
            @Override
            public void onDataEvent(Object eventArguments, Exception e)
            {
                if (e == null)
                {
                    Schoolprofile schoolprofile = (Schoolprofile)eventArguments;
                    APPCONSTANTS.SCHOOLPROFILE = schoolprofile;
                }
                else
                {
                    Toast.makeText(MainActivity.this,MainActivity.this.getResources().getString(R.string.txt_unable_load_schoolprofile),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void shareapplication()
    {

    }

    private void openlink(String type)
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        String url="";
        if(type.equals("FACEBOOK"))
        {
            url=APPCONSTANTS.SCHOOLPROFILE.getFacebook();
        }
        else if(type.equals("TWITTER"))
        {
            url=APPCONSTANTS.SCHOOLPROFILE.getTwitter();
        }
        else if(type.equals("YOUTUBE"))
        {
            url=APPCONSTANTS.SCHOOLPROFILE.getGoogleplus();
        }
        else if(type.equals("INSTAGRAM"))
        {
            url=APPCONSTANTS.SCHOOLPROFILE.getInstagram();
        }
        else if(type.equals("ADMIN"))
        {
            url=APPCONSTANTS.ADMINLOGIN;
        }

        if(!url.equals(""))
        {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
            {
                url = "http://" + url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        else
        {
            Toast.makeText(MainActivity.this,"No social media link available for this school.",Toast.LENGTH_LONG).show();
        }
    }
}
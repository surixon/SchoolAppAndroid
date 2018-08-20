package app.admin.schoolappcentral.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.entities.ContentRepo;
import app.admin.schoolappcentral.entities.Schoolprofile;
import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.ConnectionDetector;
import app.admin.schoolappcentral.healpers.dataEventListner;

/**
 * Created by admin on 11/02/2017.
 */

public class AboutFragment extends BaseFragmant
{
    private static View view;

    private TextView txt_schoolname;
    private TextView txt_schoolemail;
    private TextView txt_address;
    private TextView txt_city;
    private TextView txt_pincode;
    private TextView txt_call;
    private TextView txt_mail;
    private TextView txt_web;
    private TextView txt_insta;
    private TextView txt_gplus;
    private TextView txt_fb;
    private TextView txt_twitter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (view != null)
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
            {
                parent.removeView(view);
            }
        }

        view = inflater.inflate(R.layout.fragment_about, container,false);

        txt_schoolname = (TextView)view.findViewById(R.id.txt_schoolname);
        txt_schoolemail = (TextView)view.findViewById(R.id.txt_schoolemail);
        txt_mail = (TextView)view.findViewById(R.id.txt_mail);
        txt_address = (TextView)view.findViewById(R.id.txt_address);
        txt_city = (TextView)view.findViewById(R.id.txt_city);
        txt_pincode = (TextView)view.findViewById(R.id.txt_pincode);
        txt_call = (TextView)view.findViewById(R.id.txt_call);
        txt_web = (TextView)view.findViewById(R.id.txt_web);
        txt_insta = (TextView)view.findViewById(R.id.txt_insta);
        txt_twitter = (TextView)view.findViewById(R.id.txt_twitter);
        txt_fb = (TextView)view.findViewById(R.id.txt_fb);
        txt_gplus = (TextView)view.findViewById(R.id.txt_gplus);

        ((Button)view.findViewById(R.id.btn_refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadschoolprofile();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).setActionBarTitle("About Us");

        loadschoolprofile();
    }

    private void loadschoolprofile()
    {
        ConnectionDetector connectionDetector=new ConnectionDetector(getActivity());
        if(connectionDetector.ISCONNECTED)
        {
            showprogressbar(true);
            ContentRepo cRepo = new ContentRepo(getActivity());
            cRepo.getschoolprofile(new dataEventListner()
            {
                @Override
                public void onDataEvent(Object eventArguments, Exception e)
                {
                    showprogressbar(false);
                    if (e == null)
                    {
                        ((LinearLayout)view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                        ((ScrollView)view.findViewById(R.id.layout_detail)).setVisibility(View.VISIBLE);

                        final Schoolprofile sp = (Schoolprofile)eventArguments;

                        if(sp != null)
                        {
                          /*  ImageView imageView = (ImageView) view.findViewById(R.id.imageView2);
                            Ion.with(imageView)
                                    .placeholder(R.drawable.ic_launcher)
                                    .error(R.drawable.ic_launcher)
                                    .load(APPCONSTANTS.USERIMAGEENDPOINT + sp.getImage());*/
                            APPCONSTANTS.SCHOOLPROFILE = sp;
                            txt_schoolname.setText(sp.getName());
                            txt_schoolemail.setText(sp.getEmail());
                            txt_address.setText(sp.getAddress());
                            txt_city.setText(sp.getCity());
                            txt_pincode.setText(sp.getPincode());
                            txt_call.setText(sp.getContactnumber());
                            txt_web.setText(sp.getWeburl());
                            txt_mail.setText(sp.getEmail());
                            txt_gplus.setText(sp.getGoogleplus());
                            txt_insta.setText(sp.getInstagram());
                            txt_fb.setText(sp.getFacebook());
                            txt_twitter.setText(sp.getTwitter());


                            ((android.support.v7.widget.CardView)view.findViewById(R.id.card_view_location)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                   /* String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 22.303894, 70.802160);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    getActivity().startActivity(intent);*/

                                    if(!sp.getName().equals("")) {
                                        String strUri = "http://maps.google.com/maps?q=loc:" + sp.getLatitude() + "," + sp.getLongitude() + " (" + sp.getName() + ")";
                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                        getActivity().startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.txt_nolatlong_available), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            ((android.support.v7.widget.CardView)view.findViewById(R.id.card_view_contact)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                    if(!sp.getContactnumber().equals(""))
                                    {
                                        String phone_no= sp.getContactnumber().toString().replaceAll("-", "");
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:" + phone_no));
                                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(callIntent);
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.txt_nocontact_available), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            ((android.support.v7.widget.CardView)view.findViewById(R.id.card_view_email)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                    if(!sp.getEmail().equals(""))
                                    {
                                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                                        emailIntent.setType("plain/text");
                                        emailIntent.putExtra(android.content.Intent.CATEGORY_APP_EMAIL, new String[]{sp.getEmail()});
                                        getActivity().startActivity(emailIntent);
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.txt_noemail_available), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            ((android.support.v7.widget.CardView)view.findViewById(R.id.card_view_web)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                    if(!sp.getWeburl().equals(""))
                                    {
                                        String url=sp.getWeburl();
                                        if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        {
                                            url = "http://" + url;
                                        }
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(browserIntent);
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.txt_nourl_available), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            ((LinearLayout)view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                            ((ScrollView)view.findViewById(R.id.layout_detail)).setVisibility(View.VISIBLE);
                            ((LinearLayout)view.findViewById(R.id.layout_top)).setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            ((LinearLayout)view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            ((ScrollView)view.findViewById(R.id.layout_detail)).setVisibility(View.GONE);
                            ((LinearLayout)view.findViewById(R.id.layout_top)).setVisibility(View.GONE);
                        }
                    }
                    else
                    {
                        ((LinearLayout)view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                        ((ScrollView)view.findViewById(R.id.layout_detail)).setVisibility(View.GONE);
                        ((LinearLayout)view.findViewById(R.id.layout_top)).setVisibility(View.GONE);
                    }
                }
            });
        }
        else
        {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message),getActivity().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            // call permission granted
            if(requestCode == ((MainActivity)getActivity()).CALL_PHONE)
            {
                Toast.makeText(getActivity(),"Permission granted successfully.Plese touch call button to make a call.",Toast.LENGTH_LONG).show();
            }
        }
    }
}

package app.admin.schoolappcentral.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.UserSelectionActivity;
import app.admin.schoolappcentral.entities.Notification;
import app.admin.schoolappcentral.entities.NotificationNew;
import app.admin.schoolappcentral.entities.NotificationRepo;
import app.admin.schoolappcentral.healpers.ConnectionDetector;
import app.admin.schoolappcentral.healpers.SHAREDPREF;
import app.admin.schoolappcentral.healpers.dataEventListner;

/**
 * Created by admin on 01/17/2017.
 */

public class NotificationFragment extends BaseFragmant
{
    private static View view;
    private ArrayList<NotificationNew.Response> notificationArrayList;

    private ListView notifocationListview;
    NotificationAdapter notificationAdapter;
    private Integer page=1;

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

        view = inflater.inflate(R.layout.fragment_notification, container,false);

        notifocationListview = (ListView) view.findViewById(R.id.notificationlistview);

        ((Button)view.findViewById(R.id.btn_refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadnotifications();
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Notifications");
        loadnotifications();
    }


    private void loadnotifications()
    {
        ConnectionDetector connectionDetector=new ConnectionDetector(getActivity());
        if(connectionDetector.ISCONNECTED)
        {
            showprogressbar(true);
            NotificationRepo nRepo = new NotificationRepo(getActivity());
            nRepo.getinboxmessages(new dataEventListner()
            {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    showprogressbar(false);
                    if (e == null)
                    {
                        notificationArrayList = (ArrayList<NotificationNew.Response>) eventArguments;


                        if(notificationArrayList != null)
                        {
                            if (notificationArrayList.size() > 0)
                            {
                                notifocationListview.setVisibility(View.VISIBLE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                                /*notifocationListview.setAdapter(new NotificationAdapter(getActivity(), notificationArrayList));*/
                              notificationAdapter=new NotificationAdapter(getActivity(),notificationArrayList);
                                notifocationListview.setAdapter(notificationAdapter);
                            } else {
                                notifocationListview.setVisibility(View.GONE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            notifocationListview.setVisibility(View.GONE);
                            ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                        }
                    } else
                    {
                        FirebaseCrash.report(e);

                        if (e.getMessage() != null)
                        {
                            if (e.getMessage().equals("401"))
                            {
                                new SHAREDPREF(getActivity()).SignOut();

                                Fragment f = new LoginFragment();
                                ((MainActivity) getActivity()).loadfragmentfromfragment(f);

                                Intent i = new Intent(getActivity(), UserSelectionActivity.class);
                                startActivity(i);
                                ((MainActivity)getActivity()).finish();

                            } else
                            {
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                                notifocationListview.setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                            }
                        } else
                        {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            notifocationListview.setVisibility(View.GONE);
                            ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
        else
        {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message),getActivity().getResources().getString(R.string.txt_nointernet_title));
        }
    }


    public class NotificationAdapter extends BaseAdapter {
        Context context;

       private ArrayList<NotificationNew.Response> notifications;
        LayoutInflater inflter;

        public NotificationAdapter(Context c, ArrayList<NotificationNew.Response> as) {

            this.context = c;
            this.notifications = as;





            Log.e("size",""+notifications.size());
            //Log.e("list_size","size="+notifications.size());


            inflter = (LayoutInflater.from(c));
        }

        @Override
        public int getCount() {
            return notifications.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.item_event, null);
            Log.e("values_date",notifications.get(i).created_date+"  l");
           // TextView txt_sender =  ((TextView)view.findViewById(R.id.txt_sender));
            //txt_sender .setText(notifications.get(i).event_name);

            ((TextView)view.findViewById(R.id.txt_eventdate)).setText(getDate(Long.parseLong(notifications.get(i).created_date)));
            ((TextView)view.findViewById(R.id.txt_eventtitle)).setText((notifications.get(i).event_name));

            return view;
        }
    }
    private String getDate(long time) {
        Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));

        return sdf.format(date);
    }



   /* private class NotificationAdapter extends BaseAdapter
    {
        private Context context;
        private ArrayList<Notification.Response> notifications;


        public NotificationAdapter(Context c ,ArrayList<Notification.Response> a)
        {
            this.context = c;
            this.notifications = a;
        }
        @Override
        public View getView(int i, View convertView, ViewGroup parent)
        {
            View rowView;
                LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
                rowView = inflater.inflate(R.layout.item_notofication, parent, false);

                Log.e("name","i="+" "+i+" "+notifications.get(i).event_name);





                *//*  for(int m=0;m<=notifications.size();m++)
                {*//*





                   *//* ((TextView)rowView.findViewById(R.id.txt_sender)).setText(notifications.get(i).event_name);
                    ((TextView)rowView.findViewById(R.id.txt_date)).setText(notifications.get(i).event_name);
                    ((TextView)rowView.findViewById(R.id.txt_message)).setText(notifications.get(i).event_name);*//*

                  //  Log.e("value",notifications.get(i).event_name);

              //  }
//       final Notification.Response  item=notifications.get(i);

            *//*    final Notification.Response item= notifications.get(i);
    *//**//*        Log.e("txtsender",item.getCreatedby()+"");*//**//*

            ((TextView)rowView.findViewById(R.id.txt_sender)).setText(item.type);
            ((TextView)rowView.findViewById(R.id.txt_date)).setText(item.event_name);
            ((TextView)rowView.findViewById(R.id.txt_message)).setText(item.message);*//*

            rowView.setClickable(false);

            return rowView;
        }

        @Override
        public int getCount()
        {
            return notifications.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
    }*/
}

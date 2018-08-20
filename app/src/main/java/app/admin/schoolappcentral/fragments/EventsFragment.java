package app.admin.schoolappcentral.fragments;

import android.content.Context;
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

import java.util.ArrayList;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.entities.EventsRepo;
import app.admin.schoolappcentral.healpers.ConnectionDetector;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.EventBean;

/**
 * Created by admin on 01/17/2017.
 */

public class EventsFragment extends BaseFragmant
{
    private static View view;

    private ArrayList<EventBean.Response> eventsArrayList;
    EventAdapter eventAdapter;

    private ListView eventslistview;
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

        view = inflater.inflate(R.layout.fragment_events, container,false);

        eventslistview = (ListView) view.findViewById(R.id.eventslistview);


        ((Button)view.findViewById(R.id.btn_refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadevents();
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Events");
        loadevents();
    }

    private void loadevents()
    {
        ConnectionDetector connectionDetector=new ConnectionDetector(getActivity());
        if(connectionDetector.ISCONNECTED)
        {
            showprogressbar(true);
            EventsRepo eRepo = new EventsRepo(getActivity());
            eRepo.getallevents(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    showprogressbar(false);
                    if (e == null)
                    {
                        eventsArrayList = (ArrayList<EventBean.Response>) eventArguments;
                        Log.e("heloooo",eventsArrayList.toString()+"");

                        if(eventsArrayList != null)
                        {
                            if (eventsArrayList.size() > 0)
                            {
                                eventslistview.setVisibility(View.VISIBLE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                                eventAdapter=new EventAdapter(getActivity(),eventsArrayList);
                                eventslistview.setAdapter(eventAdapter);


//                                eventslistview.setAdapter(new EventAdapter(getActivity(), eventsArrayList));
                            } else {
                                eventslistview.setVisibility(View.GONE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            eventslistview.setVisibility(View.GONE);
                            ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                        }
                    } /*else {
                        FirebaseCrash.report(e);
                        if (e.getMessage() != null)
                        {

                            Log.e("firebase",e.getMessage()+"");

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
                                eventslistview.setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                            }
                        } else
                        {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            eventslistview.setVisibility(View.GONE);
                            ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                        }
                    }*/
                }
            });
        }
        else
        {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message),getActivity().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    private class EventAdapter extends BaseAdapter
    {
        private Context context;
        private ArrayList<EventBean.Response> events;

        public EventAdapter(Context c ,ArrayList<EventBean.Response> a)
        {
            this.context = c;
            this.events = a;
        }
        @Override
        public View getView(int i, View convertView, ViewGroup parent)
        {
            View rowView;
                LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
                rowView = inflater.inflate(R.layout.item_event, parent, false);

            final EventBean.Response item= events.get(i);
            Log.e("txtdate",item.event_date+"");

            ((TextView)rowView.findViewById(R.id.txt_eventdate)).setText(item.event_date);
            ((TextView)rowView.findViewById(R.id.txt_eventtitle)).setText(item.event_name);
            ((TextView)rowView.findViewById(R.id.txt_detail)).setText(item.event_id);

          /*  ImageView imageView = (ImageView)rowView.findViewById(R.id.event_image);
            Glide.with(getActivity())
                    .load(APPCONSTANTS.EVENTIMAGEENDPOINT+item.getImage())
                    .into(imageView);*/

            rowView.setClickable(false);

            return rowView;
        }

        @Override
        public int getCount()
        {
            return events.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
    }

}

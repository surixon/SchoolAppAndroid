package app.admin.schoolappcentral.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;

import app.admin.schoolappcentral.entities.LinksRepo;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.UserSelectionActivity;
import app.admin.schoolappcentral.entities.Links;
import app.admin.schoolappcentral.healpers.ConnectionDetector;
import app.admin.schoolappcentral.healpers.SHAREDPREF;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.LinksResponseNew;

import static app.admin.schoolappcentral.entities.LinksRepo.responce;

/**
 * Created by admin on 17/02/2017.
 */

public class LinksFragment extends BaseFragmant
{
    private static View view;

    private List<LinksResponseNew.Response> linksArrayList = new ArrayList<>();
    private ListView linkslistview;

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

        view = inflater.inflate(R.layout.fragment_link, container,false);

        linkslistview = (ListView) view.findViewById(R.id.linkslistview);

        ((Button)view.findViewById(R.id.btn_refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadlinks();
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Links");
        loadlinks();
    }

    private void loadlinks()
    {
        ConnectionDetector connectionDetector=new ConnectionDetector(getActivity());
        if(connectionDetector.ISCONNECTED)
        {
            showprogressbar(true);
            LinksRepo eRepo = new LinksRepo(getActivity());
            eRepo.getlinks(new dataEventListner()
            {
                @Override
                public void onDataEvent(Object eventArguments, Exception e)
                {
                    showprogressbar(false);
                    if (e == null)
                    {
                        linksArrayList = responce.getResponse();

                        if(linksArrayList != null) {
                            if (linksArrayList.size() > 0) {
                                linkslistview.setVisibility(View.VISIBLE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                                linkslistview.setAdapter(new LinksAdapter(getActivity(), linksArrayList));
                            } else {
                                linkslistview.setVisibility(View.GONE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            linkslistview.setVisibility(View.GONE);
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

                               /* Fragment f = new LoginFragment();
                                ((MainActivity) getActivity()).loadfragmentfromfragment(f);*/

                                Intent i = new Intent(getActivity(), UserSelectionActivity.class);
                                startActivity(i);
                                ((MainActivity)getActivity()).finish();

                            } else
                            {
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                                linkslistview.setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                            }
                        } else
                        {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            linkslistview.setVisibility(View.GONE);
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

    private class LinksAdapter extends BaseAdapter
    {
        private Context context;
        private List<LinksResponseNew.Response> links= new ArrayList<>();

        public LinksAdapter(Context c ,List<LinksResponseNew.Response> a)
        {
            this.context = c;
            this.links = a;
        }
        @Override
        public View getView(final int i, View convertView, ViewGroup parent)
        {
            View rowView;
            if (convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
                rowView = inflater.inflate(R.layout.item_links, parent, false);
            }
            else
            {
                rowView = convertView;
            }
           // final Links item= links.get(i);

            ((TextView)rowView.findViewById(R.id.txt_linkname)).setText(links.get(i).getUrl());
            ((TextView)rowView.findViewById(R.id.txt_description)).setText(links.get(i).getName());


            ((com.andexert.library.RippleView)rowView.findViewById(R.id.ripple_link)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    String url=links.get(i).getUrl();
                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                    {
                        url = "http://" + url;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });
            rowView.setClickable(false);

            return rowView;
        }

        @Override
        public int getCount()
        {
            return links.size();
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

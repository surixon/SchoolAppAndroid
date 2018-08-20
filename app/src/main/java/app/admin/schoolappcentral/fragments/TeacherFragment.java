package app.admin.schoolappcentral.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.UserSelectionActivity;
import app.admin.schoolappcentral.entities.Teacher;
import app.admin.schoolappcentral.entities.TeachersRepo;
import app.admin.schoolappcentral.healpers.ConnectionDetector;
import app.admin.schoolappcentral.healpers.SHAREDPREF;
import app.admin.schoolappcentral.healpers.dataEventListner;

/**
 * Created by admin on 17/02/2017.
 */

public class TeacherFragment extends BaseFragmant
{
    private static View view;

    private ArrayList<Teacher> teacherArrayList;
    private ListView teacherslistview;
    private TeachersAdapter teachersAdapter;

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

        view = inflater.inflate(R.layout.fragment_teacher, container,false);

        teacherslistview = (ListView) view.findViewById(R.id.teacherslistview);

        ((Button)view.findViewById(R.id.btn_refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadteachers();
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Faculty & Staff");
        loadteachers();
    }

    private void loadteachers()
    {
        ConnectionDetector connectionDetector=new ConnectionDetector(getActivity());
        if(connectionDetector.ISCONNECTED)
        {
            showprogressbar(true);
            TeachersRepo tRepo = new TeachersRepo(getActivity());
            tRepo.getallteachers(new dataEventListner()
            {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    showprogressbar(false);
                    if (e == null)
                    {
                        teacherArrayList = (ArrayList<Teacher>) eventArguments;

                        if(teacherArrayList != null)
                        {
                            if (teacherArrayList.size() > 0)
                            {
                                teacherslistview.setVisibility(View.VISIBLE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);

                                teachersAdapter = new TeachersAdapter(getActivity(), teacherArrayList);
                                teacherslistview.setAdapter(teachersAdapter);
                            } else {
                                teacherslistview.setVisibility(View.GONE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            teacherslistview.setVisibility(View.GONE);
                            ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                        }
                    } else
                    {
                        if (e.getMessage() != null)
                        {
                            if (e.getMessage().equals("401"))
                            {
                                new SHAREDPREF(getActivity()).SignOut();

                                Intent i = new Intent(getActivity(), UserSelectionActivity.class);
                                startActivity(i);
                                getActivity().finish();

                            } else
                            {
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                                teacherslistview.setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                            }
                        } else
                        {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            teacherslistview.setVisibility(View.GONE);
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

    private class TeachersAdapter extends BaseAdapter
    {
        private Context context;
        private ArrayList<Teacher> items;

        public TeachersAdapter(Context c ,ArrayList<Teacher> a)
        {
            this.context = c;
            this.items = a;
        }
        @Override
        public View getView(int i, View convertView, ViewGroup parent)
        {
            View rowView;
            final Teacher item= items.get(i);

            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);

            if(item.getIssubscribed())
            {
                rowView = inflater.inflate(R.layout.item_teacher_subscribed, parent, false);
            }
            else {
                rowView = inflater.inflate(R.layout.item_teacher, parent, false);
            }

            ((TextView)rowView.findViewById(R.id.txt_teachername)).setText(item.getUsername());
            ((TextView)rowView.findViewById(R.id.txt_des)).setText(item.getDesignation());
            ((TextView)rowView.findViewById(R.id.txt_description)).setText(item.getDescription());


            ((ImageButton)rowView.findViewById(R.id.btn_email)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(!item.getEmail().equals(""))
                    {
                        final Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                                Uri.parse("mailto:"+item.getEmail()));

                      /*  emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.CATEGORY_APP_EMAIL, new String[]{item.getEmail()});*/
                        getActivity().startActivity(emailIntent);
                    }
                }
            });

            ((ImageButton)rowView.findViewById(R.id.btn_contact)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(!item.getContactnumber().equals(""))
                    {
                        String phone_no= item.getContactnumber().toString().replaceAll("-", "");
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone_no));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(callIntent);
                    }
                }
            });


           /* de.hdodenhof.circleimageview.CircleImageView imageView=(de.hdodenhof.circleimageview.CircleImageView)rowView.findViewById(R.id.userimage);
           *//* Ion.with(imageView)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .load(APPCONSTANTS.USERIMAGEENDPOINT+item.getImage());*//*

            Glide.with(getActivity())
                    .load(APPCONSTANTS.USERIMAGEENDPOINT+item.getImage())
                    .into(imageView);*/

            if(item.getIssubscribed() == null)
            {
                item.setIssubscribed(false);
            }

            if(!item.getIssubscribed())
            {
                final ImageButton btn_subscribe =(ImageButton)rowView.findViewById(R.id.btn_subscribe);
                btn_subscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showprogressbar(true);
                        TeachersRepo tRepo = new TeachersRepo(getActivity());
                        tRepo.subscribetoteacher(new dataEventListner() {
                            @Override
                            public void onDataEvent(Object eventArguments, Exception e) {
                                showprogressbar(false);
                                if (e == null) {
                                    //subscribe to topic on fcm server
                                    FirebaseMessaging.getInstance().subscribeToTopic(item.getUserid());

                                    item.setIssubscribed(true);
                                    teachersAdapter.notifyDataSetChanged();
                               /* btn_subscribe.setVisibility(View.GONE);
                                btn_subscribed.setVisibility(View.VISIBLE);*/

                                    Toast.makeText(getActivity(), getActivity().getString(R.string.txt_message_successfullysubscribed_teacher), Toast.LENGTH_LONG).show();

                                } else {
                                    FirebaseCrash.report(e);

                                    if (e.getMessage() != null) {
                                        if (e.getMessage().equals("401")) {
                                            new SHAREDPREF(getActivity()).SignOut();

                                            Intent i = new Intent(getActivity(), UserSelectionActivity.class);
                                            startActivity(i);
                                            getActivity().finish();

                                        } else {
                                            Toast.makeText(getActivity(), getActivity().getString(R.string.txt_message_subscriberro_teacher), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), getActivity().getString(R.string.txt_message_subscriberro_teacher), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }, item.getUserid());
                    }
                });
            }
            else {
                final ImageButton btn_subscribed =(ImageButton)rowView.findViewById(R.id.btn_subscribed);
                btn_subscribed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showprogressbar(true);
                        TeachersRepo tRepo = new TeachersRepo(getActivity());
                        tRepo.unsubscribetoteacher(new dataEventListner() {
                            @Override
                            public void onDataEvent(Object eventArguments, Exception e) {
                                showprogressbar(false);

                                Log.e("hellooo",e+"");

                                if (e == null) {
                                    //unsubscribe to topic on fcm server
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(item.getUserid());

                                    item.setIssubscribed(false);

                                /*btn_subscribe.setVisibility(View.VISIBLE);
                                btn_subscribed.setVisibility(View.GONE);*/
                                    teachersAdapter.notifyDataSetChanged();

                                } else {
                                    FirebaseCrash.report(e);

                                    if (e.getMessage() != null) {
                                        if (e.getMessage().equals("401")) {
                                            new SHAREDPREF(getActivity()).SignOut();

                                            Intent i = new Intent(getActivity(), UserSelectionActivity.class);
                                            startActivity(i);
                                            getActivity().finish();

                                        } else {
                                            Toast.makeText(getActivity(), getActivity().getString(R.string.txt_message_subscriberro_teacher), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), getActivity().getString(R.string.txt_message_subscriberro_teacher), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }, item.getUserid());
                    }
                });
            }
            rowView.setClickable(false);

            return rowView;
        }

        @Override
        public int getCount()
        {
            return items.size();
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

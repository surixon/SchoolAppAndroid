package app.admin.schoolappcentral.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.UserSelectionActivity;
import app.admin.schoolappcentral.adapters.EventsHomeAdapter;
import app.admin.schoolappcentral.adapters.NotificationHomeAdapter;
import app.admin.schoolappcentral.entities.Events;
import app.admin.schoolappcentral.entities.EventsRepo;
import app.admin.schoolappcentral.entities.Notification;
import app.admin.schoolappcentral.entities.NotificationRepo;
import app.admin.schoolappcentral.entities.Schoolprofile;
import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.ConnectionDetector;
import app.admin.schoolappcentral.healpers.SHAREDPREF;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.EventsResponse;

import static android.widget.LinearLayout.HORIZONTAL;
import static app.admin.schoolappcentral.entities.EventsRepo.responce;

/**
 * Created by admin on 3/3/2015.
 */

public class HomeFragment extends BaseFragmant {
    private static View view;
    private Schoolprofile schoolprofile;
    private RecyclerView recyclerView_events, recyclerView_notifications;

    private ArrayList<Notification.Response> notificationArrayList;
    private List<EventsResponse.Response> eventsArrayList = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

        schoolprofile = APPCONSTANTS.SCHOOLPROFILE;

        ((ImageView) view.findViewById(R.id.btn_call)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (schoolprofile.getContactnumber() != null) {
                    if (!schoolprofile.getContactnumber().equals("")) {

                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            //TODO : PERMISSION CHECK
                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Need Call Permission");
                                builder.setMessage("This app needs permission to make call.It will be charge you as per your carrier service.");
                                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, ((MainActivity) getActivity()).CALL_PHONE);
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
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, ((MainActivity) getActivity()).CALL_PHONE);
                            }
                        } else {
                            String phone_no = schoolprofile.getContactnumber().toString().replaceAll("-", "");
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + phone_no));
                            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(callIntent);
                        }
                    }
                }
            }
        });

        ((ImageView) view.findViewById(R.id.btn_twitter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (schoolprofile.getEmail() != null) {
                    if (!schoolprofile.getTwitter().equals("")) {
                        String url = schoolprofile.getTwitter();
                        if (!url.equals("")) {
                            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                url = "http://" + url;
                            }
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        } else {
                            Toast.makeText(getActivity(), "No social media link available for this school.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        ((ImageView) view.findViewById(R.id.btn_facebook)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (schoolprofile.getEmail() != null) {
                    if (!schoolprofile.getFacebook().equals("")) {
                        String url = schoolprofile.getFacebook();
                        if (!url.equals("")) {
                            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                url = "http://" + url;
                            }
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        } else {
                            Toast.makeText(getActivity(), "No social media link available for this school.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });


        ((ImageView) view.findViewById(R.id.btn_web)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (schoolprofile.getWeburl() != null) {
                    if (!schoolprofile.getWeburl().equals("")) {
                        String url = schoolprofile.getWeburl();
                        if (!url.startsWith("http://") && !url.startsWith("https://")) {
                            url = "http://" + url;
                        }
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                }
            }
        });

        return view;
    }

    private void initViews(View view) {
        recyclerView_events = (RecyclerView) view.findViewById(R.id.events_list);
        recyclerView_notifications = (RecyclerView) view.findViewById(R.id.notfication_list);

        recyclerView_events.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecor = new DividerItemDecoration(getActivity(), HORIZONTAL);
        recyclerView_events.addItemDecoration(itemDecor);

        recyclerView_notifications.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecor1 = new DividerItemDecoration(getActivity(), HORIZONTAL);
        recyclerView_notifications.addItemDecoration(itemDecor1);


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getResources().getString(R.string.app_name));
        loadnotifications();
        loadevents();
    }

    private void loadnotifications() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        if (connectionDetector.ISCONNECTED) {
            showprogressbar(true);
            NotificationRepo nRepo = new NotificationRepo(getActivity());
            nRepo.getlatestmessages(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    showprogressbar(false);
                    if (e == null) {
                        notificationArrayList = (ArrayList<Notification.Response>) eventArguments;

                        if (notificationArrayList != null) {
                            if (notificationArrayList.size() > 0) {

                                NotificationHomeAdapter carTypeAdapter = new NotificationHomeAdapter(getActivity(), notificationArrayList);
                                recyclerView_notifications.setAdapter(carTypeAdapter);


//                                ((TextView)view.findViewById(R.id.nonotifications)).setVisibility(View.GONE);
//                                ((LinearLayout)view.findViewById(R.id.notificationpanel)).setVisibility(View.VISIBLE);
//
//                                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                                LinearLayout notificationparent = (LinearLayout)view.findViewById(R.id.notificationpanel);
//
//                                for (Notification.Response item :notificationArrayList)
//                                {
//                                    View rowView = vi.inflate(R.layout.item_notofication_home, null);
//
//                            /*        ((TextView)rowView.findViewById(R.id.txt_sender)).setText(item.getCreatedby());
//                                    ((TextView)rowView.findViewById(R.id.txt_date)).setText(item.getCreateddate());
//                                    ((TextView)rowView.findViewById(R.id.txt_message)).setText(item.getNotification());*/
//
//                                    ((com.andexert.library.RippleView)rowView.findViewById(R.id.notifications)).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            Fragment f = new NotificationFragment();
//                                            ((MainActivity)getActivity()).loadfragmentfromfragment(f);
//                                        }
//                                    });
//
//                                    notificationparent.addView(rowView);
//                                };
                            } else {
                                ((TextView) view.findViewById(R.id.nonotifications)).setVisibility(View.VISIBLE);
                                ((LinearLayout) view.findViewById(R.id.notificationpanel)).setVisibility(View.GONE);
                            }
                        } else {
                            ((TextView) view.findViewById(R.id.nonotifications)).setVisibility(View.VISIBLE);
                            ((LinearLayout) view.findViewById(R.id.notificationpanel)).setVisibility(View.GONE);
                        }
                    } else {
                        FirebaseCrash.report(e);

                        if (e.getMessage() != null) {
                            if (e.getMessage().equals("401")) {
                                new SHAREDPREF(getActivity()).SignOut();

                               /* Fragment f = new LoginFragment();
                                ((MainActivity) getActivity()).loadfragmentfromfragment(f);*/

                                Intent i = new Intent(getActivity(), UserSelectionActivity.class);
                                startActivity(i);
                                ((MainActivity) getActivity()).finish();

                            } else {
                                ((TextView) view.findViewById(R.id.nonotifications)).setVisibility(View.VISIBLE);
                                ((LinearLayout) view.findViewById(R.id.notificationpanel)).setVisibility(View.GONE);
                            }
                        } else {
                            ((TextView) view.findViewById(R.id.nonotifications)).setVisibility(View.VISIBLE);
                            ((LinearLayout) view.findViewById(R.id.notificationpanel)).setVisibility(View.GONE);
                        }
                    }
                }
            });
        } else {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message), getActivity().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    private void loadevents() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        if (connectionDetector.ISCONNECTED) {
            EventsRepo eRepo = new EventsRepo(getActivity());
            eRepo.getlatestevents(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    if (e == null) {
                        eventsArrayList = responce.getResponse();

                        if (eventsArrayList != null) {
                            if (eventsArrayList.size() > 0) {
                                EventsHomeAdapter carTypeAdapter = new EventsHomeAdapter(getActivity(), eventsArrayList);
                                recyclerView_events.setAdapter(carTypeAdapter);


//                                ((TextView)view.findViewById(R.id.nolatestevents)).setVisibility(View.GONE);
//                                ((LinearLayout)view.findViewById(R.id.eventspanel)).setVisibility(View.VISIBLE);
//
//
//                                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                                LinearLayout eventparent = (LinearLayout)view.findViewById(R.id.eventspanel);
//
//                                for (Events item :eventsArrayList)
//                                {
//                                    View rowView = vi.inflate(R.layout.item_event_home, null);
//
//                                    ((TextView)rowView.findViewById(R.id.txt_eventdate)).setText(item.getEventdatetime());
//                                    ((TextView)rowView.findViewById(R.id.txt_eventtitle)).setText(item.getTitle());
//                                    ((TextView)rowView.findViewById(R.id.txt_detail)).setText(item.getDetail());
//
//                                    ((com.andexert.library.RippleView)rowView.findViewById(R.id.events)).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            Fragment f = new EventsFragment();
//                                            ((MainActivity)getActivity()).loadfragmentfromfragment(f);
//                                        }
//                                    });
//
//                                    eventparent.addView(rowView);
//                                };

                            } else {
                                ((TextView) view.findViewById(R.id.nolatestevents)).setVisibility(View.VISIBLE);
                                ((LinearLayout) view.findViewById(R.id.eventspanel)).setVisibility(View.GONE);
                            }
                        } else {
                            ((TextView) view.findViewById(R.id.nolatestevents)).setVisibility(View.VISIBLE);
                            ((LinearLayout) view.findViewById(R.id.eventspanel)).setVisibility(View.GONE);
                        }
                    } else {
                        if (e.getMessage() != null) {
                            FirebaseCrash.report(e);

                            if (e.getMessage().equals("401")) {
                                new SHAREDPREF(getActivity()).SignOut();

                                Intent i = new Intent(getActivity(), UserSelectionActivity.class);
                                startActivity(i);
                                ((MainActivity) getActivity()).finish();
                            } else {
                                ((TextView) view.findViewById(R.id.nolatestevents)).setVisibility(View.VISIBLE);
                                ((LinearLayout) view.findViewById(R.id.eventspanel)).setVisibility(View.GONE);
                            }
                        } else {
                            ((TextView) view.findViewById(R.id.nolatestevents)).setVisibility(View.VISIBLE);
                            ((LinearLayout) view.findViewById(R.id.eventspanel)).setVisibility(View.GONE);
                        }
                    }
                }
            });
        } else {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message), getActivity().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // call permission granted
            if (requestCode == ((MainActivity) getActivity()).CALL_PHONE) {
                String phone_no = schoolprofile.getContactnumber().toString().replaceAll("-", "");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone_no));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        }
    }
}
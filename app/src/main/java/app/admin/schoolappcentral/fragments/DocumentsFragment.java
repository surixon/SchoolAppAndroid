package app.admin.schoolappcentral.fragments;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.UserSelectionActivity;
import app.admin.schoolappcentral.entities.Files;
import app.admin.schoolappcentral.entities.FilesRepo;
import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.ConnectionDetector;
import app.admin.schoolappcentral.healpers.SHAREDPREF;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.FilesResponseNew;

import static app.admin.schoolappcentral.entities.FilesRepo.responce;

/**
 * Created by admin on 17/02/2017.
 */

public class DocumentsFragment extends BaseFragmant {
    private static View view;


    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    private List<FilesResponseNew.Response> filesArrayList = new ArrayList<>();
    private ListView fileslistview;

    private DownloadManager downloadManager;

    private Integer page = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        view = inflater.inflate(R.layout.fragment_documents, container, false);

        fileslistview = (ListView) view.findViewById(R.id.fileslistview);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Documents");
        loaddocuments();
    }

    private void loaddocuments() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        if (connectionDetector.ISCONNECTED) {
            showprogressbar(true);
            FilesRepo eRepo = new FilesRepo(getActivity());
            eRepo.getfiles(new dataEventListner() {
                @Override
                public void onDataEvent(Object eventArguments, Exception e) {
                    showprogressbar(false);
                    if (e == null) {
                        filesArrayList = responce.getResponse();

                        if (filesArrayList != null) {
                            if (filesArrayList.size() > 0) {
                                fileslistview.setVisibility(View.VISIBLE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                                fileslistview.setAdapter(new DocumentAdapter(getActivity(), filesArrayList));
                            } else {
                                fileslistview.setVisibility(View.GONE);
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.VISIBLE);
                            }
                        } else {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            fileslistview.setVisibility(View.GONE);
                            ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                        }
                    } else {
                        if (e.getMessage() != null) {
                            FirebaseCrash.report(e);
                            if (e.getMessage().equals("401")) {
                                new SHAREDPREF(getActivity()).SignOut();

                                /*Fragment f = new LoginFragment();
                                ((MainActivity) getActivity()).loadfragmentfromfragment(f);*/
                                Intent i = new Intent(getActivity(), UserSelectionActivity.class);
                                startActivity(i);
                                ((MainActivity) getActivity()).finish();

                            } else {
                                ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                                fileslistview.setVisibility(View.GONE);
                                ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                            }
                        } else {
                            ((LinearLayout) view.findViewById(R.id.layout_reload)).setVisibility(View.VISIBLE);
                            fileslistview.setVisibility(View.GONE);
                            ((android.support.v7.widget.CardView) view.findViewById(R.id.message_card_view)).setVisibility(View.GONE);
                        }
                    }
                }
            });
        } else {
            showmwssagealert(getResources().getString(R.string.txt_nointernet_message), getActivity().getResources().getString(R.string.txt_nointernet_title));
        }
    }

    private class DocumentAdapter extends BaseAdapter {
        private Context context;
        private List<FilesResponseNew.Response> documents = new ArrayList<>();

        public DocumentAdapter(Context c, List<FilesResponseNew.Response> a) {
            this.context = c;
            this.documents = a;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {
            View rowView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
                rowView = inflater.inflate(R.layout.item_file, parent, false);
            } else {
                rowView = convertView;
            }
            // final Files item= documents.get(i);

            ((TextView) rowView.findViewById(R.id.txt_filename)).setText(documents.get(i).getName());
            ((TextView) rowView.findViewById(R.id.txt_filedescription)).setText(documents.get(i).getType());
            //  ((TextView)rowView.findViewById(R.id.txt_createdby)).setText(item.getCreatedbyname());
             ((TextView)rowView.findViewById(R.id.txt_createddate)).setText(getDate(Long.parseLong(documents.get(i).getCreated())));

            ImageView imageView = ((ImageView) rowView.findViewById(R.id.document_image));

            if (documents.get(i).getType().endsWith(".pdf")) {
                imageView.setImageResource(R.drawable.pdf);
            } else if (documents.get(i).getType().endsWith(".docx")) {
                imageView.setImageResource(R.drawable.world);
            } else if (documents.get(i).getType().endsWith(".ppt")) {
                imageView.setImageResource(R.drawable.powerpoint);
            } else if (documents.get(i).getType().endsWith(".xslx")) {
                imageView.setImageResource(R.drawable.excel);
            } else {
                imageView.setImageResource(R.drawable.documents);
            }

            ((ImageButton) rowView.findViewById(R.id.btn_download)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        //TODO : PERMISSION CHECK
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Need Call Permission");
                            builder.setMessage("This app needs permission for read and write in your storage.");
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
                        getcontentfile(documents.get(i).getName());
                    }
                }
            });

            rowView.setClickable(false);

            return rowView;
        }

        @Override
        public int getCount() {
            return documents.size();
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

    private void getcontentfile(String filename) {
        String fileurl = APPCONSTANTS.FILEEBDPOINT + filename;

        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        Uri Download_Uri = Uri.parse(fileurl);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("File Download");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading File : " + filename);
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS, filename);
        //Enqueue a new download and same the referenceId
        long downloadRef = downloadManager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // call permission granted
            if (requestCode == ((MainActivity) getActivity()).WRITE_EXTERNAL_STORAGE) {
                Toast.makeText(getActivity(), "Permission granted successfully.Plese touch call download button to download file.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDate(long time) {
        Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy "); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));

        return sdf.format(date);
    }
}

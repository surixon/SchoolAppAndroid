package app.admin.schoolappcentral.adapters;

/*
 * Created by rishav on 25/1/17.
 */

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.entities.Notification;
import app.admin.schoolappcentral.fragments.EventsFragment;
import app.admin.schoolappcentral.fragments.NotificationFragment;

public class NotificationHomeAdapter extends RecyclerView.Adapter<NotificationHomeAdapter.ViewHolder> {

    private Context context;
    ArrayList<Notification.Response> list;

    public NotificationHomeAdapter(Context context, ArrayList<Notification.Response> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(list.get(position).event_name);
        holder.date.setText(getDate(Long.parseLong(list.get(position).created_date)));
        holder.parent_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = new NotificationFragment();
                ((MainActivity) context).loadfragmentfromfragment(f);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private LinearLayout parent_ll;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            parent_ll = (LinearLayout) itemView.findViewById(R.id.parent_ll);
        }
    }

    private String getDate(long time) {
        Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));

        return sdf.format(date);
    }

}

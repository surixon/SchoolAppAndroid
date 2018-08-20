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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.entities.Events;
import app.admin.schoolappcentral.entities.Notification;
import app.admin.schoolappcentral.fragments.EventsFragment;
import app.admin.schoolappcentral.responces.EventsResponse;

public class EventsHomeAdapter extends RecyclerView.Adapter<EventsHomeAdapter.ViewHolder> {

    private Context context;
    List<EventsResponse.Response> list= new ArrayList<>();

    public EventsHomeAdapter(Context context, List<EventsResponse.Response> list) {
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
        holder.title.setText(list.get(position).getEventName());
        holder.date.setText(list.get(position).getEventDate());
        holder.parent_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = new EventsFragment();
                ((MainActivity) context).loadfragmentfromfragment(f);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,date;
        private LinearLayout parent_ll;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            parent_ll = (LinearLayout) itemView.findViewById(R.id.parent_ll);
        }
    }
}

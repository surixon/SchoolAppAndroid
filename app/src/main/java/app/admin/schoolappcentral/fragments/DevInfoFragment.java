package app.admin.schoolappcentral.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.entities.Events;

/**
 * Created by admin on 01/17/2017.
 */

public class DevInfoFragment extends BaseFragmant
{
    private static View view;
    private RelativeLayout parent_rl;
    private ArrayList<Events> eventsArrayList;

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

        view = inflater.inflate(R.layout.fragment_developerinfo, container,false);

        eventslistview = (ListView) view.findViewById(R.id.eventslistview);
        parent_rl = (RelativeLayout) view.findViewById(R.id.parent_rl);

        parent_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).setActionBarTitle("Developer Info");
    }
}

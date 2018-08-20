package app.admin.schoolappcentral.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.admin.schoolappcentral.MainActivity;
import app.admin.schoolappcentral.R;
import app.admin.schoolappcentral.entities.UserRepo;
import app.admin.schoolappcentral.entities.userprofileview;
import app.admin.schoolappcentral.healpers.APPCONSTANTS;
import app.admin.schoolappcentral.healpers.ConnectionDetector;
import app.admin.schoolappcentral.healpers.SHAREDPREF;
import app.admin.schoolappcentral.healpers.dataEventListner;
import app.admin.schoolappcentral.responces.LoginResponse;

/**
 * Created by admin on 10/02/2017.
 */

public class LoginFragment extends BaseFragmant
{
    private static View view;

    private EditText Uname ;
    private EditText Upass ;

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

        view = inflater.inflate(R.layout.fragment_login, container,false);

        Uname = ((EditText) view.findViewById(R.id.txtusername));
        Upass = ((EditText)view.findViewById(R.id.txtpassword));

        ((TextView)view.findViewById(R.id.signin1)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String username=Uname.getText().toString();
                String password = Upass.getText().toString();

                ConnectionDetector connectionDetector=new ConnectionDetector(getActivity());
                if(connectionDetector.ISCONNECTED) {
                    showprogressbar(true);
                    UserRepo usersrepo = new UserRepo(getActivity());
                    usersrepo.Login(new dataEventListner() {
                        @Override
                        public void onDataEvent(Object eventArguments, Exception e) {
                            showprogressbar(false);

                            if (e == null) {
                                Log.d("LoginResponse",eventArguments.toString()+"");
                                LoginResponse resp = (LoginResponse) eventArguments;

                                new SHAREDPREF(getActivity()).putLoginToken(resp.getAccess_token());

                                new SHAREDPREF(getActivity()).putLoginTokenExpire(String.valueOf(resp.getExpires()));

                                APPCONSTANTS.LOGINTOKEN = resp.getAccess_token();
                                // get user detail
                                getuserdetail();
                            } else {
                                Toast.makeText(getActivity(), "LOGIN FAIL : " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, username, password);
                }
                else
                {
                    showmwssagealert(getResources().getString(R.string.txt_nointernet_message),getActivity().getResources().getString(R.string.txt_nointernet_title));
                }
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }


    private void getuserdetail()
    {
        UserRepo usersrepo = new UserRepo(getActivity());
        usersrepo.GetUserDetail(new dataEventListner()
        {
            @Override
            public void onDataEvent(Object eventArguments, Exception e)
            {
                if(e == null)
                {
                    userprofileview upv = (userprofileview)eventArguments;

                    new SHAREDPREF(getActivity()).putLoginUser(upv);

                    Fragment f = new HomeFragment();
                    ((MainActivity)getActivity()).loadfragmentfromfragment(f);
                }
                else
                {
                    if(e.getMessage() != null)
                    {
                        if (e.getMessage().equals("INVALIDLOGIN"))
                        {
                            new SHAREDPREF(getActivity()).putLoginToken("");

                            Fragment f = new LoginFragment();
                            ((MainActivity)getActivity()).loadfragmentfromfragment(f);
                        }
                        else
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),R.string.txt_unspecifiederror_message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}

package com.example.fooandbar.ibeacon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.activity.MainActivity;
import com.example.fooandbar.ibeacon.utils.PreferencesUtil;

public class UserDetailsFragment extends Fragment {

    public static final String TAG = "UserDetailsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        ((MainActivity) getActivity()).getToolbar().setTitle(R.string.about_user);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView userName = (TextView) getActivity().findViewById(R.id.user_name_text_view);
        TextView mac = (TextView) getActivity().findViewById(R.id.mac_text_view);

        if (PreferencesUtil.readName(getActivity()) != null) {
            userName.setText(PreferencesUtil.readName(getActivity()));
        } else {
            userName.setText(R.string.name);
        }

        if (PreferencesUtil.readId(getActivity()) != null) {
            mac.setText(PreferencesUtil.readId(getActivity()));
        } else {
            mac.setText(R.string.mac);
        }
    }
}

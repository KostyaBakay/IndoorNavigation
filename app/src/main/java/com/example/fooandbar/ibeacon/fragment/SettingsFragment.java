package com.example.fooandbar.ibeacon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.activity.MainActivity;
import com.example.fooandbar.ibeacon.utils.PreferencesUtil;

public class SettingsFragment extends Fragment {

    public static final String TAG = "SettingsFragment";

    private EditText mUserNameEditText;
    private Button mSaveButton;
    private RadioButton maleRb;
    private RadioButton femaleRb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        maleRb = (RadioButton) view.findViewById(R.id.male_radio_button);
        femaleRb = (RadioButton) view.findViewById(R.id.female_radio_button);
        if(PreferencesUtil.readSex(getContext())){
            maleRb.setChecked(false);
            femaleRb.setChecked(true);
        }

        ((MainActivity) getActivity()).getToolbar().setTitle(R.string.settings);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mUserNameEditText = (EditText) getActivity().findViewById(R.id.user_name_edit_text);
        mUserNameEditText.setHint(PreferencesUtil.readName(getActivity()));
        mSaveButton = (Button) getActivity().findViewById(R.id.save_button);



        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
                if(mUserNameEditText.length()>0)
                    PreferencesUtil.writeName(getActivity(), mUserNameEditText.getText().toString());
                    ((MainActivity) getActivity()).addMainDataFragment();
                    if(maleRb.isChecked()){
                        PreferencesUtil.writeSex(getContext(),false);
                    }else{
                        PreferencesUtil.writeSex(getContext(),true);
                    }

            }
        });
    }
}

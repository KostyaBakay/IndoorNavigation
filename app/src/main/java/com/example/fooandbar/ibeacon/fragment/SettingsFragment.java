package com.example.fooandbar.ibeacon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.utils.PreferencesUtil;

public class SettingsFragment extends Fragment {
    private EditText mUserNameEditText;
    private Button mSaveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
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
                if (mUserNameEditText.length() > 0) {
                    Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
                    PreferencesUtil.writeName(getActivity(), mUserNameEditText.getText().toString());
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), R.string.enter_your_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

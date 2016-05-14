package com.example.fooandbar.ibeacon;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.fooandbar.ibeacon.fragment.SettingsFragment;
import com.example.fooandbar.ibeacon.fragment.UserDetailsFragment;
import com.example.fooandbar.ibeacon.utils.PreferencesUtil;
import com.kontakt.sdk.android.ble.configuration.scan.ScanContext;
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract;

public class MainActivity extends AppCompatActivity  {
    private final String TAG = "MainActivity";
    private final String TAGNETW = "NetworkBeaconRequest";
    private ProximityManagerContract proximityManager;
    private ScanContext scanContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPreferences(); //sets preferences

        startService(new Intent(MainActivity.this,BeaconListenerService.class));
        // For testing SettingsFragment
        // addSettingsFragment();

        // For testing UserDetailsFragment
        // addUserDetailsFragment();
    }



    private void addSettingsFragment() {
        SettingsFragment fragment = new SettingsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main, fragment);
        ft.commit();
    }

    private void addUserDetailsFragment() {
        UserDetailsFragment fragment = new UserDetailsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main, fragment);
        ft.commit();
    }


    private void setupPreferences() {
        if (PreferencesUtil.readName(this) == null) PreferencesUtil.writeName(this, android.os.Build.MODEL);
        if (PreferencesUtil.readId(this) == null) PreferencesUtil.writeId(this, getMacAddress());
    }

    private String getMacAddress() {
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }


}

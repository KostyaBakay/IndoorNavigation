package com.example.fooandbar.ibeacon.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fooandbar.ibeacon.BeaconListenerService;
import com.example.fooandbar.ibeacon.IntroActivity;
import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.fragment.MainDataFragment;
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
<<<<<<< HEAD
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.add(R.id.fragment_layout,new MainDataFragment()).addToBackStack(null).commit();
        // For testing SettingsFragment
        // addSettingsFragment();

        // For testing UserDetailsFragment
        // addUserDetailsFragment();
=======
>>>>>>> bf2ecb938b49d5507dbfc75186b1cb8f729cdf93
        addMainDataFragment();
    }

    public void addMainDataFragment() {
        MainDataFragment fragment = new MainDataFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main, fragment);
        ft.commit();
    }

    public void addSettingsFragment() {
        SettingsFragment fragment = new SettingsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main, fragment);
        ft.commit();
    }

    public void addUserDetailsFragment() {
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


    @Override
    protected void onResume() {
        super.onResume();
        if (!PreferencesUtil.canVerified(this)) {
            startActivity(new Intent(this, IntroActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(MainActivity.class.getSimpleName(), "onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(MainActivity.class.getSimpleName(), "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_rooms) {
            addMainDataFragment();
            return true;
        } else if (id == R.id.action_about_user) {
            addUserDetailsFragment();
            return true;
        } else if (id == R.id.action_settings) {
            addSettingsFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.fooandbar.ibeacon;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
=======
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
>>>>>>> fae46e6356e5b9a9869296dfbd4079aba1f58597

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

<<<<<<< HEAD
        startService(new Intent(MainActivity.this,BeaconListenerService.class));
        // For testing SettingsFragment
        // addSettingsFragment();

=======
>>>>>>> fae46e6356e5b9a9869296dfbd4079aba1f58597
        // For testing UserDetailsFragment
        // addUserDetailsFragment();
    }



    private void addSettingsFragment() {
        SettingsFragment fragment = new SettingsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main, fragment).addToBackStack(null);
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


<<<<<<< HEAD
=======
    @Override
    public void onScanStop() {
        Log.d(TAG, "scanning is stopped");
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
        if (id == R.id.action_settings) {
            addSettingsFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
>>>>>>> fae46e6356e5b9a9869296dfbd4079aba1f58597
}

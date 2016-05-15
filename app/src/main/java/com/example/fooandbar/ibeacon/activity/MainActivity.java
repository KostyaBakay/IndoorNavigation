package com.example.fooandbar.ibeacon.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fooandbar.ibeacon.BeaconListenerService;
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
        setupUI();
        setupPreferences(); //sets preferences
        startService(new Intent(MainActivity.this,BeaconListenerService.class));
        addMainDataFragment();
    }

    public void addMainDataFragment() {
		if (getSupportFragmentManager().findFragmentByTag(MainDataFragment.TAG) == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container_main, new MainDataFragment(), MainDataFragment.TAG)
					.commit();
		}
    }

    public void addSettingsFragment() {
		if (getSupportFragmentManager().findFragmentByTag(SettingsFragment.TAG) == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container_main, new SettingsFragment(), SettingsFragment.TAG)
					.commit();
		}
    }

    public void addUserDetailsFragment() {
		if (getSupportFragmentManager().findFragmentByTag(UserDetailsFragment.TAG) == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container_main, new UserDetailsFragment(), UserDetailsFragment.TAG)
					.commit();
		}
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
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(MainActivity.class.getSimpleName(), "onOptionsItemSelected");
        int id = item.getItemId();
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

    private void setupUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}

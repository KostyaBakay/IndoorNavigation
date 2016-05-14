package com.example.fooandbar.ibeacon;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.fooandbar.ibeacon.fragment.SettingsFragment;
import com.example.fooandbar.ibeacon.fragment.UserDetailsFragment;
import com.example.fooandbar.ibeacon.model.UserDevice;
import com.example.fooandbar.ibeacon.utils.PreferencesUtil;
import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.configuration.scan.EddystoneScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.IBeaconScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.ScanContext;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.discovery.BluetoothDeviceEvent;
import com.kontakt.sdk.android.ble.discovery.ibeacon.IBeaconDeviceEvent;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.DeviceProfile;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.RemoteBluetoothDevice;
import com.kontakt.sdk.android.manager.KontaktProximityManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements ProximityManager.ProximityListener {
    private final String TAG = "MainActivity";
    private final String TAGNETW = "NetworkBeaconRequest";
    private ProximityManagerContract proximityManager;
    private ScanContext scanContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KontaktSDK.initialize(this).setDebugLoggingEnabled(true);
        proximityManager = new KontaktProximityManager(this);
        setupPreferences(); //sets preferences

        // For testing SettingsFragment
        // addSettingsFragment();

        // For testing UserDetailsFragment
        // addUserDetailsFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        proximityManager.initializeScan(getScanContext(), new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.attachListener(MainActivity.this);
            }

            @Override
            public void onConnectionFailure() {

            }
        });
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

    @Override
    public void onEvent(BluetoothDeviceEvent bluetoothDeviceEvent) {
        List<? extends RemoteBluetoothDevice> deviceList = bluetoothDeviceEvent.getDeviceList();
        long timestamp = bluetoothDeviceEvent.getTimestamp();
        DeviceProfile deviceProfile = bluetoothDeviceEvent.getDeviceProfile();
        IBeaconDeviceEvent event = (IBeaconDeviceEvent) bluetoothDeviceEvent;
        List<IBeaconDevice> devicesList = event.getDeviceList();
        for (IBeaconDevice device : devicesList) {
            Log.d(TAG, device.getName());
            Log.d(TAG, "Mac-address of beacon: " + device.getAddress());
            Log.d(TAG, device.getUniqueId());
            Log.d(TAG, device.getMajor() + "");
            Log.d(TAG, device.getMinor() + "");
            Log.d(TAG, device.getProximityUUID().toString());
            Log.d(TAG, device.getDistance() + "");
        }
        UserDevice userDevice = null;

        switch (bluetoothDeviceEvent.getEventType()) {
            case SPACE_ENTERED:
                userDevice = setUser(devicesList.get(0));
                break;
            case DEVICE_DISCOVERED:
                userDevice = setUser(devicesList.get(0));

                break;
            case DEVICES_UPDATE:
                userDevice = setUser(devicesList.get(0));
                break;
            case DEVICE_LOST:
                // if no beacons for user device, then delete user device from all rooms
                userDevice = removeUser(devicesList.get(0));
                break;
            case SPACE_ABANDONED:
                userDevice = removeUser(devicesList.get(0));
                break;
            default:
                //
        }
    }

    public UserDevice setUser(IBeaconDevice iBeaconDevice) {
        UserDevice userDevice = new UserDevice();
        userDevice.setName("");
        userDevice.setIdBeacon(iBeaconDevice.getUniqueId());
        userDevice.setUserID("");
        userDevice.setDistance(iBeaconDevice.getDistance());
        return userDevice;
    }

    public UserDevice removeUser(IBeaconDevice iBeaconDevice) {
        UserDevice userDevice = new UserDevice();
        userDevice.setName("");
        userDevice.setIdBeacon("");
        userDevice.setUserID("");
        userDevice.setDistance(iBeaconDevice.getDistance());
        return userDevice;
    }


    private ScanContext getScanContext() {
        if (scanContext == null) {
            scanContext = new ScanContext.Builder()
                    .setScanPeriod(ScanPeriod.RANGING)
                    .setScanPeriod(new ScanPeriod(TimeUnit.SECONDS.toMillis(15), TimeUnit.SECONDS.toMillis(10)))
                    .setScanMode(ProximityManager.SCAN_MODE_BALANCED)
                    .setActivityCheckConfiguration(ActivityCheckConfiguration.MINIMAL)
                    .setForceScanConfiguration(ForceScanConfiguration.MINIMAL)
                    .setIBeaconScanContext(new IBeaconScanContext.Builder().build())
                    .setEddystoneScanContext(new EddystoneScanContext.Builder().build())
                    .setForceScanConfiguration(ForceScanConfiguration.MINIMAL)
                    .build();
        }
        return scanContext;
    }


    @Override
    protected void onStop() {
        super.onStop();
        proximityManager.detachListener(this);
        proximityManager.disconnect();
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
    public void onScanStart() {
        Log.d(TAG, "scanning is started");
    }

    @Override
    public void onScanStop() {
        Log.d(TAG, "scanning is stopped");
    }
}

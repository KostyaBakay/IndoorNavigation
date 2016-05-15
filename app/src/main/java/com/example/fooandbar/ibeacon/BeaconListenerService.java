package com.example.fooandbar.ibeacon;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.fooandbar.ibeacon.model.UserDevice;
import com.example.fooandbar.ibeacon.storage.FirebaseRepo;
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

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BeaconListenerService extends Service implements ProximityManager.ProximityListener{
    public BeaconListenerService() {
    }

    private final String TAG = "ListenerService";
    private final String TAGNETW = "NetworkBeaconRequest";
    private ProximityManagerContract proximityManager;
    private ScanContext scanContext;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        KontaktSDK.initialize(this).setDebugLoggingEnabled(true);
        proximityManager = new KontaktProximityManager(this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        proximityManager.initializeScan(getScanContext(), new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.attachListener(BeaconListenerService.this);
            }

            @Override
            public void onConnectionFailure() {
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }



    @Override
    public void onEvent(BluetoothDeviceEvent bluetoothDeviceEvent) {
        List<? extends RemoteBluetoothDevice> deviceList = bluetoothDeviceEvent.getDeviceList();
        long timestamp = bluetoothDeviceEvent.getTimestamp();
        DeviceProfile deviceProfile = bluetoothDeviceEvent.getDeviceProfile();
        IBeaconDeviceEvent event = (IBeaconDeviceEvent) bluetoothDeviceEvent;
        List<IBeaconDevice> devicesList = event.getDeviceList();

        for (IBeaconDevice device : devicesList)
        {
            /*
            Log.d(TAG,device.getName());
            Log.d(TAG,"Mac-address of beacon: " + device.getAddress());
            Log.d(TAG,device.getUniqueId());
            Log.d(TAG,device.getMajor() + "");
            Log.d(TAG,device.getMinor() + "");
            Log.d(TAG,device.getProximityUUID().toString());
            Log.d(TAG, device.getDistance() + "");
            */
        }

        UserDevice userDevice = null;

        switch (bluetoothDeviceEvent.getEventType()) {
            case SPACE_ENTERED:

                break;
            case DEVICE_DISCOVERED:
                if(devicesList.size()==1) {
                    Log.d(TAG,"Distance: "  + devicesList.get(0).getDistance());
                    Log.d(TAG,"BeaconID: "  + devicesList.get(0).getUniqueId());
                    Log.d(TAG,"Name: "  + PreferencesUtil.readName(getBaseContext()));
                    Log.d(TAG,"UserID: "  + PreferencesUtil.readId(getBaseContext()));

                    FirebaseRepo repo = new FirebaseRepo();
                    UserDevice device = new UserDevice();
                    device.setDistance(devicesList.get(0).getDistance());
                    device.setIdBeacon(devicesList.get(0).getUniqueId());
                    device.setName(PreferencesUtil.readName(getBaseContext()));
                    device.setUserID(PreferencesUtil.readId(getBaseContext()));
                    repo.saveUserDevice(device);
                    Log.d(TAG,"saveUserDevice() is called");
                }
                /*
                Log.d("TestingTag", "Count of iBeacon: " + deviceList.size());
                Log.d("TestingTag", "Name: " + userDevice.getName());
                Log.d("TestingTag", "UserID: " + userDevice.getUserID());
                Log.d("TestingTag", "BeaconID: " + userDevice.getIdBeacon());
                Log.d("TestingTag", "Distance: " + userDevice.getDistance());
                */

                break;
            case DEVICES_UPDATE:
                if(devicesList.size()==1) {
                    /*
                    Log.d(TAG,"Distance: "  + devicesList.get(0).getDistance());
                    Log.d(TAG,"BeaconID: "  + devicesList.get(0).getUniqueId());
                    Log.d(TAG,"Name: "  + PreferencesUtil.readName(getBaseContext()));
                    Log.d(TAG,"UserID: "  + PreferencesUtil.readId(getBaseContext()));

                    FirebaseRepo repo = new FirebaseRepo();
                    UserDevice device = new UserDevice();
                    device.setDistance(devicesList.get(0).getDistance());
                    device.setIdBeacon(devicesList.get(0).getUniqueId());
                    device.setName(PreferencesUtil.readName(getBaseContext()));
                    device.setUserID(PreferencesUtil.readId(getBaseContext()));
                    repo.saveUserDevice(device);
                    Log.d(TAG,"saveUserDevice() is called");
                    */
                }
                break;
            case DEVICE_LOST:
                if(deviceList.size()==1)
                {
                    Log.d(TAG,"Distance: "  + devicesList.get(0).getDistance());
                    Log.d(TAG,"BeaconID: "  + devicesList.get(0).getUniqueId());
                    Log.d(TAG,"Name: "  + PreferencesUtil.readName(getBaseContext()));
                    Log.d(TAG,"UserID: "  + PreferencesUtil.readId(getBaseContext()));

                    FirebaseRepo repo = new FirebaseRepo();
                    UserDevice device = new UserDevice();
                    device.setDistance(devicesList.get(0).getDistance());
                    device.setIdBeacon(devicesList.get(0).getUniqueId());
                    device.setName(PreferencesUtil.readName(getBaseContext()));
                    device.setUserID(PreferencesUtil.readId(getBaseContext()));
                    repo.deleteUserDevice(device);
                    Log.d(TAG, "deleteUserDevice() is called");
                }
                break;
            case SPACE_ABANDONED:
                break;
            default:
                //
        }
    }



    private ScanContext getScanContext() {
        if (scanContext == null) {
            scanContext = new ScanContext.Builder()
                    .setScanPeriod(ScanPeriod.RANGING)
                    .setScanPeriod(new ScanPeriod(TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS.toMillis(20)))
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
    public void onScanStart() {
        Log.d(TAG, "Scanning is started");
    }

    @Override
    public void onScanStop() {
        Log.d(TAG, "Scanning is stopped");
    }


    public String getDeviceName() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";

    }
}

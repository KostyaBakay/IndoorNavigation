package com.example.fooandbar.ibeacon.storage;


import com.example.fooandbar.ibeacon.model.Room;

import java.util.Map;

public interface StorageContract {

    interface OnUserDeviceSavedCallback {
        void onUserDeviceSaved();
    }

    interface OnAllRoomsMapRetrievedCallback {
        void onAllRoomsMapRetrieved(Map<String, Room> roomMap);
    }

    interface OnUserDeviceDeletedCallback {
        void onUserDeviceDeleted();
    }
}

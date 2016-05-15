package com.example.fooandbar.ibeacon.storage;

import android.util.Log;

import com.example.fooandbar.ibeacon.model.Room;
import com.example.fooandbar.ibeacon.model.UserDevice;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class FirebaseRepo {
    private static final String TAG = "myLogs: " + FirebaseRepo.class.getSimpleName();

    private String rootRef = "https://indoornavigation.firebaseio.com/";
    private String roomList = "room_list/";
    private String deviceList = "userDevices/";

// TODO add callbacks

    public void saveUserDevice(final UserDevice userDevice) {
        Log.d("TestLog", "saveUserDevice() is called");

        String runUrl = rootRef + roomList;
        final Firebase firebase = new Firebase(runUrl);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, Room>> type =
                        new GenericTypeIndicator<Map<String, Room>>() {
                        };
                Map<String, Room> roomMap = snapshot.getValue(type);
                Object[] roomIdsAsObjects = roomMap.keySet().toArray();
                String[] roomIds = new String[roomIdsAsObjects.length];
                for (int i = 0; i < roomIdsAsObjects.length; i++) {
                    roomIds[i] = (String) roomIdsAsObjects[i];
                }
                deleteUserFromRoomList(userDevice.getUserID(), roomIds);
                String runUrl = rootRef + roomList + userDevice.getIdBeacon() + "/" + deviceList + userDevice.getUserID() + "/";
                new Firebase(runUrl).setValue(userDevice);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, "onCancelled: " + firebaseError.getMessage());
            }
        });

    }

    public void createRoom(String roomId, String roomName) {
        String runUrl = rootRef + roomList + roomId + "/name/";
        new Firebase(runUrl).setValue(roomName);
    }

    public void getAllRoomsMap(final StorageContract.OnAllRoomsMapRetrievedCallback retrievedCallback) {
        String runUrl = rootRef + roomList;
        final Firebase firebase = new Firebase(runUrl);

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, Room>> type =
                        new GenericTypeIndicator<Map<String, Room>>() {
                        };

                Map<String, Room> roomMap = snapshot.getValue(type);
                retrievedCallback.onAllRoomsMapRetrieved(roomMap);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, "onCancelled: " + firebaseError.getMessage());
            }
        });
    }

    public void deleteRoomById(String roomId) {
        String runUrl = rootRef + roomList + roomId + "/";
        new Firebase(runUrl).setValue(null);
    }

    /**
     * delete UserDevice from all rooms
     */

    public void deleteUserDevice(final UserDevice userDevice) {
        // update room ids
        String runUrl = rootRef + roomList;
        final Firebase firebase = new Firebase(runUrl);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, Room>> type =
                        new GenericTypeIndicator<Map<String, Room>>() {
                        };
                Map<String, Room> roomMap = snapshot.getValue(type);
                Object[] roomIdsAsObjects = roomMap.keySet().toArray();
                String[] roomIds = new String[roomIdsAsObjects.length];
                for (int i = 0; i < roomIdsAsObjects.length; i++) {
                    roomIds[i] = (String) roomIdsAsObjects[i];
                }
                deleteUserFromRoomList(userDevice.getUserID(), roomIds);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, "onCancelled: " + firebaseError.getMessage());
            }
        });

    }

    private void deleteUserFromRoomList(String userId, String[] roomIds) {
        for (String roomId : roomIds) {
            String runUrl = rootRef + roomList + roomId + "/" + deviceList + userId + "/";
            new Firebase(runUrl).setValue(null);
        }
    }

}

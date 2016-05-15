package com.example.fooandbar.ibeacon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.adapter.RoomListAdapter;
import com.example.fooandbar.ibeacon.model.Room;
import com.example.fooandbar.ibeacon.model.UserDevice;
import com.example.fooandbar.ibeacon.storage.FirebaseRepo;
import com.example.fooandbar.ibeacon.storage.StorageContract;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainDataFragment extends Fragment implements StorageContract.OnAllRoomsMapRetrievedCallback{

    public static final String TAG = "MainDataFragment";

    LinkedHashMap<Room, ArrayList<UserDevice>> dataToSet = new LinkedHashMap<>();
    ExpandableListView roomListView;
    RoomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_data, container, false);

        roomListView = (ExpandableListView) view.findViewById(R.id.roomListView);
        roomListView.setDivider(null);
        roomListView.setChildDivider(null);
        roomListView.setGroupIndicator(null);
        new FirebaseRepo().getAllRoomsMap(this);

        return view;
    }

    public LinkedHashMap<Room, ArrayList<UserDevice>> setRoomData(ArrayList<UserDevice> userDevices) {


        ArrayList<UserDevice> roomUsersList = new ArrayList<>();
        Room room = new Room();
        for (int i = 0; i < userDevices.size(); i++) {
            for (int j = i; j < userDevices.size(); j++) {
                if (userDevices.get(i).getIdBeacon().equals(userDevices.get(j).getIdBeacon())) {
                    //to one room && not one User
                    roomUsersList.add(userDevices.get(j));
                    room.setName(userDevices.get(j).getIdBeacon());
                }
            }

            dataToSet.put(room, roomUsersList);
        }
        return dataToSet;
    }

    @Override
    public void onAllRoomsMapRetrieved(Map<String, Room> roomMap) {
        if(roomMap.size() > 0){
            Object[] objects = roomMap.keySet().toArray();
            ArrayList<UserDevice> userDevices = new ArrayList<>();
            for(int i = 0; i < roomMap.size(); i++){
                Room room = roomMap.get(objects[i]);
                if(room.getUserDevices()!=null){
                    Object[] keys = room.getUserDevices().keySet().toArray();
                    for(int j = 0; j < room.getUserDevices().size(); j++){
                        userDevices.add(room.getUserDevices().get(keys[j]));
                    }
                    dataToSet.put(room,userDevices);
                    userDevices = new ArrayList<>();
                }

            }
            adapter = new RoomListAdapter(getContext(), dataToSet);
            roomListView.setAdapter(adapter);
            dataToSet = new LinkedHashMap<>();
        }

    }
}
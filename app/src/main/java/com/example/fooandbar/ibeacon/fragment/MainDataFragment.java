package com.example.fooandbar.ibeacon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.adapter.RoomListAdapter;
import com.example.fooandbar.ibeacon.model.Room;
import com.example.fooandbar.ibeacon.model.UserDevice;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainDataFragment extends Fragment {
    LinkedHashMap<Room, ArrayList<UserDevice>> dataToSet = new LinkedHashMap<>();
    ExpandableListView roomListView;
    RoomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_data, container, false);
        Log.d("some_tag", "fragment");

        roomListView = (ExpandableListView) view.findViewById(R.id.roomListView);
        roomListView.setDivider(null);
        roomListView.setChildDivider(null);
        roomListView.setGroupIndicator(null);
        ArrayList<UserDevice> devices = new ArrayList<>();
        UserDevice userDevice = new UserDevice();
        userDevice.setName("My Name");
        userDevice.setDistance(15);
        userDevice.setIdBeacon("Nexus 5");
        userDevice.setUserID("878787");
        devices.add(userDevice);
        devices.add(userDevice);
        devices.add(userDevice);
        adapter = new RoomListAdapter(getContext(), setRoomData(devices));
        roomListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
}

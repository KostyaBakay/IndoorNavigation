package com.example.fooandbar.ibeacon.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.adapter.RoomListAdapter;
import com.example.fooandbar.ibeacon.model.Room;

import java.util.ArrayList;

public class MainDataFragment extends Fragment {
    ArrayList<Room> roomsArrayList;
    ListView roomListView;
    RoomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_data,container);
        roomListView = (ListView) view.findViewById(R.id.roomListView);
        roomsArrayList = new ArrayList<>();




        return  view;
    }

    public void setRoomData(){
        Room singleRoom = new Room();
        //singleRoom.set();
        roomsArrayList.add(singleRoom);
    }
}

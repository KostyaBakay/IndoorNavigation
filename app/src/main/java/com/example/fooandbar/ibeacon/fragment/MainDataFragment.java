package com.example.fooandbar.ibeacon.fragment;

import android.os.Bundle;
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
    ArrayList<Room> roomsArrayList = new ArrayList<>();;
    ListView roomListView;
    RoomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_data,container,false);

        roomListView = (ListView) view.findViewById(R.id.roomListView);
        adapter = new RoomListAdapter(getContext(), R.layout.item_room,roomsArrayList);
        roomListView.setDivider(null);
        roomListView.setAdapter(adapter);
        setRoomData();


        return  view;
    }

    public void setRoomData(){
        Room singleRoom = new Room();
//        singleRoom.setAlias("SomeAlias");
//        singleRoom.setmUsersInRoom(21);
//        singleRoom.setName("Texas");
        roomsArrayList.add(singleRoom);
        roomsArrayList.add(singleRoom);
        roomsArrayList.add(singleRoom);
        roomsArrayList.add(singleRoom);
        adapter.notifyDataSetChanged();
    }
}

package com.example.fooandbar.ibeacon.adapter;


import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.fooandbar.ibeacon.model.Room;

public class RoomListAdapter extends ArrayAdapter<Room>{

    public RoomListAdapter(Context context, int resource) {
        super(context, resource);
    }
}

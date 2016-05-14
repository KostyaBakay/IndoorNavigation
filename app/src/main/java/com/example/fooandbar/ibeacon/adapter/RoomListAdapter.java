package com.example.fooandbar.ibeacon.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.model.Room;

import java.util.ArrayList;

public class RoomListAdapter extends ArrayAdapter<Room>{
    ArrayList<Room> arrayList;
    Context mContext;

    public RoomListAdapter(Context context, int resource, ArrayList<Room> arrayList) {
        super(context, resource,arrayList);
        this.mContext = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_room,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            convertView.getTag();
        }


        Room singleRoom = getItem(position);
        viewHolder.roomNameTextView.setText(singleRoom.getName());
        viewHolder.usersInRoomTextView.setText(singleRoom.getmUsersInRoom()+"");
        viewHolder.roomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"clicked on " + position,Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }

    class ViewHolder{
        TextView usersInRoomTextView;
        TextView roomNameTextView;
        LinearLayout roomHolder;

        public ViewHolder(View view){
            usersInRoomTextView = (TextView) view.findViewById(R.id.usersInRoomTextView);
            roomNameTextView = (TextView) view.findViewById(R.id.roomName);
            roomHolder = (LinearLayout) view.findViewById(R.id.roomHolder);
        }
    }
}

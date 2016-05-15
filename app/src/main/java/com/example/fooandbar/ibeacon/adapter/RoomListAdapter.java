package com.example.fooandbar.ibeacon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.model.Room;
import com.example.fooandbar.ibeacon.model.UserDevice;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RoomListAdapter extends BaseExpandableListAdapter{
    LinkedHashMap<Room,ArrayList<UserDevice>> mMap;
    Context mContext;
    Object[] rooms;

    public RoomListAdapter(Context context, LinkedHashMap<Room,ArrayList<UserDevice>> map) {
        this.mContext = context;
        this.mMap = map;
        this.rooms = mMap.keySet().toArray();
    }

    @Override
    public int getGroupCount() {
        return mMap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mMap.get(rooms[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return rooms[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mMap.get(rooms[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_room,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(mMap.get(rooms[groupPosition]).size()<10){
            viewHolder.usersInRoomTextView.setText(mMap.get(rooms[groupPosition]).size()+" ");
        }else{
            viewHolder.usersInRoomTextView.setText(mMap.get(rooms[groupPosition]).size()+"");
        }
        viewHolder.roomName.setText(((Room)rooms[groupPosition]).getName());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_user,parent,false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.userDistanceTextView.setText(mMap.get(rooms[groupPosition]).get(childPosition).getDistance()+"");
        childViewHolder.userNameTextView.setText(mMap.get(rooms[groupPosition]).get(childPosition).getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    class ViewHolder{
        TextView usersInRoomTextView;
        TextView roomName;
        LinearLayout roomHolder;
        public ViewHolder(View view) {
            usersInRoomTextView = (TextView) view.findViewById(R.id.usersInRoomTextView);
            roomName = (TextView) view.findViewById(R.id.roomName);
            roomHolder = (LinearLayout) view.findViewById(R.id.roomHolder);
        }
    }
    class ChildViewHolder{
        TextView userDistanceTextView;
        TextView userNameTextView;
        LinearLayout childHolder;
        public ChildViewHolder(View view) {
            userDistanceTextView = (TextView) view.findViewById(R.id.userDistanceTextView);
            userNameTextView = (TextView) view.findViewById(R.id.userNameTextView);
            childHolder = (LinearLayout) view.findViewById(R.id.childHolder);
        }
    }
}
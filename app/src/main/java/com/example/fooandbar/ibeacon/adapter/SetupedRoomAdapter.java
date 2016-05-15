package com.example.fooandbar.ibeacon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.abstractions.AbstractListAdapter;
import com.example.fooandbar.ibeacon.model.Room;

import java.util.ArrayList;

public class SetupedRoomAdapter extends AbstractListAdapter<Room> {

	public SetupedRoomAdapter(Context context, ArrayList<Room> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_setted_room, parent, false);
		}
		Room item = mList.get(position);

		((TextView) convertView.findViewById(R.id.name)).setText(item.getName());
		return convertView;
	}
}

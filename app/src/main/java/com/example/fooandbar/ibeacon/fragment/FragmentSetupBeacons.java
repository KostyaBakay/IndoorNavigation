package com.example.fooandbar.ibeacon.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.fooandbar.ibeacon.R;
import com.example.fooandbar.ibeacon.activity.MainActivity;
import com.example.fooandbar.ibeacon.adapter.SetupedRoomAdapter;
import com.example.fooandbar.ibeacon.model.Room;
import com.example.fooandbar.ibeacon.model.UserDevice;
import com.example.fooandbar.ibeacon.storage.FirebaseRepo;
import com.example.fooandbar.ibeacon.storage.StorageContract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class FragmentSetupBeacons extends Fragment
		implements View.OnClickListener, StorageContract.OnAllRoomsMapRetrievedCallback, AdapterView.OnItemLongClickListener {

	public static final String TAG = "FragmentSetupBeacons";

	private ListView mListView;
	private SetupedRoomAdapter mAdapter;

	public static FragmentSetupBeacons getInstance(@Nullable Bundle data) {
		FragmentSetupBeacons fragment = new FragmentSetupBeacons();
		fragment.setArguments(data == null ? new Bundle() : data);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setup_beacons, null);
		((MainActivity) getActivity()).getToolbar().setTitle(R.string.edit_rooms);
		setupUI(view);
		updateContent();
		return view;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.fab:
				showAddedDialog();
				break;
		}
	}

	private void setupUI(View view) {
		FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(this);
		mListView = (ListView) view.findViewById(R.id.list_view);
		mListView.setOnItemLongClickListener(this);
	}

	private void updateContent() {
		new FirebaseRepo().getAllRoomsMap(this);
	}

	private void showAddedDialog() {
		final View view = getActivity().getLayoutInflater().inflate(R.layout.view_add_room, null);
		final EditText etName = (EditText) view.findViewById(R.id.name);
		final EditText etUniqueId = (EditText) view.findViewById(R.id.unique_id);
		new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog)
				.setTitle("Add Room")
				.setView(view)
				.setPositiveButton("Add", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String name = etName.getText().toString();
						String uniqueId = etUniqueId.getText().toString();
						if (!name.isEmpty() && !uniqueId.isEmpty()) {
							new FirebaseRepo().createRoom(uniqueId, name);
							new FirebaseRepo().getAllRoomsMap(FragmentSetupBeacons.this);
						}
					}
				})
				.create().show();
	}

	@Override
	public void onAllRoomsMapRetrieved(Map<String, Room> roomMap) {
		if (roomMap != null && !roomMap.isEmpty()) {
			Iterator iterator = roomMap.values().iterator();
			ArrayList<Room> list = new ArrayList<>();
			while (iterator.hasNext()) {
				list.add((Room)iterator.next());
			}

			if (mAdapter == null) {
				mAdapter = new SetupedRoomAdapter(getActivity(), list);
			}
			else mAdapter.setList(list);
			if (mListView != null) mListView.setAdapter(mAdapter);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		if (mAdapter != null) {
			Room room = mAdapter.getItem(position);
			if (room != null) {
				Map<String, UserDevice> map = room.getUserDevices();
				if (map != null) {
					Iterator<UserDevice> iterator = map.values().iterator();
					if (iterator.hasNext()) {
						new FirebaseRepo().deleteRoomById(iterator.next().getIdBeacon());
						new FirebaseRepo().getAllRoomsMap(this);
					}
				}
			}
		}
		return true;
	}
}

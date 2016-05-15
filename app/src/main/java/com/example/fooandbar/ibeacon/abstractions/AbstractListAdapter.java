package com.example.fooandbar.ibeacon.abstractions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Template of BaseAdapter<br>
 *
 * <b>Available:</b>
 * <ul>
 *     <li>{@link ArrayList} mList</li>
 *     <li>{@link LayoutInflater} mLayoutInflater</li>
 * </ul>
 * @param <OL> item of list
 */
public abstract class AbstractListAdapter<OL> extends BaseAdapter {

	protected ArrayList<OL> mList;
	protected LayoutInflater mLayoutInflater;

	public AbstractListAdapter(Context context, ArrayList<OL> list) {
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList = list;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return mList != null ? mList.size() : 0;
	}

	@Override
	abstract public View getView(int position, View convertView, ViewGroup parent);



	@Nullable
	@Override
	public OL getItem(int position) {
		if (mList != null) {
			if (position >= 0 && position < mList.size()) {
				return mList.get(position);
			}
		}
		return null;
	}

	/**
	 * Return list, if null return empty ArrayList
	 *
	 * @return ArrayList
	 */
	@NonNull
	public ArrayList<OL> getList() {
		if (mList != null) return mList;
		else return new ArrayList<>();
	}

	/**
	 * Insert item to list
	 *
	 * @param item object item
	 */
	public void addItem(OL item) {
		if (mList != null) {
			mList.add(item);
			notifyDataSetChanged();
		}
	}

	/**
	 * Insert item to list
	 *
	 * @param index position
	 * @param item object item
	 */
	public void addItem(int index, OL item) {
		if (mList != null) {
			mList.add(index, item);
			notifyDataSetChanged();
		}
	}

	/**
	 * Replace item
	 *
	 * @param index position
	 * @param item object item
	 */
	public void replaceItem(int index, OL item) {
		if (mList != null) {
			mList.set(index, item);
			notifyDataSetChanged();
		}
	}

	/**
	 * Remove item
	 *
	 * @param index position
	 */
	public void removeItem(int index) {
		if (mList != null) {
			mList.remove(index);
			notifyDataSetChanged();
		}
	}

	/**
	 * Change list
	 *
	 * @param list new list
	 */
	public void setList(ArrayList<OL> list) {
		if (list != null) {
			mList = list;
			notifyDataSetChanged();
		}
	}

}

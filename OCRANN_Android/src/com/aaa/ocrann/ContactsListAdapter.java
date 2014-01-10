package com.aaa.ocrann;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ContactsListAdapter extends BaseAdapter implements ListAdapter {

	ArrayList<ContactObject> contactsList;

	Context mcontext;

	public ContactsListAdapter(Context context, ArrayList<ContactObject> contactsList) {
		this.mcontext = context;
		this.contactsList = contactsList;
	}

	@Override
	public int getCount() {
		return contactsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View v = ((Activity) mcontext).getLayoutInflater().inflate(
				R.layout.contact_item_layout, null, false);
		((TextView)v.findViewById(R.id.contact_name)).setText(contactsList.get(arg0).name);
		((TextView)v.findViewById(R.id.contact_number)).setText(contactsList.get(arg0).number);
		
		return v;
	}

}

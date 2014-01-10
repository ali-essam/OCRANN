package com.aaa.ocrann;

import java.io.InputStream;
import java.util.ArrayList;

import com.aaa.ocrann.views.DrawingArea;
import com.aaa.ocrann.views.OnDrawCompleteListener;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends Activity {

	DrawingArea digitDrawingArea;
	ImageButton deleteButton;
	EditText phoneEditText;
	ListView contactsListView;
	ArrayList<ContactObject> contactObjectList;
	ArrayList<ContactObject> filteredContactObjectList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		digitDrawingArea = (DrawingArea) findViewById(R.id.DigitDrawingArea);
		deleteButton = (ImageButton) findViewById(R.id.DeleteButton);
		phoneEditText = (EditText) findViewById(R.id.PhoneText);
		contactsListView = (ListView) findViewById(R.id.ContactsList);

		digitDrawingArea
				.setOnDrawCompleteListener(new OnDrawCompleteListener() {
					@Override
					public void onDrawComplete(Bitmap bitmap) {
						MainActivity.this.onDrawComplete(bitmap);
					}
				});

		getContactsList();
		filteredContactObjectList = contactObjectList;

		contactsListView.setAdapter(new ContactsListAdapter(this,
				filteredContactObjectList));

		phoneEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				filteredContactObjectList = new ArrayList<ContactObject>();
				for (int i = 0; i < contactObjectList.size(); i++) {
					if (contactObjectList.get(i).number.startsWith(s.toString())) {
						filteredContactObjectList.add(contactObjectList.get(i));
					}
				}
				contactsListView.setAdapter(new ContactsListAdapter(
						MainActivity.this, filteredContactObjectList));
			}
		});

		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Editable text = phoneEditText.getText();
				if (text.length() > 0){
					phoneEditText.setText(text.subSequence(0, text.length() - 1));
					phoneEditText.setSelection(text.length()-1);
				}
			}
		});
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}
	
	
	
	private void onDrawComplete(Bitmap bitmap) {
		phoneEditText.append("0");
		bitmap.recycle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void getContactsList() {
		contactObjectList = new ArrayList<ContactObject>();
		Cursor phones = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {			
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			phoneNumber = phoneNumber.replaceAll("[\\+\\-]", "");
			// Log.e("contacts", name + ": " + phoneNumber);
			ContactObject contact = new ContactObject(name, phoneNumber);
			contactObjectList.add(contact);
		}
		phones.close();
	}

}

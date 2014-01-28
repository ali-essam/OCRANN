package com.aaa.ocrann;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import neuralNetworkLibrary.NeuralNet;

import com.aaa.ocrann.views.DrawingArea;
import com.aaa.ocrann.views.OnDrawCompleteListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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

	NeuralNet net;

	public static NeuralNet loadNetwork() {
		File sdcard = Environment.getExternalStorageDirectory();

		// Get the text file
		File file = new File(sdcard, "net8");

		NeuralNet net = null;
		try {
			FileInputStream door = new FileInputStream(file);
			ObjectInputStream reader = new ObjectInputStream(door);
			net = (NeuralNet) reader.readObject();
			reader.close();
		} catch (Exception e) {
			Log.e("nn", e.getMessage());
		}
		return net;
	}
	
	private class LoadNetworkTask extends AsyncTask<Boolean, Boolean, Boolean>{

		ProgressDialog dialog;
		@Override
		protected void onPreExecute(){
			dialog = ProgressDialog.show(MainActivity.this, "Loading Handwritting Data", "Loading Handwritting Data");
		}
		
		@Override
		protected Boolean doInBackground(Boolean... params) {
			net = loadNetwork();
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean arg){
			dialog.dismiss();
		}
		
	}

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
					if (contactObjectList.get(i).number.contains(s.toString())) {
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
				if (text.length() > 0) {
					phoneEditText.setText(text.subSequence(0, text.length() - 1));
					phoneEditText.setSelection(text.length() - 1);
				}
			}
		});
		
		deleteButton.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				phoneEditText.setText("");
				return true;
			}
		});

		LoadNetworkTask loadNetworkTask = new LoadNetworkTask();
		loadNetworkTask.execute(true);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	double[] bitmapToArray(Bitmap bitmap) {
		double[] ar = new double[bitmap.getWidth() * bitmap.getHeight()];
		for (int i = 0; i < bitmap.getHeight(); i++) {
			for (int j = 0; j < bitmap.getWidth(); j++) {
				ar[i * bitmap.getHeight() + j] = (Color.red(bitmap.getPixel(j,
						i)) > 128) ? 1 : 0;
			}
		}
		return ar;
	}

	private int getMax(double[] ar) {
		double m = -1;
		int mi = -1;
		for (int i = 0; i < ar.length; i++) {
			if (m <= ar[i]) {
				m = ar[i];
				mi = i;
			}
		}
		return mi;
	}

	private Bitmap cropToCenter(Bitmap bitmap){
		int h = bitmap.getHeight(), w = bitmap.getWidth();
		int top= h,bottom = 0,left = w,right = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if(Color.red(bitmap.getPixel(j, i))>128){
					top = Math.min(i,top);
					bottom = Math.max(i,bottom);
					left = Math.min(j, left);
					right = Math.max(j,right);
				}
			}
		}
		int nw = Math.abs(right - left);
		int nh = Math.abs(bottom - top);
		int side = Math.max(nw, nh);
		
		//Bitmap nbmp = Bitmap.createBitmap(bitmap, left - wDif/2, top-hDif/2, side, side);
		Bitmap nbmp = Bitmap.createBitmap(side,side,Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(nbmp);
		canvas.drawBitmap(bitmap, new Rect(left, top, right, bottom), new Rect(side/2-nw/2, side/2-nh/2, side/2+nw/2, side/2+nh/2), new Paint());
		
		return nbmp;
	}
	
	
	int i=0;
	private void onDrawComplete(Bitmap bitmap) {
		int squareSide = 50;
		Bitmap squaredBmp = Bitmap.createScaledBitmap(bitmap,squareSide , squareSide, false);
		bitmap.recycle();
		Bitmap cropped = cropToCenter(squaredBmp);
		squaredBmp.recycle();
		Bitmap scaledBmp = Bitmap.createScaledBitmap(cropped, 20, 20, false);
		cropped.recycle();
		Bitmap centered = Bitmap.createBitmap(28, 28, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(centered);
		canvas.drawBitmap(scaledBmp, 4, 4, new Paint());
		scaledBmp.recycle();
		
		try {
			FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath()+"/OCRANN/Pic"+ i +".png"));i++;
			centered.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double[] inp = bitmapToArray(centered);
		centered.recycle();
		double[] out = net.run(inp);
		//double out[] = new double[10];
		int max = getMax(out);
		
		phoneEditText.append(max+"");
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
			phoneNumber = phoneNumber.replaceAll("[\\+\\- ]", "");
			// Log.e("contacts", name + ": " + phoneNumber);
			ContactObject contact = new ContactObject(name, phoneNumber);
			contactObjectList.add(contact);
		}
		phones.close();
	}

}

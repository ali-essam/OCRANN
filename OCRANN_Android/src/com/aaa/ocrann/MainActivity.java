package com.aaa.ocrann;

import com.aaa.ocrann.views.DrawingArea;
import com.aaa.ocrann.views.OnDrawCompleteListener;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	DrawingArea digitDrawingArea;
	Button deleteButton;
	EditText phoneEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		digitDrawingArea = (DrawingArea)findViewById(R.id.DigitDrawingArea);
		deleteButton = (Button)findViewById(R.id.DeleteButton);
		phoneEditText = (EditText)findViewById(R.id.PhoneText);
		
		digitDrawingArea.setOnDrawCompleteListener(new OnDrawCompleteListener() {
			@Override
			public void onDrawComplete(Bitmap bitmap) {
				MainActivity.this.onDrawComplete(bitmap);
			}
		});
	}
	
	private void onDrawComplete(Bitmap bitmap){
		phoneEditText.append("0");
		bitmap.recycle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

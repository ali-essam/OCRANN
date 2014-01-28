package com.aaa.ocrann.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class DrawingArea extends View {

	Paint mpaint;

	Canvas mcanvas;
	Bitmap mbitmap;
	Rect rect;
	Context mcontext;
	float lastX = -1, lastY = -1;
	float drawingRadius;
	float drawingScale = (float)(20.0/320.0);

	int boxWidth;
	Rect boundsRect;
	
	OnDrawCompleteListener mOnDrawCompleteListener;

	public DrawingArea(Context context) {
		super(context);
		mcontext = context;
		init();
	}

	public DrawingArea(Context context, AttributeSet attrs) {
		super(context, attrs);
		mcontext = context;
		init();
	}

	public DrawingArea(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mcontext = context;
		init();
	}

	private int convertPxtoDip(int pixel){
	    float scale = getResources().getDisplayMetrics().density;
	    int dips=(int) ((pixel / scale) + 0.5f);
	    return dips;
	}
	
	private void init() {
		mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mpaint.setColor(0xFFFFFFFF);
	}

	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mbitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mcanvas = new Canvas(mbitmap);
		
		drawingRadius = convertPxtoDip(Math.min(w, h)) * drawingScale;
		mpaint.setStrokeWidth(drawingRadius * 2);
		
		Log.i("dp",convertPxtoDip(w) + "");
		Log.i("dp",convertPxtoDip(h) + "");
		
		boundsRect = new Rect();
		if(w>h){
			int dif = w-h;
			boundsRect.top = 0;
			boundsRect.left = dif/2;
			boundsRect.right = h+dif/2;
			boundsRect.bottom = h;
		}
		else{
			int dif = h-w;
			boundsRect.top = dif/2;
			boundsRect.left = 0;
			boundsRect.right = w;
			boundsRect.bottom = w+dif/2;
		}
		
		rect = new Rect(0, 0, w, h);
		//Paint boundsPaint = new Paint();
		//mcanvas.drawRect(boundsRect, mpaint);
	}

	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(mbitmap, rect, rect, null);
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			float x = event.getX();
			float y = event.getY();
			if (lastX == -1) {
				lastX = x;
				lastY = y;
			}
			mcanvas.drawLine(x, y, lastX, lastY, mpaint);
			mcanvas.drawCircle(x, y, drawingRadius, mpaint);
			lastX = x;
			lastY = y;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (mOnDrawCompleteListener != null)
				mOnDrawCompleteListener.onDrawComplete(mbitmap);
			mbitmap = Bitmap.createBitmap(mbitmap.getWidth(),
					mbitmap.getHeight(), Bitmap.Config.ARGB_8888);
			mcanvas = new Canvas(mbitmap);
			lastX = lastY = -1;
		}
		invalidate();
		return true;
	}

	/**
	 * @param mOnDrawCompleteListener
	 *            the mOnDrawCompleteListener to set
	 */
	public void setOnDrawCompleteListener(
			OnDrawCompleteListener mOnDrawCompleteListener) {
		this.mOnDrawCompleteListener = mOnDrawCompleteListener;
	}
}
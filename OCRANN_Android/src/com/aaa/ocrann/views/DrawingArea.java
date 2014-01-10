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

	private void init() {
		mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mpaint.setColor(0xFFFFFFFF);
		drawingRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				15, mcontext.getResources().getDisplayMetrics());
		mpaint.setStrokeWidth(drawingRadius * 2);
	}

	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mbitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mcanvas = new Canvas(mbitmap);
		rect = new Rect(0, 0, w, h);
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
			Log.e("aybtngan", x + "," + y);
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
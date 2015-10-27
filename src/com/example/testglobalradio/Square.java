package com.example.testglobalradio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Square extends ImageView{
	private Context context;
	private GestureDetector gestureListener;
	public boolean isSuckedIn = false;
	public Square(Context context) {
		super(context);
		this.context=context;
		gestureListener = new GestureDetector(context, new GestureListener());
		
	}
	
	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureListener.onTouchEvent(event);
		
		return super.onTouchEvent(event);
	}

	   private class GestureListener extends GestureDetector.SimpleOnGestureListener {

	        @Override
	        public boolean onDown(MotionEvent e) {
	            return true;
	        }
	       
	        @Override
	        public boolean onDoubleTap(MotionEvent e) {
	            float x = e.getX();
	            float y = e.getY();

	          

	            return true;
	        }
	        
	    }
}

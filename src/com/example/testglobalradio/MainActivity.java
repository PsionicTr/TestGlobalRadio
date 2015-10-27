package com.example.testglobalradio;

import java.util.ArrayList;

import android.R.attr;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

@SuppressLint("ClickableViewAccessibility") public class MainActivity extends Activity {

	private Button addShape, suckIn;
	private ImageView blackHole;
	private RelativeLayout mainLayout;
	private int counter =0;
	private ArrayList<Square> squares;
    private int windowwidth;
    private int windowheight;
    private float centreX;
    private float centreY;
    private boolean isAnimationRunning=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		squares = new ArrayList<Square>();
		setContentView(R.layout.activity_main);
		mainLayout = (RelativeLayout) findViewById(R.id.main); 
		mainLayout.setBackgroundColor(Color.WHITE);
		initUI();
		setClickListeners();
	    DisplayMetrics dm = new DisplayMetrics();
	    this.getWindowManager().getDefaultDisplay().getMetrics( dm );
        windowwidth =dm.widthPixels;
        windowheight = dm.heightPixels;

        
	}
	
	private void initUI(){
		addShape = (Button) findViewById(R.id.addShape);
		suckIn = (Button) findViewById(R.id.suckIn);
		blackHole = (ImageView) findViewById(R.id.blackHole);
	}
	private void setClickListeners(){
		addShape.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addRandomSquare();
							
			}
		});
		
		suckIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				suckIn();

			}
		});
	}
	private void suckIn(){
		if(squares.size()>0){
			for(Square square : squares){
				if(!square.isSuckedIn)
				animate(square);
			}
		}
	}
	/**
	 * Adds a random square to the screen
	 */
	private void addRandomSquare(){	
		Point randomPostion = Utils.generateRandomPosition(0, addShape.getTop()-Constants.SQUARE_SIDE, 0, mainLayout.getRight());
		final Square temp = new Square(MainActivity.this);
		temp.setClickable(true);
		RelativeLayout.LayoutParams paramas = new RelativeLayout.LayoutParams(Constants.SQUARE_SIDE, Constants.SQUARE_SIDE);
		temp.setLayoutParams(paramas);
		temp.setX(randomPostion.x);
		temp.setY(randomPostion.y);
		temp.setBackgroundColor(Utils.randInt(Color.BLACK, Color.WHITE));
		temp.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                  LayoutParams layoutParams = (LayoutParams) temp
                                .getLayoutParams();
                  switch (event.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                         break;
                  case MotionEvent.ACTION_MOVE:
                         int x_cord = (int) event.getRawX();
                         int y_cord = (int) event.getRawY();
                
                         x_cord-=25;
                         y_cord-=75;
                         /**
                          * Uncomment if you wan't to have screen boarders when dragging the squares
                          */
                         
                         //right side boarder
//                         if ((x_cord+Constants.SQUARE_SIDE) > windowwidth) {
//                                x_cord = windowwidth-Constants.SQUARE_SIDE;
//                         }
//                         //left side boarder
//                         if(x_cord<0){
//                        	 x_cord = 0;
//                         }
//                         //top side boarder
//                         if (y_cord < 0) {
//                                y_cord = 0;
//                         }
//                         //bottom side boarder
//                         if (y_cord+Constants.SQUARE_SIDE>=mainLayout.getHeight() ){
//                        	 	y_cord = mainLayout.getHeight()-Constants.SQUARE_SIDE;
//                         }
                         temp.setX(x_cord);
                         temp.setY(y_cord);
                         break;
                  case MotionEvent.ACTION_UP:
                	  Log.i("", "SQUARE_X "+temp.getX());
                	  if(Utils.doesColide(temp,blackHole)){
                		  animate(temp);
                	  }
                	  break;
                  default:
                         break;
                  }
                  return true;
            }
     });
		squares.add(temp);
		mainLayout.addView(temp);
	}
	
	/**
	 * Performs a scale down animation of the passed in ImageView
	 * @param view - ImageView to be scaled down
	 * @param isDrop - animation type
	 * 			true if the animation is triggered from a drag collision , 
	 * 			false if animation is triggered from something else
	 */
	private void animate(final Square view) {
		view.isSuckedIn = true;
		calculateBlackHoleCenter();
		ScaleAnimation scale = new ScaleAnimation(1, 0, 1, 0,blackHole.getX()+(blackHole.getWidth()/2),blackHole.getY()+(blackHole.getHeight()/2));
		scale.setDuration(Constants.ANIMATION_DURATION);
		scale.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				isAnimationRunning = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mainLayout.removeView(view);
				isAnimationRunning = false;	
			}
		});
		view.startAnimation(scale);

	}
	private void calculateBlackHoleCenter(){
		 centreX= (blackHole.getX() + (blackHole.getWidth()  / 2));
		 centreY= (blackHole.getY() + (blackHole.getHeight() / 2));
	}
}

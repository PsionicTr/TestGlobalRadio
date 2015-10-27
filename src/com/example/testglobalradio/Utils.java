package com.example.testglobalradio;

import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class Utils {
	/**
	 * Generates a random point within a given range of X and Y axis
	 * assuming we are working only in a Cartesian coordinate system
	 * 
	 * This method is used to ensure the squares are spawned only within the 
	 * screen dimensions and not overlapping the buttons or the black hole
	 * 
	 * @param yStart
	 * @param yEnd
	 * @param xStart
	 * @param xEnd
	 * @return
	 */
	public static Point generateRandomPosition(int yStart,
			int yEnd, int xStart, int xEnd) {
		
		Point result = new Point();
		result.x = randInt(xStart, xEnd-(int)Constants.SQUARE_SIDE);
		result.y = randInt(yStart, yEnd);
		
		return result;
	}

	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	/**
	 * Performs a collision check between the 2 passed in ImageViews
	 * @param square - a square ImageView or rectangular ImageView 
	 * @param blackHole - an ImageView containing a circle bitmap
	 * 
	 * In order this method to work the circle diameter should be equal to the side of the square 
	 * 
	 * 
	 * @return 
	 * 	true - if the rectangle intersects with the circle ImageView
	 * 	false - if bith ImageViews don't intersect
	 */
	public static boolean doesColide(ImageView square, ImageView blackHole){
	
			double circleCenterY = blackHole.getY()+(blackHole.getHeight()/2);
			double circleCenterX = blackHole.getX()+(blackHole.getWidth()/2);
			double rectangleCenterY = square.getY()+(square.getHeight()/2);
			double rectangleCenterX = square.getX()+(square.getWidth()/2);
			double radius = blackHole.getHeight()/2;
			
			double yDistance = Math.abs(circleCenterY-rectangleCenterY);
			double xDistance = Math.abs(circleCenterX-rectangleCenterX);
			
			if(xDistance>(square.getWidth()/2+radius)){
				return false;
			}
			if(yDistance>(square.getHeight()/2+radius)){
				return false;
			}
			if(xDistance<(square.getWidth()/2)){
				return true;
			}
			if(yDistance<(square.getHeight()/2)){
				return true;
			}
			
			double cornerDistance = Math.pow((xDistance-square.getWidth()/2), 2)+ Math.pow((yDistance-square.getHeight()/2), 2);
			return (cornerDistance<Math.pow(radius, 2));

	}
}

package com.cope.flight.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TextButtonAccessor implements TweenAccessor<TextButton> {
	
	public static final int PosX = 1;
	public static final int PosY = 2;
	public static final int PosXY = 3;
	
	public static final int Width = 4;
	public static final int Height = 5;
	public static final int Dimensions = 6;
	
	public static final int Red = 7;
	public static final int Green = 8;
	public static final int Blue = 9;
	public static final int Alpha = 10;
	public static final int Colour = 11;
	
	@Override
	public int getValues(TextButton target, int tweenType, float[] returnValues) {

		switch (tweenType){
			
			case PosX:
				returnValues[0] = target.getX();
				return 1;
			case PosY:
				returnValues[0] = target.getY();
				return 1;
			case PosXY:
				returnValues[0] = target.getX();
				returnValues[1] = target.getY();
				return 2;
				
			case Width:
				returnValues[0] = target.getX();
				return 1;
			case Height:
				returnValues[0] = target.getY();
				return 1;
			case Dimensions:
				returnValues[0] = target.getX();
				returnValues[1] = target.getY();
				return 2;
				
			case Red:
				returnValues[0] = target.getColor().r;
				return 1;
			case Green:
				returnValues[0] = target.getColor().g;
				return 1;
			case Blue:
				returnValues[0] = target.getColor().b;
				return 1;
			case Alpha:
				returnValues[0] = target.getColor().a;
				return 1;				
			case Colour:
				returnValues[0] = target.getColor().r;
				returnValues[1] = target.getColor().g;
				returnValues[2] = target.getColor().b;
				returnValues[3] = target.getColor().a;
				return 4;
			
			default: assert false; return -1;
			
		}
	}

	@Override
	public void setValues(TextButton target, int tweenType, float[] newValues) {

		switch (tweenType){
			
			case PosX:
				target.setX(newValues[0]);
				break;
			case PosY:
				target.setY(newValues[0]);
				break;
			case PosXY:
				target.setPosition(newValues[0], newValues[1]);
				break;
			
			case Width:
				target.setSize(newValues[0], target.getHeight());
				break;
			case Height:
				target.setSize(target.getWidth(), newValues[0]);
				break;
			case Dimensions:
				target.setSize(newValues[0], newValues[1]);
				break;
				
			case Red:
				target.setColor(
						newValues[0], 
						target.getColor().g, 
						target.getColor().b, 
						target.getColor().a);
				break;
			case Green:
				target.setColor(
						target.getColor().r,
						newValues[0], 
						target.getColor().b, 
						target.getColor().a);
				break;
			case Blue:
				target.setColor(
						target.getColor().r, 
						target.getColor().g, 
						newValues[0], 
						target.getColor().a);
				break;
			case Colour:
				target.setColor(
						newValues[0], 
						newValues[1], 
						newValues[2], 
						newValues[3]);
				break;
				
			default: assert false; break;
		}
		
		
	}
	
}

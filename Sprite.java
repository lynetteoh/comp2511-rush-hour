import javafx.scene.shape.Rectangle;

import java.util.Random;

import javafx.scene.paint.Color;

/*
	*** Front-end equivalent of Vehicle.java ***
	- planning to rename it

==> Functions that might be in here?
		canMoveForward()
		canMoveBackward()
*/

public class Sprite extends Rectangle {

	public Sprite(int xPos, int yPos, int width, int height){
		super(xPos, yPos, width, height);
		super.setArcHeight(5);
		super.setArcWidth(5);
		super.setFill(Color.web(getColor()));	//color(Math.random(), Math.random(), Math.random()));
	}

	public void setColor(){
		while (super.getFill().equals(Color.RED)){
			super.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		}
	}
	
	public String getColor()
	{
		String[] colors = {
				"#B1F7EC", "#BCFEDA", "#BCE9FE", "#E7D1F7", "#BECBD4",
				"#B0ECE0", "#BAF3D3", "#BAE0F7", "#E3C8F1", "#BBC6CA",
				"#FEFBAE", "#FFDFB8", "#FECCC5", "#FFFFFF", "#E6EEF0",
				"#FEE9B1", "#FCCEAC", "#F7C4C0", "#F4F9FD", "#DBE7E7"
		};
		
		return colors[(int)(Math.random() * 20)];
		
	}

}

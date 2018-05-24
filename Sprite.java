import javafx.scene.shape.Rectangle;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * @author Emily Kim Ngo (z5164090)
 * Front-end equivalent of Vehicle.java
 */

public class Sprite extends Rectangle {

	/**
	 * This constructor creates a Sprite with given dimensions and position on grid
	 * @param xPos: the x-value of the sprite's top left corner on grid
	 * @param yPos: the y-value of the sprite's top left corner on grid
	 * @param w: width of the sprite
	 * @param h: height of the sprite
	 * @pre: valid dimensions and position values
	 * @post: a Rectangle with rounded edges filled with a colour is created as a Sprite
	 */
	public Sprite(int xPos, int yPos, int width, int height){
		super(xPos, yPos, width, height);
		super.setArcHeight(5);
		super.setArcWidth(5);
		super.setFill(Color.web(getColor()));
	}

	/**
	 * This function returns the side length of a grid square
	 * @pre: none
	 * @post: Sprite is filled with a colour that's not red (since that is unique to only player's main vehicle)
	 */
	public void setColor(){
		while (super.getFill().equals(Color.RED)){
			super.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		}
	}

	/**
	 * This function returns a random colour used to fill in the block's colour
	 * @return: a random colour
	 */
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

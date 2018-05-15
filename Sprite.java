import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

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
		super.setFill(Color.color(Math.random(), Math.random(), Math.random()));
	}

	//@Override
	// public Sprite cloneSprite() {
	// 	// create object
	// 	Sprite s = new Sprite((int)(this.getX()), (int)(this.getY()), (int)(this.getWidth()), (int)(this.getHeight()));
	// 	return s;
	// 	// object.setX(this.getX())
	// }
}

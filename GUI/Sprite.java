import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class Sprite extends Rectangle {
	//Rectangle sprite;
	private String orientation = "";

	public Sprite(int xPos, int yPos, int width, int height){
	    //this.sprite = new Rectangle(xPos, yPos, width, height);
		super(xPos, yPos, width, height);
		super.setArcHeight(5);
		super.setArcWidth(5);
		//super.setFill(Color.BLUE);
		super.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		//super.setStroke(Color.BLUE);

		// this.sprite.setArcHeight(5);
		// this.sprite.setArcWidth(5);
		// this.sprite.setFill(Color.BLUE);
		// this.sprite.setStroke(Color.BLUE);
		System.out.println("width: " + width + " | height: " + height);
		if (width > height){
			orientation += "HORIZONTAL";
			System.out.println(">> H");
		}
		else {
			orientation += "VERTICAL";
			System.out.println(">> V");
		}
	}

	// public Rectangle getSprite(){
	// 	return this.sprite;
	// }

	public String getOrientation(){
		return this.orientation;
	}

}

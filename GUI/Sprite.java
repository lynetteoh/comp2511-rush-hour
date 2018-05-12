import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class Sprite extends Rectangle {
	private String orientation = "";

	public Sprite(int xPos, int yPos, int width, int height){
		super(xPos, yPos, width, height);
		super.setArcHeight(5);
		super.setArcWidth(5);
		super.setFill(Color.color(Math.random(), Math.random(), Math.random()));
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

	public String getOrientation(){
		return this.orientation;
	}

}

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
		super(xPos, yPos, width, height);
		super.setArcHeight(5);
		super.setArcWidth(5);
		super.setFill(Color.color(Math.random(), Math.random(), Math.random()));

		if (width > height){
			orientation += "HORIZONTAL";
		}
		else {
			orientation += "VERTICAL";
		}
	}

	public String getOrientation(){
		return this.orientation;
	}

}

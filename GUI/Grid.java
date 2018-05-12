import javafx.scene.shape.Rectangle;
import javafx.scene.Cursor;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Node;

public class Grid {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";

	private ArrayList<Rectangle> gridSquares = new ArrayList<Rectangle>();
	private ArrayList<Sprite> blocks = new ArrayList<Sprite>();
	private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;
	private int gridLength;
	private int sLength;


	public Grid(int size){
		this.sLength = 50;
		this.gridLength = size*sLength;
		double xPos = 0;
		double yPos = 0;
		for (int i=0; i < size*size; i++){
			if (i % size == 0 && i != 0){
				xPos = 0;
				yPos += 50;
			}
			Rectangle r = new Rectangle(xPos, yPos, this.sLength, this.sLength);
			r.setFill(Color.TRANSPARENT);
			r.setStroke(Color.BLACK);
			this.gridSquares.add(r);
			xPos += sLength;
		}
	}

	public Group createSprite(int xPos, int yPos, int w, int h){
		Group dragNode = new Group();
		Sprite s = new Sprite(xPos, yPos, w, h);
		dragNode.getChildren().add(s);
		dragNode.setOnMousePressed(rectOnMousePressedEventHandler);
		dragNode.setOnMouseDragged(rectOnMouseDraggedEventHandler);
		this.blocks.add(s);
		return dragNode;
	}

	public ArrayList<Rectangle> getGridSquares(){
		return this.gridSquares;
	}

	EventHandler<MouseEvent> rectOnMousePressedEventHandler =
		new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			orgSceneX = t.getSceneX();
			orgSceneY = t.getSceneY();
			orgTranslateX = ((Group)t.getSource()).getChildren().get(0).getTranslateX();
			orgTranslateY = ((Group)t.getSource()).getChildren().get(0).getTranslateY();
		}
	};

	EventHandler<MouseEvent> rectOnMouseDraggedEventHandler =
		new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			Sprite block = ((Sprite)((Group)t.getSource()).getChildren().get(0)); // block that is currently being dragged
			if (block.getOrientation().equals("HORIZONTAL")){ // IF BLOCK IS HORIZONTAL

				double offsetX = t.getSceneX() - orgSceneX;
				double newTranslateX = orgTranslateX + offsetX;
				if (block.getX() + newTranslateX < 0){ // if going out of left end of grid
					if (block.getX() == 0){
						newTranslateX = 0;
					}
					else {
						newTranslateX += Math.abs(newTranslateX + block.getX());
					}
				}
				else if ((block.getX() + newTranslateX + block.getWidth()) > gridLength){ // if going out of right end of grid
					newTranslateX -= (block.getX() + newTranslateX + block.getWidth()) - gridLength;
				}
				((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
			}
			else { // IF BLOCK IS VERTICAL
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;
				if ((block.getY() + newTranslateY) < 0){ // if going out of left end of grid
					if (block.getY() == 0){
						newTranslateY = 0;
					}
					else {
						newTranslateY += Math.abs(newTranslateY + block.getY());
					}
				}
				else if ((block.getY() + newTranslateY + block.getHeight()) > gridLength){ // if going out of right end of grid
					newTranslateY -= (block.getY() + newTranslateY + block.getHeight()) - gridLength;
				}
				((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
			}


		}
	};
}

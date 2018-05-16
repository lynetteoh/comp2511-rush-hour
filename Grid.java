import javafx.scene.shape.Rectangle;
import javafx.scene.Cursor;
import javafx.stage.Screen;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import java.lang.*;
/*
	*** Front-end equivalent of Board.java ***

==> Functions that might be in here?
		placeVehicle()
		moveForward()
		moveBackward()
		canMove()
		canPlaceVehicle()
*/

public class Grid {

	// colored text options for debugging purposes
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";

	private ArrayList<Rectangle> gridSquares = new ArrayList<Rectangle>();
    private ArrayList<Shape> blocks = new ArrayList<Shape>();
	private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;
	private int gridLength;
	private int sLength;
	private Board board;
	private ArrayList<Group> g = new ArrayList<Group>();


	public Grid(Board b, int sLength){
		this.board = b;
		this.sLength = sLength; // grid square side length
		this.gridLength = b.getN()*sLength;
		double xPos = 0;
		double yPos = 0;
		// creates grid
		int i = 0;
		for (i=0; i < b.getN()*b.getN(); i++){
			if (i % b.getN() == 0 && i != 0){
				xPos = 0;
				yPos += sLength;
			}
			Rectangle r = new Rectangle(xPos, yPos, this.sLength, this.sLength);
			r.setFill(Color.WHITE);
			r.setStroke(Color.BLACK);
			this.gridSquares.add(r);
			xPos += sLength;
		}
		// creates sprites equivalent to their vehicle counterpart
		for (i=0; i < b.getVehiclesList().size(); i++){
			Vehicle curr = b.getVehiclesList().get(i);
			Group dragNode = new Group(); // place sprite in a Group object so it can be added to root
			Sprite newSprite;
			if (curr.getOrient() == 1){ // horizontal
				newSprite = this.createSprite(curr.getPosition()[0]*sLength+1, curr.getPath()*sLength+1, curr.getLength()*sLength-2, sLength-2);

				if (curr.getId() == 1){ // first vehicle - always the red car
					newSprite.setFill(Color.RED);
				}
			}
			else {
				newSprite = this.createSprite(curr.getPath()*sLength+1, curr.getPosition()[0]*sLength+1, sLength-2, curr.getLength()*sLength-2);
			}
			dragNode.getChildren().add(newSprite);
			dragNode.setOnMousePressed(OnMousePressedEventHandler);
			dragNode.setOnMouseDragged(OnMouseDraggedEventHandler);
			dragNode.setOnMouseReleased(OnMouseReleasedEventHandler);
			this.g.add(dragNode);
		}
		//System.out.println(ANSI_BLUE + "\t DONE" + ANSI_RESET);
	}

	public Sprite createSprite(int xPos, int yPos, int w, int h){
		Sprite s = new Sprite(xPos, yPos, w, h);
		this.blocks.add(s);
		return s;
	}

	public ArrayList<Rectangle> getGridSquares(){
		return this.gridSquares;
	}

	public ArrayList<Group> getBlockGroups(){
		return this.g;
	}

	public boolean isGoalState(Vehicle v){
		if (v.getId() == 1){
			if (board.fin(v, board.getDoor(board.getN()))){
				System.out.println(ANSI_BLUE + "\t Congrats!" + ANSI_RESET);
				return true;
			}
			return false;
		}
		return false;
	}

	EventHandler<MouseEvent> OnMousePressedEventHandler =
		new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			orgSceneX = t.getSceneX();
			orgSceneY = t.getSceneY();
			orgTranslateX = ((Group)t.getSource()).getChildren().get(0).getTranslateX();
			orgTranslateY = ((Group)t.getSource()).getChildren().get(0).getTranslateY();
		}
	};

	EventHandler<MouseEvent> OnMouseDraggedEventHandler =
		new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			Sprite block = ((Sprite)((Group)t.getSource()).getChildren().get(0));
			Vehicle v = board.getVehiclesList().get(g.indexOf(((Group)t.getSource())));

			if (v.getOrient() == 1){ // horizontal vehicle

				double offsetX = t.getSceneX() - orgSceneX;
				double newTranslateX = orgTranslateX + offsetX;

				if (offsetX > 0){
					if (board.canMoveForward(v) > 0) {
						int maxMove = board.canMoveForward(v);
							if (maxMove * sLength < newTranslateX) {
								newTranslateX = maxMove * sLength;
							}
							((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
							// System.out.println(ANSI_BLUE + "MOVE " + maxMove + ANSI_RESET);
							// board.moveNSpaces(v, maxMove);
							// board.printBoard();
						}
					}
				else if (offsetX < 0){
					if (board.canMoveBackward(v) > 0) {
						int maxMove = board.canMoveBackward(v);

						if (maxMove * sLength < -newTranslateX) {
							newTranslateX = -maxMove * sLength;
						}
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					}
				}
			}
			else {
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				if (offsetY > 0){
					if (board.canMoveForward(v) > 0) {
						int maxMove = board.canMoveForward(v);
							if (maxMove * sLength < newTranslateY) {
								newTranslateY = maxMove * sLength;
							}
							((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
					}
				}
					// else {
					// 	//System.out.println(ANSI_RED + " Can't move forward!!" + ANSI_RESET);
					// }

				else if (offsetY < 0){
					if (board.canMoveBackward(v) > 0) {
int maxMove = board.canMoveBackward(v);

							if (maxMove * sLength < -newTranslateY) {
								newTranslateY = -maxMove * sLength;
							}

							((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
						}

				}
			}
		}
	};

	EventHandler<MouseEvent> OnMouseReleasedEventHandler =
		new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			Sprite block = ((Sprite)((Group)t.getSource()).getChildren().get(0));
			Vehicle v = board.getVehiclesList().get(g.indexOf(((Group)t.getSource())));

			if (v.getOrient() == 1){ // horizontal vehicles

				double offsetX = t.getSceneX() - orgSceneX;
				double newTranslateX = orgTranslateX + offsetX;

				if (offsetX >= 0){
					if (Math.abs(offsetX % sLength) >= sLength / 2) {
						newTranslateX += sLength - Math.abs(offsetX % sLength);
					}  else {
							newTranslateX -= Math.abs(offsetX % sLength);
					}
				} else if (offsetX < 0) {
					if (Math.abs(offsetX % sLength) >= sLength / 2) {
						newTranslateX -= sLength - Math.abs(offsetX % sLength);
					}  else {
							newTranslateX += Math.abs(offsetX % sLength);
					}

				}
				((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
			}
			else {
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				// if ((offsetY > 0 && board.canMoveForward(v) > 0) || (offsetX > 0 && board.canMoveForward(v) > 0)){
				// 	if (newTranslateY % sLength >= sLength / 2) {
				// 		newTranslateY += sLength - (newTranslateY % sLength);
				// 	}  else {
				// 		newTranslateY -= (newTranslateY % sLength);
				// 	}
				// }
			((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);

	        }
		}
	};

}

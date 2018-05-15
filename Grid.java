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
//int i = 0;
			if (v.getOrient() == 1){ // horizontal vehicle

				double offsetX = t.getSceneX() - orgSceneX;
				double newTranslateX = orgTranslateX + offsetX;
				//System.out.println(ANSI_RED + "getSceneX: " + t.getSceneX() + "| " + orgSceneX + "OF: " + offsetX  + " | " + orgTranslateX + ANSI_RESET);

				if (offsetX > 0){
					if (board.canMoveForward(v) > 0) {
						//System.out.println(ANSI_BLUE + " Can move forward!!" + ANSI_RESET);
						double c = (block.getX() + newTranslateX + block.getWidth());
						double g = (c - (c % sLength) + sLength);
						//System.out.println(ANSI_BLUE + "newTR: " + c + " | nextEdge: " + g);
						if (c >= g - 1){ // now works, just need to ensure it is not the current block it resides in!
							//System.out.println(ANSI_GREEN + "\t [MOVED FORWARD]" + ANSI_RESET);
							newTranslateX -= c - g + 1;
							((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
							board.moveForward(v);
							if (isGoalState(v)) System.exit(0);

						 	board.printBoard();
						}
						else {
							//System.out.println("FORWARD");
							if (block.getX() + newTranslateX < 0){ // if going out of left end of grid
								if (block.getX() == 1){
									newTranslateX = 0;
								}
								else {
									newTranslateX += Math.abs(newTranslateX + block.getX() - 1);
								}
							}
							else if ((block.getX() + newTranslateX + block.getWidth()) > gridLength - 1){ // if going out of right end of grid
								newTranslateX -= (block.getX() + newTranslateX + block.getWidth()) - gridLength + 1;
							}
							((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
						}
					}
					else {
						//System.out.println(ANSI_RED + " Can't move forward!!" + ANSI_RESET);
					}
				}
				else if (offsetX < 0){
					if (board.canMoveBackward(v) > 0) {
						//System.out.println(ANSI_RED + " Can move backward!!" + ANSI_RESET);
						double c = (block.getX() + newTranslateX + block.getWidth());
						double g = (c + (c % sLength) - sLength);
						//System.out.println(ANSI_PURPLE + "newTR: " + c + " | nextEdge: " + g);
						if (c <= g + 1){ // now works, just need to ensure it is not the current block it resides in!
							//System.out.println(ANSI_GREEN + "\t [MOVED BACKWARD]" + ANSI_RESET);
							//newTranslateX += c - g - 1;
							((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
							// (offsetX - (offsetX % sLength)) + sLength
							board.moveBackward(v);
						 	board.printBoard();
						}
						else {
							//System.out.println(ANSI_GREEN + "PRINT" + ANSI_RESET);
							if (block.getX() + newTranslateX < 0){ // if going out of left end of grid
								if (block.getX() == 1){
									newTranslateX = 0;
								}
								else {
									newTranslateX += Math.abs(newTranslateX + block.getX() - 1);
								}
							}
							else if ((block.getX() + newTranslateX + block.getWidth()) > gridLength - 1){ // if going out of right end of grid
								newTranslateX -= (block.getX() + newTranslateX + block.getWidth()) - gridLength + 1;
							}
							((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
						}
					}
					else {
						//System.out.println(ANSI_RED + " Can't move backward!!" + ANSI_RESET);
					}
				}
			}
			else {
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				if (offsetY > 0){
					if (board.canMoveForward(v) > 0) {
						//System.out.println(ANSI_BLUE + " Can move forward!!" + ANSI_RESET);
						double c = (block.getY() + newTranslateY + block.getHeight());
						double g = (c - (c % sLength) + sLength);
						//System.out.println(ANSI_BLUE + "newTR: " + c + " | nextEdge: " + g);
						if (c >= g - 1){ // now works, just need to ensure it is not the current block it resides in!
							//System.out.println(ANSI_GREEN + "\t [MOVED FORWARD]" + ANSI_RESET);
							newTranslateY -= c - g + 1;
							((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
							board.moveForward(v);
							board.printBoard();
						}
						else {
							if (block.getY() + newTranslateY < 0){ // if going out of left end of grid
								if (block.getY() == 1){
									newTranslateY = 0;
								}
								else {
									newTranslateY += Math.abs(newTranslateY + block.getY() - 1);
								}
							}
							else if ((block.getY() + newTranslateY + block.getHeight()) > gridLength - 1){ // if going out of right end of grid
								newTranslateY -= (block.getY() + newTranslateY + block.getHeight()) - gridLength + 1;
							}
							((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
						}
					}
					else {
						//System.out.println(ANSI_RED + " Can't move forward!!" + ANSI_RESET);
					}
				}
				else {
					if (board.canMoveBackward(v) > 0) {
						//System.out.println(ANSI_RED + " Can move backward!!" + ANSI_RESET);
						double c = (block.getY() + newTranslateY + block.getHeight());
						double g = (c + (c % sLength) - sLength);
						//System.out.println(ANSI_PURPLE + "newTR: " + c + " | nextEdge: " + g);
						if (c <= g + 1){ // now works, just need to ensure it is not the current block it resides in!
							//System.out.println(ANSI_GREEN + "\t [MOVED BACKWARD]" + ANSI_RESET);
							//newTranslateX += c - g - 1;
							((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
							// (offsetX - (offsetX % sLength)) + sLength
							board.moveBackward(v);
							board.printBoard();
						}
						else {
							if (block.getY() + newTranslateY < 0){ // if going out of left end of grid
								if (block.getY() == 1){
									newTranslateY = 0;
								}
								else {
									newTranslateY += Math.abs(newTranslateY + block.getY() - 1);
								}
							}
							else if ((block.getY() + newTranslateY + block.getHeight()) > gridLength - 1){ // if going out of right end of grid
								newTranslateY -= (block.getY() + newTranslateY + block.getHeight()) - gridLength + 1;
							}
							((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
						}
					}
					else {
						//System.out.println(ANSI_RED + " Can't move backward!!" + ANSI_RESET);
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
				//System.out.println(ANSI_GREEN + "[NX: " + newTranslateX + ANSI_RED + " | OFF: " + offsetX + ANSI_RESET);
				// can be potentially reduced to less nested if else statements
				if ((block.getX() + newTranslateX) >= 2 && (block.getX() + newTranslateX + block.getWidth()) <= gridLength-2){ // if going out of left end of grid
					double slideCorrection;
					slideCorrection = newTranslateX - (newTranslateX % sLength);
					//System.out.println("|||||" + (offsetX % sLength) + " | sL: " + sLength);
					if (Math.abs(offsetX % sLength) >= (sLength/2)){ // moving forwards
						if (offsetX > 0){
							//System.out.println(ANSI_PURPLE + (offsetX % sLength) + "| " + (sLength/2));
							//System.out.println(ANSI_BLUE + "S+L" + ANSI_RESET); // moveForward
							newTranslateX = slideCorrection + sLength;
							board.moveForward(v);
							if (isGoalState(v)) System.exit(0);
							board.printBoard();
						}
						else if (offsetX < 0) {
								//System.out.println(ANSI_RED + "S-L" + ANSI_RESET); // moveBackwards
								newTranslateX = slideCorrection - sLength;
								board.moveBackward(v);
								board.printBoard();
						}
						// else {
						// 	newTranslateX = slideCorrection;
						// }
					}
					// else if (-(newTranslateX % sLength) >= (sLength/2) && offsetX < 0){ // moving backwards
					// 	System.out.println(ANSI_RED + "S-L" + ANSI_RESET); // moveBackwards
					// 	newTranslateX = slideCorrection - sLength;
					// 	board.moveBackward(v);
					// 	board.printBoard();
					// }
					else { // when it does not reach the halfway point of either adjacent cells ie/ it slides back to its original position
						//System.out.println(":(");
						// if (offsetX > 0){
						// 	newTranslateX -= newTranslateX % sLength;
						// }
						// else if (offsetX < 0){
						// 	newTranslateX += newTranslateX % sLength;
						// }
						newTranslateX = slideCorrection;
						board.printBoard();
					}
					((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
				}
			}
			else {
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				// can be potentially reduced to less nested if else statements
				if ((block.getY() + newTranslateY) >= 0 && (block.getY() + newTranslateY + block.getHeight()) <= gridLength){ // if going out of left end of grid
					double slideCorrection;
					slideCorrection = newTranslateY - (newTranslateY % sLength);
					if (newTranslateY % sLength >= (sLength/2)){
						newTranslateY = slideCorrection + sLength;
					}
					else if (-(newTranslateY % sLength) >= (sLength/2)){
						newTranslateY = slideCorrection - sLength;
					}
					else {
						newTranslateY = slideCorrection;
					}
					((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
				}
	        }
		}
	};

}

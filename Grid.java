// horizontal blocks no longer exit grid

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
	private double dragTranslate;
	private double dragOffset;
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
					int maxMove = board.canMoveForward(v);
					if (maxMove > 0) {
						if (maxMove * sLength < offsetX) {
							newTranslateX = orgTranslateX + maxMove * sLength;
							System.out.println("========MAX MOVE F " + maxMove + " ======");
							dragTranslate = maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateX;
						}
						System.out.println(ANSI_BLUE + "DRAG: : " + dragTranslate + " | " + "N: " + newTranslateX + ANSI_RESET);

						dragOffset = offsetX;
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					}
				}
				else if (offsetX < 0){
					int maxMove = board.canMoveBackward(v);
					if (maxMove > 0) {
						if (maxMove * sLength < -offsetX) {
							newTranslateX = orgTranslateX - maxMove * sLength;
							System.out.println("========MAX MOVE B " + maxMove + " ======");
							dragTranslate = -maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateX;
						}
						System.out.println(ANSI_BLUE + "DRAG:: " + dragTranslate + " | " + "N: " + newTranslateX + ANSI_RESET);
						dragOffset = offsetX;
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					}
				}
			}
			else {
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				if (offsetY > 0){
					int maxMove = board.canMoveForward(v);
					if (maxMove > 0) {
						if (maxMove * sLength < offsetY) {
							newTranslateY = orgTranslateY + maxMove * sLength;
							System.out.println("========MAX MOVE F " + maxMove + " ======");
							dragTranslate = maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateY;
						}
						System.out.println(ANSI_BLUE + "DRAG: : " + dragTranslate + " | " + "N: " + newTranslateY + ANSI_RESET);

						dragOffset = offsetY;
						((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
					}
				}
				else if (offsetY < 0){
					int maxMove = board.canMoveBackward(v);
					if (maxMove > 0) {
						if (maxMove * sLength < -offsetY) {
							newTranslateY = orgTranslateY - maxMove * sLength;
							System.out.println("========MAX MOVE B " + maxMove + " ======");
							dragTranslate = -maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateY;
						}
						System.out.println(ANSI_BLUE + "DRAG:: " + dragTranslate + " | " + "N: " + newTranslateY + ANSI_RESET);
						dragOffset = offsetY;
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
			int moves = Math.abs((int)(dragTranslate/sLength));

			System.out.println("MAHTHHH " + moves);
			board.printBoard();

			if (moves > 0 && Math.abs(dragOffset) > sLength){ // dragTranslate >= sLength
				System.out.println(ANSI_PURPLE + "\t MOVEN" + ANSI_RESET);
				if (dragOffset > 0){ // if dragging block forward
					board.moveNSpaces(v, moves);
					System.out.println(ANSI_CYAN + "\t\tF [MAX MOVE FORWARD " + moves + " release]" + ANSI_RESET);
					board.printBoard();
					System.out.println("FF==============================================================");
				} else if (dragOffset < 0) { // if dragging block backward
					board.moveNSpaces(v, -1*moves);
					System.out.println(ANSI_CYAN + "\t\tB [MAX MOVE BACK " + moves + " release]" + ANSI_RESET);
					board.printBoard();
					System.out.println("BB==============================================================");
				}
			}
			if (Math.abs(dragOffset) > Math.abs(dragTranslate) && Math.abs(dragOffset % sLength) != 0 && Math.abs(dragTranslate % sLength) != 0){ // dragTranslate < sLength - move once
				moves = Math.abs((int)((Math.abs(dragOffset)-Math.abs(dragTranslate))/sLength));
				System.out.println(ANSI_BLUE + "\t\tNEEDS TO MOVES MORE: " + moves + ANSI_RESET);
				if (dragOffset > 0){
					board.moveNSpaces(v, moves);
				}
				else if (dragOffset < 0){
					board.moveNSpaces(v, -1*moves);
				}
				board.printBoard();
			}

			if (v.getOrient() == 1){ // horizontal vehicles

				double offsetX = t.getSceneX() - orgSceneX;
				double newTranslateX = orgTranslateX + offsetX;
				System.out.println(ANSI_RED + "DRAG_TRANS: " + dragTranslate + " | " + (dragTranslate % sLength) + ANSI_RESET);
				System.out.println(ANSI_BLUE + "RELEASE: O: " + orgTranslateX + " | " + "N: " + newTranslateX + " OS: " + orgSceneX + ANSI_RESET);
				System.out.println(ANSI_PURPLE + "DRAGOFF: " + dragOffset + " | OFF: " + offsetX + " | TT: " + (block.getX() + block.getWidth() + offsetX) + ANSI_RESET);





				if (dragTranslate % sLength != 0.0){
					System.out.println("MOD: " + (dragTranslate % sLength));
					if (offsetX >= 0){
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateX += sLength - Math.abs(dragOffset % sLength);
							board.moveForward(v);
							System.out.println(ANSI_BLUE + "[updated release]" + ANSI_RESET);
							board.printBoard();
						}
						else {
								newTranslateX -= Math.abs(dragOffset % sLength);
						}
						System.out.println(ANSI_RED + "AA|RELEASE: O: " + orgTranslateX + " | " + "N: " + newTranslateX + " OS: " + orgSceneX + ANSI_RESET);
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					} else if (dragOffset < 0) {
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateX -= sLength - Math.abs(dragOffset % sLength);
							board.moveBackward(v);
							System.out.println(ANSI_RED + "B [updated release]" + ANSI_RESET);
							board.printBoard();
						}
						else {
							System.out.println(ANSI_GREEN + "[NOCHANGE release]" + ANSI_RESET);
								newTranslateX += Math.abs(dragOffset % sLength);
						}
						System.out.println(ANSI_RED + "AA|RELEASE: O: " + orgTranslateX + " | " + "N: " + newTranslateX + " OS: " + orgSceneX + ANSI_RESET);
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					}
					else {
						System.out.println("SPECIALLLLLLLLLLLLL");
					}
				}
				dragTranslate = 0;
				dragOffset = 0;
		}
		else {
			double offsetY = t.getSceneY() - orgSceneY;
			double newTranslateY = orgTranslateY + offsetY;
			System.out.println(ANSI_RED + "DRAG_TRANS: " + dragTranslate + " | " + (dragTranslate % sLength) + ANSI_RESET);
			System.out.println(ANSI_BLUE + "RELEASE: O: " + orgTranslateY + " | " + "N: " + newTranslateY + " OS: " + orgSceneY + ANSI_RESET);
			System.out.println(ANSI_PURPLE + "DRAGOFF: " + dragOffset + " | OFF: " + offsetY + " | TT: " + (block.getY() + block.getHeight() + offsetY) + ANSI_RESET);

			if (dragTranslate % sLength != 0.0){
				System.out.println("MOD: " + (dragTranslate % sLength));
				if (dragOffset >= 0){
					if (Math.abs(dragOffset % sLength) >= sLength / 2) {
						newTranslateY += sLength - Math.abs(dragOffset % sLength);
						board.moveForward(v);
						System.out.println(ANSI_BLUE + "[updated release]" + ANSI_RESET);
						board.printBoard();
					}
					else {
							newTranslateY -= Math.abs(dragOffset % sLength);
					}
				} else if (dragOffset < 0) {
					if (Math.abs(dragOffset % sLength) >= sLength / 2) {
						newTranslateY -= sLength - Math.abs(dragOffset % sLength);
						board.moveBackward(v);
						System.out.println(ANSI_RED + "B [updated release]" + ANSI_RESET);
						board.printBoard();
					}
					else {
						System.out.println(ANSI_GREEN + "[NOCHANGE release]" + ANSI_RESET);
							newTranslateY += Math.abs(dragOffset % sLength);
					}
				}
				System.out.println(ANSI_RED + "AA|RELEASE: O: " + orgTranslateY + " | " + "N: " + newTranslateY + " OS: " + orgSceneY + ANSI_RESET);
				((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
			}
			dragTranslate = 0;

        }
		}
	};

}

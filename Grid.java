import javafx.scene.shape.Rectangle;
import javafx.scene.Cursor;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.Stack;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
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
    private ArrayList<Sprite> blocks = new ArrayList<Sprite>();
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
			//Image background = new Image("file:resource/background.jpg");
			//r.setFill(new ImagePattern(background));
			r.setFill(Color.web("rgba(0,0,255, 0.1)"));
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
	}

	public Sprite createSprite(int xPos, int yPos, int w, int h){
		Sprite s = new Sprite(xPos, yPos, w, h);
		s.setColor();
		this.blocks.add(s);
		return s;
	}

	public ArrayList<Rectangle> getGridSquares(){
		return this.gridSquares;
	}

	public ArrayList<Group> getBlockGroups(){
		return this.g;
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
							dragTranslate = maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateX;
						}

						dragOffset = offsetX;
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					}
				}
				else if (offsetX < 0){
					int maxMove = board.canMoveBackward(v);
					if (maxMove > 0) {
						if (maxMove * sLength < -offsetX) {
							newTranslateX = orgTranslateX - maxMove * sLength;
							dragTranslate = -maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateX;
						}
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
							// System.out.println("========MAX MOVE F " + maxMove + " ======");
							dragTranslate = maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateY;
						}
						dragOffset = offsetY;
						((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
					}
				}
				else if (offsetY < 0){
					int maxMove = board.canMoveBackward(v);
					if (maxMove > 0) {
						if (maxMove * sLength < -offsetY) {
							newTranslateY = orgTranslateY - maxMove * sLength;
							dragTranslate = -maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateY;
						}
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

			// board.printBoard();

			if (moves > 0 && Math.abs(dragOffset) > sLength){ // dragTranslate >= sLength
				if (dragOffset > 0){ // if dragging block forward
					board.moveNSpaces(v, moves);
					// board.printBoard();
				} else if (dragOffset < 0) { // if dragging block backward
					board.moveNSpaces(v, -1*moves);
					// board.printBoard();
				}
			}
			if (Math.abs(dragOffset) > Math.abs(dragTranslate) && Math.abs(dragOffset % sLength) != 0 && Math.abs(dragTranslate % sLength) != 0){ // dragTranslate < sLength - move once
				moves = Math.abs((int)((Math.abs(dragOffset)-Math.abs(dragTranslate))/sLength));
				if (dragOffset > 0){
					board.moveNSpaces(v, moves);
				}
				else if (dragOffset < 0){
					board.moveNSpaces(v, -1*moves);
				}
				// board.printBoard();
			}

			if (v.getOrient() == 1){ // horizontal vehicles

				double offsetX = t.getSceneX() - orgSceneX;
				double newTranslateX = orgTranslateX + offsetX;

				if (dragTranslate % sLength != 0.0){
					if (offsetX >= 0){
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateX += sLength - Math.abs(dragOffset % sLength);
							board.moveForward(v);
							// board.printBoard();
						}
						else {
								newTranslateX -= Math.abs(dragOffset % sLength);
						}
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					} else if (dragOffset < 0) {
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateX -= sLength - Math.abs(dragOffset % sLength);
							board.moveBackward(v);
							// board.printBoard();
						}
						else {
								newTranslateX += Math.abs(dragOffset % sLength);
						}
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					}
				}
			}
			else {
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				if (dragTranslate % sLength != 0.0){
					if (dragOffset >= 0){
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateY += sLength - Math.abs(dragOffset % sLength);
							board.moveForward(v);
							// board.printBoard();
						}
						else {
								newTranslateY -= Math.abs(dragOffset % sLength);
						}
					} else if (dragOffset < 0) {
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateY -= sLength - Math.abs(dragOffset % sLength);
							board.moveBackward(v);
							// board.printBoard();
						}
						else {
								newTranslateY += Math.abs(dragOffset % sLength);
						}
					}
					((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
				}
	        }
			dragTranslate = 0;
			dragOffset = 0;
			if (board.fin(v)){
				System.out.println(ANSI_BLUE + "\tYOU WIN!!" + ANSI_RESET);
			}
		}
	};


	public int getMoves() {
		return board.getnMoves();
	}

	public int getBoardSize() {
		return board.getN();
	}

	public int getsLength() {
		return sLength;
	}

	public void setsLength(int sLength) {
		this.sLength = sLength;
	}
	public void setGridLength(int sLength) {
		this.gridLength = sLength * board.getN();
	}

}

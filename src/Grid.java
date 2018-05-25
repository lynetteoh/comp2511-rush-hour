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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import java.lang.*;

/**
 * @author Emily Kim Ngo (z5164090)
 * Front-end equivalent of Board.java
 */

public class Grid {

	private Board board; // backend board which this grid should always correspond with
	private ArrayList<Rectangle> gridSquares = new ArrayList<Rectangle>(); // list of squares which form the background grid
    private ArrayList<Sprite> blocks = new ArrayList<Sprite>(); // the sprites that represent each vehicle in backend
	private ArrayList<Group> g = new ArrayList<Group>(); // each group contains a sprite for each vehicle - needed to be able to add sprites into scene
	private int gridLength; // side length of background grid
	private int sLength; // side length of a grid square

	// doubles below are used to keep track of dragging changes to update each block's placement in grid
	private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;
	private double dragTranslate;
	private double dragOffset;

	/**
	 * This constructor creates the grid and the creation and placement of sprites which correspond to backend's vehicles
	 * @param b: the backend board this grid corresponds with
	 * @param sLength: side length of the grid
	 */
	public Grid(Board b, int sLength){
		this.board = b;
		this.sLength = sLength; // grid square side length
		this.gridLength = b.getSize()*sLength;
		double xPos = 0;
		double yPos = 0;
		// creates grid
		int i = 0;
		for (i=0; i < b.getSize()*b.getSize(); i++){
			if (i % b.getSize() == 0 && i != 0){
				xPos = 0;
				yPos += sLength;
			}
			Rectangle r = new Rectangle(xPos, yPos, this.sLength, this.sLength);
			r.setFill(Color.web("rgba(254, 247, 205, 0.2)"));
			r.setStroke(Color.BLACK);
			this.gridSquares.add(r);
			xPos += sLength;
		}
		// creates sprites equivalent to their vehicle counterpart
		for (i=0; i < b.getVehiclesList().size(); i++){
			Vehicle curr = b.getVehiclesList().get(i);
			Group dragNode = new Group(); // place sprite in a Group object so it can be added to root
			Sprite newSprite;
			if (curr.getOrient() == 1){ // if vehicle's orientation is horizontal
				newSprite = this.createSprite(curr.getPosition()[0]*sLength+1, curr.getPath()*sLength+1, curr.getLength()*sLength-2, sLength-2);
				if (curr.getId() == 1){ // first vehicle - always the red car
					newSprite.setFill(Color.RED);
				}
			}
			else { // if vehicle's orientation is vertical
				newSprite = this.createSprite(curr.getPath()*sLength+1, curr.getPosition()[0]*sLength+1, sLength-2, curr.getLength()*sLength-2);
			}
			dragNode.getChildren().add(newSprite); // add sprite to a Group object to add to this.g
			dragNode.setOnMousePressed(OnMousePressedEventHandler);
			dragNode.setOnMouseDragged(OnMouseDraggedEventHandler);
			dragNode.setOnMouseReleased(OnMouseReleasedEventHandler);
			this.g.add(dragNode);
		}
	}

	/**
	 * This function creates a block (Sprite object) with given dimensions and position on grid
	 * @param xPos: the x-value of the sprite's top left corner on grid
	 * @param yPos: the y-value of the sprite's top left corner on grid
	 * @param w: width of the sprite
	 * @param h: height of the sprite
	 */
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

	/**
	 * This EventHandler keeps track of the cursor's details (position in scene etc..) and deals with when the player clicks on a block
	 */
	EventHandler<MouseEvent> OnMousePressedEventHandler =
		new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			// get x,y coords of the mouse in scene (just when the player clicks)
			orgSceneX = t.getSceneX();
			orgSceneY = t.getSceneY();
			// get the original translates of the selected block (before they drag)
			orgTranslateX = ((Group)t.getSource()).getChildren().get(0).getTranslateX();
			orgTranslateY = ((Group)t.getSource()).getChildren().get(0).getTranslateY();
		}
	};

	/**
	 * This EventHandler keeps track of the cursor's details and deals with the event where the player is dragging a block
	 * @pre: a block has been selected and dragged
	 * @post: the block is dragged as far as possible towards the cursor
	 */
	EventHandler<MouseEvent> OnMouseDraggedEventHandler =
		new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			Sprite block = ((Sprite)((Group)t.getSource()).getChildren().get(0)); // current block being dragged
			Vehicle v = board.getVehiclesList().get(g.indexOf(((Group)t.getSource()))); // corresponding vehicle that this block represents visually

			if (v.getOrient() == 1){ // if horizontal vehicle
				double offsetX = t.getSceneX() - orgSceneX;
				double newTranslateX = orgTranslateX + offsetX;

				if (offsetX > 0){ // if block is being dragged to the right
					int maxMove = board.canMoveForward(v);
					if (maxMove > 0) {
						if (maxMove * sLength < offsetX) { // if player drags block further than max number of moves
							newTranslateX = orgTranslateX + maxMove * sLength; // change new translate to just be max moves since we cannot move any further
							dragTranslate = maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateX; // move as far as the player has dragged the block
						}
						dragOffset = offsetX;
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					}
				}
				else if (offsetX < 0){ // if block is being dragged to the left
					int maxMove = board.canMoveBackward(v);
					if (maxMove > 0) {
						if (maxMove * sLength < -offsetX) { // if player drags block further than max number of moves
							newTranslateX = orgTranslateX - maxMove * sLength; // change new translate to just be max moves since we cannot move any further
							dragTranslate = -maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateX; // move as far as the player has dragged the block
						}
						dragOffset = offsetX;
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					}
				}
			}
			else { // if vertical vehicle
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				if (offsetY > 0){ // if dragging upwards
					int maxMove = board.canMoveForward(v);
					if (maxMove > 0) {
						if (maxMove * sLength < offsetY) {
							newTranslateY = orgTranslateY + maxMove * sLength;
							dragTranslate = maxMove * sLength;
						}
						else {
							dragTranslate = newTranslateY;
						}
						dragOffset = offsetY;
						((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
					}
				}
				else if (offsetY < 0){ // if dragging downwards
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

	/**
	 * This EventHandler updates the backend matrix of the selected vehicle and corrects its position within grid cells in the event where the player releases a block
	 * @pre: a block has been clicked/dragged and just released
	 * @post: the block corrects its position to always have its body lie within a block of grid cells rather than overlapping a cell midway and backend matrix is updated
	 */
	EventHandler<MouseEvent> OnMouseReleasedEventHandler =
		new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			Sprite block = ((Sprite)((Group)t.getSource()).getChildren().get(0));
			Vehicle v = board.getVehiclesList().get(g.indexOf(((Group)t.getSource())));
			int moves = Math.abs((int)(dragTranslate/sLength));

			// updates the backend matrix on the block's current position when block is dragged across more than one grid cell
			if (dragOffset > 0){ // if dragging block forward
				board.moveNSpaces(v, moves);
			} else if (dragOffset < 0) { // if dragging block backward
				board.moveNSpaces(v, -1*moves);
			}

			if (Math.abs(dragOffset) > Math.abs(dragTranslate) && Math.abs(dragOffset % sLength) != 0 && Math.abs(dragTranslate % sLength) != 0){
				moves = Math.abs((int)((Math.abs(dragOffset)-Math.abs(dragTranslate))/sLength));
				if (dragOffset > 0){
					board.moveNSpaces(v, moves);
				}
				else if (dragOffset < 0){
					board.moveNSpaces(v, -1*moves);
				}
			}

			if (v.getOrient() == 1){ // if vehicle is horizontal
				double offsetX = t.getSceneX() - orgSceneX;
				double newTranslateX = orgTranslateX + offsetX;

				if (dragTranslate % sLength != 0.0){
					if (offsetX >= 0){
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateX += sLength - Math.abs(dragOffset % sLength);
							board.moveNSpaces(v, 1);
						}
						else {
								newTranslateX -= Math.abs(dragOffset % sLength);
						}
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					} else if (dragOffset < 0) {
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateX -= sLength - Math.abs(dragOffset % sLength);
							board.moveNSpaces(v, -1);
						}
						else {
								newTranslateX += Math.abs(dragOffset % sLength);
						}
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
					}
				}
			}
			else { // if vehicle is vertical
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				if (dragTranslate % sLength != 0.0){
					if (dragOffset >= 0){
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateY += sLength - Math.abs(dragOffset % sLength);
							board.moveNSpaces(v, 1);
						}
						else {
								newTranslateY -= Math.abs(dragOffset % sLength);
						}
					} else if (dragOffset < 0) {
						if (Math.abs(dragOffset % sLength) >= sLength / 2) {
							newTranslateY -= sLength - Math.abs(dragOffset % sLength);
							board.moveNSpaces(v, -1);
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
		}
	};

	/**
	 * This function creates a block with given dimensions and position on grid
	 * @return: the number of moves performed on this Grid
	 */
	public int getMoves() {
		return board.getnMoves();
	}

	/**
	 * This function gets size of the board
	 * @return: size of backend's board
	 */
	public int getBoardSize() {
		return board.getSize();
	}

	/**
	 * This function returns the side length of a grid square
	 * @return: side length of a grid square
	 */
	public int getsLength() {
		return sLength;
	}

	/**
	 * This function returns the backend board
	 * @return: Board object which this Grid object corresponds with
	 */
	public Board getBoard() {
		return board;
	}
}

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
			System.out.println("newSprite: " + curr.getId());
			dragNode.setOnMouseDragged(OnMouseDraggedEventHandler);
			dragNode.setOnMouseReleased(OnMouseReleasedEventHandler);
			this.g.add(dragNode);
		}
		System.out.println(ANSI_BLUE + "\t DONE" + ANSI_RESET);
	}

	public Sprite createSprite(int xPos, int yPos, int w, int h){
		Sprite s = new Sprite(xPos, yPos, w, h);
		this.blocks.add(s);
		return s;
	}

	private Boolean checkShapeIntersection(Shape block) {
	  boolean collisionDetected = false;
	  for (Shape static_bloc : this.blocks) {
		if (static_bloc != block) {
		  Shape intersect = Shape.intersect(block, static_bloc);
		  if (intersect.getBoundsInLocal().getWidth() != -1) {
			collisionDetected = true;
		  }
		}
	  }
	  if (collisionDetected) {
		System.out.println(ANSI_RED + "[Collision detected]" + ANSI_RESET);
	  } else {
		System.out.println(ANSI_BLUE + "[Empty]" + ANSI_RESET);
	  }
	  return collisionDetected;
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

	// If we don't drag block further (when we can see it is blocked by another block, then it looks like it's working :D else the block will go through)
	/* Problem: - OnMouseReleased probably needs to be redone/modified...or have an if statement that stops it from moving if it is blocked by another block
				- OnMouseDragged needs to have checks for whether it can drag this many blocks forward/backward. Wrote code but dont think it worked. (Line 151-175)
	*/
	EventHandler<MouseEvent> OnMouseDraggedEventHandler =
		new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {

			Sprite block = ((Sprite)((Group)t.getSource()).getChildren().get(0));
			Vehicle v = board.getVehiclesList().get(g.indexOf(((Group)t.getSource())));

			if (v.getOrient() == 1){ // horizontal vehicle
				double offsetX = t.getSceneX() - orgSceneX;
				double newTranslateX = orgTranslateX + offsetX;

				if (block.getX() + newTranslateX < 0){ // if going out of left end of grid
					System.out.println(ANSI_RED + " GONNA EXIT LEFT !!" + ANSI_RESET);
					if (block.getX() == 1){
						newTranslateX = 0;
					}
					else {
						newTranslateX += Math.abs(newTranslateX + block.getX() - 1);
					}
				}
				else if ((block.getX() + newTranslateX + block.getWidth()) > gridLength - 1){ // if going out of right end of grid
					System.out.println(ANSI_BLUE + " GONNA EXIT RIGHT !!" + ANSI_RESET);
					newTranslateX -= (block.getX() + newTranslateX + block.getWidth()) - gridLength + 1;
				}
				else { // is within bounds - need to check if it intersects with other blocks
				}

				if (checkShapeIntersection(block) == false){ // seems to work bit better than my above broken code using backend functions
					((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
				}
				// else {
				// 	((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX - (newTranslateX % sLength));
				// }
			}
			else { // vertical vehicle
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				if ((block.getY() + newTranslateY) < 0){ // if going out of left end of grid
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
				if (checkShapeIntersection(block) == false){
					((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY);
				}
				// else {
				// 	((Group)t.getSource()).getChildren().get(0).setTranslateY(newTranslateY - (newTranslateY % sLength));
				// }
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
				//Boolean canMove = false;
				/* // UPDATING BACKEND'S MATRIX HERE
				if (offsetX > 0){
					if (Math.abs(offsetX/sLength) <= board.canMoveForward(v) && Math.abs(offsetX/sLength) >= 0.5) {
						//canMove = true;
						for (int i=0; i < ((int)Math.abs(offsetX/sLength));i++){
							System.out.println(ANSI_RED + "MF : " + ((int)Math.abs(offsetX/sLength)));
							board.moveForward(v);
							board.printBoard();
						}
						if (((offsetX % sLength) >= (sLength/2)) || ((offsetX/sLength) >= 0.5)){
							System.out.println(ANSI_RED + "1MF : " + ((int)Math.abs(offsetX/sLength)));
							board.moveForward(v);
							board.printBoard();
						}
					}
				}
				else if (offsetX < 0){
					if (Math.abs(offsetX/sLength) <= board.canMoveBackward(v) && Math.abs(offsetX/sLength) >= 0.5) {
						//canMove = true;
						for (int i=0; i < ((int)Math.abs(offsetX/sLength));i++){
							System.out.println(ANSI_BLUE + "MB : " + ((int)Math.abs(offsetX/sLength)));
							board.moveBackward(v);
							board.printBoard();
						}
						if ((Math.abs(offsetX % sLength) >= (sLength/2)) || (Math.abs(offsetX/sLength) >= 0.5)){
							System.out.println(ANSI_BLUE + "1MB : " + ((int)Math.abs(offsetX/sLength)));
							board.moveBackward(v);
							board.printBoard();
						}
					}
				} */
				//if (canMove){
					if ((block.getX() + newTranslateX) >= 0 && (block.getX() + newTranslateX + block.getWidth()) <= gridLength){ // if going out of left end of grid
						double slideCorrection;
						slideCorrection = newTranslateX - (newTranslateX % sLength);
						if (newTranslateX % sLength >= (sLength/2)){
							newTranslateX = slideCorrection + sLength;
						}
						else if (-(newTranslateX % sLength) >= (sLength/2)){
							newTranslateX = slideCorrection - sLength;
						}
						else {
							newTranslateX = slideCorrection;
						}
					}
						((Group)t.getSource()).getChildren().get(0).setTranslateX(newTranslateX);
				//}
			}
			else { // vertical vehicles
				double offsetY = t.getSceneY() - orgSceneY;
				double newTranslateY = orgTranslateY + offsetY;

				/* if (offsetY > 0){
					if (Math.abs(offsetY/sLength) <= board.canMoveForward(v) && Math.abs(offsetY/sLength) >= 0.5) {
						//canMove = true;
						for (int i=0; i < ((int)Math.abs(offsetY/sLength));i++){
							System.out.println(ANSI_RED + "MF : " + ((int)Math.abs(offsetY/sLength)));
							board.moveForward(v);
							board.printBoard();
						}
						if (((offsetY % sLength) >= (sLength/2)) || ((offsetY/sLength) >= 0.5)){
							System.out.println(ANSI_RED + "1MF : " + ((int)Math.abs(offsetY/sLength)));
							board.moveForward(v);
							board.printBoard();
						}
					}
				}
				else if (offsetY < 0){
					if (Math.abs(offsetY/sLength) <= board.canMoveBackward(v) && Math.abs(offsetY/sLength) >= 0.5) {
						//canMove = true;
						for (int i=0; i < ((int)Math.abs(offsetY/sLength));i++){
							System.out.println(ANSI_BLUE + "MB : " + ((int)Math.abs(offsetY/sLength)));
							board.moveBackward(v);
							board.printBoard();
						}
						if ((Math.abs(offsetY % sLength) >= (sLength/2)) || (Math.abs(offsetY/sLength) >= 0.5)){
							System.out.println(ANSI_BLUE + "1MB : " + ((int)Math.abs(offsetY/sLength)));
							board.moveBackward(v);
							board.printBoard();
						}
					}
				}*/

				if ((block.getY() + newTranslateY) >= 0 && (block.getY() + newTranslateY + block.getHeight()) <= gridLength){
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

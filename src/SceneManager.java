import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.paint.Color;


/**
 * This class acts as the controller between the model and the view
 * @author lean lynn oh
 *
 */
public class SceneManager {
	private double sceneWidth;
	private double sceneHeight;
	private Stage stage;
	private HashMap<String, Scene> scenes;
	private SceneView sceneView;
	private AudioClip backgroundClip;
	private ToggleButton menuMuteButton;
	private final int BOARDSIZE = 6;
	private Generator g;

	/**
	 * class constructor
	 * @param width: sceneWidth
	 * @param height: sceneHeight
	 * @param stage: the stage to show the scene
	 */
	public SceneManager(double width, double height, Stage stage) {
		this.sceneWidth = width;
		this.sceneHeight = height;
		this.stage = stage;
		this.scenes = new HashMap<String, Scene>();
		this.sceneView = new SceneView(sceneWidth, sceneHeight);
		this.g = new Generator(BOARDSIZE);
	}

	/**
	 * This function creates a game menu scene
	 * @return menuScene: the scene to display on screen
	 */
	public Scene createMenuScene() {
		//create the menu layout
		AnchorPane menuLayout = sceneView.createMenu();

		//get the mute button
		menuMuteButton = (ToggleButton) menuLayout.getChildren().get(3);

		//defines the functionality of the mute button
		menuMuteButton.setOnAction(e->{
			if(menuMuteButton.isSelected()) {
				menuMuteButton.setText("UNMUTE");
			}else {
				menuMuteButton.setText("MUTE");
			}
			playOrStopMusic();
			muteScene("MENU");

		});

		//gets all the buttons in the menu scene except the mute button
		VBox buttons = (VBox) menuLayout.getChildren().get(2);

		//add functionality to each button
		for (int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			String name = b.getText();
			switch(name) {
				case("EASY"):
					b.setOnAction(e->changeScene(name, b));
					break;
				case("MEDIUM"):
					b.setOnAction(e->changeScene(name, b));
					break;
				case("HARD"):
					b.setOnAction(e->changeScene(name, b));
					break;
				case("EXIT"):
					b.setOnAction(e->closeProgram(stage));
					break;
            }

		}

		//add background music for the game
		String soundName = "file:resource/backgroundMusic.mp3";
		backgroundClip = new AudioClip(soundName);
		backgroundClip.play();

		//create the scene object for menu page
		Scene menuScene = new Scene(menuLayout, sceneWidth, sceneHeight);

		//add the scene to a hash table
		scenes.put("MENU", menuScene);

		//add listener to the scene
		sceneListener(menuScene);

		//display the menu with small menu layout
		sceneView.smallMenuLayout();

		//create animation
		sceneView.animation();
		return menuScene;
	}

	/**
	 * This function creates a game scene
	 * @param hardness: hardness of a level
	 * @return gameScene: the game scene to display on screen
	 */
	public Scene createGameScene(String hardness) {
		//create game layout
		AnchorPane gameLayout = sceneView.createGameLayout(hardness);

		//get the mute button
		ToggleButton mute = (ToggleButton) gameLayout.getChildren().get(4);

		//add functionality to the mute button
		mute.setOnAction(e->{
			if(mute.isSelected()) {
				mute.setText("UNMUTE");
			}else {
				mute.setText("MUTE");
			}
			muteScene(hardness);
		});

		//get all the buttons in a game scene except the mute button
		VBox buttons = (VBox) gameLayout.getChildren().get(3);

		//add functionality to each of the buttons
		for(int i = 0; i < buttons.getChildren().size(); i++) {
			HBox gameButtons = (HBox) buttons.getChildren().get(i);
			for (int j = 0; j < gameButtons.getChildren().size(); j++) {
				Button b = (Button) gameButtons.getChildren().get(j);
				String name = b.getText();
				switch(name) {
					case("UNDO"):
						b.setOnAction(e->undo());
						break;
					case("RESET"):
						b.setOnAction(e->resetBoard(hardness));
						break;
					case("HOME"):
						b.setOnAction(e->changeScene("MENU", b));
						break;
					case("HINTS"):
						b.setOnAction(e->getHint());
						break;
					case("NEXT"):
						b.setOnAction(e->changeScene(hardness, b));
						break;
					case("PREVIOUS"):
						b.setOnAction(e->changeScene(hardness, b));
						break;

				}
			}
		}

		//create the scene object for the game scene
		Scene gameScene = new Scene(gameLayout, sceneWidth, sceneHeight);

		//add the scene to the hash map
		scenes.put(hardness, gameScene);

		//add listener to the scene
		sceneListener(gameScene);

		//choose which size to display depends on the scene width and scene height
		if(sceneWidth >= 900  && sceneHeight  >= 700) {
			sceneView.bigGameLayout(hardness);
		}else {
			sceneView.smallGameLayout(hardness);
		}
		return gameScene;
	}

	/**
	 * This function change the scene according to user input and display it on screen
	 * @precondition user pressed a button and the name of the scene is valid
	 * @postcondition the program changes the scene accordingly
	 * @param name: name of the scene
	 * @param button: button that a user pressed
	 */
	private void changeScene(String name, Button button) {
		//find the scene in the hash map
		Scene scene = scenes.get(name);

		//the scene has not been created
		if(scene == null) {
			//create the scene
			scene = createGameScene(name);
		}

		//if the scene is a game scene
		if(name.equals("EASY") || name.equals("MEDIUM") || name.equals("HARD")) {
			String buttonText = button.getText();
			//create the puzzle
			AnchorPane currentGameLayout = sceneView.renderPuzzle(name, g, buttonText);
			Group root = (Group) currentGameLayout.getChildren().get(5);
			root.setOnMouseReleased(OnMouseReleasedEventHandler);
			if(sceneWidth >= 900  && sceneHeight  >= 700) {
				sceneView.bigGrid();
			}else {
				sceneView.smallGrid();
			}
		}

		//if the scene is a menu scene
		if(name.equals("MENU")) {

			//play or stop the background music in the menu scene
			playOrStopMusic();
		}else {

			//stop the background music in menu scene
			backgroundClip.stop();
		}

		//display the scene
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This function creates a alert box to prompt the user if they really want to exit the game
	 * @precondition user clicks on the exit button or the cross button on the left hand corner of the window
	 * @postcondition a alert box will pop up and ask the user for response
	 * @param stage: game window
	 */
	public void closeProgram(Stage stage) {
		Alert alert = new Alert(AlertType.NONE, "Are you sure to exit " + " ?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			stage.close();
		}
	}

	/**
	 * This function change the view of the scene (ie the size of items in the scene) based on current scene width and scene height
	 * @precondition scene size changes
	 * @postcondition items in the scene change their size and position
	 * @param scene: current scene
	 */
	public void resizeScene(Scene scene) {
		String key = null;
		for(Entry<String, Scene> entry: scenes.entrySet()){
			if(scene.equals(entry.getValue())) {
				key = (String) entry.getKey();
				break;
			}
		}

		if(sceneWidth >= 900  && sceneHeight  >= 700) {
			switch(key) {
				case("EASY"):
					sceneView.bigGameLayout(key);
					sceneView.bigGrid();
					break;
				case("MEDIUM"):
					sceneView.bigGameLayout(key);
					sceneView.bigGrid();
					break;
				case("HARD"):
					sceneView.bigGameLayout(key);
					sceneView.bigGrid();
					break;
				case("MENU"):
					sceneView.bigMenuLayout();
					break;
				case("WIN"):
					AnchorPane winLayout = (AnchorPane) scene.getRoot();
					sceneView.bigWinScene(winLayout);
					break;
			}

		}else {
			switch(key) {
				case("EASY"):
					sceneView.smallGameLayout(key);
					sceneView.smallGrid();
					break;
				case("MEDIUM"):
					sceneView.smallGameLayout(key);
					sceneView.smallGrid();
					break;
				case("HARD"):
					sceneView.smallGameLayout(key);
					sceneView.smallGrid();
					break;
				case("MENU"):
					sceneView.smallMenuLayout();
					break;
				case("WIN"):
					AnchorPane winLayout = (AnchorPane) scene.getRoot();
					sceneView.smallWinScene(winLayout);
					break;
			}

		}

	}

	/**
	 * This function plays or stops the background music in the main menu
	 * @precondition mute button in the menu page is pressed
	 * @postcondition If the mute button is selected, the background music is stopped.
	 * 					If unmute button is unselected, the background music is played.
	 */
	public void playOrStopMusic() {
		if(menuMuteButton.isSelected()) {
			backgroundClip.stop();
		}else {
			backgroundClip.play();
		}
	}


	/**
	 * This function changes the state of all toggle buttons in the game
	 * @precondition a mute button is pressed
	 * @postcondition all mute buttons in the game has changed its state
	 * @param sceneName: current scene
	 */
	public void muteScene(String sceneName) {
		ToggleButton mute = null;
		for(Entry<String, Scene> entry: scenes.entrySet()) {

			//ignore mute button in the current scene and in the win scene
			if(entry.getKey().equals(sceneName) || entry.getKey().equals("WIN")) {
				continue;
			}

			//get the scene
			Scene scene = entry.getValue();

			//get the layout
			AnchorPane layout = (AnchorPane) scene.getRoot();

			//get the mute button in the layout
			if(entry.getKey().equals("MENU")) {
				mute = (ToggleButton) layout.getChildren().get(3);

			}else {
				mute = (ToggleButton) layout.getChildren().get(4);
			}

			//change the state of the mute button
			if(mute.isSelected()) {
				mute.setSelected(false);
				mute.setText("MUTE");
			}else {
				mute.setSelected(true);
				mute.setText("UNMUTE");
			}
		}
	}

	/**
	 * This function undo a move made by the player in the back end and display the changed in the front end
	 * @precondition the player has pressed on the undo button
	 * @postcondition if the player has made at least one move before pressing the undo, the game will undo the move in the back
	 *				end and front end. Otherwise, no changes is made.
	 *
	 */
	public void undo() {
		//get the front end view of the board
		Grid grid = sceneView.getGrid();

		//get the board from back end
		Board board = grid.getBoard();

		//undo the move in back end
		Move m = board.undo();

		//if there is no move made previous
		if(m == null) {
			return;
		}

		//get the block that is previously move
		Vehicle v = m.getVehicle();

		//get the block's id
		int id = v.getId();

		//get the direction of move
		int direction = m.getDirection();

		//get the group of blocks from front end
		ArrayList<Group> vehicles = grid.getBlockGroups();
		Group vehicle = (Group) vehicles.get(id-1);
		Sprite s = (Sprite) vehicle.getChildren().get(0);
		//the length of each block in the grid
		int blockLength = sceneView.getBlockLength();

		//horizontal blocks
		if(v.getOrient() == 1) {
			double value = s.getTranslateX() - direction*blockLength;
			//set its new position
			s.setTranslateX(value);

		} else {
			double value =  s.getTranslateY() - direction*blockLength;
			//set its new position
			s.setTranslateY(value);

		}

		//update the number of moves
		sceneView.updateMove(board);
//		board.printBoard();

	}

	/**
	 * This function reset the board in the front end and back end
	 * @precondition the player clicks on the reset button
	 * @postcondition the front end view of the board restore its original state and the back end board return its original state
	 * @param sceneName: the name of the game scene (ie. the hardness of a level)
	 */
	public void resetBoard(String sceneName) {
		//get the front end view of the puzzle
		Grid grid = sceneView.getGrid();

		//get the back end view of the puzzle
		Board board = grid.getBoard();

		//reset the board in the back end
		int[][] b = board.resetBoard();
		b = board.copyMatrix(b);
		board.setMatrix(b);
//		board.printBoard();

		//reset the display for moves
		sceneView.updateMove(board);

		//restore the front end puzzle to its original state
		Group root = sceneView.createPuzzle(sceneName, board);
		root.setOnMouseReleased(OnMouseReleasedEventHandler);

		//determine the position of the puzzle in a scene based on the scene size
		if(sceneWidth >= 900  && sceneHeight  >= 700) {
			sceneView.bigGrid();
		}else {
			sceneView.smallGrid();
		}
	}

	public void getHint(){
		Grid grid = sceneView.getGrid();
		Board puzzle = grid.getBoard();
		Move m = puzzle.getHint();
		if(m == null) {
			return;
		}
		Vehicle v = m.getVehicle();
		Sprite s;
		if (v.getOrient() == 1){
			s = new Sprite((v.getPosition()[0] + m.getDirection())*grid.getsLength()+1, v.getPath()*grid.getsLength()+1, v.getLength()*grid.getsLength()-2, grid.getsLength()-2);
		}
		else {
			s = new Sprite(v.getPath()*grid.getsLength()+1, (v.getPosition()[0] + m.getDirection())*grid.getsLength()+1, grid.getsLength()-2, v.getLength()*grid.getsLength()-2);
		}
		s.setFill(Color.GREEN);
		s.setOpacity(0.2);
		Group g = new Group();
		g.getChildren().add(s);
		System.out.println(m.toString());
		System.out.println("Orient: " + v.getOrient() + " | path: " + v.getPath() + " | fPos: " + v.getPosition()[0] + " | length: " + v.getLength());
		AnchorPane gameLayout = (AnchorPane) sceneView.getCurrentGameLayout().getValue();
		Group root = (Group) gameLayout.getChildren().get(5);
		root.getChildren().add(g);
		System.out.println("=== GET HINTS ===");
	}

	/**
	 * This event handler keeps track of the mouse clicks and update the moves in the game scene and plays the sound effect
	 */
	EventHandler<MouseEvent> OnMouseReleasedEventHandler =
			new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				//get the front end board
				Grid grid = sceneView.getGrid();
				Pair<String, AnchorPane> currentGameLayout = sceneView.getCurrentGameLayout();
				AnchorPane gameLayout = currentGameLayout.getValue();
				String hardness = currentGameLayout.getKey();

				//get the back end board
				Board board = grid.getBoard();
				Vehicle v = board.getVehiclesList().get(0);

				//add sound effect for a button click
				String musicFile = "file:resource/click.mp3";
				AudioClip gameClip = new AudioClip(musicFile);


				//get the mute button in a scene
				ToggleButton mute = (ToggleButton) gameLayout.getChildren().get(4);

				//play or stop the sound effect
				if(mute.isSelected()) {
					gameClip.stop();
				}else {
					gameClip.play();
				}

				//update the display of the move
				sceneView.updateMove(board);

				//check if the red car is at the goal
				if(board.fin(v)) {
					win(board, hardness);
				}
			}
		};

	/**
	 * This function creates the win scene for the game
	 * @precondition the red car has reached the goal
	 * @postcondition the win scene is displayed
	 * @param board: the puzzle for a level
	 * @param hardness: the hardness of a level
	 */
	public void win(Board board, String hardness) {
		//get the minimum number of moves to complete the puzzle
		int perfectMoves = board.getSolution().size();

		//get the layout of the win scene
		AnchorPane winLayout = sceneView.winScene(board);

		//get the text in the layout
		Text perfect = (Text) winLayout.getChildren().get(4);

		//get all the buttons in the win scene
		HBox buttons = (HBox) winLayout.getChildren().get(3);

		if(board.getnMoves() == perfectMoves) {
			perfect.setText("PERFECT !");
		}

		//add functionality to each button
		for (int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			String btnText = b.getText();
			switch(btnText) {
				case("RESET"):
					b.setOnAction(e->{
						resetBoard(hardness);
						Scene scene = scenes.get(hardness);
						stage.setScene(scene);
					});
					break;
				case("HOME"):
					b.setOnAction(e->changeScene("MENU", b));
					break;
				case("NEXT"):
					b.setOnAction(e->changeScene(hardness, b));
					break;
	        }
		}

		//create the scene
		Scene scene = new Scene(winLayout, sceneWidth, sceneHeight);

		//choose the layout of the scene based on the scene size
		if(sceneWidth >= 900 && sceneHeight >= 700) {
			sceneView.bigWinScene(winLayout);
		}else {
			sceneView.smallWinScene(winLayout);
		}

		//add sound effect for the win scene
		String musicFile = "file:resource/win.mp3";
		AudioClip effectClip = new AudioClip(musicFile);

		//play the sound effect
		if(menuMuteButton.isSelected()) {
			effectClip.stop();
		}else {
			effectClip.play();
		}

		//add the scene to the hash map
		scenes.put("WIN", scene);

		//add listener to the scene
		sceneListener(scene);

		//display the scene
		stage.setScene(scene);
	}

	/**
	 * This function acts as the listener for the scene. It listens to changes in the scene's width and scene's height
	 * @precondition a scene is created
	 * @postcondition any changes in the scene size is detected and changes the layout
	 * @param scene: current scene
	 */
	public void sceneListener(Scene scene) {
		scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
		    	//change the scene width in this class
		    	sceneWidth = (double) newWidth;
		    	//change the scene width in the scene view class
		    	sceneView.setSceneWidth((double)newWidth);
		    	//resize current scene
		        resizeScene(scene);
		    }
		});

		scene.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
		    	//change the scene height in this class
		    	sceneHeight = (double) newHeight;
		        //changes the scene height in the scene view class
		    	sceneView.setSceneHeight((double) newHeight);
		        //resize current scene
		        resizeScene(scene);
		    }
		});
	}
}

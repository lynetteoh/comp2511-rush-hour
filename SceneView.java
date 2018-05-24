import java.util.HashMap;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * This class acts as the view for the program.
 * Every scene is initialized in this class. 
 * @author lean lynn oh 
 *
 */
public class SceneView {
	private AnchorPane menuLayout;			
	private double sceneWidth;
	private double sceneHeight;
	private int blockLength;
	private HashMap<String, AnchorPane> gameLayout;
	private Pair<String, Group> gameBoard;
	private Grid grid;	
	private int easyLevel;
	private int mediumLevel;
	private int hardLevel;
	private ToggleButton menuMuteButton;
	private Pair currentGameLayout;
	
	/**
	 * Class constructor for SceneView
	 * @param width: the width of the scene 
	 * @param height: the height of the scene
	 */
	public SceneView (double width, double height) {
		this.menuLayout = new AnchorPane();
		this.sceneWidth = width;
		this.sceneHeight = height;
		this.blockLength = 65;
		this.easyLevel = 0; 
		this.mediumLevel = 0; 
		this.hardLevel = 0;
		this.gameLayout = new HashMap<String, AnchorPane>();
	}
	
	/**
	 * Setter method for sceneWidth
	 * @param sceneWidth: the width of the scene
	 */
	public void setSceneWidth(double sceneWidth) {
		this.sceneWidth = sceneWidth;
	}
	
	/**
	 * Setter method for sceneHeight
	 * @param sceneHeight: the height of the scene
	 */
	public void setSceneHeight(double sceneHeight) {
		this.sceneHeight = sceneHeight;
	}
	
	/**
	 * Getter method to get the grid
	 * @return grid: the front end board
	 */
	public Grid getGrid() {
		return grid;
	}
	
	/**
	 * Getter method to the length of each block in the grid
	 * @return
	 */
	public int getBlockLength() {
		return blockLength;
	}
	
	/**
	 * Getter method to  get the current game layout pane 
	 * @return  currentGameLayout: the current game layout pane
	 */
	public Pair getCurrentGameLayout() {
		return currentGameLayout;
	}
	
	/**
	 * This function creates the layout of the game menu scene 
	 * @return menuLayout: the layout of the game menu
	 */
	public AnchorPane createMenu() {
		//create vertical box
		VBox menuButtons = new VBox(30);
		
		//defines the shape of the button 
		Polygon polygon = new Polygon(
				0, 0,
				200, 0,
				215, 15,
				200, 30,
				0, 30
		);
		
		//create text for game title
		Text gameTitle = new Text("G R I D L O C K");
		
		//set the font of the game title
		gameTitle.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 48));
		
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		
		//align the text
		gameTitle.setTextAlignment(TextAlignment.CENTER);
		
		//create background for the menu scene
		ImageView background = getBackground("file:resource/background.jpg", 1.0);
		Button easyBtn = createBtn("EASY", polygon);
		Button mediumBtn = createBtn("MEDIUM", polygon);
		Button hardBtn = createBtn("HARD", polygon);
		Button exitBtn = createBtn("EXIT", polygon);
		
		//create toggle button as mute button 
		menuMuteButton = new ToggleButton("MUTE");
		
		//define the style of the toggle button 
		menuMuteButton.setStyle("-fx-background-color: #f9d1ae");
		
		//set the effect of the toggle button 
		Effect shadow = new DropShadow(5, Color.GREY);
		menuMuteButton.setEffect(shadow);
		menuMuteButton.styleProperty().bind( Bindings.when(menuMuteButton.hoverProperty())
                .then("-fx-background-color:#fbdfc6")   
                .otherwise("-fx-background-color: #f9d1ae"));
		
		//add all buttons into the vbox
		menuButtons.getChildren().addAll(easyBtn, mediumBtn, hardBtn,exitBtn);
		
		
		//add all items to menu layout
		menuLayout.getChildren().addAll(background, gameTitle, menuButtons, menuMuteButton);

		return menuLayout;		
	}
	
	/**
	 * This function creates an ImageView object for the background of a scene
	 * @precondition Provided the image exist, the path given is valid and the contrast value > 0
	 * @postcondition ImageView object created
	 * @param path: the location of the image
	 * @param contrast: the contrast value for the image
	 * @return background: the ImageView object 
	 */
	private ImageView getBackground(String path, double contrast) {
		//create image object 
		Image image = new Image(path);
		
		//create ImageView object
		ImageView background = new ImageView();
		
		//set background image 
		background.setImage(image);
		
		//set the prefer size for background
		background.prefHeight(sceneHeight);
		background.prefWidth(sceneWidth);
		
		//adjust the contrast 
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setContrast(contrast);
		background.setEffect(colorAdjust);
		return background;		
	}
	
	/**
	 * This function create a button for a scene 
	 * @precondition Given the name of a button / text to display on a button and the shape of a button
	 * @postcondition a button object is created
	 * @param name: name of the button and the text displayed on a button
	 * @param polygon: shape of the button
	 * @return button: the button object 
	 */
	private Button createBtn(String name, Polygon polygon) {
		//create button object 
		Button button = new Button(name);
		
		//set the shape of the button 
		button.setShape(polygon);
		
		//set the color of the button 
		button.setStyle("-fx-background-color: #f9d1ae");
		button.styleProperty().bind( Bindings.when(button.hoverProperty())
                .then("-fx-background-color:#fbdfc6")   
                .otherwise("-fx-background-color: #f9d1ae"));
		
		//set the font of the button 
		button.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 18));
		
		//set the effect of the button 
		Effect shadow = new DropShadow(5, Color.GREY);
		button.setEffect(shadow);
		
		//set the position of the buttons
		button.setTranslateX(-300);
		return button;
	}
	
	/**
	 * This function creates the animation effect for the buttons on the menu scene
	 * @precondition all buttons in the menu scene has been created
	 * @postcondition buttons will move from the left of the scene to the center when the game is launched 
	 */
	public void animation() {
		//get all buttons in the menu layout
		VBox menuButtons = (VBox) menuLayout.getChildren().get(2);
		for (int i = 0; i < menuButtons.getChildren().size(); i++) {
			Button b = (Button) menuButtons.getChildren().get(i);
			//create transition
			TranslateTransition tt = new TranslateTransition(Duration.seconds(1.15 + i*0.2), b);
			
			//set starting position
			tt.setToX(0);
    		
    		//set ending position
			tt.setOnFinished(e->b.translateXProperty().negate());
			tt.play();
		} 
	}
	
	/**
	 * This function changes the position and size of the items in menu layout when scene width >= 900 and scene height >= 700
	 * @precondition scene width < 900, scene height < 700 and menu layout has been created 
	 * @postcondition items in menu scene looks bigger
	 */
	public void bigMenuLayout() {
		//get all the buttons in the menu layout
		VBox menuButtons = (VBox) menuLayout.getChildren().get(2);
		for (int i = 0; i < menuButtons.getChildren().size(); i++) {
			Button b = (Button) menuButtons.getChildren().get(i);
			b.setFont(Font.font(25));
			b.setPrefSize(300, 40);
		}
		
		//get the game title
		Text gameTitle = (Text) menuLayout.getChildren().get(1);
		
		//change game title font size
		gameTitle.setFont(Font.font(70));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		
		//set game title position
		gameTitle.setTranslateX(sceneWidth/2 - titleWidth/2);
		gameTitle.setTranslateY(sceneHeight/4);
		
		//set buttons position
		menuButtons.setTranslateX(sceneWidth/2 - titleWidth/2 + 100);
		menuButtons.setTranslateY(sceneHeight/3 + 50);
		
		//set mute button position
		menuMuteButton.setTranslateX(sceneWidth - 100);
		menuMuteButton.setTranslateY(20);
	}
	
	/**
	 * This function changes the position and size of the items in menu layout when scene width < 900 and scene height < 700 
	 * @precondition scene width < 900, scene height < 700 and menu layout has been created
	 * @postcondition items in menu scene looks smaller
	 */
	public void smallMenuLayout() {
		//get all the buttons in the menu layout
		VBox menuButtons = (VBox) menuLayout.getChildren().get(2);
		for (int i = 0; i < menuButtons.getChildren().size(); i++) {
			Button b = (Button) menuButtons.getChildren().get(i);
			b.setFont(Font.font(18));
			b.setPrefSize(200, 30);
		}
		
		//get the game title
		Text gameTitle = (Text) menuLayout.getChildren().get(1);
		
		//change game title font size
		gameTitle.setFont(Font.font(48));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		
		//set game title position
		gameTitle.setTranslateX(sceneWidth/2 - titleWidth/2);
		gameTitle.setTranslateY(sceneHeight/4);
		
		//set buttons position
		menuButtons.setTranslateX(sceneWidth/2 - 100);
		menuButtons.setTranslateY(sceneHeight/3 + 50);
		
		//set mute button position
		menuMuteButton.setTranslateX(sceneWidth - 100);
		menuMuteButton.setTranslateY(20);
	}
	
	/**
	 * This function changes the position and size of the items in game layout when scene width >= 900 and scene height >= 700
	 * @precondition scene width >= 900, scene height >= 700 and the game layout has been created
	 * @postcondition the position of the items are changed and the size increases
	 * @param hardness: hardness of a level (easy, medium or hard)
	 */
	public void bigGameLayout(String hardness) {
		AnchorPane layout = gameLayout.get(hardness);	
		Text levelBoard = (Text) layout.getChildren().get(1);
		Text scoreBoard = (Text) layout.getChildren().get(2);
		VBox gameButtons = (VBox) layout.getChildren().get(3);
		ToggleButton mute = (ToggleButton) layout.getChildren().get(4);
		
		//set the size of the buttons
		for(int i = 0; i < gameButtons.getChildren().size(); i++) {
			HBox buttons = (HBox) gameButtons.getChildren().get(i);
			for (int j = 0; j < buttons.getChildren().size(); j++) {
				Button b = (Button) buttons.getChildren().get(j);
				b.setFont(Font.font(18));
				b.setPrefSize(150,30);
			}
			
		}
		
		//set the position of the mute button 
		mute.setTranslateX(sceneWidth - 100);
		mute.setTranslateY(20);
		
		//set position of all the other buttons 
		gameButtons.setTranslateX(sceneWidth/2 - 100);
		gameButtons.setTranslateY(sceneHeight/2 + 100);
		
		//set the font
		levelBoard.setFont(Font.font(36));
		
		//set the position of the display for level
		levelBoard.setTranslateX(sceneWidth/2 - 400);
		levelBoard.setTranslateY(sceneHeight/4);
		
		//set the font for moves
		scoreBoard.setFont(Font.font(36));
		
		//set the position for the score board 
		scoreBoard.setTranslateX(sceneWidth/2 - 400);
		scoreBoard.setTranslateY(sceneHeight/4 + 100);
	}
	
	/**
	 * This function changes position and size of the items in game layout when scene width < 900 and scene height < 700
	 * @precondition scene width < 900, scene height < 700 and the game layout has been created
	 * @postcondition the position of the items are changed and the size decreases
	 * @param hardness: hardness of a game level (easy, medium or hard)
	 */
	public void smallGameLayout(String hardness) {	
		AnchorPane layout = gameLayout.get(hardness);	
		Text levelBoard = (Text) layout.getChildren().get(1);
		Text scoreBoard = (Text) layout.getChildren().get(2);
		VBox gameButtons = (VBox) layout.getChildren().get(3);
		ToggleButton mute = (ToggleButton) layout.getChildren().get(4);
		
		//set the size of the buttons
		for(int i = 0; i < gameButtons.getChildren().size(); i++) {
			HBox buttons = (HBox) gameButtons.getChildren().get(i);
			for (int j = 0; j < buttons.getChildren().size(); j++) {
				Button b = (Button) buttons.getChildren().get(j);
				b.setFont(Font.font(16));
				b.setPrefSize(120, 20);			
			}
		}
		
		//set the position of the mute button 
		mute.setTranslateX(sceneWidth - 100);
		mute.setTranslateY(20);
		
		//set position of all the other buttons
		gameButtons.setTranslateX(sceneWidth/2 - 50);
		gameButtons.setTranslateY(sceneHeight/2 + 50);
		
		//set the font
		levelBoard.setFont(Font.font(24));
		
		//set the position of the display for level
		levelBoard.setTranslateX(sceneWidth/2 - 350);
		levelBoard.setTranslateY(sceneHeight/4 - 20);
		
		//set the font for moves
		scoreBoard.setFont(Font.font(24));
		
		//set the position for the score board 
		scoreBoard.setTranslateX(sceneWidth/2 - 350);
		scoreBoard.setTranslateY(sceneHeight/4 + 50);
	}
	
	/**
	 * This function creates the layout of a game scene
	 * @precondition the hardness of a level is given and it is valid
	 * @postcondition the layout of the scene is defined
	 * @param hardness: the hardness of a level (easy, medium or hard)
	 * @return layout: the game layout 
	 */
	public AnchorPane createGameLayout(String hardness) {
		AnchorPane layout = new AnchorPane();
		VBox gameButtons = new VBox(30);
		HBox gameButtonsHolder = new HBox(20);
		HBox gameButtonsHolder1 = new HBox(20);
		HBox gameButtonsHolder2 = new HBox(20);
		
		String Level = "Level : 0";
		String Moves = "Moves : 0";
		
		//create background for the scene
		ImageView background = getBackground("file:resource/background.jpg", 0.1);
		
		//define the shape of the button
		Polygon polygon = new Polygon(
				0, 0,
				200, 0,
				200, 30,
				0, 30
		);
		
		//create buttons
		Button homeBtn = createBtn("HOME", polygon);
		Button resetBtn = createBtn("RESET", polygon);	
		Button hintsBtn = createBtn("HINTS", polygon);
		Button undoBtn = createBtn("UNDO", polygon);
		Button nextBtn = createBtn("NEXT", polygon);
		Button previousBtn = createBtn("PREVIOUS", polygon);
		gameButtonsHolder.getChildren().addAll(hintsBtn, undoBtn);
		gameButtonsHolder1.getChildren().addAll(resetBtn, homeBtn);
		gameButtonsHolder2.getChildren().addAll(previousBtn, nextBtn);
		
		//add buttons to vbox
		gameButtons.getChildren().addAll(gameButtonsHolder, gameButtonsHolder1, gameButtonsHolder2);
		
		//create mute button
		ToggleButton mute = new ToggleButton("MUTE");
		
		//set style for mute button
		mute.setStyle("-fx-background-color: #f9d1ae");
		Effect shadow = new DropShadow(5, Color.GREY);
		mute.setEffect(shadow);
		mute.styleProperty().bind( Bindings.when(mute.hoverProperty())
                .then("-fx-background-color:#fbdfc6")   
                .otherwise("-fx-background-color: #f9d1ae"));
		if(menuMuteButton.isSelected()) {
			mute.setSelected(true);
			mute.setText("UNMUTE");
		}
		
		//create text to display level
		Text levelBoard = new Text(Level);
		
		//set font style
		levelBoard.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
		
		//create text to display score
		Text scoreBoard = new Text(Moves);	
		
		//set font style
		scoreBoard.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
		
		//add items into pane
		layout.getChildren().addAll(background, levelBoard, scoreBoard, gameButtons, mute);
		
		//add game layout to hash map
		gameLayout.put(hardness, layout);
		return layout;
	}
	
	/**
	 * This function creates the win scene after the red car has reached the goal
	 * @precondition Given the board is valid and the player has solved the puzzle
	 * @postcondition the layout of the scene is defined
	 * @param board: the puzzle of a level
	 * @return winLayout: the layout of the scene
	 */
	public AnchorPane winScene(Board board) {
		AnchorPane winLayout = new AnchorPane();
		String Moves = "Moves made: " + board.getnMoves();
		HBox hbox = new HBox(30);
		
		//create shape for button
		Polygon polygon = new Polygon(
				0, 0,
				200, 0,
				200, 30,
				0, 30
		);
		
		//create buttons
		Button homeBtn = createBtn("HOME", polygon);
		Button resetBtn = createBtn("RESET", polygon);
		Button nextBtn = createBtn("NEXT", polygon);
		
		//add buttons to hbox
		hbox.getChildren().addAll(homeBtn, resetBtn, nextBtn);
		
		//create text to display moves
		Text moves = new Text(Moves);
		
		//display message when user made least number of moves to solve the puzzle
		Text perfect = new Text();
		
		//create text to display message
		Text win = new Text("Congratulations! You Win."); 
		
		//create background
		ImageView background = getBackground("file:resource/background.jpg", 1.0);
		
		//add items to pane
		winLayout.getChildren().addAll(background, win, moves, hbox, perfect);
		return winLayout;
	}
	
	/**
	 * This function changes the position and size of the items in win layout when scene width < 900 and scene height < 700
	 * @precondition scene width < 900, scene height < 700 and the win layout has been created
	 * @postcondition the items in the win layout has changed its position and size
	 * @param winLayout: the layout of the win scene
	 */
	public void smallWinScene(AnchorPane winLayout) {
		Text win = (Text) winLayout.getChildren().get(1);
		Text moves = (Text) winLayout.getChildren().get(2);
		HBox buttons = (HBox) winLayout.getChildren().get(3);
		Text perfect = (Text) winLayout.getChildren().get(4);
		
		//set buttons size
		for(int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			b.setPrefSize(120, 20);
		}
		
		//set font style and size
		win.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 26));
		moves.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 26));
		perfect.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 40));
		
		double winPosition = sceneWidth - win.getLayoutBounds().getWidth();
		double movesPosition = sceneWidth - moves.getLayoutBounds().getWidth();
		double perfectPosition = sceneWidth - perfect.getLayoutBounds().getWidth();
		
		//set position
		perfect.setTranslateX(perfectPosition/2);
		perfect.setTranslateY(sceneHeight/4);
		win.setTranslateX(winPosition/2);
		win.setTranslateY(sceneHeight/3 + 20);
		moves.setTranslateX(movesPosition/2);
		moves.setTranslateY(sceneHeight/3 + 70);
		buttons.setTranslateX(sceneWidth/2 + 100);
		buttons.setTranslateY(sceneHeight/2 + 100);
	}
	
	/**
	 * This function changes the position and size of the items in win layout when scene width >= 900 and scene Height >= 700
	 * @precondition scene width > 900, scene height > 700 and the win layout has been created
	 * @postcondition the items in the win layout has changed its position and size
	 * @param winLayout: layout for the win scene
	 */
	public void bigWinScene(AnchorPane winLayout) {
		Text win = (Text) winLayout.getChildren().get(1);
		Text moves = (Text) winLayout.getChildren().get(2);
		HBox buttons = (HBox) winLayout.getChildren().get(3);
		Text perfect = (Text) winLayout.getChildren().get(4);
		
		//set buttons size
		for(int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			b.setPrefSize(150, 30);
		}
		
		//change the font style and size
		win.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 36));
		moves.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 36));
		perfect.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 50));
		
		double winPosition = sceneWidth - win.getLayoutBounds().getWidth();
		double movesPosition = sceneWidth - moves.getLayoutBounds().getWidth(); 
		double perfectPosition = sceneWidth - perfect.getLayoutBounds().getWidth();
		
		//set position
		perfect.setTranslateX(perfectPosition/2);
		perfect.setTranslateY(sceneHeight/4);
		win.setTranslateX(winPosition/2);
		win.setTranslateY(sceneHeight/3);
		moves.setTranslateX(movesPosition/2);
		moves.setTranslateY(sceneHeight/3 + 50);
		buttons.setTranslateX(sceneWidth/2 + 50);
		buttons.setTranslateY(sceneHeight/2 + 50);
	}
	
	/**
	 * This function 
	 * @param hardness: hardness of a level
	 * @param g: the generator
	 * @param buttonText: name of a button to determine its functionality
	 * @return
	 */
	public AnchorPane renderPuzzle(String hardness, Generator g, String buttonText) {
		Board puzzle = null;
		String level = "Level: ";
		String move = "Moves: 0";
		AnchorPane layout = gameLayout.get(hardness);	
		Text levelBoard = (Text) layout.getChildren().get(1);
		Text score = (Text) layout.getChildren().get(2);
		score.setText(move);
		Group root = new Group();
		
		//if the function is called from pressing previous button 
		if(buttonText.equals("PREVIOUS")) {
			switch(hardness) {
				case("EASY"):
					if(easyLevel > 1) { 
						easyLevel--;
						level = level + easyLevel;
						levelBoard.setText(level);
						puzzle = g.GetPreviousEasyBoard();
						root = createPuzzle(hardness, puzzle);					
					}
					
					break;
				case("MEDIUM"):
					if(mediumLevel > 1) {
						mediumLevel--;
						puzzle = g.GetPreviousMediumBoard();
						root = createPuzzle(hardness, puzzle);
						level = level + mediumLevel;
						levelBoard.setText(level);
					}
					
					break;
				case("HARD"):
					if(hardLevel > 1) {
						hardLevel--;
						puzzle = g.GetPreviousHardBoard();
						root = createPuzzle(hardness, puzzle);
						level = level + hardLevel;
						levelBoard.setText(level);
					}	
					break;
			}
			
		}else {
			switch(hardness) {
				case("EASY"):
					easyLevel++;
					level = level + easyLevel;
					levelBoard.setText(level);
					puzzle = g.GetNextEasyBoard();
					root = createPuzzle(hardness, puzzle);
					break;
				case("MEDIUM"):
					mediumLevel++;
					level = level + mediumLevel;
					levelBoard.setText(level);
					puzzle = g.GetNextMediumBoard();
					root = createPuzzle(hardness, puzzle);
					break;
				case("HARD"):
					hardLevel++;
					level = level + hardLevel;
					levelBoard.setText(level);
					puzzle = g.GetNextHardBoard();
					root = createPuzzle(hardness, puzzle);
					break;			
			}		
		}
		
		//current game layout
		currentGameLayout = new Pair<String, AnchorPane>(hardness, layout);
		return layout;
	}
	
	/**
	 * This function creates the puzzle for front end
	 * @precondition the puzzle provided is valid and the hardness of a level is valid
	 * @postcondition front end view of the puzzle is created
	 * @param hardness: hardness of a level
	 * @param puzzle: back end generated puzzle 
	 * @return root: front end view of the puzzle
	 */
	public Group createPuzzle(String hardness, Board puzzle) {
		if(gameBoard != null) {
			//remove the board in the game layout
			removeBoard();
		}
		
		Group root = new Group();
		
		//get the correct game layout
		AnchorPane layout = gameLayout.get(hardness);	
		
		//create puzzle
		switch(hardness) {
			case("EASY"):
				grid = new Grid(puzzle, blockLength);
				root.getChildren().addAll(grid.getGridSquares());
				root.getChildren().addAll(grid.getBlockGroups());				
				break;
			case("MEDIUM"):
				grid = new Grid(puzzle, blockLength);
				root.getChildren().addAll(grid.getGridSquares());
				root.getChildren().addAll(grid.getBlockGroups());
				break;
			case("HARD"):
				grid = new Grid(puzzle, blockLength);
				root.getChildren().addAll(grid.getGridSquares());
				root.getChildren().addAll(grid.getBlockGroups());
				break;
		}
	
		gameBoard = new Pair<String, Group>(hardness, root);
		
		//add puzzle to game layout
		layout.getChildren().add(root);
		return root;
	}
	
	/**
	 * This function changes the position of the game board when scene width >= 900 and scene height >= 700
	 * @precondition scene width >= 900, scene height >= 700
	 * @postcondition the position of the game board has changed
	 */
	public void bigGrid() {
		Group board = (Group) gameBoard.getValue();	
		
		//set position of board
		board.setTranslateX(sceneWidth/2 + 20);
		board.setTranslateY(sceneHeight/5);
	}	
	
	/**
	 * This function changes the position of the game board when scene width < 900 and scene height < 700
	 * @precondition scene width < 900, scene height < 700
	 * @postcondition the position of the game board has changed
	 */
	public void smallGrid() {
		Group board = (Group) gameBoard.getValue();
		
		//set position of board
		board.setTranslateX(sceneWidth/2);
		board.setTranslateY(sceneHeight/5);		
	}

	/**
	 * This function updates the display of the move in the game scene
	 * @precondition the user made a move 
	 * @postcondition the number of moves is updated and displayed
	 * @param board: current puzzle
	 */
	public void updateMove(Board board) {
		AnchorPane gameLayout = (AnchorPane) currentGameLayout.getValue();
		int moves = board.getnMoves();
		String Moves = "Moves: " + moves;
		Text score = (Text) gameLayout.getChildren().get(2);
		score.setText(Moves);	
	}
	
	
	/**
	 * This function removes old puzzle from a game layout
	 * @precondition gameBoard is valid and not null 
	 * @postcondition gameBoard is removed from the game layout
	 */
	public void removeBoard() {	
		String hardness = (String) gameBoard.getKey();
		AnchorPane layout = gameLayout.get(hardness);	
		Group board = (Group) gameBoard.getValue();
		switch(hardness) {
			case("EASY"):
				layout.getChildren().remove(board);
				break;
			case("MEDIUM"):
				layout.getChildren().remove(board);
				break;
			case("HARD"):
				layout.getChildren().remove(board);
				break;
		}
		
		gameBoard = null;
	}
	

}
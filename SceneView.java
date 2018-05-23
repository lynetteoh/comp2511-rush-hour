import java.io.File;
import java.util.HashMap;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.util.Pair;

public class SceneView extends Pane{
	private VBox menuButtons;
	private AnchorPane menuLayout;
	private double sceneWidth;
	private double sceneHeight;
	private VBox gameButtons;
	private int blockLength;
	private HashMap<String, AnchorPane> gameLayout;
	private Text score;
	private Pair<String, Group> gameBoard;
	private Grid grid;	
	private int easyLevel;
	private int mediumLevel;
	private int hardLevel;
//	private Board puzzle;
	private ToggleButton menuMuteButton;
	private Pair currentGameLayout;
	
	
	public SceneView (double width, double height) {
		this.menuLayout = new AnchorPane();
		this.menuButtons = new VBox(30);
		this.sceneWidth = width;
		this.sceneHeight = height;
		this.blockLength = 65;
		this.easyLevel = 0; 
		this.mediumLevel = 0; 
		this.hardLevel = 0;
		this.gameLayout = new HashMap<String, AnchorPane>();
	}
	
	public VBox getMenuButtons() {
		return menuButtons;
	} 
	
	public VBox getGameButtons() {
		return gameButtons;
	}
	
	
	public void setSceneWidth(double sceneWidth) {
		this.sceneWidth = sceneWidth;
	}

	public void setSceneHeight(double sceneHeight) {
		this.sceneHeight = sceneHeight;
	}
	
	public ToggleButton getMenuMuteButton() {
		return menuMuteButton;
	}

	public HashMap<String, AnchorPane> getGameLayout() {
		return gameLayout;
	}

	public Grid getGrid() {
		return grid;
	}

	public int getBlockLength() {
		return blockLength;
	}

	public Pair getCurrentGameLayout() {
		return currentGameLayout;
	}

	public AnchorPane createMenu() {
		//int btnWidth = 200;
		//int btnHeight = 30;
		Polygon polygon = new Polygon(
				0, 0,
				200, 0,
				215, 15,
				200, 30,
				0, 30
		);
		Text gameTitle = createText("G R I D L O C K" , 48);
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTextAlignment(TextAlignment.CENTER);
		gameTitle.setTranslateX(sceneWidth/2 - titleWidth/2);
		gameTitle.setTranslateY(sceneHeight/4);
		ImageView background = getBackground("file:resource/background.jpg", 1.0);
		menuButtons.setTranslateX(sceneWidth/2 - 100);
		menuButtons.setTranslateY(sceneHeight/3 + 50);
		Button easyBtn = createBtn("EASY", polygon);
		Button mediumBtn = createBtn("MEDIUM", polygon);
		Button hardBtn = createBtn("HARD", polygon);
		Button exitBtn = createBtn("EXIT", polygon);
		menuMuteButton = new ToggleButton("MUTE");
		menuMuteButton.setStyle("-fx-background-color: #f9d1ae");
		Effect shadow = new DropShadow(5, Color.GREY);
		menuMuteButton.setEffect(shadow);
		menuMuteButton.styleProperty().bind( Bindings.when(menuMuteButton.hoverProperty())
                .then("-fx-background-color:#fbdfc6")   
                .otherwise("-fx-background-color: #f9d1ae"));
		menuButtons.getChildren().addAll(easyBtn, mediumBtn, hardBtn,exitBtn);
		menuLayout.getChildren().addAll(background, gameTitle, menuButtons, menuMuteButton);
		smallMenuLayout();
		animation();
		return menuLayout;		
	}
	
	private ImageView getBackground(String path, double contrast) {
		Image image = new Image(path);
		ImageView background = new ImageView();
		background.setImage(image);
		background.prefHeight(sceneHeight);
		background.prefWidth(sceneWidth);
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setContrast(contrast);
		background.setEffect(colorAdjust);
		return background;		
	}
	
	
	private Text createText(String name, int fontSize) { 
		Text title = new Text(name);
		title.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, fontSize));
		return title;
		
	}
	
	private Button createBtn(String name, Polygon polygon) {
		Button button = new Button(name);
		button.setShape(polygon);
		button.setStyle("-fx-background-color: #f9d1ae");
		button.styleProperty().bind( Bindings.when(button.hoverProperty())
                .then("-fx-background-color:#fbdfc6")   
                .otherwise("-fx-background-color: #f9d1ae"));
		//button.setTextFill(Color.WHITE);
		//button.setStyle("-fx-background-color: -fx-body-color;");
		button.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 18));
		//button.setTextFill(Color.WHITE);
		Effect shadow = new DropShadow(5, Color.GREY);
		button.setEffect(shadow);
		button.setTranslateX(-300);
		return button;
	}
	
	private void animation() {
		for (int i = 0; i < menuButtons.getChildren().size(); i++) {
			Button b = (Button) menuButtons.getChildren().get(i);
			TranslateTransition tt = new TranslateTransition(Duration.seconds(1.15 + i*0.2), b);
    		tt.setToX(0);
    		tt.setOnFinished(e->b.translateXProperty().negate());
    		tt.play();
		} 
	}
	
	public void bigMenuLayout() {
		for (int i = 0; i < menuButtons.getChildren().size(); i++) {
			Button b = (Button) menuButtons.getChildren().get(i);
			b.setFont(Font.font(25));
			b.setPrefSize(300, 40);
		}
		Text gameTitle = (Text) menuLayout.getChildren().get(1);
		gameTitle.setFont(Font.font(70));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTranslateX(sceneWidth/2 - titleWidth/2);
		gameTitle.setTranslateY(sceneHeight/4);
		menuButtons.setTranslateX(sceneWidth/2 - titleWidth/2 + 100);
		menuButtons.setTranslateY(sceneHeight/3 + 50);
		menuMuteButton.setTranslateX(sceneWidth - 100);
		menuMuteButton.setTranslateY(20);
	}
	
	public void smallMenuLayout() {
		for (int i = 0; i < menuButtons.getChildren().size(); i++) {
			Button b = (Button) menuButtons.getChildren().get(i);
			b.setFont(Font.font(18));
			b.setPrefSize(200, 30);
		}
		Text gameTitle = (Text) menuLayout.getChildren().get(1);
		gameTitle.setFont(Font.font(48));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTranslateX(sceneWidth/2 - titleWidth/2);
		gameTitle.setTranslateY(sceneHeight/4);
		menuButtons.setTranslateX(sceneWidth/2 - 100);
		menuButtons.setTranslateY(sceneHeight/3 + 50);
		menuMuteButton.setTranslateX(sceneWidth - 100);
		menuMuteButton.setTranslateY(20);
	}
	
	public void bigGameLayout(String difficulty) {
		AnchorPane layout = gameLayout.get(difficulty);	
		Text levelBoard = (Text) layout.getChildren().get(1);
		Text scoreBoard = (Text) layout.getChildren().get(2);
		gameButtons = (VBox) layout.getChildren().get(3);
		ToggleButton mute = (ToggleButton) layout.getChildren().get(4);
		for(int i = 0; i < gameButtons.getChildren().size(); i++) {
			HBox buttons = (HBox) gameButtons.getChildren().get(i);
			for (int j = 0; j < buttons.getChildren().size(); j++) {
	            Button b = (Button) buttons.getChildren().get(j);
	            b.setFont(Font.font(18));
	            b.setPrefSize(150,30);
			}
			
		}
		mute.setTranslateX(sceneWidth - 100);
		mute.setTranslateY(20);
		gameButtons.setTranslateX(sceneWidth/2 - 100);
		gameButtons.setTranslateY(sceneHeight/2 + 100);
		levelBoard.setFont(Font.font(36));
		levelBoard.setTranslateX(sceneWidth/2 - 400);
		levelBoard.setTranslateY(sceneHeight/4);
		scoreBoard.setFont(Font.font(36));
		scoreBoard.setTranslateX(sceneWidth/2 - 400);
		scoreBoard.setTranslateY(sceneHeight/4 + 100);
	}
	
	public void smallGameLayout(String difficulty) {	
		AnchorPane layout = gameLayout.get(difficulty);	
		Text levelBoard = (Text) layout.getChildren().get(1);
		Text scoreBoard = (Text) layout.getChildren().get(2);
		gameButtons = (VBox) layout.getChildren().get(3);
		ToggleButton mute = (ToggleButton) layout.getChildren().get(4);
		for(int i = 0; i < gameButtons.getChildren().size(); i++) {
			HBox buttons = (HBox) gameButtons.getChildren().get(i);
			for (int j = 0; j < buttons.getChildren().size(); j++) {
	            Button b = (Button) buttons.getChildren().get(j);
	            b.setFont(Font.font(16));
	            b.setPrefSize(120, 20);			
			}
		}
		mute.setTranslateX(sceneWidth - 100);
		mute.setTranslateY(20);
		gameButtons.setTranslateX(sceneWidth/2 - 50);
		gameButtons.setTranslateY(sceneHeight/2 + 50);
		levelBoard.setFont(Font.font(24));
		levelBoard.setTranslateX(sceneWidth/2 - 350);
		levelBoard.setTranslateY(sceneHeight/4 - 20);
		scoreBoard.setFont(Font.font(24));
		scoreBoard.setTranslateX(sceneWidth/2 - 350);
		scoreBoard.setTranslateY(sceneHeight/4 + 50);
	}
	
	public AnchorPane createGameLayout(String difficulty) {
		AnchorPane layout = new AnchorPane();
		gameButtons = new VBox(30);
		ImageView background = getBackground("file:resource/background.jpg", 0.1);
		HBox gameButtonsHolder = new HBox(20);
		HBox gameButtonsHolder1 = new HBox(20);
		HBox gameButtonsHolder2 = new HBox(20);
		Polygon polygon = new Polygon(
				0, 0,
				200, 0,
				200, 30,
				0, 30
        );

		Button homeBtn = createBtn("HOME", polygon);
		Button resetBtn = createBtn("RESET", polygon);
		Button hintsBtn = createBtn("HINTS", polygon);
		Button undoBtn = createBtn("UNDO", polygon);
		Button nextBtn = createBtn("NEXT", polygon);
		Button previousBtn = createBtn("PREVIOUS", polygon);
		gameButtonsHolder.getChildren().addAll(hintsBtn, undoBtn);
		gameButtonsHolder1.getChildren().addAll(resetBtn, homeBtn);
		gameButtonsHolder2.getChildren().addAll(previousBtn, nextBtn);
		gameButtons.getChildren().addAll(gameButtonsHolder, gameButtonsHolder1, gameButtonsHolder2);
		ToggleButton mute = new ToggleButton("MUTE");
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
		String Level = "Level : 0";
		String Moves = "Moves : 0";
		Text levelBoard = createText(Level, 24);
		Text scoreBoard = createText(Moves, 24);	
		layout.getChildren().addAll(background, levelBoard, scoreBoard, gameButtons, mute);
		gameLayout.put(difficulty, layout);
		return layout;
	
	}
	
	public AnchorPane winningScene(Board board) {
		AnchorPane winLayout = new AnchorPane();
		String Moves = "Moves made: " + board.getnMoves();
		HBox hbox = new HBox(30);
		Polygon polygon = new Polygon(
				0, 0,
				200, 0,
				200, 30,
				0, 30
        );
		Button homeBtn = createBtn("HOME", polygon);
		Button resetBtn = createBtn("RESET", polygon);
		Button nextBtn = createBtn("NEXT", polygon);
		hbox.getChildren().addAll(homeBtn, resetBtn, nextBtn);
		Text moves = new Text(Moves);
		Text win = new Text("Congratulations! You Win."); 
		ImageView background = getBackground("file:resource/background.jpg", 1.0);
		winLayout.getChildren().addAll(background, win, moves, hbox);
		return winLayout;
	}
	
	public void smallWiningScene(AnchorPane winLayout) {
		Text win = (Text) winLayout.getChildren().get(1);
		Text moves = (Text) winLayout.getChildren().get(2);
		HBox buttons = (HBox) winLayout.getChildren().get(3);
		for(int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			b.setPrefSize(120, 20);
		}
		win.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 26));
		moves.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 26));
		double winPosition = sceneWidth - win.getLayoutBounds().getWidth();
		double movesPosition = sceneWidth - moves.getLayoutBounds().getWidth();
		win.setTranslateX(winPosition/2);
		win.setTranslateY(sceneHeight/3);
		moves.setTranslateX(movesPosition/2);
		moves.setTranslateY(sceneHeight/3 + 50);
		buttons.setTranslateX(sceneWidth/2 + 100);
		buttons.setTranslateY(sceneHeight/2 + 50);
	}
	
	public void bigWinningScene(AnchorPane winLayout) {
		Text win = (Text) winLayout.getChildren().get(1);
		Text moves = (Text) winLayout.getChildren().get(2);
		HBox buttons = (HBox) winLayout.getChildren().get(3);
		for(int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			b.setPrefSize(150, 30);
		}
		win.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 36));
		moves.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 36));
		double winPosition = sceneWidth - win.getLayoutBounds().getWidth();
		double movesPosition = sceneWidth - moves.getLayoutBounds().getWidth();
		win.setTranslateX(winPosition/2);
		win.setTranslateY(sceneHeight/3);
		moves.setTranslateX(movesPosition/2);
		moves.setTranslateY(sceneHeight/3 + 50);
		buttons.setTranslateX(sceneWidth/2 + 50);
		buttons.setTranslateY(sceneHeight/2 + 50);
	}
	
	public AnchorPane renderPuzzle(String difficulty, Generator g, String buttonText) {
		Board puzzle = null;
		String level = "Level: ";
		String move = "Moves: 0";
		AnchorPane layout = gameLayout.get(difficulty);	
		Text levelBoard = (Text) layout.getChildren().get(1);
		score = (Text) layout.getChildren().get(2);
		score.setText(move);
		Group root = new Group();

		if(buttonText.equals("PREVIOUS")) {
			switch(difficulty) {
				case("EASY"):
					if(easyLevel > 1) { 
						easyLevel--;
						level = level + easyLevel;
						levelBoard.setText(level);
						puzzle = g.GetPreviousEasyBoard();
						root = createPuzzle(difficulty, puzzle);					
					}
					
					break;
				case("MEDIUM"):
					if(mediumLevel > 1) {
						mediumLevel--;
						puzzle = g.GetPreviousMediumBoard();
						root = createPuzzle(difficulty, puzzle);
						level = level + mediumLevel;
						levelBoard.setText(level);
					}
					
					break;
				case("HARD"):
					if(hardLevel > 1) {
						hardLevel--;
						puzzle = g.GetPreviousHardBoard();
						root = createPuzzle(difficulty, puzzle);
						level = level + hardLevel;
						levelBoard.setText(level);
					}	
					break;
			}
			
		}else {
			switch(difficulty) {
				case("EASY"):
					easyLevel++;
					level = level + easyLevel;
					levelBoard.setText(level);
					puzzle = g.GetNextEasyBoard();
					root = createPuzzle(difficulty, puzzle);
					break;
				case("MEDIUM"):
					mediumLevel++;
					level = level + mediumLevel;
					levelBoard.setText(level);
					puzzle = g.GetNextMediumBoard();
					root = createPuzzle(difficulty, puzzle);
					break;
				case("HARD"):
					hardLevel++;
					level = level + hardLevel;
					levelBoard.setText(level);
					puzzle = g.GetNextHardBoard();
					root = createPuzzle(difficulty, puzzle);
					break;
			
			}
			
		}
		currentGameLayout = new Pair<String, AnchorPane>(difficulty, layout);
		return layout;
		
	}
	
	public Group createPuzzle(String difficulty, Board puzzle) {
		if(gameBoard != null) {
			removeBoard();
		}
		Group root = new Group();
		AnchorPane layout = gameLayout.get(difficulty);	
		switch(difficulty) {
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
		gameBoard = new Pair<String, Group>(difficulty, root);
		//root.setOnMouseReleased(OnMouseReleasedEventHandler);
		layout.getChildren().add(root);
		return root;
	}
	
	public void bigGrid() {
		Group board = (Group) gameBoard.getValue();	
		board.setTranslateX(sceneWidth/2 + 20);
		board.setTranslateY(sceneHeight/5);
	
	}	
	
	public void smallGrid() {
		Group board = (Group) gameBoard.getValue();
		board.setTranslateX(sceneWidth/2);
		board.setTranslateY(sceneHeight/5);		
	}

	public void updateMove(Board board) {
		AnchorPane gameLayout = (AnchorPane) currentGameLayout.getValue();
		int moves = board.getnMoves();
		String Moves = "Moves: " + moves;
		Text score = (Text) gameLayout.getChildren().get(2);
		score.setText(Moves);	
	}
	
	
	public void removeBoard() {	
		String difficulty = (String) gameBoard.getKey();
		AnchorPane layout = gameLayout.get(difficulty);	
		Group board = (Group) gameBoard.getValue();
		switch(difficulty) {
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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javafx.animation.TranslateTransition;
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
import javafx.scene.shape.Rectangle;
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
	private int gridLength;
	private HashMap<String, AnchorPane> gameLayout;
	private Text score;
	private Pair<String, Group> gameBoard;
	private Grid grid;	
	private int easyLevel;
	private int mediumLevel;
	private int hardLevel;
	private Board puzzle;
	private ToggleButton menuMuteButton;
	private AnchorPane currentGameLayout;
	
	
	public SceneView (double width, double height) {
		this.menuLayout = new AnchorPane();
		this.menuButtons = new VBox(30);
		this.sceneWidth = width;
		this.sceneHeight = height;
		this.gridLength = 50;
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

	public Board getPuzzle() {
		return puzzle;
	}

	public Grid getGrid() {
		return grid;
	}

	public int getGridLength() {
		return gridLength;
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
		button.setStyle("-fx-background-color: -fx-body-color;");
		button.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 18));
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
		gameButtons.setTranslateX(sceneWidth/2 - 150);
		gameButtons.setTranslateY(sceneHeight/2 + 100);
		levelBoard.setFont(Font.font(36));
		levelBoard.setTranslateX(sceneWidth/2 - 450);
		levelBoard.setTranslateY(sceneHeight/4);
		scoreBoard.setFont(Font.font(36));
		scoreBoard.setTranslateX(sceneWidth/2 - 450);
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
		gameButtons.setTranslateX(sceneWidth/2);
		gameButtons.setTranslateY(sceneHeight/2 + 50);
		levelBoard.setFont(Font.font(24));
		levelBoard.setTranslateX(sceneWidth/2 - 300);
		levelBoard.setTranslateY(sceneHeight/4 - 20);
		scoreBoard.setFont(Font.font(24));
		scoreBoard.setTranslateX(sceneWidth/2 - 300);
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
		gameButtonsHolder1.getChildren().addAll(homeBtn, resetBtn);
		gameButtonsHolder2.getChildren().addAll(nextBtn, previousBtn);
		gameButtons.getChildren().addAll(gameButtonsHolder, gameButtonsHolder1, gameButtonsHolder2);
		ToggleButton mute = new ToggleButton("MUTE");
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
		if(sceneWidth >= 900  && sceneHeight  >= 700) {
			bigGameLayout(difficulty);
		}else {
			smallGameLayout(difficulty);
		}
		
		return layout;
	
	}
	
	public void renderPuzzle(String difficulty, String buttonText) {
		String move = "Moves: 0";
		String level = "Level: ";
		AnchorPane layout = gameLayout.get(difficulty);	
		Text levelBoard = (Text) layout.getChildren().get(1);
		score = (Text) layout.getChildren().get(2);
		score.setText(move);
		removeBoard();
		Group puzzle = null;
		if(buttonText.equals("NEXT")) {
			switch(difficulty) {
				case("EASY"):
					easyLevel--;
					level = level + easyLevel;
					levelBoard.setText(level);
					//puzzle = easyBoards.get(easyLevel);
					layout.getChildren().add(puzzle);
					break;
				case("MEDIUM"):
					mediumLevel--;
					break;
				case("HARD"):
					hardLevel--;
					break;
			
			}
		}else if(buttonText.equals("PREVIOUS")) {
			switch(difficulty) {
				case("EASY"):
					easyLevel++;
					level = level + easyLevel;
					levelBoard.setText(level);
					//puzzle = easyBoards.get(easyLevel);
					layout.getChildren().add(puzzle);
					break;
				case("MEDIUM"):
					mediumLevel++;
					break;
				case("HARD"):
					hardLevel++;
					break;
			
			}
			
		}
		gameBoard = new Pair<String, Group>(difficulty, puzzle);
	}
	
	public void createPuzzle(String difficulty, Generator g) {
		String level = "Level: ";
		String move = "Moves: 0";
		AnchorPane layout = gameLayout.get(difficulty);	
		Text levelBoard = (Text) layout.getChildren().get(1);
		score = (Text) layout.getChildren().get(2);
		score.setText(move);
		Group root = new Group();
		if(gameBoard != null) {
			removeBoard();
		}
		switch(difficulty) {
			case("EASY"):
				puzzle = g.RandomEasyGenerator(6);
				grid = new Grid(puzzle, gridLength);
				root.getChildren().addAll(grid.getGridSquares());
				root.getChildren().addAll(grid.getBlockGroups());
				easyLevel++;
				level = level + easyLevel;
				levelBoard.setText(level);
				layout.getChildren().add(root);
				//easyBoards.add(root);
				break;
			case("MEDIUM"):
				mediumLevel++; 
				puzzle = g.RandomMediumGenerator(6);
				level = level + mediumLevel;
				System.out.println(level);
				levelBoard.setText(level);
				grid = new Grid(puzzle, gridLength);
				root.getChildren().addAll(grid.getGridSquares());
				root.getChildren().addAll(grid.getBlockGroups());
				layout.getChildren().add(root);
				break;
			case("HARD"):
				hardLevel++;
				level = level + hardLevel;
				levelBoard.setText(level);
				System.out.println(level);
				//grid = new Grid(g.RandomGenerator1(6), gridLength);
				root.getChildren().addAll(grid.getGridSquares());
//				root.getChildren().addAll(grid.getBlockGroups());
				layout.getChildren().add(root);
				break;
		}
		currentGameLayout = layout;
		root.setOnMouseReleased(OnMouseReleasedEventHandler);
		gameBoard = new Pair<String, Group>(difficulty, root);
	}
	
	public void bigGrid() {
		double xPos = 0;
		double yPos = 0;
		int i = 0;
		double size = gridLength*1.5;
		//double size = gridLength;
		grid.setsLength((int)size);
		grid.setGridLength((int) size);
		Board b = grid.getBoard();
//		Group board = (Group) gameBoard.getValue();
		String difficulty = gameBoard.getKey();
		Group root = new Group();
		ArrayList<Rectangle> gridSquares = grid.getGridSquares();
		ArrayList<Group> vehicles = grid.getBlockGroups();
		for(Rectangle r: gridSquares) {
			if (i % grid.getBoardSize() == 0 && i != 0){
				xPos = 0;
				yPos += size;
			}
			r.setWidth(size);
			r.setHeight(size);
			r.setX(xPos);
			r.setY(yPos);
			xPos += size;
			i++;
		}
		//System.out.println(grid.getsLength());
		vehicles.clear();
		grid.placeVehicle(b);
		root.getChildren().addAll(gridSquares);
		root.getChildren().addAll(vehicles);
//		for(int j = 0; j < vehicles.size(); j++) {
//			Group g = vehicles.get(j);
//			Sprite s = (Sprite) g.getChildren().get(0);
//			int xPosVehicles = (int) ((s.getX()/gridLength)*size);
//			int yPosVehicles = (int) ((s.getY()/gridLength)*size);
//			double x = s.getTranslateX() + s.getX();
//			double y = s.getTranslateY() + s.getY();
//			int xPosVehicles = (int) (s.getTranslateX()/orgGridLength);
//			int yPosVehicles = (int) (s.getTranslateY()/orgGridLength);
//			int xPosVehicles = (int) (x/gridLength);
//			int yPosVehicles = (int) (y/gridLength);
//			if(s.getWidth() > s.getHeight()) {	//horizontal
//				if(s.getWidth() <  gridLength*3-2) {	//car
//					s.setWidth(size*2-2);
//					s.setHeight(size-2);
//				}else {
//					s.setWidth(size*3-2);
//					s.setHeight(size-2);
//				}
//				
//			}else {
//				if(s.getWidth() < gridLength*3-2) {
//					s.setHeight(size*2-2);
//					s.setWidth(size-2);
//				}else {
//					s.setHeight(size*3-2);
//					s.setWidth(size-2);
//				}
//			}
//			s.setX(xPosVehicles+1);
//			s.setY(yPosVehicles+1);
//			System.out.println("x " + (xPosVehicles+1));
//			System.out.println(s.getX());
//			System.out.println("y" + (yPosVehicles+1));
//			System.out.println(s.getY());
//		}
		
//		board.setTranslateX(sceneWidth/2 + 20);
//		board.setTranslateY(sceneHeight/5);
		root.setTranslateX(sceneWidth/2 + 20);
		root.setTranslateY(sceneHeight/5);
		root.setOnMouseReleased(OnMouseReleasedEventHandler);
		gameBoard = new Pair<String, Group>(difficulty, root);
		currentGameLayout.getChildren().remove(5);
		currentGameLayout.getChildren().add(root);
		
	}	
	
	public void smallGrid() {
		double xPos = 0;
		double yPos = 0;
		int i = 0;
		double size = gridLength;
		double orgGridLength = grid.getsLength();
		grid.setsLength((int)size);
		grid.setGridLength((int) size);
		Board b = grid.getBoard();
		String difficulty = gameBoard.getKey();
		Group root = new Group();
		ArrayList<Rectangle> gridSquares = grid.getGridSquares();
		ArrayList<Group> vehicles = grid.getBlockGroups();
		
		for(Rectangle r: gridSquares) {
			if (i % grid.getBoardSize() == 0 && i != 0){
				xPos = 0;
				yPos += size;
			}
			r.setWidth(size);
			r.setHeight(size);
			r.setX(xPos);
			r.setY(yPos);
			xPos += size;
			i++;
		}
		vehicles.clear();
		System.out.println(vehicles.size());
		grid.placeVehicle(b);
		root.getChildren().addAll(gridSquares);
		root.getChildren().addAll(vehicles);
//		for(Group g: vehicles) {
//			Sprite s = (Sprite) g.getChildren().get(0);
//			double x = s.getTranslateX() + s.getX();
//			double y = s.getTranslateY() + s.getY();
////			int xPosVehicles = (int) (s.getTranslateX()/orgGridLength);
////			int yPosVehicles = (int) (s.getTranslateY()/orgGridLength);
//			int xPosVehicles = (int) (x/orgGridLength);
//			int yPosVehicles = (int) (y/orgGridLength);
//			if(s.getWidth() > s.getHeight()) {	//horizontal
//				if(s.getWidth() < orgGridLength*3-2) {	//car
//					s.setWidth(size*2-2);
//					s.setHeight(size-2);
//				}else {
//					s.setWidth(size*3-2);
//					s.setHeight(size-2);
//				}
//				
//			}else {
//				if(s.getWidth() < orgGridLength*3-2) {
//					s.setHeight(size*2-2);
//					s.setWidth(size-2);
//				}else {
//					s.setHeight(size*3-2);
//					s.setWidth(size-2);
//				}
//			}
//			s.setX(xPosVehicles*size+1);
//			s.setY(yPosVehicles*size+1);
////			System.out.println("x " + (xPosVehicles*size+1.0));
////			System.out.println(s.getX());
////			System.out.println((yPosVehicles*size+1.0));
////			System.out.print(s.getY());
//		}
		
//		Group board = (Group) gameBoard.getValue();
//		board.setTranslateX(sceneWidth/2 + 20);
//		board.setTranslateY(sceneHeight/5);
		root.setTranslateX(sceneWidth/2 + 20);
		root.setTranslateY(sceneHeight/5);
		root.setOnMouseReleased(OnMouseReleasedEventHandler);
		gameBoard = new Pair<String, Group>(difficulty, root);
		currentGameLayout.getChildren().remove(5);
		currentGameLayout.getChildren().add(root);
		
		
	}
	
	EventHandler<MouseEvent> OnMouseReleasedEventHandler =
			new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				int moves = grid.getMoves();
				String Moves = "Moves : " + moves;
				Text score = (Text) currentGameLayout.getChildren().get(2);
				ToggleButton mute = (ToggleButton) currentGameLayout.getChildren().get(4);
				String musicFile = "resource/click.mp3";    
				Media sound = new Media(new File(musicFile).toURI().toString());
				MediaPlayer effectMP = new MediaPlayer(sound);
				if(mute.isSelected()) {
					effectMP.stop();
				}else {
					effectMP.play();
				}
				score.setText(Moves);				
			}
		};
	
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
	}
	

}
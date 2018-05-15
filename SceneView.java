import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class SceneView extends Pane{
	private VBox menuButtons;
	private AnchorPane menuLayout;
	private Text gameTitle;
	private double sceneWidth;
	private double sceneHeight;
	private VBox gameButtons;
	private int gridLength;
	private int rectLength;
	private AnchorPane gameLayout;
	private Text levelBoard;
	private Text scoreBoard;
	private Group gameBoard;
	private Grid grid;
	
	
	public SceneView (double width, double height) {
		this.menuLayout = new AnchorPane();
		this.menuButtons = new VBox(30);
		this.gameButtons = new VBox(20);
		this.sceneWidth = width;
		this.sceneHeight = height;
		this.gridLength = 50;
		this.rectLength = gridLength*2;	
		this.gameBoard = new Group();
		this.gameLayout = new AnchorPane();	
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

	public Parent createMenu() {
		int btnWidth = 200;
		int btnHeight = 30;
		Polygon polygon = new Polygon(
				0, 0,
				200, 0,
				215, 15,
				200, 30,
				0, 30
		);
		gameTitle = createText("G R I D L O C K" , 48);
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTextAlignment(TextAlignment.CENTER);
		gameTitle.setTranslateX(sceneWidth/2 - titleWidth/2);
		gameTitle.setTranslateY(sceneHeight/4);
		ImageView background = getBackground("file:resource/background.jpg", 1.0);
		menuButtons.setTranslateX(sceneWidth/2 - 100);
		menuButtons.setTranslateY(sceneHeight/3 + 50);
		Button easyBtn = createBtn("EASY", polygon, btnWidth, btnHeight);
		Button mediumBtn = createBtn("MEDIUM", polygon, btnWidth, btnHeight);
		Button hardBtn = createBtn("HARD", polygon, btnWidth, btnHeight);
		Button exitBtn = createBtn("EXIT", polygon, btnWidth, btnHeight);
		menuButtons.getChildren().addAll(easyBtn, mediumBtn, hardBtn,exitBtn);
		//smallMenuLayout(sceneWidth, sceneHeight);
		animation();
		menuLayout.getChildren().addAll(background, gameTitle, menuButtons);
		//menuLayout.getChildren().addAll(background, gameTitle, menuButtons);
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
	
	private Button createBtn(String name, Polygon polygon, int btnWidth, int btnHeight) {
		Button button = new Button(name);
		button.setShape(polygon);
		button.setPrefSize(btnWidth, btnHeight);
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
		
		gameTitle.setFont(Font.font(70));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTranslateX(sceneWidth/2 - titleWidth/2);
		gameTitle.setTranslateY(sceneHeight/4);
		menuButtons.setTranslateX(sceneWidth/2 - titleWidth/2 + 100);
		menuButtons.setTranslateY(sceneHeight/3 + 50);
	}
	
	public void smallMenuLayout() {
		for (int i = 0; i < menuButtons.getChildren().size(); i++) {
			Button b = (Button) menuButtons.getChildren().get(i);
			b.setFont(Font.font(18));
			b.setPrefSize(200, 30);
		}
		
		gameTitle.setFont(Font.font(48));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTranslateX(sceneWidth/2 - titleWidth/2);
		gameTitle.setTranslateY(sceneHeight/4);
		menuButtons.setTranslateX(sceneWidth/2 - 100);
		menuButtons.setTranslateY(sceneHeight/3 + 50);
	}
	
	public void bigGameLayout() {
		for(int i = 0; i < gameButtons.getChildren().size(); i++) {
			HBox buttons = (HBox) gameButtons.getChildren().get(i);
			for (int j = 0; j < buttons.getChildren().size(); j++) {
	            Button b = (Button) buttons.getChildren().get(j);
	            b.setFont(Font.font(18));
	            b.setPrefSize(150,30);
			}
			
		}
		ArrayList<Rectangle> gridSquares = grid.getGridSquares();
		ArrayList<Group> vehicles = grid.getBlockGroups();
		gameButtons.setTranslateX(sceneWidth/2 - 100);
		gameButtons.setTranslateY(sceneHeight/2 + 100);
		levelBoard.setFont(Font.font(36));
		levelBoard.setTranslateX(sceneWidth/2 - 500);
		levelBoard.setTranslateY(sceneHeight/4);
		scoreBoard.setFont(Font.font(36));
		scoreBoard.setTranslateX(sceneWidth/2 - 500);
		scoreBoard.setTranslateY(sceneHeight/4 + 100);
		gameBoard.setTranslateX(sceneWidth/2 + 20);
		gameBoard.setTranslateY(sceneHeight/5);
		//root.setTranslateX(sceneWidth/2 + 50);
		//System.out.println("big");
	}
	
	public void smallGameLayout() {
		for(int i = 0; i < gameButtons.getChildren().size(); i++) {
			HBox buttons = (HBox) gameButtons.getChildren().get(i);
			for (int j = 0; j < buttons.getChildren().size(); j++) {
	            Button b = (Button) buttons.getChildren().get(j);
	            b.setFont(Font.font(16));
	            b.setPrefSize(100, 20);			
			}
		}
		gameButtons.setTranslateX(sceneWidth/2);
		gameButtons.setTranslateY(sceneHeight/2 + 50);
		levelBoard.setFont(Font.font(24));
		levelBoard.setTranslateX(sceneWidth/2 - 300);
		levelBoard.setTranslateY(sceneHeight/4 - 20);
		scoreBoard.setFont(Font.font(24));
		scoreBoard.setTranslateX(sceneWidth/2 - 300);
		scoreBoard.setTranslateY(sceneHeight/4 + 50);
		gameBoard.setTranslateX(sceneWidth/2 + 20);
		gameBoard.setTranslateY(sceneHeight/5);
		//System.out.println("small");
	}
	
	public Parent createGameLayout(String difficulty) {
		int moves = 0;
		int level = 0;
		int btnWidth = 100;
		int btnHeight = 20;
		ImageView background = getBackground("file:resource/background.jpg", 0.1);
		HBox gameButtonsHolder = new HBox(20);
		HBox gameButtonsHolder1 = new HBox(20);
		Polygon polygon = new Polygon(
				0, 0,
                200, 0,
                200, 30,
                0, 30
        );
		Button homeBtn = createBtn("HOME", polygon, btnWidth, btnHeight);
		Button resetBtn = createBtn("RESET", polygon, btnWidth, btnHeight);
		Button hintsBtn = createBtn("HINTS", polygon, btnWidth, btnHeight);
		Button undoBtn = createBtn("UNDO", polygon, btnWidth, btnHeight);
		gameButtonsHolder.getChildren().addAll(hintsBtn, undoBtn);
		gameButtonsHolder1.getChildren().addAll(homeBtn, resetBtn);
		gameButtons.getChildren().addAll(gameButtonsHolder, gameButtonsHolder1);
		String Level = "Level : " + level;
		String Moves = "Moves : " + moves;
		levelBoard = createText(Level, 24);
		scoreBoard = createText(Moves, 24);
//		//Text levelBoard = createText(Level, 24);
//		levelBoard.setTranslateX(sceneWidth/2 - 300);
//		//levelBoard.setTranslateX(50);
//		levelBoard.setTranslateY(sceneHeight/4 - 20);
//		//Text scoreBoard = createText(Moves, 24);
//		scoreBoard.setTranslateY(sceneHeight/4 + 50);
//		scoreBoard.setTranslateX(sceneWidth/2 - 300);
//		gameButtons.setTranslateX(sceneWidth/2);
//		gameButtons.setTranslateY(sceneHeight/2 + 50);
		gameLayout.getChildren().addAll(background, levelBoard, scoreBoard, gameButtons);
		if(sceneWidth >= 1000  && sceneHeight  >= 800) {
			bigGameLayout();
		}else {
			smallGameLayout();
		}
		
		return gameLayout;
	
	}
	
	public void createPuzzle(String difficulty) {
		if(gameBoard != null) {
			gameLayout.getChildren().remove(gameBoard);
		}
		Group root = new Group();
		Generator g = new Generator();
		switch(difficulty) {
			case("EASY"):
				grid = new Grid(g.RandomGenerator1(6), gridLength);
				root.getChildren().addAll(grid.getGridSquares());
				root.getChildren().addAll(grid.getBlockGroups());
				break;
			case("MEDIUM"):
				//root.getChildren().addAll(grid.getGridSquares());
				break;
			case("HARD"):
				//root.getChildren().addAll(grid.getGridSquares());
				break;
		}
		gameBoard = root;
		root.setTranslateX(sceneWidth/2 + 20);
		root.setTranslateY(sceneHeight/5);
		root.setOnMouseReleased(OnMouseReleasedEventHandler);
		gameLayout.getChildren().add(root);
	}
	EventHandler<MouseEvent> OnMouseReleasedEventHandler =
			new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				int moves = grid.getMoves();
				String Moves = "Moves : " + moves;
				scoreBoard.setText(Moves);
			}
		};
	
}
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UI extends Pane {
	private int screenWidth;
	private int screenHeight;
	private VBox menu;
	private AnchorPane menuLayout;
	private Text gameTitle;
	
	public UI(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
		this.menuLayout = new AnchorPane();
		this.menu = new VBox(30);
	}
	
	public Parent createMenu(Stage stage) {
		//Pane layout = new Pane();
		//VBox menu  = new VBox(20);
		
		gameTitle = createTitle("G R I D L O C K" , 48);
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTextAlignment(TextAlignment.CENTER);
		gameTitle.setTranslateX(screenWidth/2 - titleWidth/2);
		gameTitle.setTranslateY(screenHeight/4);
		ImageView background = getBackground("file:resource/background.jpg");
		menu.setTranslateX(screenWidth/2 - 100);
		menu.setTranslateY(screenHeight/3 + 50);
		Button easyBtn = createBtn("EASY");
		easyBtn.setOnAction(e->renderBoard("EASY"));
		Button mediumBtn = createBtn("MEDIUM");
		Button hardBtn = createBtn("HARD");
		Button exitBtn = createBtn("EXIT");
		exitBtn.setOnAction(e->closeProgram(stage));
		menu.getChildren().addAll(easyBtn, mediumBtn, hardBtn,exitBtn);
		animation();
		menuLayout.getChildren().addAll(background, gameTitle, menu);
		return menuLayout;		
	}
	
	private Button createBtn(String name) {
		int btnWidth = 200;
		int btnHeight = 30;
		Polygon polygon = new Polygon(
			 	0, 0,
                200, 0,
                215, 15,
                200, 30,
                0, 30
		);
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
	
	private ImageView getBackground(String path) {
		Image image = new Image(path);
		ImageView background = new ImageView();
		background.setImage(image);
		background.prefHeight(screenHeight);
		background.prefWidth(screenWidth);
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setContrast(1.0);
		background.setEffect(colorAdjust);
		return background;		
	}
	
	private Text createTitle(String name, int fontSize) { 
		Text title = new Text(name);
		title.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, fontSize));
		return title;
		
	}
	
	private void animation() {
		for (int i = 0; i < menu.getChildren().size(); i++) {
            Button b = (Button) menu.getChildren().get(i);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1.15 + i*0.2), b);
    		tt.setToX(0);
    		tt.setOnFinished(e->b.translateXProperty().negate());
    		tt.play();
		} 
	}
	
	public void closeProgram(Stage stage) {
		Alert alert = new Alert(AlertType.NONE, "Are you sure to exit " + " ?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			stage.close();
		}
	}
	
	public void resizeScene(double width, double height) {
		
		if(width > 1000  && height  > 800) {
			resizeBigger(width, height);
	
		}else {
			resizeSmaller(width, height);
		}	
		
	}
	
	public void resizeBigger(double width, double height) {
		for (int i = 0; i < menu.getChildren().size(); i++) {
            Button b = (Button) menu.getChildren().get(i);
            b.setFont(Font.font(25));
            b.setPrefSize(300, 40);
		}
		
		gameTitle.setFont(Font.font(70));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTranslateX(width/2 - titleWidth/2);
		gameTitle.setTranslateY(height/4);
		menu.setTranslateX(width/2 - titleWidth/2 + 100);
		menu.setTranslateY(height/3 + 50);
	}
	
	public void resizeSmaller(double width, double height) {
		for (int i = 0; i < menu.getChildren().size(); i++) {
            Button b = (Button) menu.getChildren().get(i);
            b.setFont(Font.font(18));
            b.setPrefSize(200, 30);
		}
		
		gameTitle.setFont(Font.font(48));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTranslateX(width/2 - titleWidth/2);
		gameTitle.setTranslateY(height/4);
		menu.setTranslateX(width/2 - 100);
		menu.setTranslateY(height/3 + 50);
	}
}

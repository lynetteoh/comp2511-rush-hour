import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
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
	private int gridLength;
	private int rectLength;
	private AnchorPane gameLayout;
	
	
	public SceneView (double width, double height) {
		this.menuLayout = new AnchorPane();
		this.menuButtons = new VBox(30);
		this.sceneWidth = width;
		this.sceneHeight = height;
		
	}
		
	public Parent createMenu() {
		int btnWidth = 200;
		int btnHeight = 30;
		VBox buttons = new VBox(30);
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
		animation();
		menuLayout.getChildren().addAll(background, gameTitle, menuButtons);
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
	
	public void resizeMenuBigger(double width, double height) {
		for (int i = 0; i < menuButtons.getChildren().size(); i++) {
            Button b = (Button) menuButtons.getChildren().get(i);
            b.setFont(Font.font(25));
            b.setPrefSize(300, 40);
		}
		
		gameTitle.setFont(Font.font(70));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTranslateX(width/2 - titleWidth/2);
		gameTitle.setTranslateY(height/4);
		menuButtons.setTranslateX(width/2 - titleWidth/2 + 100);
		menuButtons.setTranslateY(height/3 + 50);
	}
	
	public void resizeMenuSmaller(double width, double height) {
		for (int i = 0; i < menuButtons.getChildren().size(); i++) {
            Button b = (Button) menuButtons.getChildren().get(i);
            b.setFont(Font.font(18));
            b.setPrefSize(200, 30);
		}
		
		gameTitle.setFont(Font.font(48));
		double titleWidth = gameTitle.getLayoutBounds().getWidth();
		gameTitle.setTranslateX(width/2 - titleWidth/2);
		gameTitle.setTranslateY(height/4);
		menuButtons.setTranslateX(width/2 - 100);
		menuButtons.setTranslateY(height/3 + 50);
	}
}
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UI extends Pane {
	private int screenWidth;
	private int screenHeight;
	
	public UI(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
	}
	
	public Parent createMenu() {
		Pane layout = new Pane();
		Text title = createTitle("G R I D L O C K");
		double titleWidth = title.getLayoutBounds().getWidth();
		title.setTranslateX(screenWidth/2 - titleWidth/2);
		title.setTranslateY(screenHeight/4);
		ImageView background = getBackground("file:resource/background.jpg");
		VBox menuBox  = new VBox(20);
		menuBox.setTranslateX(screenWidth/2 - 100);
        menuBox.setTranslateY(screenHeight/3 + 50);
		
		Button easyBtn = createBtn("EASY");
		Button mediumBtn = createBtn("MEDIUM");
		Button hardBtn = createBtn("HARD");
		Button exitBtn = createBtn("EXIT");
		xitBtn.setOnAction(e->closeProgram());
		menuBox.getChildren().addAll(easyBtn, mediumBtn, hardBtn,exitBtn);
		layout.getChildren().addAll(background, title, menuBox);
		return layout;		
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
		button.setStyle("-fx-focus-color: transparent;");
		Effect shadow = new DropShadow(5, Color.GREY);
		button.setEffect(shadow);
		return button;
	}
	
	private ImageView getBackground(String path) {
		Image image = new Image(path);
		ImageView background = new ImageView();
		background.setImage(image);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		return background;
		
	}
	
	private Text createTitle(String name) { 
		Text title = new Text(name);
		title.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 48));
		return title;
		
	}

	public void closeProgram() {
		Alert alert = new Alert(AlertType.NONE, "Are you sure to exit " + " ?", 
				 ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			Platform.exit();
		}
		
	}
}

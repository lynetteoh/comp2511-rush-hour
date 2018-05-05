import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GridLock extends Application {

	private UI ui; 
	private int screenWidth;
	private int screenHeight;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			screenWidth = 800;
			screenHeight = 600;
			primaryStage.setTitle("GridLock");
			ui = new UI(screenWidth, screenHeight);
			Scene scene = new Scene(ui.createMenu(), screenWidth, screenHeight);
			primaryStage.setScene(scene);
		    primaryStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

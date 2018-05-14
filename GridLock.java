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
			primaryStage.setMinWidth(700);
			primaryStage.setMinHeight(500);
			primaryStage.setMaxHeight(1200);
			primaryStage.setMaxWidth(1980);
			//scene.getStylesheets().add("style.css");
			primaryStage.setScene(scene);
		    primaryStage.show();
		    primaryStage.setOnCloseRequest(e->ui.closeProgram());
		}catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	public static void main(String[] args) {
		Generator g = new Generator();
		g.RandomGenerator1(6);
		//launch(args);
	}

}

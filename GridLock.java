import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GridLock extends Application {

	private SceneManager sceneManager; 
	private double screenMinWidth;
	private double screenMinHeight;
	private double screenMaxWidth;
	private double screenMaxHeight;
	private Scene scene;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			screenMinWidth = 700;
			screenMinHeight = 500;
			screenMaxWidth = 1980;
			screenMaxHeight = 1200;
			
			primaryStage.setTitle("GridLock");
			primaryStage.setMinWidth(screenMinWidth);
			primaryStage.setMinHeight(screenMinHeight);
			primaryStage.setMaxHeight(screenMaxHeight);
			primaryStage.setMaxWidth(screenMaxWidth);
			sceneManager = new SceneManager(screenMinWidth, screenMinHeight, primaryStage);
			scene = sceneManager.createMenuScene();
			//scene.getStylesheets().add("style.css");
			primaryStage.setScene(scene);
			stageListener(primaryStage);
			primaryStage.show();
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				sceneManager.closeProgram(primaryStage);
			});
			

	    
		}catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

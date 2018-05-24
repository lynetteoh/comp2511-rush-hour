import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author lean lynn oh
 *
 */
public class GridLock extends Application {

	private SceneManager sceneManager;
	private double screenMinWidth;
	private double screenMinHeight;
	private double screenMaxWidth;
	private double screenMaxHeight;
	private Scene scene;
	
	/**
	 * This function is the entry point for the view. It sets up the window and display the first scene on screen
	 * @param primaryStage: the window of the game
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			screenMinWidth = 800;
			screenMinHeight = 600;
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

	
	/**
	 * Main function of the gridLock game
	 * @param args
	 */
	public static void main(String[] args) {	
		launch(args);
	}
	
	/**
	 * This function listen to the changes made on the window and updates the width and height of the window accordingly
	 * @param stage: the window of the gridLock game
	 */
	public void stageListener(Stage stage) {
		stage.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
		        stage.setWidth((double) newWidth);
		    }
		});

		stage.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
		    	stage.setHeight((double) newHeight);
		    }
		});
	}

}

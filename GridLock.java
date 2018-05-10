import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GridLock extends Application {

	private UI ui; 
	private int screenMinWidth;
	private int screenMinHeight;
	private int screenMaxWidth;
	private int screenMaxHeight;
	private Scene scene;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			screenMinWidth = 700;
			screenMinHeight = 500;
			screenMaxWidth = 1980;
			screenMaxHeight = 1200;
			
			primaryStage.setTitle("GridLock");
			ui = new UI(screenMinWidth, screenMinHeight);
			scene = new Scene(ui.createMenu(primaryStage), screenMinWidth, screenMinHeight);
			primaryStage.setMinWidth(screenMinWidth);
			primaryStage.setMinHeight(screenMinHeight);
			primaryStage.setMaxHeight(screenMaxHeight);
			primaryStage.setMaxWidth(screenMaxWidth);
			//scene.getStylesheets().add("style.css");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				ui.closeProgram(primaryStage);
			});
			scene.widthProperty().addListener(new ChangeListener<Number>() {
			    @Override 
			    public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
			        double height = scene.getHeight();
			        ui.resizeScene((double)newWidth, height);
			    }
			});
		
			scene.heightProperty().addListener(new ChangeListener<Number>() {
			    @Override 
			    public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
			        double width = scene.getWidth();
			        ui.resizeScene(width, (double)newHeight);
			    }
			});

	    
		}catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

import java.util.HashMap;
import java.util.Map.Entry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneManager extends Pane {
	private double sceneWidth;
	private double sceneHeight;
	private Stage stage;
	private HashMap<String, Scene> scenes;
	private SceneView sceneView;
	
	public SceneManager(double width, double height, Stage stage) {
		this.sceneWidth = width;
		this.sceneHeight = height;
		this.stage = stage;
		this.scenes = new HashMap<String, Scene>();
		this.sceneView = new SceneView(sceneWidth, sceneHeight);	
	}
	
	public Scene createMenuScene() {
		Parent menuLayout = sceneView.createMenu();
		Scene menuScene = new Scene(menuLayout, sceneWidth, sceneHeight);
		VBox buttons = sceneView.getMenuButtons();
		addMenuButtonAction(buttons);
		scenes.put("MENU", menuScene);
		sceneListener(menuScene);
		return menuScene;
	}

	
	private void addMenuButtonAction(VBox buttons) {
		for (int i = 0; i < buttons.getChildren().size(); i++) {
            Button b = (Button) buttons.getChildren().get(i);
            String name = b.getText();
            switch(name) {
				case("EASY"):
					b.setOnAction(e->createGameScene(name));
					break;
				case("MEDIUM"):
					b.setOnAction(e->createGameScene(name));
					break;
				case("HARD"):
					b.setOnAction(e->createGameScene(name));
					break;
				case("EXIT"):
					b.setOnAction(e->closeProgram(stage));
					break;
            }
			
		}
	}
	public void addGameButtonAction(HBox buttons) {
		for (int i = 0; i < buttons.getChildren().size(); i++) {
            Button b = (Button) buttons.getChildren().get(i);
            String name = b.getText();
            switch(name) {
				case("UNDO"):
				
					break;
				case("RESET"):
		
					break;
				case("HOME"):
					b.setOnAction(e->changeScene("MENU"));
					break;
				case("HINTS"):
					
					break;
            }
			
		}
		
	}

	private void changeScene(String name) {
		return;
	}

	public void closeProgram(Stage stage) {
		Alert alert = new Alert(AlertType.NONE, "Are you sure to exit " + " ?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			stage.close();
		}
	}
	
	public void resizeScene(Scene scene, double width, double height) {
		String key = null;
		for(Entry<String, Scene> entry: scenes.entrySet()){
			if(scene.equals(entry.getValue())) {
				key = (String) entry.getKey();
				break;
			}
		}
		
		if(width >= 1000  && height  >= 800) {
			switch(key) {
				case("EASY"):
					
					break;
				case("MEDIUM"):
					
					break;
				case("HARD"):
					
					break;
				case("MENU"):
					sceneView.resizeMenuBigger(width, height);
					break;
			}
			
		}else {
			switch(key) {
				case("EASY"):
					
					break;
				case("MEDIUM"):
					
					break;
				case("HARD"):
					
					break;
				case("MENU"):
					sceneView.resizeMenuSmaller(width, height);
					break;
			}
	
		}
		
	}
	
	public void sceneListener(Scene scene) {
		scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override 
		    public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
		    	sceneWidth = (double) newWidth;
		    	sceneHeight = scene.getHeight();
		        resizeScene(scene, sceneWidth, sceneHeight);
		    }
		});
	
		scene.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override 
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
		    	sceneHeight = (double) newHeight;
		        sceneWidth = scene.getWidth();
		        resizeScene(scene, sceneWidth, sceneHeight);
		    }
		});
	}
}

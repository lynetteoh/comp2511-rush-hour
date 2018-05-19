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
	
	public Scene createGameScene(String difficulty) {
		Parent gameLayout = sceneView.createGameLayout(difficulty);
		Scene gameScene = new Scene(gameLayout, sceneWidth, sceneHeight);
		VBox buttons = sceneView.getGameButtons();
		for(int i = 0; i < buttons.getChildren().size(); i++) {
			HBox gameButtons = (HBox) buttons.getChildren().get(i);
			addGameButtonAction(gameButtons, difficulty);
			
		}
		scenes.put(difficulty, gameScene);
		sceneListener(gameScene);
		return gameScene;
	}

	
	private void addMenuButtonAction(VBox buttons) {
		for (int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			String name = b.getText();
			switch(name) {
				case("EASY"):
					b.setOnAction(e->changeScene(name));
					break;
				case("MEDIUM"):
					b.setOnAction(e->changeScene(name));
					break;
				case("HARD"):
					b.setOnAction(e->changeScene(name));
					break;
				case("EXIT"):
					b.setOnAction(e->closeProgram(stage));
					break;
            }
			
		}
	}
	
	public Scene getScene(String name) {
		Scene scene = null;
		for(Entry<String, Scene> entry: scenes.entrySet()){
			if(entry.getKey().equals(name)) {
				scene = entry.getValue();
				break;
			}
		}
		return scene;
	}

	public void addGameButtonAction(HBox buttons, String difficulty) {
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
				case("NEXT"):
					b.setOnAction(e->changeScene(difficulty));
					break;
				case("PREVIOUS"):
					b.setOnAction(e->changeScene(difficulty));
					break;
				
            }
			
		}
		
	}

	private void changeScene(String name) {
		System.out.println("scene " +name);
//		String sceneName = null;
		
//		if(name.equals("EASY") || name.equals("MEDIUM") || name.equals("HARD")) {
//			sceneName = "GAME";
//		}else {
//			sceneName = "MENU";
//		}
//		
//		Scene scene = getScene(sceneName);
		//Scene scene = getScene(name);
		Scene scene = scenes.get(name);
		if(scene != null) {
			if(name.equals("EASY") || name.equals("MEDIUM") || name.equals("HARD")) {
				sceneView.createPuzzle(name);
			}		
		}else {
			scene = createGameScene(name);
			sceneView.createPuzzle(name);
		}
		
		if(sceneWidth >= 900  && sceneHeight  >= 700) {
			sceneView.bigGrid();
		}else {
			sceneView.smallGrid();
		}
		
		stage.setScene(scene);
		stage.show();
	}

	public void closeProgram(Stage stage) {
		Alert alert = new Alert(AlertType.NONE, "Are you sure to exit " + " ?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			stage.close();
		}
	}
	
	public void resizeScene(Scene scene) {
		String key = null;
		for(Entry<String, Scene> entry: scenes.entrySet()){
			if(scene.equals(entry.getValue())) {
				key = (String) entry.getKey();
				break;
			}
		}
		
		if(sceneWidth >= 900  && sceneHeight  >= 700) {
			switch(key) {
				case("EASY"):
					//sceneView.bigGrid();
					sceneView.bigGameLayout(key);
					sceneView.bigGrid();
					break;
				case("MEDIUM"):
					sceneView.bigGameLayout(key);
					sceneView.bigGrid();
					break;
				case("HARD"):
					sceneView.bigGameLayout(key);
					sceneView.bigGrid();
					break;
				case("MENU"):
					sceneView.bigMenuLayout();
					break;
			}
			
		}else {
			switch(key) {
				case("EASY"):
					//sceneView.smallGrid();
					sceneView.smallGameLayout(key);	
					sceneView.smallGrid();
					break;
				case("MEDIUM"):
					sceneView.smallGameLayout(key);	
					sceneView.smallGrid();
					break;
				case("HARD"):
					sceneView.smallGameLayout(key);	
					sceneView.smallGrid();
					break;
				case("MENU"):
					sceneView.smallMenuLayout();
					break;
			}
	
		}
		
	}
	
	public void sceneListener(Scene scene) {
		scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override 
		    public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
		    	sceneWidth = (double) newWidth;
		    	//sceneHeight = scene.getHeight();
		    	sceneView.setSceneWidth((double)newWidth);
		        resizeScene(scene);
		    }
		});
	
		scene.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override 
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
		    	sceneHeight = (double) newHeight;
		        //sceneWidth = scene.getWidth();
		        sceneView.setSceneHeight((double) newHeight);
		        resizeScene(scene);
		    }
		});
	}
}

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SceneManager extends Pane {
	private double sceneWidth;
	private double sceneHeight;
	private Stage stage;
	private HashMap<String, Scene> scenes;
	private SceneView sceneView;
	private MediaPlayer backgroundMP;
	private boolean playMusic;
	private ToggleButton menuMuteButton;
	
	
	public SceneManager(double width, double height, Stage stage) {
		this.sceneWidth = width;
		this.sceneHeight = height;
		this.stage = stage;
		this.scenes = new HashMap<String, Scene>();
		this.sceneView = new SceneView(sceneWidth, sceneHeight);
	}
	
	public Scene createMenuScene() {
		AnchorPane menuLayout = sceneView.createMenu();
		Scene menuScene = new Scene(menuLayout, sceneWidth, sceneHeight);
		VBox buttons = sceneView.getMenuButtons();
		addMenuButtonAction(buttons);
		addMedia();
		backgroundMP.play();
		scenes.put("MENU", menuScene);
		sceneListener(menuScene);
		return menuScene;
	}
	
	public Scene createGameScene(String difficulty) {
		AnchorPane gameLayout = sceneView.createGameLayout(difficulty);
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
		menuMuteButton = sceneView. getMenuMuteButton();
		menuMuteButton.setOnAction(e->{
			if(menuMuteButton.isSelected()) {
				menuMuteButton.setText("UNMUTE");
			}else {
				menuMuteButton.setText("MUTE");
			}
			playOrStopMusic();
			
		});
		for (int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			String name = b.getText();
			switch(name) {
				case("EASY"):
					b.setOnAction(e->changeScene(name, b));
					break;
				case("MEDIUM"):
					b.setOnAction(e->changeScene(name, b));
					break;
				case("HARD"):
					b.setOnAction(e->changeScene(name, b));
					break;
				case("EXIT"):
					b.setOnAction(e->closeProgram(stage));
					break;
            }
			
		}
	}
	
<<<<<<< HEAD
	public void addGameButtonAction(HBox buttons, String sceneName) {
		
=======
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
>>>>>>> 5c6b022e25d4d01c64e658affa7dbeeae414d12d
		for (int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			String name = b.getText();
			switch(name) {
				case("UNDO"):
				
					break;
				case("RESET"):
		
					break;
				case("HOME"):
					b.setOnAction(e->changeScene("MENU", b));
					break;
				case("HINTS"):
					
					break;
				case("NEXT"):
					b.setOnAction(e->changeScene(sceneName, b));
					break;
				case("PREVIOUS"):
					b.setOnAction(e->changeScene(sceneName, b));
					break;
				
            }
			
		}
		
	}
	
	private void changeScene(String name, Button button) {
		System.out.println("scene " + name);
		Generator g = new Generator();
		Scene scene = scenes.get(name);
		if(scene != null) {
			if(name.equals("EASY") || name.equals("MEDIUM") || name.equals("HARD")) {
				sceneView.createPuzzle(name, g);	
			} 
		}else {
			scene = createGameScene(name);
			sceneView.createPuzzle(name, g);

		}
		
		if(name.equals("MENU")) {
			playOrStopMusic();
		}else {
			backgroundMP.stop();
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
	
	public void playOrStopMusic() {
		if(menuMuteButton.isSelected()) {
			backgroundMP.pause();
		}else {
			backgroundMP.play();
		}
	}
	
	public void addMedia() {
		String musicFile = "resource/backgroundMusic.mp3";
		Media sound = new Media(new File(musicFile).toURI().toString());
		backgroundMP = new MediaPlayer(sound);		
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

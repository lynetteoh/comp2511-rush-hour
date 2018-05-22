import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
		menuMuteButton = sceneView.getMenuMuteButton();
		menuMuteButton.setOnAction(e->{
			if(menuMuteButton.isSelected()) {
				menuMuteButton.setText("UNMUTE");
			}else {
				menuMuteButton.setText("MUTE");
			}
			playOrStopMusic();
			
		});
		VBox buttons = sceneView.getMenuButtons();
		addMenuButtonAction(buttons);
		addMedia();
		backgroundMP.play();
		Scene menuScene = new Scene(menuLayout, sceneWidth, sceneHeight);
		scenes.put("MENU", menuScene);
		sceneListener(menuScene);
		return menuScene;
	}
	
	public Scene createGameScene(String difficulty) {
		AnchorPane gameLayout = sceneView.createGameLayout(difficulty);
		ToggleButton mute = (ToggleButton) gameLayout.getChildren().get(4);
		mute.setOnAction(e->{
			if(mute.isSelected()) {
				mute.setText("UNMUTE");
			}else {
				mute.setText("MUTE");
			}
			muteMenu();
			muteGame(difficulty);
		});
		VBox buttons = sceneView.getGameButtons();
		for(int i = 0; i < buttons.getChildren().size(); i++) {
			HBox gameButtons = (HBox) buttons.getChildren().get(i);
			addGameButtonAction(gameButtons, difficulty);	
		}
		Scene gameScene = new Scene(gameLayout, sceneWidth, sceneHeight);
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
	

	public void addGameButtonAction(HBox buttons, String sceneName) {	
		for (int i = 0; i < buttons.getChildren().size(); i++) {
			Button b = (Button) buttons.getChildren().get(i);
			String name = b.getText();
			switch(name) {
				case("UNDO"):
					b.setOnAction(e->undo());
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
		Generator g = new Generator();
		Scene scene = scenes.get(name);
		if(scene == null) {
			scene = createGameScene(name);
		}
		
		if(name.equals("EASY") || name.equals("MEDIUM") || name.equals("HARD")) {
			String buttonText = button.getText();
			sceneView.renderPuzzle(name, g, buttonText);	
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
			backgroundMP.stop();
		}else {
			backgroundMP.play();
		}
	}
	
	public void addMedia() {
		String musicFile = "resource/backgroundMusic.mp3";
		Media sound = new Media(new File(musicFile).toURI().toString());
		backgroundMP = new MediaPlayer(sound);
	}
	
	public void muteGame(String sceneName) {
		HashMap<String, AnchorPane> gameLayout = sceneView.getGameLayout();
		for(Entry<String, AnchorPane> entry: gameLayout.entrySet()) {
			if(entry.getKey().equals(sceneName)) {
				continue;
			}
			AnchorPane layout = entry.getValue();
			
			ToggleButton mute = (ToggleButton) layout.getChildren().get(4);
			if(mute.isSelected()) {
				mute.setSelected(false);
				mute.setText("MUTE");
			}else {
				mute.setSelected(true);
				mute.setText("UNMUTE");
			}
		}
	}
	
	public void muteMenu() {
		if(menuMuteButton.isSelected()) {
			menuMuteButton.setSelected(false);
			menuMuteButton.setText("MUTE");
		}else {
			menuMuteButton.setSelected(true);
			menuMuteButton.setText("UNMUTE");
		}
	}
	
	public void undo() {
		Grid grid = sceneView.getGrid();
		Board puzzle = grid.getBoard();
		Move m = puzzle.undo();
		if(m == null) {
			return;
		}
		Vehicle v = m.getVehicle();
		int id = v.getId();
		int direction = m.getDirection();
		ArrayList<Group> vehicles = grid.getBlockGroups();
		Group vehicle = (Group) vehicles.get(id-1);
		Sprite s = (Sprite) vehicle.getChildren().get(0);
		int gridLength = sceneView.getBlockLength();
		System.out.println("length " + (direction*gridLength));
		if(v.getOrient() == 1) {
			double value = s.getTranslateX() - direction*gridLength;
//			System.out.println(s.getTranslateX());
//			s.setTranslateX(value);
//			System.out.println("previous position: " + s.getTranslateX());
			
		} else {
			double value =  s.getTranslateY() - direction*gridLength; 
//			System.out.println(s.getTranslateY());
//			s.setTranslateY(value);	
//			System.out.println("previous position: " + s.getTranslateY());
		}
		sceneView.updateMove();
//		puzzle.printBoard();
//		System.out.println("");
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

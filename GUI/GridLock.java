import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;

public class GridLock extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Group root = new Group();
			Grid grid = new Grid(6);

			//Rectangle r = grid.createSprite(0,0,100,50);

			root.getChildren().addAll(grid.getGridSquares());
			//root.getChildren().add(r);
			root.getChildren().addAll(grid.createSprite(1,1,98,48));
			root.getChildren().addAll(grid.createSprite(201,51,98,48));
			root.getChildren().addAll(grid.createSprite(51,201,98,48)); // this and above are horizontal blocks
						// //System.out.println("----");
			root.getChildren().addAll(grid.createSprite(201,101,48,98)); // this and below are vertical blocks
			// root.getChildren().addAll(grid.createSprite(0,0,50,100));
			// root.getChildren().addAll(grid.createSprite(50,50,50,100));
			// root.getChildren().addAll(grid.createSprite(100,100,50,100));
			// root.getChildren().addAll(grid.createSprite(150,200,50,100));
			// root.getChildren().addAll(grid.createSprite(250,200,50,100));

			Scene scene = new Scene(root);
			primaryStage.setTitle("GridLock");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}

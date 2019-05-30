package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import player.Player;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		
		Player player = new Player();
		//opens a new JavaFX scene of resolution 800px by 600px containing the master screen
		Scene scene = new Scene(player.getPane(),800, 600);
		
		//sets the primary stage to contain the current scene and gives it a title and shows it
		stage.setScene(scene);
		stage.setTitle("Chuse");
		stage.show();
		player.play();
		
		
	}

}

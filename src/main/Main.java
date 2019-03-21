package main;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import player.Player;

public class Main extends Application {
	
	public static void main(String[] args) {
		// Notify the program of where to find VLCJ
		//NativeLibrary.addSearchPath("libvlc", "C:/Program Files (x86)/VideoLAN/VLC");
		
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Canvas canvas = new Canvas(600,600);
		Player vidplayer  = new Player(canvas);
		Scene scene = new Scene(vidplayer);
		stage.setScene(scene);
		stage.show();
		
		// Stops playing audio when screen is closed.
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
		
	}
	
	

}

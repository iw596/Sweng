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
		Canvas canvas = new Canvas(1000,600);
		Player vidplayer  = new Player(canvas);
		String [] paths = {
		         "C:\\Users\\isaac\\Downloads\\Sweng-master (1)\\Sweng-master\\videoviewer\\1280.mp4",
				"https://www.youtube.com/watch?v=tc8CyxdLGaA",
				"https://www.youtube.com/watch?v=Ta4o3VDi5vs",
		         "C:\\Users\\isaac\\Downloads\\Sweng-master (1)\\Sweng-master\\videoviewer\\1280.mp4",
		         "https://www.youtube.com/watch?v=Ta4o3VDi5vs",
		};
		vidplayer.loadPaths(paths);
		
				
				
		Scene scene = new Scene(vidplayer);
		stage.setScene(scene);
		stage.show();
		
		// Stops playing audio when screen is closed.
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        	
            @Override
            public void handle(WindowEvent event) {
            	System.out.println("Closed request");
                Platform.exit();
                System.exit(0);
            }
        });
		
	}
	
	

}

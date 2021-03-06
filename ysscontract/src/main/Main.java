package main;


import com.sun.jna.NativeLibrary;

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
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		//System.setProperty("prism.order", "sw");
		// TODO Auto-generated method stub
		
		Canvas canvas = new Canvas(750,500);
		
		Player vidplayer  = new Player(canvas,20,50, stage);
		
		/* String [] paths = {
				"a"
		}; */
		
		String [] paths = {
				"1280.mp4",
				"1280.mp4",
				"https://www.youtube.com/watch?v=tc8CyxdLGaA",
				"C:\\Users\\Dan\\eclipse-workspace\\Sweng\\ysscontract\\1280.mp4",
		         "a error",
				"https://www.youtube.com/watch?v=tc8CyxdLGaA",
				"https://www.youtube.com/watch?v=Ta4o3VDi5vs",
		         "C:\\Users\\Dan\\eclipse-workspace\\Sweng\\ysscontract\\1280.mp4",
		         "https://www.youtube.com/watch?v=Ta4o3VDi5vs",
		         "D:\\Users\\Dan\\eclipse-workspace\\Sweng\\ysscontract\\1280.mp4"
		};
		
		vidplayer.loadPaths(paths);
	
		// Open player using given width and height and in specified x and y position
		Scene scene = new Scene(vidplayer,vidplayer.getWindowWidth(),vidplayer.getWindowHeight());
		stage.setX(vidplayer.getXScreenPosition());
		stage.setY(vidplayer.getYScreenPosition());
		//stage.setFullScreen(false);	
		stage.setScene(scene);
		stage.show();
		


		
	}
	
	
	
	

}

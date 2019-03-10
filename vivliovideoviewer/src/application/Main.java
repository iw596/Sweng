package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import videoviewer.VideoViewer;


/**
 * Main is the main class in the videoviewer project. This handles the front-end
 * user interaction with the application.
 * 
 * Date created: 21/02/2019
 * Date last edited: 26/02/2019
 * Last edited by: Isaac Watson and Harry Ogden
 *
 *@author Isaac Watson
 */


public class Main extends Application {
	
	/**
	 * Constructor function for Main class. Lets user pick a file to watch then launches the media player.
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	/**
	 * Method to instantiate the media player and determine what local files should be loaded.
	 * 
	 * @param stage - the stage the loaded media will be shown on.
	 */
	public  void start(Stage stage) throws Exception {
		
		VideoViewer vid_view = new VideoViewer("1280.mp4", stage);
			
		Scene scene = new Scene(vid_view, 720, 535, Color.BLACK);
			 
		stage.setScene(scene); // Setting the scene to stage 
		
		
		vid_view.setFullscreen();
		
		stage.show(); // Showing the stage
	}

}

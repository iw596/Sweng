package application;

import javafx.scene.web.WebView;

import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
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

	// File choose allows user to pick a file to watch
	FileChooser file_chooser;
	
	/**
	 * Constructor function for Main class.Lets user pick a file to watch then launches the media player or youtube
	 * player which allows user to see what is being played.
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	/**
	 * Method to determine what type of media the user wants to play either Yotube
	 * videos or videos located in the local file system.
	 * 
	 * @param stage - the stage the loaded media will be shown on.
	 */
	public  void start(Stage stage) throws Exception {
		
		
		
		// If user wants to watch a youtube video then create a 
		// youtube player and attach the player to the scene.

		VideoViewer vid_view = new VideoViewer("1280.mp4");
			
		Scene scene = new Scene(vid_view, 720, 535, Color.BLACK);
			 
		//vid_view.pressedScreen(scene);
		stage.setScene(scene); // Setting the scene to stage 
		
		vid_view.setFullscreen(stage);
		stage.show(); // Showing the stage
	

			


	}

}

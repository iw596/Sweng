package application;

import javafx.scene.web.WebView;

import java.util.ArrayList;
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
import videoviewer.YoutubeDownloader;
import videoviewer.YoutubePlayer;

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
	int user_choice ;
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
	 * Method to determine what type of media the user wants to play either Youtube
	 * videos or videos located in the local file system.
	 * 
	 * @param stage - the tage the loaded media will be shown on.
	 */
	public  void start(Stage stage) throws Exception {
		
		//YoutubeDownloader.DownloadVideos("https://www.youtube.com/watch?v=Rzd0mLf366I");
		
		// Create scanner and listen for what user wants to do
		Scanner reader = new Scanner(System.in);
		System.out.println("Press 1 to load youtube, press 2 for local");
		user_choice = reader.nextInt();
		reader.close();
		
		// If user wants to watch a youtube video then create a 
		// youtube player and attach the player to the scene.

		if (user_choice == 1){
			YoutubePlayer yt_player = new YoutubePlayer();
			yt_player.loadPage();
			//VBox root = new VBox();
			//root.getChildren().addAll(yt_player.getWebView());
			
			Scene scene = new Scene(yt_player,500,200);
			stage.setScene(scene);
			
			stage.show();
			
		}
		
		// If user wants to watch a local video then a media player will
		// be created and attached to the scene
		else if (user_choice == 2){
			ArrayList Videos = new ArrayList();
			Videos.add("small.mp4");
			Videos.add("1280.mp4");
			
			VideoViewer vid_view = new VideoViewer(Videos);
			
			Scene scene = new Scene(vid_view, 720, 535, Color.BLACK);
			vid_view.setSize(); // Call method to enable vid_view to be resizable
			//vid_view.pressedScreen(scene);
			stage.setScene(scene); // Setting the scene to stage 
			stage.show(); // Showing the stage 	
		}

	}

}

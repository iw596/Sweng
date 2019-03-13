package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * Main is the main class in the YoutubeVideoViewer project. This handles the front-end
 * interaction with the application.
 * 
 * Date created: 13/03/2019
 * Date last edited: 13/03/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden & Isaac Watson
 *
 */
public class Main extends Application {
	@Override
	/**
	 * Method to instantiate the youtube player window.
	 * 
	 * @param primaryStage - the stage the loaded video will be shown on.
	 */
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("YoutubePlayer.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor function for Main class. Plays YouTube videos specified in url.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

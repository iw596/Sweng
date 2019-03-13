package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * Main is the main class in the LocalVideoViewer project. This handles the front-end
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
	
	/**
	 * Method to instantiate the media player window.
	 * 
	 * @param primaryStage - the stage the loaded media will be shown on.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("VideoPlayer.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor function for Main class. Plays local mp4 video specified in file path.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * Main is the main class in the ResultsScreen project. This handles the front-end
 * interaction with the application.
 * 
 * Date created: 14/03/2019
 * Date last edited: 14/03/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden & Aeri Olsson
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("ResultsScreen.fxml"));
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor function for Main class. Displays application results screen.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

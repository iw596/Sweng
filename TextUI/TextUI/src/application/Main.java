package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 * Main is the main class in the TextUI project. This handles the basic required UI components
 * for text list creation.
 * 
 * Date created: 05/03/2019
 * Date last edited: 08/03/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden & Luke Fisher
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Create a pane to hold the screen views and load initially with text list creation screen
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,640,480);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main Program Function
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

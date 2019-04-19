package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * Main is the main class in the SearchScreen project. This handles the basic required UI components
 * for searching.
 * 
 * Date created: 17/04/2019
 * Date last edited: 19/04/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden & Luke Fisher
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
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

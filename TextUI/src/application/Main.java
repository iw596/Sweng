package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root01 = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene01 = new Scene(root01,640,480);
			AnchorPane root02 = (AnchorPane)FXMLLoader.load(getClass().getResource("ManualTextScreen.fxml"));
			Scene scene02 = new Scene(root02,640,480);
			scene01.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene02.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene01);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

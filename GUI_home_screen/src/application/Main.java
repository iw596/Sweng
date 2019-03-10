package application;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage){
		try {
			// build main GUI home screen from fxml file that was build in scene builder
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			// set to a scene
			Scene scene = new Scene(root,800,500);
			// apply css file (style)
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// display GUI home page
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// launch app
		launch(args);
	}
}

package application;
	
import backEnd.BackEndContainer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sidebarContainerGUI.MasterScreenController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BackEndContainer back_end = new BackEndContainer();
			
			FXMLLoader loader = new FXMLLoader(sidebarContainerGUI.MasterScreenController.class.getResource("MasterScreen.fxml"));
			
			MasterScreenController controller = new MasterScreenController(back_end);
			
			loader.setController(controller);
			
			BorderPane root = loader.load();

			Scene scene = new Scene(root,800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			root.requestFocus();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					Platform.exit();
					System.exit(0);
					
				}
				
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

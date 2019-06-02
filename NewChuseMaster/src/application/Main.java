package application;
	
import com.sun.jna.NativeLibrary;

import backEnd.BackEndContainer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sidebarContainerGUI.MasterScreenController;
import uk.co.caprica.vlcj.binding.RuntimeUtil;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Class for the main application. This is where the application is launched from. This extends the Application
 * superclass as is necessary for a JavaFX application. All of the code is launched from within the overridden
 * start() method.
 * 
 * Date created: 26/03/2019
 * Date last edited: 26/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files (x86)/VideoLAN/VLC");
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files/VideoLAN/VLC");

			//creates a new back end container
			BackEndContainer back_end = new BackEndContainer();
			
			//loads the master screen (sidebar with homescreen within it)
			FXMLLoader loader = new FXMLLoader(sidebarContainerGUI.MasterScreenController.class.getResource("MasterScreen.fxml"));
			
			//sets the controller of the master screen and passes it the back end container
			MasterScreenController controller = new MasterScreenController(back_end);
			
			//sets the controller of the FXML file to the one just instantiated
			loader.setController(controller);
			
			//loads the FXML file
			BorderPane root = loader.load();

			//opens a new JavaFX scene of resolution 800px by 600px containing the master screen
			Scene scene = new Scene(root,1000, 750);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//sets the primary stage to contain the current scene and gives it a title and shows it
			primaryStage.setScene(scene);
			primaryStage.setTitle("Chuse");
			primaryStage.show();
			
			//focuses on the master screen
			root.requestFocus();
			
			//sets up the close functionality
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					//exits the window and ends all processes
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

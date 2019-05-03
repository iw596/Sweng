package homeScreenGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import cloudInteraction.DownloadingScreenController;
import cloudInteraction.RunnableUserListFetcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import multithreading.NotifyingThread;
import multithreading.ThreadTerminationListener;

/**
 * Controller class for the Cloud File List browser which is displayed in the Online Lists tab.
 * Adds a set of buttons for all the lists stored on the currently logged in user's Cloud storage.
 * 
 * Date created: 24/04/2019
 * Date last edited: 28/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class CloudFileListController implements Initializable, ThreadTerminationListener {

    @FXML
    private ScrollPane cloud_scroll_pane;

    @FXML
    private VBox files_pane;
	
	private ArrayList<String> cloud_paths;
	
	private BackEndContainer back_end;
	
	private HomeScreenController home_controller;
	
	/**
	 * Constructor for the Cloud File List controller. Passes in reference to the back end container as well as
	 * reference to the home screen controller. Starts a new thread to fetch the lists from within.
	 * 
	 * @param back_end			reference to the back end
	 * @param home_controller	reference to the parent screen's controller (home screen controller)
	 */
	public CloudFileListController(BackEndContainer back_end, HomeScreenController home_controller) {
		this.back_end = back_end;
		this.home_controller = home_controller;
		
		//starts the user list fetcher in a new thread
		RunnableUserListFetcher list_fetcher = new RunnableUserListFetcher(back_end,
				back_end.getLocalAccount().getUsername());
		list_fetcher.addTerminationListener(this);
		Thread thread = new Thread(list_fetcher);
		thread.start();

	}
	
	@Override
	/**
	 * Method called when the FXMl is loaded. Sets the scroll pane to fit the size of its content.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		
		cloud_scroll_pane.setFitToHeight(true);
		cloud_scroll_pane.setFitToWidth(true);
		
	}
	
	/**
	 * Method to create the buttons for the cloud files.
	 */
	private void createButtons() {
		
		if(cloud_paths == null) {
			return;
		}
		
		ArrayList<JFXButton> file_buttons = new ArrayList<JFXButton>();
		
		//loops through every path in the array list of paths
		for(String path : cloud_paths) {
			String file_name = path.split("/")[2];
			//if the path contains the string "/public/" then adds a comment stating it is stored publicly
			if(path.contains("/public/")) {
				file_name += " - (Stored publicly)";
			}
			
    		//add a button with the text being the name of the file
    		file_buttons.add(new JFXButton(file_name));
    		file_buttons.get(file_buttons.size() - 1).setMnemonicParsing(false);
    		//give the button an id
    		file_buttons.get(file_buttons.size() - 1).setId("button" + (file_buttons.size() - 1));
    		//set some padding for the button
    		file_buttons.get(file_buttons.size() - 1).setPadding(new Insets(10, 10, 10, 15));
    		
    		file_buttons.get(file_buttons.size() - 1).setAlignment(Pos.CENTER_LEFT);
    		
    		file_buttons.get(file_buttons.size() - 1).setLayoutX(files_pane.getWidth());
    		
    		//give the button an action listener
    		//when a button is pressed, it opens up one of the saved files ready for comparison
    		file_buttons.get(file_buttons.size() - 1).setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					
					try {
						//load the comparison screen and start the tournament comparison algorithm
						FXMLLoader loader = new FXMLLoader(cloudInteraction.DownloadingScreenController.class.getResource("DownloadingScreen.fxml"));
						DownloadingScreenController controller = new DownloadingScreenController(back_end, path);
				    	loader.setController(controller);
						BorderPane new_pane = loader.load();
						home_controller.showInSelf(new_pane);
						System.gc();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
    			
    		});
			
		}
		
		files_pane.getChildren().addAll(file_buttons);
		
	}

	@Override
	/**
	 * Method called when the user list fetcher thread terminates. Fetches the set of locally stored Cloud paths to
	 * user lists, then uses this set of lists to create buttons.
	 */
	public void notifyOfThreadTermination(NotifyingThread thread) {
		cloud_paths = back_end.getLoggedInUsersLists();
		
		//waits for the program to be back on the main JavaFX application thread
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				createButtons();
			}
			
		});
		
	}

}

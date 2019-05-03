package cloudInteraction;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import backEnd.BackEndContainer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import multithreading.NotifyingThread;
import multithreading.ThreadTerminationListener;
import resultsScreenGUI.ResultsScreenController;

/**
 * Controller class for the uploading splash screen. Implements the Cloud file uploader
 * within its own thread to keep the UI responsive.
 * 
 * Date created: 24/04/2019
 * Date last edited: 28/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class UploadingScreenController implements ThreadTerminationListener, Initializable {

    @FXML
    private BorderPane root;
    
    private BackEndContainer back_end;
    
    private String path;
    
    private Thread thread;
    
    private String username;
    
    private Boolean make_public;
    
    /**
     * Constructor for the uploading screen controller. Passes in reference to the back end, as well as 
     * the path to a local file and whether or not to make the list public.
     * 
     * @param back_end
     * @param local_path
     * @param make_public
     */
    public UploadingScreenController(BackEndContainer back_end, String local_path, Boolean make_public) {
    	this.back_end = back_end;
    	this.path = local_path;
    	this.make_public = make_public;
    }

    /**
     * Method to load the results screen.
     * 
     * @throws IOException
     */
    private void loadNextScreen() throws IOException {

    	//back_end.uploadList();
    	
    	this.username = back_end.getListOwner();
    	
    	if(this.username == null) {
    		this.username = back_end.getLocalAccount().getUsername();
    	}
    	
		FXMLLoader loader = new FXMLLoader(resultsScreenGUI.ResultsScreenController.class.getResource("ResultsScreen.fxml"));
    	ResultsScreenController controller = new ResultsScreenController(back_end);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	showInSelf(new_pane);
    	
    }
    
    /**
     * Method to load another pane within the main window.
     * @param new_pane
     */
	private void showInSelf(Pane new_pane) {
    	new_pane.prefWidthProperty().bind(root.widthProperty());
    	new_pane.prefHeightProperty().bind(root.heightProperty());
    	new_pane.minWidthProperty().bind(root.minWidthProperty());
    	new_pane.minHeightProperty().bind(root.minHeightProperty());
    	new_pane.maxWidthProperty().bind(root.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(root.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	root.getChildren().removeAll();
    	
    	root.setCenter(new_pane);
    	
    	root.requestFocus();
    	
    	System.gc();
	}

	@Override
	/**
	 * Method called when the FXML is loaded. Starts the list upload within its own thread.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		RunnableUploader uploader = new RunnableUploader(back_end, path, make_public);
		uploader.addTerminationListener(this);
		thread = new Thread(uploader);
		thread.start();
		
	}

	@Override
	/**
	 * Method called when the uploading list thread terminates. Loads the results screen.
	 */
	public void notifyOfThreadTermination(NotifyingThread thread) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					loadNextScreen();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	

}

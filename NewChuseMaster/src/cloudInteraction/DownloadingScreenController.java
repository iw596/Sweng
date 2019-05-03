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
import previewListScreenGUI.PreviewListController;

/**
 * Controller class for the downloading splash screen. Implements the Cloud file downloader
 * within its own thread to keep the UI responsive.
 * 
 * Date created: 24/04/2019
 * Date last edited: 28/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class DownloadingScreenController implements ThreadTerminationListener, Initializable {

    @FXML
    private BorderPane root;
    
    private BackEndContainer back_end;
    
    private String path;
    
    private Thread thread;
    
    private String username;
    
    /**
     * Constructor for the downloading screen controller. Passes in reference to the back
     * end container and the path to the directory to be donwloaded from the Cloud.
     * 
     * @param back_end		reference to the back end container
     * @param cloud_path	the path to the cloud directory
     */
    public DownloadingScreenController(BackEndContainer back_end, String cloud_path) {
    	this.back_end = back_end;
    	this.path = cloud_path;
    }

    /**
     * Method to load the preview list screen.
     * 
     * @throws IOException
     */
    private void loadNextScreen() throws IOException {

    	this.username = back_end.getListOwner();
    	
    	//if there is not a list owner stored in the back end, set username to 
    	//the local account's username
    	if(this.username == null) {
    		this.username = back_end.getLocalAccount().getUsername();
    	}
    	
		//load the comparison screen and start the tournament comparison algorithm
    	FXMLLoader loader = new FXMLLoader(previewListScreenGUI.PreviewListController
    			.class.getResource("PreviewList.fxml"));
    	PreviewListController controller = new PreviewListController(back_end, this.username);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	showInSelf(new_pane);
    	
    }
    
    /**
     * Method to show a pane within the current window.
     * 
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
	 * Method called when the FXML file is loaded. Starts the download process within its
	 * own thread.
	 */
	public void initialize(URL location, ResourceBundle resources) {

		RunnableDownloader downloader = new RunnableDownloader(back_end, path);
		downloader.addTerminationListener(this);
		thread = new Thread(downloader);
		thread.start();
		
	}

	@Override
	/**
	 * Method called when the download thread finishes.
	 */
	public void notifyOfThreadTermination(NotifyingThread thread) {
		
		//waits for the program to be back on the main JavaFX application thread
		Platform.runLater(new Runnable() {
			@Override
			//loads the preview list screen
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

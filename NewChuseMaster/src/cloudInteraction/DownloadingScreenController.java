package cloudInteraction;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import backEnd.BackEndContainer;
import comparisonScreenGUI.ComparisonScreenController;
import homeScreenGUI.HomeScreenController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import multithreading.NotifyingThread;
import multithreading.ThreadTerminationListener;
import previewListScreenGUI.PreviewListController;

public class DownloadingScreenController implements ThreadTerminationListener, Initializable {

    @FXML
    private BorderPane root;
    
    private BackEndContainer back_end;
    
    private String path;
    
    private Thread thread;
    
    private String username;
    
    public DownloadingScreenController(BackEndContainer back_end, String cloud_path) {
    	this.back_end = back_end;
    	this.path = cloud_path;
    }

    private void loadNextScreen() throws IOException {

    	this.username = back_end.getListOwner();
    	
    	if(this.username == null) {
    		this.username = back_end.getLocalAccount().getUsername();
    	}
    	
		//load the comparison screen and start the tournament comparison algorithm
    	FXMLLoader loader = new FXMLLoader(previewListScreenGUI.PreviewListController.class.getResource("PreviewList.fxml"));
    	PreviewListController controller = new PreviewListController(back_end, this.username);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	showInSelf(new_pane);
    	
    }
    
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
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		RunnableDownloader downloader = new RunnableDownloader(back_end, path);
		downloader.addTerminationListener(this);
		thread = new Thread(downloader);
		thread.start();
		
	}

	@Override
	public void notifyOfThreadTermination(NotifyingThread thread) {
		// TODO Auto-generated method stub
		
		System.out.println("Finished downloading.");
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					loadNextScreen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
	
	

}

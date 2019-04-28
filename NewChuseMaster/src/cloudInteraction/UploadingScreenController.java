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

public class UploadingScreenController implements ThreadTerminationListener, Initializable {

    @FXML
    private BorderPane root;
    
    private BackEndContainer back_end;
    
    private String path;
    
    private Thread thread;
    
    private String username;
    
    private Boolean makePublic;
    
    public UploadingScreenController(BackEndContainer back_end, String local_path, Boolean makePublic) {
    	this.back_end = back_end;
    	this.path = local_path;
    	this.makePublic = makePublic;
    }

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
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		System.out.println(path);
		
		RunnableUploader uploader = new RunnableUploader(back_end, path, makePublic);
		uploader.addTerminationListener(this);
		thread = new Thread(uploader);
		thread.start();
		
	}

	@Override
	public void notifyOfThreadTermination(NotifyingThread thread) {
		// TODO Auto-generated method stub
		
		System.out.println("Finished uploading.");
		
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

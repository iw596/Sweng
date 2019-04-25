package intermediateScreensGUI;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import comparisonScreenGUI.ComparisonScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class for the home intermediate video screen controller. This class handles all button listeners and interactivity
 * and holds a reference to the back-end code of the program for communication.
 * 
 * Date created: 09/03/2019
 * Date last edited: 01/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Jack Small and Aeri Olsson
 *
 */
public class InterVideoController {

    @FXML
    private BorderPane root;
	
    @FXML
    private JFXButton IFYvideo;

    @FXML
    private JFXButton IFFSVideo;

    private BackEndContainer back_end;
    
    public InterVideoController(BackEndContainer back_end) {
    	this.back_end = back_end;
    }
    
    @FXML
    /**
     * Method to open the video file chooser when the button is clicked.
     * @param event
     */
    void importFromFilesVideo(ActionEvent event) throws IOException {
    	
    	System.out.println("Load video files.");
    	back_end.loadVideoFiles((Stage) root.getScene().getWindow());
    	//  back_end.loa
    	
    	if(back_end.getCurrentListSize() < 2) {
    		System.out.println("Nothing to compare - only one item in list.");
    	} else {
        	FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
        	
        	ComparisonScreenController controller = new ComparisonScreenController(back_end);
        	
        	loader.setController(controller);
        	
        	BorderPane new_pane = loader.load();
        	
        	showInSelf(new_pane);
    	}	
    }

    @FXML
    /**
     * Method to open the YouTube import dialogue when the button is clicked.
     * @param event
     */
    void importFromYouTubeVideo(ActionEvent event) {
    	System.out.println("Import from YouTube");

		try {
	    	Stage popup = new Stage();
	    	popup.setTitle("YouTube Playlist URL Loader");
	    	
			FXMLLoader loader = new FXMLLoader(intermediateScreensGUI.InterVideoController.class.getResource("YouTubePopup.fxml"));
			
			YouTubePopupController controller = new YouTubePopupController(back_end, this);
			
			loader.setController(controller);
			
			BorderPane new_root = loader.load();
			Scene scene = new Scene(new_root, 480, 125);
			popup.setScene(scene);
			popup.show();
			new_root.requestFocus();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		root.requestFocus();
    	
    	
    }
    
    /**
     * Method to start the tournament comparison for a list of YouTube items.
     */
    public void startYouTubeComparison() {
    	
    	FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
    	
    	ComparisonScreenController controller = new ComparisonScreenController(back_end);
    	
    	loader.setController(controller);
    	
    	BorderPane new_pane;
		try {
			new_pane = loader.load();
	    	showInSelf(new_pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    /**
     * Method to display another .fxml file within the current screen.
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
    	root.setCenter(new_pane);
    	root.requestFocus();
	
    	System.gc();
    	
    }

}
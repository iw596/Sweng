package intermediateScreensGUI;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import comparisonScreenGUI.ComparisonScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Class for the intermediate audio screen controller. This class handles all button listeners and interactivity
 * and holds a reference to the back-end code of the program for communication.
 * 
 * Date created: 09/03/2019
 * Date last edited: 01/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Jack Small and Aeri Olsson
 *
 */
public class InterAudioController {

    @FXML
    private BorderPane root;
	
    @FXML
    private JFXButton IFSAudio;

    @FXML
    private JFXButton IFFAudio;

    private BackEndContainer back_end;
    
    /**
     * Constructor for the intermediate audio screen controller. 
     * @param back_end
     */
    public InterAudioController(BackEndContainer back_end) {
    	this.back_end = back_end;
    }
    
    @FXML
    /**
     * Method to open the audio file chooser.
     * @param event
     */
    void importFromFilesAudio(ActionEvent event) throws IOException {
    	
    	back_end.loadAudioFiles((Stage) root.getScene().getWindow());
    	
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
     * Method to open the Spotify import window.
     * @param event
     */
    void importFromSpotifyAudio(ActionEvent event) {
    	System.out.println("Import from spotify");
    	WebView login_viewer = new WebView();
    	WebEngine login_engine = new WebEngine();
    	
    	
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
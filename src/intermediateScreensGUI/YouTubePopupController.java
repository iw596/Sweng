package intermediateScreensGUI;

import java.io.IOException;

import com.jfoenix.controls.JFXTextField;

import backEnd.BackEndContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Class for the YouTube URL input popup controller. This class handles all button listeners and interactivity
 * and holds a reference to the back-end code of the program for communication.
 * 
 * Date created: 14/03/2019
 * Date last edited: 14/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class YouTubePopupController {

	BackEndContainer back_end;

    @FXML
    private BorderPane root;
	
    @FXML
    private JFXTextField url_text_field;
    
    InterVideoController parent_controller;
    
    /**
     * Constructor for the YouTube URL input popup controller.
     * @param back_end
     */
    public YouTubePopupController(BackEndContainer back_end, InterVideoController controller) {
    	this.back_end = back_end;
    	this.parent_controller = controller;
    }

    @FXML
    /**
     * Method to submit the URL that was input by the user.
     * @param event
     */
    void submitURL(ActionEvent event) {
    	
    	try {
    		//creates a youtube playlist
			back_end.createYouTubeList(url_text_field.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	//gets the stage the popup is open in
    	Stage stage = (Stage)root.getScene().getWindow();
    	
    	//closes the window
    	stage.close();
    	
    	parent_controller.startYouTubeComparison();
    	
    }
}

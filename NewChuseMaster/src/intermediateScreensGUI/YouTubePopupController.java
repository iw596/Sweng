package intermediateScreensGUI;

import java.io.IOException;


import com.jfoenix.controls.JFXTextField;

import apiHandlers.YouTubeAPIHandler;
import backEnd.BackEndContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import listDataStructure.ChuseList;

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
    
    @FXML
    private Text error;
    
    InterVideoController parent_controller;
    private ChuseList playlist_list;
    
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
    		playlist_list = YouTubeAPIHandler.getPlaylistData(url_text_field.getText());
    		if (playlist_list == null){
    			if (this.error.isVisible() == false){
    				this.error.setVisible(true);
    				System.gc();
    			}

    		}
    		else{
    			back_end.createYouTubeList(YouTubeAPIHandler.getPlaylistData(url_text_field.getText()));    	//gets the stage the popup is open in
    			if (this.error.isVisible() == true){
    				this.error.setVisible(false);
    			}
    			
    			Stage stage = (Stage)root.getScene().getWindow();
    	    	
    	    	//closes the window
    	    	stage.close();
    	    	
    	    	parent_controller.startYouTubeComparison();
    			
    		}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}


    	
    }
}

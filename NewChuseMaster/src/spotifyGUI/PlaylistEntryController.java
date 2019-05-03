package spotifyGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.wrapper.spotify.SpotifyApi;

import backEnd.BackEndContainer;
import comparisonScreenGUI.ComparisonScreenController;
import intermediateScreensGUI.InterVideoController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import listDataStructure.BasicItem;
import spotifydata.GetPlaylistsTracks;

/** This controller handles the spotify playlist entry screen where the users are able
 * to enter a spotify playlist URL or ID which this class then uses to call methods
 * to gather information about the playlist before passing this information onto the 
 * comparison screen.
 * 
 * Date created: 27/04/2019
 * Date last edited 29/04/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson and Harry Ogden
 *
 */
public class PlaylistEntryController implements Initializable {

    @FXML private BorderPane root_pane02;
    @FXML private BorderPane root;
    @FXML private JFXTextField playlist_field;
    @FXML private Text error_message;
    
    private SpotifyApi spotify_api;
    ArrayList<BasicItem> spotify_items;
	String playlist_id;
    private BackEndContainer back_end;

    
	@Override
	/**
	 * Method called when the FXML is loaded. Sets the error message to be invisible.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// Run once filename has been passed to controller 
		error_message.setVisible(false);
		
	}
	/** Listener for the text entry field. This listens for the user entering a
	 *  URL and then calls methods which requests data about the spotify playlist
	 */
	public void playlist_entered(KeyEvent event) throws IOException{
		if (event.getCode().toString() == "ENTER"){
    		String playlist_id = playlist_field.getText();
			// Extract playlist id from URL
			playlist_id = playlist_field.getText().substring(playlist_field.getText().lastIndexOf("/") + 1);
			
    		GetPlaylistsTracks get_tracks = new GetPlaylistsTracks(spotify_api,playlist_id);
    		spotify_items = GetPlaylistsTracks.getPlaylistsTracks_Sync();

    		// If entered playlist was valid then progress to comparison screen
    		if (spotify_items != null){
        		back_end.loadSpotifyItems(spotify_items);
    	      	if(back_end.getCurrentListSize() < 2) {
    	      		//TODO ADD MESSAGE ABOUT ONLY 1 ITEM
            	} else {
            		// Once we have the data then load the comparison screen
    	        	FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
    	        	ComparisonScreenController controller = new ComparisonScreenController(back_end);
    	        	loader.setController(controller);
    	        	BorderPane new_pane;
    	        	new_pane = loader.load();
    	        	showInSelf(new_pane);
    	        }
    			
    		}
    		// If not valid then print error message
    		else{
    			error_message.setVisible(true);
    			error_message.setText("Please enter a valid Spotify Playlist URL");
    			error_message.setFont(Font.font("verdana",FontPosture.REGULAR, 15));
    		
    		}

    	}
	}
	/** 
	 * Setter for  the Spotify API.
	 * 
	 * @param api
	 */
	public void setAPI(SpotifyApi api){
		spotify_api = api;
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
	
    }
    
    /**
     * Method to pass reference to the back end into the controller.
     * @param back_end	
     */
    public void setBackEnd(BackEndContainer back_end) {
    	this.back_end = back_end;
    }
}

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
import javafx.stage.Stage;
import listDataStructure.BasicItem;
import spotifydata.GetPlaylistsTracks;

public class PlaylistEntryController implements Initializable {

    @FXML private BorderPane root_pane02;
    @FXML private BorderPane root;
    @FXML private JFXTextField playlist_field;
    
    private SpotifyApi spotifyApi;
    ArrayList<BasicItem> spotify_items;
	String playlist_id;
    private BackEndContainer back_end;

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Run once filename has been passed to controller 
		
	}
	
	public void playlist_entered(KeyEvent event) throws IOException{
		if (event.getCode().toString() == "ENTER"){
    		String playlist_id = playlist_field.getText();
			System.out.println(playlist_field.getText());
			// Extract playlist id from URL
			playlist_id = playlist_field.getText().substring(playlist_field.getText().lastIndexOf("/") + 1);
			System.out.println(playlist_id);
			
    		GetPlaylistsTracks get_tracks = new GetPlaylistsTracks(spotifyApi,playlist_id);
    		spotify_items = get_tracks.getPlaylistsTracks_Sync();
    		
    		back_end.loadSpotifyItems((Stage) root.getScene().getWindow(),spotify_items);
        	
        	if(back_end.getCurrentListSize() < 2) {
        		System.out.println("Nothing to compare - only one item in list.");
        	} else {
    		
	        	FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
	        	ComparisonScreenController controller = new ComparisonScreenController(back_end);
	        	loader.setController(controller);
	        	BorderPane new_pane;
	        	new_pane = loader.load();
	        	showInSelf(new_pane);
	        	System.out.println("Loaded");
	        }
			
    	}
	}
	
	public void setAPI(SpotifyApi Api){
		spotifyApi = Api;
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
    
    public void setBackEnd(BackEndContainer back_end) {
    	this.back_end = back_end;
    }
}

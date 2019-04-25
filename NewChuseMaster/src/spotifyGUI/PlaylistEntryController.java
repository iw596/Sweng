package spotifyGUI;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.wrapper.spotify.SpotifyApi;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import spotifydata.GetPlaylistsTracks;

public class PlaylistEntryController implements Initializable {

    @FXML private BorderPane root_pane02;
    @FXML private JFXTextField playlist_field;
    
	private static SpotifyApi spotifyApi;
	private GetPlaylistsTracks get_tracks;
	String playlist_id;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Run once filename has been passed to controller 
		
	}
	
	public void playlist_entered(KeyEvent event){
		if (event.getCode().toString() == "ENTER"){
    		playlist_id = playlist_field.getText();
    		System.out.println(playlist_id);
    		
    		get_tracks = new GetPlaylistsTracks(spotifyApi,playlist_id);
			get_tracks.getPlaylistsTracks_Sync();
			
    	}
	}
	
	public void setAPI(SpotifyApi Api){
		spotifyApi = Api;
	}
}

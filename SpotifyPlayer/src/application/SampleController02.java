package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.wrapper.spotify.SpotifyApi;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import listDataStructure.BasicItem;
import spotifydata.GetPlaylistsTracks;
import spotifyPlayback.GetAvailableDevices;


public class SampleController02 implements Initializable {

    @FXML private BorderPane root_pane02;
    @FXML private JFXTextField playlist_field;
    
    private SpotifyApi spotifyApi;
    private GetAvailableDevices get_devices;
    ArrayList<BasicItem> spotify_items;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			
			
		});
		
	}
	
	public void playlist_entered(KeyEvent event){
		if (event.getCode().toString() == "ENTER"){
    		String playlist_id = playlist_field.getText();
			System.out.println(playlist_field.getText());
			// Extract playlist id from URL
			playlist_id = playlist_field.getText().substring(playlist_field.getText().lastIndexOf("/") + 1);
			System.out.println(playlist_id);
			
    		GetPlaylistsTracks get_tracks = new GetPlaylistsTracks(spotifyApi,playlist_id);
    		spotify_items = get_tracks.getPlaylistsTracks_Sync();
    		
    		// Load second screen with list details
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpotifyPlayerPage.fxml"));
			try {
				BorderPane pane01 = fxmlLoader.load();
				SampleController03 controller = fxmlLoader.getController();
				controller.setAPI(spotifyApi);
				controller.setItems(spotify_items);
				root_pane02.getChildren().setAll(pane01);
				

				} catch (Exception e) {
					e.printStackTrace();
			}
    	}
	}

	public void setAPI(SpotifyApi spotifyApi) {
		// TODO Auto-generated method stub
		this.spotifyApi = spotifyApi;
		
		
	}
}

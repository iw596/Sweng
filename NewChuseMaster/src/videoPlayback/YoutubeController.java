package videoPlayback;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Controller for the YoutubePlayer.fxml file. Initialises WebView for YouTube playback.
 * 
 * Date created: 13/03/2019
 * Date last edited: 13/03/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden
 *
 */
public class YoutubeController implements Initializable {
	
	// Component Id's from fxml file
    @FXML
    private BorderPane root;
	@FXML
	WebView webview;
	
	// WebEngine to load video and url for specified playback video.
	private WebEngine web_engine;
	private String VideoUrl;
	
	/**
	 * Constructor function for the YouTube viewer's controller. Takes the video id as argument and
	 * turns that into an embedded video URL for the web view.
	 * @param video_id
	 */
	public YoutubeController(String video_id) {
		VideoUrl = "https://www.youtube.com/embed/" + video_id;
	}
	
	/**
	 * Method for initialising YoutubeController. Instantiates WebView and loads video for display.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		web_engine = webview.getEngine();		
		web_engine.load(VideoUrl);
	}
	
}

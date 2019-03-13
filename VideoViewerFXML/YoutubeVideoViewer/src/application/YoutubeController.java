package application;

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
	BorderPane root_pane;
	@FXML
	WebView webview;
	
	// WebEngine to load video and url for specified playback video.
	private WebEngine web_engine;
	private String VideoUrl = "https://www.youtube.com/embed/RcP5jDFd0aY";
	
	/**
	 * Method for initialising YoutubeController. Instantiates WebView and loads video for display.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		web_engine = webview.getEngine();		
		web_engine.load(VideoUrl);
	}
	
}

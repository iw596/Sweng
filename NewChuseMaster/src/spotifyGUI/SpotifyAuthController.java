package spotifyGUI;

import java.net.URL;
import java.util.ResourceBundle;

import com.wrapper.spotify.SpotifyApi;

import backEnd.BackEndContainer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import spotifyauth.AuthorisationCode;
import spotifyauth.AuthorisationURI;

public class SpotifyAuthController implements Initializable {

    @FXML private BorderPane root_pane01;	
    @FXML private WebView web_view;
	String redirectURI = "https://localhost:8888";
	String token = "a";
	
	String username;
	String password;
	
	BorderPane pane01;
	private BackEndContainer back_end;
	
	private static SpotifyApi spotifyApi;
	
	@Override
	public void initialize(URL url01, ResourceBundle resources) {
		Platform.runLater(() -> {
    	AuthorisationURI auth_uri = new AuthorisationURI();
		WebEngine web_engine = web_view.getEngine();
		web_engine.load(AuthorisationURI.authorizationCodeUri_Sync().toString());
		web_engine.getLoadWorker().stateProperty().addListener((ov, o, n) -> {
			
		//System.out.println("Change");
		if (web_engine.getLocation().contains(redirectURI)) {
				System.out.println("Video has ended");
				String location = web_engine.getLocation();
				System.out.println("The token is: " + location.substring(location.indexOf("=") + 1,location.indexOf("&")));
				token = location.substring(location.indexOf("=") + 1,location.indexOf("&"));
				// Have token so can now compliw596ete authorisation
				AuthorisationCode authCode = new AuthorisationCode(token, auth_uri.getSpotifyAPI());
				AuthorisationCode.authorizationCode_Sync();
				//GetPlaylistsTracks getTracks = new GetPlaylistsTracks(authCode.getSpotifyApi(),"3AGOiaoRXMSjswCLtuNqv5");
				//getTracks.getPlaylistsTracks_Sync();
				//GetPlaylistExample playlist = new GetPlaylistExample(authCode.getSpotifyApi());
				//playlist.getPlaylist_Async();
				//GetPlaylistsTracksExample test = new GetPlaylistsTracksExample(authCode.getSpotifyApi(),"37i9dQZF1DX5hHfOi73rY3");
				//test.getPlaylistsTracks_Sync();
				
				spotifyApi = authCode.getSpotifyApi();
				
				// Load second screen with list details
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlaylistEntryPage.fxml"));
				try {
					pane01 = fxmlLoader.load();
					PlaylistEntryController controller = fxmlLoader.getController();
					// Pass clicked list filename to second sample controller
					controller.setAPI(spotifyApi);
					controller.setBackEnd(back_end);
					showInSelf(pane01);
					
					/*FXMLLoader loader = new FXMLLoader(intermediateScreensGUI.InterVideoController.class.getResource("IntermediateVideoPage.fxml"));
		        	InterVideoController controller = new InterVideoController(back_end);
		        	loader.setController(controller);
		        	BorderPane pane01;
		        	pane01 = loader.load();
		        	showInSelf(pane01);*/
					} catch (Exception e) {
						e.printStackTrace();
				}
			}
		
		});
		
	    });
		

		
		
		
	}
	
	public void setBackEnd(BackEndContainer back_end) {
		   this.back_end = back_end;
	}
	
    private void showInSelf(Pane new_pane) {
    	
    	new_pane.prefWidthProperty().bind(root_pane01.widthProperty());
    	new_pane.prefHeightProperty().bind(root_pane01.heightProperty());
    	new_pane.minWidthProperty().bind(root_pane01.minWidthProperty());
    	new_pane.minHeightProperty().bind(root_pane01.minHeightProperty());
    	new_pane.maxWidthProperty().bind(root_pane01.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(root_pane01.maxHeightProperty());
    	new_pane.setManaged(true);
    	root_pane01.setCenter(new_pane);
    	root_pane01.requestFocus();
	
    }
	
}

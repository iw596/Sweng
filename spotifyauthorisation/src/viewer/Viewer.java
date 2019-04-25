package viewer;

import java.net.URI;

import auth.AuthURI;
import auth.AuthorizationCode;
import data.GetPlaylistExample;
import data.GetPlaylistsTracks;
import data.GetPlaylistsTracksExample;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Viewer extends BorderPane {
	
	WebView web_view;
	WebEngine web_engine;
	String redirectURI = "https://localhost:8888";
	//http://localhost:8888
	private Pane pane;
	// Link to spotify login page
	private URI spotify_uri;
	private Stage stage;
	public String token = "a";
	private AuthURI auth_uri;
	public Viewer(URI spotify_uri, Stage stage, AuthURI auth_uri) {
		web_view = new WebView();
		pane  = new Pane();
		this.stage = stage;
		this.auth_uri = auth_uri;
		web_engine = web_view.getEngine();
		web_engine.load(spotify_uri.toString());
		pane.getChildren().add(web_view);
		setCenter(web_view);
	}
	
	@SuppressWarnings("static-access")
	public void fetchToken() {
		web_engine.getLoadWorker().stateProperty().addListener((ov, o, n) -> {
			//System.out.println("Change");
			if (web_engine.getLocation().contains(redirectURI)) {
				System.out.println("Video has ended");
				String location = web_engine.getLocation();
				stage.close();
				System.out.println("The token is: " + location.substring(location.indexOf("=") + 1,location.indexOf("&")));
				token = location.substring(location.indexOf("=") + 1,location.indexOf("&"));
				// Have token so can now compliw596ete authorisation
				AuthorizationCode authCode = new AuthorizationCode(token, auth_uri.getSpotifyAPI());
				authCode.authorizationCode_Sync();
				//GetPlaylistsTracks getTracks = new GetPlaylistsTracks(authCode.getSpotifyApi(),"3AGOiaoRXMSjswCLtuNqv5");
				//getTracks.getPlaylistsTracks_Sync();
				//GetPlaylistExample playlist = new GetPlaylistExample(authCode.getSpotifyApi());
				//playlist.getPlaylist_Async();
				GetPlaylistsTracksExample test = new GetPlaylistsTracksExample(authCode.getSpotifyApi(),"37i9dQZF1DX5hHfOi73rY3");
				test.getPlaylistsTracks_Sync();

				
			}
			//System.out.println(web_engine.getLocation());
	    });
					
	}
		


	
	

}

package spotifyGUI;

import java.net.URL;
import java.util.ResourceBundle;

import com.wrapper.spotify.SpotifyApi;

import backEnd.BackEndContainer;
import homeScreenGUI.HomeScreenController;
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

/** This controller handles the spotify authentication screen which handles authenticating
 * users with the spotify server in order to obtain an access token which is needed to
 * access the spotify API. This class uses a webview to handle displaying the webpage
 * to the user and then after a access token is retrieved the user is sent to the 
 * playlist entry page 
 *  
 * Date created: 27/04/2019
 * Date last edited 29/04/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson and Harry Ogden
 *
 */
public class SpotifyAuthController implements Initializable {

    @FXML private BorderPane root_pane01;	
    @FXML private WebView web_view;
    // This is where user will be redircted to after logging in 
	String redirectURI = "https://localhost:8888/?code=";
	String token = "a";
	
	BorderPane pane01;
	
	private BackEndContainer back_end;
	private static SpotifyApi spotify_api;
	
	/** This method creates loads up the webpage containing the spotify login screen and
	 * then  waits until the user has authenticated themselve by detecting url change to
	 * redirect url
	 */
	@Override
	public void initialize(URL url01, ResourceBundle resources) {
		Platform.runLater(() -> {
			// Create class to obtain URI
	    	AuthorisationURI auth_uri = new AuthorisationURI();
			WebEngine web_engine = web_view.getEngine();
			// Load in spotify login screen
			web_engine.load(AuthorisationURI.authorizationCodeUri_Sync().toString());
			// Add listener to listen for redirect
			web_engine.getLoadWorker().stateProperty().addListener((ov, o, n) -> {
				// If redirected to correct screen parse token then can load next screen
				System.out.println(web_engine.getLocation());
				if (web_engine.getLocation().contains(redirectURI)) {
						String location = web_engine.getLocation();
						System.out.println("The token is: " + location.substring(location.indexOf("=") + 1,location.indexOf("&")));
						// Parse out token 
						token = location.substring(location.indexOf("=") + 1,location.indexOf("&"));
						// Have token so can now complete authorisation
						AuthorisationCode authCode = new AuthorisationCode(token, auth_uri.getSpotifyAPI());
						AuthorisationCode.authorizationCode_Sync();
						spotify_api = authCode.getSpotifyApi();
						// Load second screen with list details
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlaylistEntryPage.fxml"));
						try {
							pane01 = fxmlLoader.load();
							PlaylistEntryController controller = fxmlLoader.getController();
							// Pass clicked list filename to second sample controller
							controller.setAPI(spotify_api);
							controller.setBackEnd(back_end);
							showInSelf(pane01);
		
							} catch (Exception e) {
								e.printStackTrace();
						}
					}
				// If users do not accept agreement then redirect them back home screen
				else if (web_engine.getLocation().equals("https://accounts.spotify.com/authorize/cancel")){
					System.out.println("Not accepted");
			    	//load the home screen
			    	FXMLLoader loader = new FXMLLoader(homeScreenGUI.HomeScreenController.class.getResource("HomeScreen.fxml"));
			    	HomeScreenController controller = new HomeScreenController(back_end);
			    	loader.setController(controller);
			    	BorderPane new_pane;
					web_view = null;
					try {
						new_pane = loader.load();
						bindSizeProperties(new_pane);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	
					
				}
			
			});
		
	    });
		

		
		
		
	}
	/** This method sets the back_end variable
	 * 
	 * @param back_end
	 */
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
    /**
     * Method to bind the size of a contained .fxml pane to the same size as the main content window.
     * @param new_pane
     */
    private void bindSizeProperties(BorderPane new_pane) {
    	
		//binds the new panes width and height to the widht and height of the content (main) pane
    	new_pane.prefWidthProperty().bind(root_pane01.widthProperty());
		new_pane.prefHeightProperty().bind(root_pane01.heightProperty());
		new_pane.minWidthProperty().bind(root_pane01.minWidthProperty());
		new_pane.minHeightProperty().bind(root_pane01.minHeightProperty());
		new_pane.maxWidthProperty().bind(root_pane01.maxWidthProperty());
		new_pane.maxHeightProperty().bind(root_pane01.maxHeightProperty());
    	new_pane.setManaged(true);
    	
    	//displays the new pane in the content pane's centre panel
    	root_pane01.setCenter(new_pane);
    	
    }
	
}

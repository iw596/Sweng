package spotifyauth;

import java.net.URI;

/** This class handles the generation of a URI which will take users to a spotify
 * login screen. Also generates a spotify object which will be used to store permissions
 * and the access token
 * 
 * Date created: 27/04/2019
 * Date last edited 29/04/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson and Harry Ogden
 *
 */

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

public class AuthorisationURI {
	  // These show the spotify server we are a valid application
	  private static final String client_id = "990db6620e3c45bfbf24b43c212e5d6d";
	  private static final String client_secret = "f36398282edb4202aee3cfc9cad1e36e";
	  private static SpotifyApi spotify_api;
	  private static  AuthorizationCodeUriRequest authorization_code_uri_request; 
	  URI redirect_uri = SpotifyHttpManager.makeUri("https://localhost:8888");
	  
	  /** This consructor creates a spotify API object which will be used to store our
	   * access token.
	   */
	  @SuppressWarnings("static-access")
	  public AuthorisationURI(){
		  this.spotify_api = new SpotifyApi.Builder().setClientId(client_id).setClientSecret(client_secret).setRedirectUri(redirect_uri).build();  
		  this.authorization_code_uri_request = this.spotify_api.authorizationCodeUri()
		          .state("x4xkmn9pu3j6ukrs8n")
		          .scope("user-read-birthdate,user-read-email")
		          .show_dialog(true)
		          .build();
	  	}
	  
	  /** This method creates a URI which will be used to send the user to a login screen
	   * where they can authenticate themselves with the spotify server and thus retrieve
	   * an acess token 
	   * @return URI - returns the spotify login screen uri
	   */
	  public static URI authorizationCodeUri_Sync() {
		    final URI uri = authorization_code_uri_request.execute();

		    System.out.println("URI: " + uri.toString());
		    return uri;
	  }
	
	  /** Getter for the spotify api
	   * 
	   * @return spotify_api 
	   */
	  public SpotifyApi getSpotifyAPI() {
			// TODO Auto-generated method stub
			return spotify_api;
		}


}

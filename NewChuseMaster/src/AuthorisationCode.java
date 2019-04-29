package spotifyauth;
import java.io.IOException;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

/** This class handles the generation of a access token using the code retrieved
 * when a user logs into the spotify server.
 * 
 * Date created: 27/04/2019
 * Date last edited 29/04/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson and Harry Ogden
 *
 */


public class AuthorisationCode {

	  private static SpotifyApi spotify_api;
	  private static AuthorizationCodeRequest authorizationCodeRequest; 
	          //.build();
	  /** The constructor takes the spotify api object which will be given the access token
	   * and the code needed to obtain the acess token
	   * @param code - code needed to obtain access token
	   * @param api - api object which will be used to store access token 
	   */

	  @SuppressWarnings("static-access")
	  public AuthorisationCode(String code,SpotifyApi api){
		  this.spotify_api = api;
		  this.authorizationCodeRequest = spotify_api.authorizationCode(code)
		          .build(); 
		 
	  }
	  /** This method makes a request to the spotify server using the code to obtain
	   *  the access token and a refresh token
	   *  
	   */
	  public static void authorizationCode_Sync() {
	    try {
	      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

	      // Set access and refresh token for further "spotifyApi" object usage
	      spotify_api.setAccessToken(authorizationCodeCredentials.getAccessToken());
	      spotify_api.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

	      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }

	  /** Getter for the spotify api 
	   * 
	   * @return
	   */
	  public SpotifyApi getSpotifyApi(){
		  return spotify_api;
	  }

}
package spotifyauth;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

public class AuthorisationCode {
	  private static String code = "";

	  private static SpotifyApi spotifyApi;
	  private static AuthorizationCodeRequest authorizationCodeRequest; //= spotifyApi.authorizationCode(code)
	          //.build();

	  @SuppressWarnings("static-access")
	  public AuthorisationCode(String code,SpotifyApi spotifyApi){
		  this.spotifyApi = spotifyApi;
		  this.code = code;
		  this.authorizationCodeRequest = spotifyApi.authorizationCode(code)  
				  .build(); 
		 
	  }
	  
	  public static void authorizationCode_Sync() {
	    try {
	      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

	      // Set access and refresh token for further "spotifyApi" object usage
	      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
	      spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

	      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }

	  
	  public SpotifyApi getSpotifyApi(){
		  return spotifyApi;
	  }

}

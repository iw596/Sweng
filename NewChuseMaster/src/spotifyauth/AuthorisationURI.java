package spotifyauth;

import java.net.URI;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

public class AuthorisationURI {
	  private static final String client_id = "990db6620e3c45bfbf24b43c212e5d6d";
	  private static final String client_secret = "f36398282edb4202aee3cfc9cad1e36e";
	  private static SpotifyApi spotify_api;
	  private static  AuthorizationCodeUriRequest authorization_code_uri_request; 
	  URI redirect_uri = SpotifyHttpManager.makeUri("https://localhost:8888");
	  
	  @SuppressWarnings("static-access")
	  public AuthorisationURI(){
		  this.spotify_api = new SpotifyApi.Builder().setClientId(client_id).setClientSecret(client_secret).setRedirectUri(redirect_uri).build();  
		  this.authorization_code_uri_request = this.spotify_api.authorizationCodeUri()
		          .state("x4xkmn9pu3j6ukrs8n")
		          .scope("user-read-birthdate,user-read-email")
		          .show_dialog(true)
		          .build();
	  	}
	  
	  public static URI authorizationCodeUri_Sync() {
		    final URI uri = authorization_code_uri_request.execute();

		    System.out.println("URI: " + uri.toString());
		    return uri;
	  }
	
	  
	  public SpotifyApi getSpotifyAPI() {
			// TODO Auto-generated method stub
			return spotify_api;
		}


}

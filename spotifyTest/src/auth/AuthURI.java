package auth;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyApi.Builder;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AuthURI {
	  private static final String clientId = "990db6620e3c45bfbf24b43c212e5d6d";
	  private static final String clientSecret = "f36398282edb4202aee3cfc9cad1e36e";
	  private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8081");

	  private static SpotifyApi spotifyApi; /*= new SpotifyApi.Builder()
	          .setClientId(clientId)
	          .setClientSecret(clientSecret)
	          .setRedirectUri(redirectUri)
	          .build(); */
	  private static  AuthorizationCodeUriRequest authorizationCodeUriRequest; /*= spotifyApi.authorizationCodeUri()
	          .state("x4xkmn9pu3j6ukrs8n")
	          .scope("user-read-birthdate,user-read-email")
	          .show_dialog(true)
	          .build(); */
	  
	  @SuppressWarnings("static-access")
	public AuthURI(){
		  this.spotifyApi = new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret).setRedirectUri(redirectUri).build();  
		 
		  this.authorizationCodeUriRequest = this.spotifyApi.authorizationCodeUri()
		          .state("x4xkmn9pu3j6ukrs8n")
		          .scope("user-read-birthdate,user-read-email")
		          .show_dialog(true)
		          .build();
	  }
	  
	  
	  public static URI authorizationCodeUri_Sync() {
	    final URI uri = authorizationCodeUriRequest.execute();

	    System.out.println("URI: " + uri.toString());
	    return uri;
	  }

	  public static String authorizationCodeUri_Async() {
	    try {
	      final Future<URI> uriFuture = authorizationCodeUriRequest.executeAsync();

	      // ...

	      final URI uri = uriFuture.get();
	      
	      //String token = ClientCredentials.getAccessToken();
	      System.out.println("URI: " + uri.toString());
	      return uri.toString();
	      
	    } catch (InterruptedException | ExecutionException e) {
	      System.out.println("Error: " + e.getCause().getMessage());
	      return "Error";
	      
	    }
	    
	  }
	  
	  public SpotifyApi getSpotifyAPI(){
		  return spotifyApi;
	  }

}

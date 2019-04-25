package data;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GetPlaylistExample {
  private static final String playlistId = "37i9dQZF1DX4W3aJJYCDfV";

  private static SpotifyApi spotifyApi;
  private static  GetPlaylistRequest getPlaylistRequest;
 
  @SuppressWarnings("static-access")
  public GetPlaylistExample (SpotifyApi spotifyApi) {
	  this.spotifyApi = spotifyApi;
	  this.getPlaylistRequest = spotifyApi.getPlaylist(playlistId)
	          .fields("description,name")
	          
	        //  .market(CountryCode.SE)
	          .build();
	  
  }
  
  
  public static void getPlaylist_Sync() {
    try {
      final Playlist playlist = getPlaylistRequest.execute();
      System.out.println(playlist);
      System.out.println(playlist.getDescription());
      
      System.out.println("Name: " + playlist.getName());
      System.out.println("Description: " + playlist.getDescription());
      System.out.println(playlist.getTracks());
     
    } catch (IOException | SpotifyWebApiException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void getPlaylist_Async() {
    try {
      final Future<Playlist> playlistFuture = getPlaylistRequest.executeAsync();

      // ...

      final Playlist playlist = playlistFuture.get();

      System.out.println("Name: " + playlist.getName());
      System.out.println("Description: " + playlist.getDescription());
      
      System.out.println(playlist.getTracks());
    } catch (InterruptedException | ExecutionException e) {
      System.out.println("Error: " + e.getCause().getMessage());
    }
  }
}
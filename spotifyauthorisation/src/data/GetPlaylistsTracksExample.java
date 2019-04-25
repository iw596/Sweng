package data;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GetPlaylistsTracksExample {
  
  private static String playlist_id; //= "3AGOiaoRXMSjswCLtuNqv5";

  private static SpotifyApi spotifyApi;
  
  private static  GetPlaylistsTracksRequest getPlaylistsTracksRequest;
  
  @SuppressWarnings("static-access")
public GetPlaylistsTracksExample(SpotifyApi spotifyApi, String playlist_id){
	  this.spotifyApi = spotifyApi;
	  this.playlist_id = playlist_id;
	  this.getPlaylistsTracksRequest = this.spotifyApi
      .getPlaylistsTracks(playlist_id)
      /*.fields("description")*/
      .limit(100)
      .offset(0)
      .market(CountryCode.GB)
      .build();
  }

  public static void getPlaylistsTracks_Sync() {
    try {
      final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsTracksRequest.execute();
    
      System.out.println(getPlaylistsTracksRequest.execute());
      //System.out.println(playlistTrackPaging.getLimit());
      System.out.println(playlistTrackPaging);
      
      System.out.println("Total: " + playlistTrackPaging.getTotal());
      System.out.println("Href: " + playlistTrackPaging.getHref());
      for (int i= 0; i < playlistTrackPaging.getTotal(); i++) {
    	  System.out.println("Track "+ i + " name: " + playlistTrackPaging.getItems()[i].getTrack().getName());
      }
     // System.out.println(playlistTrackPaging.getItems());
      
      //System.out.println("Track 1 name: " + playlistTrackPaging.getItems()[1].getTrack().getName());
    } catch (IOException | SpotifyWebApiException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void getPlaylistsTracks_Async() {
    try {
      final Future<Paging<PlaylistTrack>> pagingFuture = getPlaylistsTracksRequest.executeAsync();

      // ...

      final Paging<PlaylistTrack> playlistTrackPaging = pagingFuture.get();

      System.out.println("Total: " + playlistTrackPaging.getTotal());
      
      
    } catch (InterruptedException | ExecutionException e) {
      System.out.println("Error: " + e.getCause().getMessage());
    }
  }
}

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
  
  private static final String playlistId = "3AGOiaoRXMSjswCLtuNqv5";

  private static SpotifyApi spotifyApi;
  
  private static  GetPlaylistsTracksRequest getPlaylistsTracksRequest;
  
  @SuppressWarnings("static-access")
public GetPlaylistsTracksExample(SpotifyApi spotifyApi){
	  this.spotifyApi = spotifyApi;
	  this.getPlaylistsTracksRequest = this.spotifyApi
      .getPlaylistsTracks(playlistId)
      /*.fields("description")*/
      .limit(60)
      .offset(0)
      .market(CountryCode.GB)
      .build();
  }

  public static void getPlaylistsTracks_Sync() {
    try {
      final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsTracksRequest.execute();
      spotifyApi.getPlay
      System.out.println(getPlaylistsTracksRequest.execute());
      //System.out.println(playlistTrackPaging.getLimit());
      System.out.println(playlistTrackPaging);
      
      System.out.println("Total: " + playlistTrackPaging.getTotal());
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

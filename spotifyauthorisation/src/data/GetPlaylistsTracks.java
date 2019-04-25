package data;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;

public class GetPlaylistsTracks {
	private static String playlistId ;

	  private static SpotifyApi spotifyApi;
	  
	  private static  GetPlaylistsTracksRequest getPlaylistsTracksRequest;
	  
	  @SuppressWarnings("static-access")
	public GetPlaylistsTracks(SpotifyApi spotifyApi, String playlist_id){
		  this.playlistId = playlist_id;
		  this.spotifyApi = spotifyApi;
		  this.getPlaylistsTracksRequest = this.spotifyApi
	      .getPlaylistsTracks(playlistId)
	      .fields("name")
	      .limit(100)
	      .market(CountryCode.GB)
	      .build();
	  }

	  public static void getPlaylistsTracks_Sync() {
	    try {
	      Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsTracksRequest.execute();
		  System.out.println(playlistTrackPaging);	      
	      System.out.println("Total: " + playlistTrackPaging.getTotal());
	      System.out.println(getPlaylistsTracksRequest.getJson());
	      System.out.println(playlistTrackPaging.getLimit());
	     // System.out.println(playlistTrackPaging);	      
	     
	   //   System.out.println(playlistTrackPaging.getHref());
	      
	      //System.out.println(playlistTrackPaging.getItems());
	      System.out.println(playlistTrackPaging.getNext());
	      
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }
	  public static void getPlaylistsTracks_Async() {
		    try {
		      final Future<Paging<PlaylistTrack>> pagingFuture = getPlaylistsTracksRequest.executeAsync();

		      // ...
		      if (pagingFuture.isDone()) {
		    	  System.out.println("Done");
		      }
		      else {
		    	  System.out.println("Not Done");
		    	  
		      }
		      
		      while(pagingFuture.isDone() == false) {
		    	  System.out.println("Not Done");
		      }

		      final Paging<PlaylistTrack> playlistTrackPaging = pagingFuture.get();

		      System.out.println("Total: " + playlistTrackPaging.getItems().length);
		      
		      
		    } catch (InterruptedException | ExecutionException e) {
		      System.out.println("Error: " + e.getCause().getMessage());
		    }
		  }



}

package spotifydata;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;

import listDataStructure.AudioItem;
import listDataStructure.BasicItem;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GetPlaylistsTracks {
	 private static String playlist_id; //= "3AGOiaoRXMSjswCLtuNqv5";

	 private static SpotifyApi spotifyApi;
	  
	 private static  GetPlaylistsTracksRequest getPlaylistsTracksRequest;
	  
	  @SuppressWarnings("static-access")
	public GetPlaylistsTracks (SpotifyApi spotifyApi, String playlist_id){
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
		   ArrayList<BasicItem> spotify_items = new ArrayList<BasicItem>();

			//loops through every file path in the list 
			for(int i = 0; i < playlistTrackPaging.getTotal(); i++ ) {
				//adds every file path to the audio item array list
		        spotify_items.add(new SpotifyItem(playlistTrackPaging.getItems()[i].getTrack().getId(),));
			}
	     // System.out.println(playlistTrackPaging.getItems());
	      
	      //System.out.println("Track 1 name: " + playlistTrackPaging.getItems()[1].getTrack().getName());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	    

	  }

}

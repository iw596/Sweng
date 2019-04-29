package spotifydata;
import java.io.IOException;
import java.util.ArrayList;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;

import listDataStructure.BasicItem;
import listDataStructure.SpotifyItem;
/** This class handles requesting data about a playlist from spotify servers. It uses
 *  a spotify API object to handle making the requests and then formats the data. Note to
 *  use this class the passed in spotifyapi object 	MUST have a valid access token assoicated
 *  with it.
 * 
 * Date created: 27/04/2019
 * Date last edited 29/04/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson and Harry Ogden
 *
 */
public class GetPlaylistsTracks {

	 private static SpotifyApi spotify_api;
	  
	 private static  GetPlaylistsTracksRequest get_playlist_tracks_request;
	  
	 /** Contructor for GetPlaylistTracks class. It takes the spotify api which has
	  *  the access token and a id for the playlist we want the data from and stores
	  *  this information
	  * @param api - spotify api with valid access token
	  * @param playlist_id - playlist we want to gather data from
	  */
	  @SuppressWarnings("static-access")
	public GetPlaylistsTracks (SpotifyApi api, String playlist_id){
		  this.spotify_api = api;
		  this.get_playlist_tracks_request = this.spotify_api
	      .getPlaylistsTracks(playlist_id)
	      .offset(0)
	      .market(CountryCode.GB)
	      .build();
	  }
	  
	  /** This method is used to make a request to the spotify servers to return information
	   * about a playlist. This method makes the request and stores all the returned data
	   * in a array of basic items which is then returned.
	   * @return
	   */
	  public static ArrayList<BasicItem> getPlaylistsTracks_Sync() {
		    try {
		      // Make the request
		      final Paging<PlaylistTrack> playlistTrackPaging = get_playlist_tracks_request.execute();

		      int playlist_length;
		      // If more than 100 items just use the first 100 
		      if(playlistTrackPaging.getTotal() > 100) {
		    	  playlist_length = 100;
		      } else {
		    	  playlist_length = playlistTrackPaging.getTotal();
		      }
		      
		      
		      ArrayList<BasicItem> spotify_items = new ArrayList<BasicItem>();
		      
		      //loops through every file path in the list 
		      for(int i = 0; i < playlist_length; i++ ) {
		    	  //adds every file path to the audio item array list
		    	  
		    	  ArrayList<String> track_metadata = new ArrayList<String>();
		    	  
		    	  track_metadata.add(playlistTrackPaging.getItems()[i].getTrack().getName());
		    	  String artist = playlistTrackPaging.getItems()[i].getTrack().getArtists()[0].getName();
		    	  // If multple artists then need to specifically add each one to the item
		    	  if(playlistTrackPaging.getItems()[i].getTrack().getArtists().length > 1) {
		    		  for(int j=1; j < playlistTrackPaging.getItems()[i].getTrack().getArtists().length; j++) {
		    			  artist += ", " + playlistTrackPaging.getItems()[i].getTrack().getArtists()[j].getName();
		    		  }
		    	  }
		    	  
		    	  track_metadata.add(artist);
		    	  track_metadata.add(playlistTrackPaging.getItems()[i].getTrack().getAlbum().getName());
		    	  track_metadata.add(Double.toString(((double)playlistTrackPaging.getItems()[i].getTrack().getDurationMs() / 1000) / 60));

		    	  
		    	  spotify_items.add(new SpotifyItem(playlistTrackPaging.getItems()[i].getTrack().getId(), track_metadata,playlistTrackPaging.getItems()[i].getTrack().getPreviewUrl(), playlistTrackPaging.getItems()[i].getTrack().getAlbum().getImages()[0]));
		      }
	
		      return spotify_items;
		      
		    } catch (IOException | SpotifyWebApiException e) {
		    	System.out.println("Error: " + e.getMessage());
		    }
		    
		    return null;

		  }

}

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

public class GetPlaylistsTracks {
//	 private static String playlist_id; //= "3AGOiaoRXMSjswCLtuNqv5";

	 private static SpotifyApi spotifyApi;
	  
	 private static  GetPlaylistsTracksRequest getPlaylistsTracksRequest;
	  
	  @SuppressWarnings("static-access")
	public GetPlaylistsTracks (SpotifyApi spotifyApi, String playlist_id){
		  this.spotifyApi = spotifyApi;
//		  this.playlist_id = playlist_id;
		  this.getPlaylistsTracksRequest = this.spotifyApi
	      .getPlaylistsTracks(playlist_id)
	      /*.fields("description")*/
//	      .limit(100)
	      .offset(0)
	      .market(CountryCode.GB)
	      .build();
	  }

	  public static ArrayList<BasicItem> getPlaylistsTracks_Sync() {
		    try {
		      final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsTracksRequest.execute();
		      
		      
		      System.out.println(getPlaylistsTracksRequest.execute());
		      //System.out.println(playlistTrackPaging.getLimit());
		      System.out.println(playlistTrackPaging);
		      
		      System.out.println("Total: " + playlistTrackPaging.getTotal());
		      System.out.println("Href: " + playlistTrackPaging.getHref());

		      int playlist_length;
		      
		      if(playlistTrackPaging.getTotal() > 100) {
		    	  playlist_length = 100;
		      } else {
		    	  playlist_length = playlistTrackPaging.getTotal();
		      }
		      
		      for (int i= 0; i < playlist_length; i++) {
		    	  System.out.println("Track "+ i + " name: " + playlistTrackPaging.getItems()[i].getTrack().getName());
		    	  System.out.println(playlistTrackPaging.getItems()[i].getTrack().getPreviewUrl());
		      }
		      
		      ArrayList<BasicItem> spotify_items = new ArrayList<BasicItem>();
		      
		      //loops through every file path in the list 
		      for(int i = 0; i < playlist_length; i++ ) {
		    	  //adds every file path to the audio item array list
		    	  
		    	  ArrayList<String> track_metadata = new ArrayList<String>();
		    	  
		    	  track_metadata.add(playlistTrackPaging.getItems()[i].getTrack().getName());
		    	  
		    	  String artist = playlistTrackPaging.getItems()[i].getTrack().getArtists()[0].getName();
		    	  
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
		      // System.out.println(playlistTrackPaging.getItems());
		      
		      
		      for(BasicItem item : spotify_items) {
		    	  item.print();
		      }
		      return spotify_items;
		      
		      //System.out.println("Track 1 name: " + playlistTrackPaging.getItems()[1].getTrack().getName());
		    } catch (IOException | SpotifyWebApiException e) {
		    	System.out.println("Error: " + e.getMessage());
		    }
		    
		    return null;

		  }

}

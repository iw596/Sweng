package spotifyPlayback;

import java.io.IOException;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.data.player.PauseUsersPlaybackRequest;

public class PlaybackPause {
	
	private SpotifyApi api;
	private static PauseUsersPlaybackRequest pauseUsersPlaybackRequest;
	
	public PlaybackPause(SpotifyApi api) {
		this.api = api;
		pauseUsersPlaybackRequest = api.pauseUsersPlayback().build();
	}
	
	 public static void pauseUsersPlayback_Sync() {
		  try {
		       String string = pauseUsersPlaybackRequest.execute();

		      System.out.println("Null: " + string);
		    } catch (IOException | SpotifyWebApiException e) {
		      System.out.println("Error: " + e.getMessage());
		    }
		  }
	
	
	

}

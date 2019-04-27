package spotifyPlayback;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.miscellaneous.Device;
import com.wrapper.spotify.requests.data.player.GetUsersAvailableDevicesRequest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GetAvailableDevices {
	private SpotifyApi api;
	static GetUsersAvailableDevicesRequest getUsersAvailableDevicesRequest;

	public GetAvailableDevices (SpotifyApi api){
		this.api = api;
		this.getUsersAvailableDevicesRequest = api.getUsersAvailableDevices().build();
		
	}
	public static void getUsersAvailableDevices_Sync() {
		try {
		      Device[] devices = getUsersAvailableDevicesRequest.execute();
		      System.out.println("Length: " + devices.length);
		      for (int i = 0; i< devices.length; i++){
		    	  System.out.println("Device ID: " + devices[i].getId());
		    	  System.out.println("Device Name: " + devices[i].getName());
		    	  System.out.println("Device active: " + devices[i].getIs_active());
		      }
		    } catch (IOException | SpotifyWebApiException e) {
		      System.out.println("Error: " + e.getMessage());
		    }
	}
	
	
}

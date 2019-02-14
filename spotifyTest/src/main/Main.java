package main;

import auth.*;
import data.GetPlaylistsTracksExample;
import server.*;
import com.wrapper.spotify.SpotifyApi;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.wrapper.spotify.model_objects.credentials.ClientCredentials;

public class Main {
	
	public static void main(String [] args){
		SimpleHTTPServer server = new SimpleHTTPServer();
		AuthURI authStage1 = new AuthURI();
		URI oURL;
		try {
			oURL = new URI(authStage1.authorizationCodeUri_Async());
			Desktop desktop = java.awt.Desktop.getDesktop();
			desktop.browse(oURL);
			
			
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SpotifyApi spotApi = authStage1.getSpotifyAPI();
		try {
			String code = server.getCode();
			System.out.println("The code is: " + code);
			AuthorizationCodeExample authStage2 = new AuthorizationCodeExample(code,spotApi);
			authStage2.authorizationCode_Async();
			spotApi = authStage2.getSpotifyApi();
			//AuthorizationCodeRefreshExample refresh = new AuthorizationCodeRefreshExample(spotApi);
			//refresh.authorizationCodeRefresh_Async();
			GetPlaylistsTracksExample get = new GetPlaylistsTracksExample(spotApi);
			get.getPlaylistsTracks_Sync();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		


	}
	


}

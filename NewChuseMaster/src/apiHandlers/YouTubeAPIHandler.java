package apiHandlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

/**
 * Abstract class used for handling the YouTube data API
 * 
 * Date created: 12/02/2019
 * Date last edited: 01/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Harry Ogden
 *
 */
public abstract class YouTubeAPIHandler {

	public static final int max_playlist_length = 50;
	
	/** 
	 * ALL CODE FROM THIS LINE (41) TO LINE 180 HAS BEEN ADAPTED FROM EXAMPLE CODE
	 * TAKEN FROM THE GOOGLE YOUTUBE DATA API EXAMPLES AND PARTS ARE VERY SIMILAR.
	 * The source of this can be found at: 
	 * https://developers.google.com/youtube/v3/docs/playlists/list
	 * 
	 **/
	
    /** Application name. */
    private static final String APPLICATION_NAME = "Chuse";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
    System.getProperty("user.home"), ".credentials/ChuseCredentials");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(YouTubeScopes.YOUTUBE_READONLY);
    
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    private static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = YouTubeAPIHandler.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader( in ));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(DATA_STORE_FACTORY)
            .setAccessType("offline")
            .build();
        Credential credential = new AuthorizationCodeInstalledApp(
        flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
            "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized API client service, such as a YouTube
     * Data API client service.
     * @return an authorized API client service
     * @throws IOException
     */
    private static YouTube getYouTubeService() throws IOException {
        Credential credential = authorize();
        return new YouTube.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    /**
     * Method to get YouTube video data from a YouTube playlist when given a playlist URL.
     * 
     * @param playlist_url - the URL of the video playlist
     * @return video_list - the list of video items with associated metadata
     * @throws IOException
     */
    public static listDataStructure.ChuseList getPlaylistData(String playlist_url) throws IOException{
    	
    	//gets a youtube service
        YouTube youtube = getYouTubeService();
        
        //gets the playlist ID from the URL
        String playlist_id = getPlaylistIdFromUrl(playlist_url);
        
        //creates a new initialised list to contain videos
        listDataStructure.ChuseList video_list = new listDataStructure.ChuseList("YouTube List");
        
        try {
        	//creates a hap mash for storing the different metadata from 
        	//the YouTube videos in the playlist
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet");
            parameters.put("maxResults","" + max_playlist_length + "");
            parameters.put("playlistId", playlist_id);

            //gets the playlist data
            YouTube.PlaylistItems.List playlistItemsListByPlaylistIdRequest = youtube.playlistItems().list(parameters.get("part").toString());
            
            //if the parameters contains the max number of results, finds the max 
            //number of results
            if (parameters.containsKey("maxResults")) {
                playlistItemsListByPlaylistIdRequest.setMaxResults(Long.parseLong(parameters.get("maxResults").toString()));
            }
            
            //if the parameters contain the playlist ID and this isn't blank
            //then sets the playlist ID equal to the found playlist ID
            if (parameters.containsKey("playlistId") && parameters.get("playlistId") != "") {
                playlistItemsListByPlaylistIdRequest.setPlaylistId(parameters.get("playlistId").toString());
            }
            
            //sets the data fields to pull from the playlist
            playlistItemsListByPlaylistIdRequest.setFields("items(snippet)");

            //gets the data
            PlaylistItemListResponse response = playlistItemsListByPlaylistIdRequest.execute();
            
            //stores the response in a JSON Object
            JSONObject jsonObject = new JSONObject(response);

            int i;
            
            //loops through every item in the JSON object (every video in the playlist)
            for(i=0;i < jsonObject.getJSONArray("items").length(); i++) {
            	
            	//creates a JSON object containing just the snippet tagged items from the response
            	JSONObject snippet = jsonObject.getJSONArray("items").getJSONObject(i).getJSONObject("snippet");
            	
            	//adds a new video item to the array list, with the relevant information being
            	//pulled from the JSON object and added to the video item
            	video_list.addItem(new listDataStructure.VideoItem(
            			snippet.getString("title"), snippet.getJSONObject("resourceId").getString("videoId"), snippet.getString("description"), snippet.getString("channelTitle")));
            	
            }

        //exception catching
        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        
        return video_list;
    	
    }
    
    /**
     * Method for getting the playlist ID from a given YouTube playlist URL.
     * 
     * @param playlist_url - the YouTube playlist url
     * @return url_parts[2] - the ID of the YouTube playlist
     */
    private static String getPlaylistIdFromUrl(String playlist_url) {
    	
    	//splits the string into separate parts everytime there is a "=" character
    	
    	String[] url_parts = playlist_url.split("=");
    	
    	int count;
    	
    	for(count=0; count < url_parts.length;count++);
    	
    	if(count == 2) {
    		return url_parts[1];
    	} else {
    		return "no valid url";
    	}
    	
    }

}

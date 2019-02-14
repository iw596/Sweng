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

public class YouTubeAPIHandler {

public static final int max_playlist_length = 10;
	
    /** Application name. */
    private static final String APPLICATION_NAME = "API Sample";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
    System.getProperty("user.home"), ".credentials/java-youtube-api-tests");

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
    public static Credential authorize() throws IOException {
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
    public static YouTube getYouTubeService() throws IOException {
        Credential credential = authorize();
        return new YouTube.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    public static listDataStructure.ChuseList getPlaylistData(String playlist_url) throws IOException{
    	
        YouTube youtube = getYouTubeService();
        
        String playlist_id = getPlaylistIdFromUrl(playlist_url);
        
        //ArrayList<ArrayList<String>> playlist_property_container = new ArrayList<ArrayList<String>>();
        
        listDataStructure.ChuseList video_list = new listDataStructure.ChuseList("Test");
        
        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet");
            parameters.put("maxResults","" + max_playlist_length + "");
            parameters.put("playlistId", playlist_id);

            YouTube.PlaylistItems.List playlistItemsListByPlaylistIdRequest = youtube.playlistItems().list(parameters.get("part").toString());
            if (parameters.containsKey("maxResults")) {
                playlistItemsListByPlaylistIdRequest.setMaxResults(Long.parseLong(parameters.get("maxResults").toString()));
            }

            if (parameters.containsKey("playlistId") && parameters.get("playlistId") != "") {
                playlistItemsListByPlaylistIdRequest.setPlaylistId(parameters.get("playlistId").toString());
            }
            playlistItemsListByPlaylistIdRequest.setFields("items(snippet)");

            PlaylistItemListResponse response = playlistItemsListByPlaylistIdRequest.execute();
            
            JSONObject jsonObject = new JSONObject(response);

            int i;
            
            for(i=0;i < jsonObject.getJSONArray("items").length(); i++) {
            	
            	JSONObject snippet = jsonObject.getJSONArray("items").getJSONObject(i).getJSONObject("snippet");

            	//ArrayList<String> video_properties = new ArrayList<String>();
            	
            	video_list.addItem(new listDataStructure.VideoItem(
            			snippet.getString("title"), snippet.getJSONObject("resourceId").getString("videoId"), snippet.getString("description"), snippet.getString("channelTitle")));
            	
/*            	video_properties.add(snippet.getString("title"));
                video_properties.add(snippet.getString("channelTitle"));
                video_properties.add(snippet.getString("description"));
                video_properties.add(snippet.getJSONObject("resourceId").getString("videoId"));*/
                
                //playlist_property_container.add(video_properties);
            	
            }

        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        
        return video_list;
    	
    }
    
    private static String getPlaylistIdFromUrl(String playlist_url) {
    	
    	String[] url_parts = playlist_url.split("=");
    	
    	return url_parts[2];
    	
    }
    
    
/*    public static void main(String[] args) throws IOException {

    	ArrayList<ArrayList<String>> output = getPlaylistData("https://www.youtube.com/watch?v=gZBbICLk_tM&list=PL11pFjJATdhmvAtI_76hy5Mwb9qTOpk1J");
    	
    	int i;
    	
    	for(i=0; i < output.size(); i++) {
    		System.out.println("Item: " + i);
    		System.out.println(output.get(i));
    		System.out.println("");
    	}
        
    }*/
}

package comparisonScreenGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sun.jna.NativeLibrary;

import audioPlayback.AppController;
import backEnd.BackEndContainer;
import imageDisplay.ImageDisplayController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import listDataStructure.BasicItem;
import resultsScreenGUI.ResultsScreenController;
import spotifyplayer.SpotifyPlayer;
import videoPlayback.Player;

/**
 * Class for the comparison screen controller. This class handles all button listeners and interactivity
 * and holds a reference to the back-end code of the program for communication.
 * 
 * Date created: 13/03/2019
 * Date last edited: 27/04/2019
 * Last edited by: Harry Ogden and Isaac Watson
 * 
 * @author Dan Jackson
 *
 */
public class ComparisonScreenController implements Initializable {

    @FXML
    private BorderPane root;
	
    @FXML
    private StackPane left_content;

    @FXML
    private JFXButton left_button;

    @FXML
    private StackPane right_content;

    @FXML
    private JFXButton right_button;
    
    private ArrayList<AppController> audio_controllers;
   
    private ArrayList<Player> video_controllers;
    
    private ArrayList<ImageDisplayController> image_controllers;
    
    private BackEndContainer back_end;

	private ArrayList<SpotifyPlayer> spotify_controllers;
    
    /**
     * Constructor for the comparison screen controller. 
     * @param back_end
     */
    public ComparisonScreenController(BackEndContainer back_end) {
    	this.back_end = back_end;
    	back_end.startComparison();
    	audio_controllers = new ArrayList<AppController>();
    	image_controllers = new ArrayList<ImageDisplayController>();
    	video_controllers = new ArrayList<Player>();
    	spotify_controllers = new ArrayList<SpotifyPlayer>();
    }
    
    @FXML
    /**
     * Method to select the left item when the button is clicked.
     * @param event
     */
    void leftItemSelected(ActionEvent event) {
    	
    	if(audio_controllers.size() > 0) {
    		for(int i = 0; i < audio_controllers.size(); i++) {
    			audio_controllers.get(i).exit();
    		}
    	} else if(image_controllers.size() > 0) {
    		for(int i = 0; i < image_controllers.size(); i++) {
    			image_controllers.get(i).exit();
    		}
    	}  
    	
    	oddCheck();
    	
    	back_end.getComparison().chooseItem(0);
    	back_end.getComparison().advancePosition();
    	
    	endCheck();

    }

    @FXML
    /**
     * Method to select the right item when the button is clicked.
     * @param event
     */
    void rightItemSelected(ActionEvent event) {
    	
    	//if there are audio or image controllers, loops through them all
    	//and exits them
    	if(audio_controllers.size() > 0) {
    		for(int i = 0; i < audio_controllers.size(); i++) {
    			audio_controllers.get(i).exit();
    		}
    	} else if(image_controllers.size() > 0) {
    		for(int i = 0; i < image_controllers.size(); i++) {
    			image_controllers.get(i).exit();
    		}
    	}  
    	
    	
    	oddCheck();
    	
    	//chooses item 2 and advances the algorithms position
    	back_end.getComparison().chooseItem(1);
    	back_end.getComparison().advancePosition();
    	
    	endCheck();
    	
    }
    
    /**
     * Method to check if the list used in the tournament algorithm has an odd number of items in it, and if it does
     * to take the appropriate course of action.
     */
    private void oddCheck() {
    	
    	//checks if the list is odd
    	if(!back_end.getComparison().evenCheck()) {
    		//if the list is odd, checks whether the odd item exists
    		if(back_end.getComparison().oddItemExists()) {
    			//if the odd item does exist, adds the odd item to the current list
    			back_end.getComparison().addOddItemToList();
    		} else {
    			//if the odd item does not exist, stores the odd item and removes the last item from the list
    			back_end.getComparison().storeOddItem();
    			back_end.getComparison().removeOddItemFromList();
    		}
    	}
    	
    }
    
    /**
     * Method to check whether the tournament algorithm has completed all it's rounds, and if 
     * not whether to start a new round or continue on the same round.
     */
    private void endCheck() {
    	
    	//checks if the round is over
    	if(!back_end.getComparison().roundIsOver()) {
    		//if the round is not over, updates the text on the buttons with the next item's titles
    		right_button.setText(back_end.getComparison().getCurrentPairTitles().get(1));
        	left_button.setText(back_end.getComparison().getCurrentPairTitles().get(0));
        	checkObjectType();
    	} else {
    		
    		//if the round is over, stores the results from the round
    		back_end.getComparison().storeResults();
    		
    		// If round over and uses video player then need to silence players
    		if(video_controllers.size() > 0) {
        		for(int i = 0; i < video_controllers.size(); i++) {
        			video_controllers.get(i).exit();	
        		}
    		}else if(spotify_controllers.size() > 0) {
        		for(int i = 0; i < spotify_controllers.size(); i++) {
        			spotify_controllers.get(i).exit();	
        		}
    		}
    		
    		//checks if the entire tournament is over
        	if(back_end.getComparison().isFinished()) {
        		
        		//if it is, checks if the odd item exists
        		if(back_end.getComparison().oddItemExists()) {
        			//if the odd item does exist, adds the odd item to the list, advances the round
        			//resets the algorithm's position
        			back_end.getComparison().addOddItemToList();
        			back_end.getComparison().resetPosition();
        			//if the round is not over, updates the text on the buttons with the next item's titles
        			right_button.setText(back_end.getComparison().getCurrentPairTitles().get(1));
                	left_button.setText(back_end.getComparison().getCurrentPairTitles().get(0));
                	checkObjectType();
        		} else {
        			//if the odd item does not exist, removes the duplicated items from each result list
        			back_end.getComparison().removeDuplicates();
        			back_end.getComparison().rankResults();
            		back_end.getRankedResults();

					try {
						FXMLLoader loader = new FXMLLoader(resultsScreenGUI.ResultsScreenController
								.class.getResource("ResultsScreen.fxml"));
	                	ResultsScreenController controller = new ResultsScreenController(back_end);
	                	loader.setController(controller);
	                	BorderPane new_pane = loader.load();
	                	showInSelf(new_pane);
					} catch (IOException e) {
						e.printStackTrace();
					}

        		}
        	} else {
        		//if the tournament is not over, advances the round, resets the position and
        		//updates the text on the buttons with the next item's titles
        		back_end.getComparison().resetPosition();
        		right_button.setText(back_end.getComparison().getCurrentPairTitles().get(1));
            	left_button.setText(back_end.getComparison().getCurrentPairTitles().get(0));
            	checkObjectType();
        	}

    	}
    	
    }

	@Override
	/**
	 * Method that is called when the comparison screen is loaded. This sets the text of the buttons and calls
	 * the checkObjectType function, which checks the object type and instantiates the relevant media viewer.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
    	left_button.setText(back_end.getComparison().getCurrentPairTitles().get(0));
    	right_button.setText(back_end.getComparison().getCurrentPairTitles().get(1));
		checkObjectType();
	}
	

	/**
	 * Method to check what type of item the items currently being compared are, and instantiate the relevant
	 * media viewer for the item.
	 */
	private void checkObjectType() {
		
		audio_controllers = new ArrayList<AppController>();
		image_controllers = new ArrayList<ImageDisplayController>();
		
		BasicItem left_object = back_end.getComparison().getCurrentPair().get(0);
		BasicItem right_object = back_end.getComparison().getCurrentPair().get(1);

		left_content.getChildren().removeAll();
		right_content.getChildren().removeAll();
		
		//if the left object is a video item, instantiate the video player
		if((left_object.getType().equals("VideoItem") 
				||left_object.getType().equals("YouTubeItem")) && video_controllers.size() < 2) {
			instantiateVideoPlayer(left_object, left_content);
			back_end.setVideoControllers(video_controllers);
		} else if(left_object.getType().equals("ImageItem")) {
			instantiateImageViewer(left_object, left_content);
		} else if(left_object.getType().equals("AudioItem")) {
			instantiateAudioPlayer(left_object, left_content);
			back_end.setAudioControllers(audio_controllers);
		} else if ((left_object.getType().equals("VideoItem")
				||left_object.getType().equals("YouTubeItem")) && video_controllers.size() > 1){
			changeVideoPlayerVideo(left_object.getPath(),0);
		} else if (left_object.getType().equals("SpotifyItem") 
				&& spotify_controllers.size() != 2){
			instantiateSpotifyPlayer(left_object, left_content);
			back_end.setSpotifyControllers(spotify_controllers);
		}else if (left_object.getType().equals("SpotifyItem") 
				&& spotify_controllers.size() == 2){
			changeSpotifyPlayerSong(left_object.getPreview(),0);
			spotify_controllers.get(0).setMeta(left_object.getMetadata());
			spotify_controllers.get(0).setArt(new Image(left_object.getImage().getUrl()));
		}
		
		//if the right object is a video item, instantiate the video player
		if((right_object.getType().equals("VideoItem") 
				|| right_object.getType().equals("YouTubeItem")) && video_controllers.size() < 2 ) {
			instantiateVideoPlayer(right_object, right_content);
			back_end.setVideoControllers(video_controllers);
		} else if(right_object.getType().equals("ImageItem")) {
			instantiateImageViewer(right_object, right_content);
		} else if(right_object.getType().equals("AudioItem")) {
			instantiateAudioPlayer(right_object, right_content);
			back_end.setAudioControllers(audio_controllers);
		} else if ((right_object.getType().equals("VideoItem") 
				|| right_object.getType().equals("YouTubeItem")) && video_controllers.size() > 1){
			changeVideoPlayerVideo(right_object.getPath(),1);
		} else if (right_object.getType().equals("SpotifyItem") && spotify_controllers.size() != 2){
			instantiateSpotifyPlayer(right_object, right_content);
			back_end.setSpotifyControllers(spotify_controllers);
		} else if (right_object.getType().equals("SpotifyItem") && spotify_controllers.size() == 2){
			changeSpotifyPlayerSong(right_object.getPreview(),1);
			spotify_controllers.get(1).setMeta(right_object.getMetadata());
			spotify_controllers.get(1).setArt(new Image(right_object.getImage().getUrl()));
		}
		
	}
	
	/** Method to instantiate the image viewer. This method takes an image and a pane
	 * 	where the image will be displayed.
	 * @param item - Image item to be displayed
	 * @param pane - Pane where image will be displayed
	 */
	private void instantiateImageViewer(BasicItem item, Pane pane) {
		
		try {
			// Instantiate the loader and controller
			FXMLLoader loader = new FXMLLoader(imageDisplay.ImageDisplayController.class.getResource("ImageDisplay.fxml"));
			ImageDisplayController controller = new ImageDisplayController(item.getPath());
			image_controllers.add(controller);
			loader.setController(controller);
			// Load the panes and add the image pane
			BorderPane image_root = loader.load();
			StackPane.setAlignment(image_root, Pos.CENTER);
			pane.getChildren().add(image_root);
			// Set width and height preferences
			image_root.prefWidthProperty().bind(pane.widthProperty());
			image_root.prefHeightProperty().bind(pane.heightProperty());
			image_root.minWidthProperty().bind(pane.minWidthProperty());
			image_root.minHeightProperty().bind(pane.minHeightProperty());
			image_root.maxWidthProperty().bind(pane.maxWidthProperty());
			image_root.maxHeightProperty().bind(pane.maxHeightProperty());

			
		} catch (IOException e) {
			
		}
		
	}
	
	/** Method to instantiate the audio player. This method takes an an audio track and a pane
	 * 	where the image will be displayed.
	 * @param item - Audio item to be played
	 * @param pane - Pane where audio player control will be displayed
	 */
	private void instantiateAudioPlayer(BasicItem item, Pane pane) {
		
		try {
			
			NativeLibrary.addSearchPath("libvlc", "C:/Program Files (x86)/VideoLAN/VLC");
			// Instantiate the loader and controller
			FXMLLoader loader = new FXMLLoader(audioPlayback.AppController.class.getResource("App.fxml"));
			AppController controller = new AppController(item.getPath());
			audio_controllers.add(controller);
			loader.setController(controller);
			// Load the panes and add the image pane
			GridPane audio_root = loader.load();
			StackPane.setAlignment(audio_root, Pos.CENTER);
			pane.getChildren().add(audio_root);
			// Set width and height preferences
			audio_root.prefWidthProperty().bind(pane.widthProperty());
			audio_root.prefHeightProperty().bind(pane.heightProperty());
			audio_root.minWidthProperty().bind(pane.minWidthProperty());
			audio_root.minHeightProperty().bind(pane.minHeightProperty());
			audio_root.maxWidthProperty().bind(pane.maxWidthProperty());
			audio_root.maxHeightProperty().bind(pane.maxHeightProperty());

			
		} catch (IOException e) {
			
		}
		
	}
	/** This method instantiates a video player to be displayed on the comparison screen GUI. It takes a item
	 *  to be displayed and the pane to display the player on. Then it creates the player and adds it to the pane
	 * 
	 * @param item - the item that the player will play
	 * @param pane - the pane where the player will be added
	 */
	private void instantiateVideoPlayer(BasicItem item, Pane pane) {
		// Find vlc path
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files (x86)/VideoLAN/VLC");
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
		// Convert item path into array so its compatible with the player
		String [] paths = {item.getPath()};
		// Create player with media and correct height
		Player player = new Player((int) pane.getWidth(),(int) pane.getHeight());
		// If first player then add to first index in video controllers
		if (video_controllers.size() == 0){
			video_controllers.add(player);
			//video_controllers.add(player);
			StackPane.setAlignment(player, Pos.CENTER);
			pane.getChildren().add(player);
			player.loadPaths(paths);
			// Set width and height preferences
			player.prefWidthProperty().bind(pane.widthProperty());
			player.prefHeightProperty().bind(pane.heightProperty());
			player.minWidthProperty().bind(pane.minWidthProperty());
			player.minHeightProperty().bind(pane.minHeightProperty());
			player.maxWidthProperty().bind(pane.maxWidthProperty());
			player.maxHeightProperty().bind(pane.maxHeightProperty());

			
		}
		// If the second player then add to second index in video controllers
		else if (video_controllers.size() == 1){
			video_controllers.add(player);
			//video_controllers.add(player);
			StackPane.setAlignment(player, Pos.CENTER);
			pane.getChildren().add(player);
			player.loadPaths(paths);
			// Set width and height preferences
			player.prefWidthProperty().bind(pane.widthProperty());
			player.prefHeightProperty().bind(pane.heightProperty());
			player.minWidthProperty().bind(pane.minWidthProperty());
			player.minHeightProperty().bind(pane.minHeightProperty());
			player.maxWidthProperty().bind(pane.maxWidthProperty());
			player.maxHeightProperty().bind(pane.maxHeightProperty());
			
		} 

	}
	/** This method changes the video that the player will play
	 * 
	 * @param file_path the media path that will be loaded by the video player
	 * @param index_player the index of the player in the video_controllers that will have its media changed
	 */
	private void changeVideoPlayerVideo (String file_path, int index_player){
		System.gc();
		video_controllers.get(index_player).changeVideo(file_path);	
	}
	
	
	
	/** This method instantiates a Spotify player to be displayed on the comparison screen GUI. It takes a item
	 *  to be displayed and the pane to display the player on. Then it creates the player and adds it to the pane
	 * 
	 * @param item - the item that the player will play
	 * @param pane - the pane where the player will be added
	 */
	private void instantiateSpotifyPlayer(BasicItem item, Pane pane) {
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files (x86)/VideoLAN/VLC");
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
		String [] paths = {item.getPreview()};
		SpotifyPlayer player = new SpotifyPlayer((int) pane.getWidth(),(int) pane.getHeight());
		// If first player then add to first index in spotify controllers
		if (spotify_controllers.size() == 0){
			spotify_controllers.add(player);
			//video_controllers.add(player);
			StackPane.setAlignment(player, Pos.CENTER);
			pane.getChildren().add(player);
			player.loadPaths(paths);
			// Set width and height preferences
			player.prefWidthProperty().bind(pane.widthProperty());
			player.prefHeightProperty().bind(pane.heightProperty());
			player.minWidthProperty().bind(pane.minWidthProperty());
			player.minHeightProperty().bind(pane.minHeightProperty());
			player.maxWidthProperty().bind(pane.maxWidthProperty());
			player.maxHeightProperty().bind(pane.maxHeightProperty());
			player.setMeta(item.getMetadata());
			player.setArt(new Image(item.getImage().getUrl()));
			
		}
		// If second player then add to second index in spotify controllers
		else if (spotify_controllers.size() == 1){
			spotify_controllers.add(player);
			//video_controllers.add(player);
			StackPane.setAlignment(player, Pos.CENTER);
			pane.getChildren().add(player);
			player.loadPaths(paths);
			// Set width and height preferences
			player.prefWidthProperty().bind(pane.widthProperty());
			player.prefHeightProperty().bind(pane.heightProperty());
			player.minWidthProperty().bind(pane.minWidthProperty());
			player.minHeightProperty().bind(pane.minHeightProperty());
			player.maxWidthProperty().bind(pane.maxWidthProperty());
			player.maxHeightProperty().bind(pane.maxHeightProperty());
			player.setMeta(item.getMetadata());
			player.setArt(new Image(item.getImage().getUrl()));
			
		} 

	}
	/** This method changes the spotify preview that will be played by the spotify player
	 * 
	 * @param track_path  The path to the preview track
	 * @param index_player The index of the spotify player in spotify controllers that will have its track changed
	 */
	private void changeSpotifyPlayerSong (String track_path, int index_player){
		System.gc();
		spotify_controllers.get(index_player).changeSong(track_path);	
	}
	
    /**
     * Method to display another .fxml file within the current screen and bind its resize properties to that of
     * the current screen.
     * @param new_pane
     */
    private void showInSelf(Pane new_pane) {
    	
    	new_pane.prefWidthProperty().bind(root.widthProperty());
    	new_pane.prefHeightProperty().bind(root.heightProperty());
    	new_pane.minWidthProperty().bind(root.minWidthProperty());
    	new_pane.minHeightProperty().bind(root.minHeightProperty());
    	new_pane.maxWidthProperty().bind(root.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(root.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	root.getChildren().clear();
    	
    	root.setCenter(new_pane);
    	
    	root.requestFocus();
    	
    	audio_controllers = null;
    	image_controllers = null;
    	video_controllers = null;
    	
    	System.gc();
	
    }
	
}

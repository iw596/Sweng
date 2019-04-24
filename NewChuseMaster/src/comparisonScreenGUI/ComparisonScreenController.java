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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import listDataStructure.BasicItem;
import resultsScreenGUI.ResultsScreenController;
import videoPlayback.Player;
import videoPlayback.YoutubeController;

/**
 * Class for the comparison screen controller. This class handles all button listeners and interactivity
 * and holds a reference to the back-end code of the program for communication.
 * 
 * Date created: 13/03/2019
 * Date last edited: 15/04/2019
 * Last edited by: Dan Jackson and Isaac Watson
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
    	}else if(video_controllers.size() > 0) {
    		System.out.println(video_controllers.size());
    		for(int i = 0; i < video_controllers.size(); i++) {
    			video_controllers.get(i).exit();
    		}
    		video_controllers.remove(1);
    		video_controllers.remove(0);

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
    	
    	if(audio_controllers.size() > 0) {
    		for(int i = 0; i < audio_controllers.size(); i++) {
    			audio_controllers.get(i).exit();
    		}
    	} else if(image_controllers.size() > 0) {
    		for(int i = 0; i < image_controllers.size(); i++) {
    			image_controllers.get(i).exit();
    		}
    	} else if(video_controllers.size() > 0) {
    		System.out.println(video_controllers.size());
    		for(int i = 0; i < video_controllers.size(); i++) {
    			video_controllers.get(i).exit();
    		}
    		video_controllers.remove(1);
    		video_controllers.remove(0);
    	
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
     * Method to check whether the tournament algorithm has completed all it's rounds, and if not whether to start a new round or 
     * continue on the same round.
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
    		
    		//checks if the entire tournament is over
        	if(back_end.getComparison().isFinished()) {
        		
        		//if it is, checks if the odd item exists
        		if(back_end.getComparison().oddItemExists()) {
        			//if the odd item does exist, adds the odd item to the list, advances the round
        			//resets the algorithm's position
        			back_end.getComparison().addOddItemToList();
        			back_end.getComparison().advanceRound();
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
						FXMLLoader loader = new FXMLLoader(resultsScreenGUI.ResultsScreenController.class.getResource("ResultsScreen.fxml"));
	                	ResultsScreenController controller = new ResultsScreenController(back_end);
	                	loader.setController(controller);
	                	BorderPane new_pane = loader.load();
	                	showInSelf(new_pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

        		}
        	} else {
        		//if the tournament is not over, advances the round, resets the position and
        		//updates the text on the buttons with the next item's titles
        		back_end.getComparison().advanceRound();
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
		// TODO Auto-generated method stub
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
		if(left_object.getType().equals("VideoItem")) {
			instantiateVideoPlayer(left_object, left_content);
		} else if(left_object.getType().equals("ImageItem")) {
			instantiateImageViewer(left_object, left_content);
		} else if(left_object.getType().equals("AudioItem")) {
			instantiateAudioPlayer(left_object, left_content);
		}
		
		//if the right object is a video item, instantiate the video player
		if(right_object.getType().equals("VideoItem")) {
			instantiateVideoPlayer(right_object, right_content);
		} else if(right_object.getType().equals("ImageItem")) {
			instantiateImageViewer(right_object, right_content);
		} else if(right_object.getType().equals("AudioItem")) {
			instantiateAudioPlayer(right_object, right_content);
		}
		
	}
	
	/**
	 * Method to instantiate a YouTube video player within another pane on the JavaFX stage.
	 * 
	 * @param item - the item to retrieve the media data from
	 * @param pane - the pane to instantiate the video player in
	 */
	private void instantiateYouTubePlayer(BasicItem item, Pane pane) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(videoPlayback.YoutubeController.class.getResource("YoutubePlayer.fxml"));
			YoutubeController controller = new YoutubeController(item.getPath());
			loader.setController(controller);
			BorderPane youtube_root = loader.load();
			StackPane.setAlignment(youtube_root, Pos.CENTER);
			pane.getChildren().add(youtube_root);
			
			youtube_root.prefWidthProperty().bind(pane.widthProperty());
			youtube_root.prefHeightProperty().bind(pane.heightProperty());
			youtube_root.minWidthProperty().bind(pane.minWidthProperty());
			youtube_root.minHeightProperty().bind(pane.minHeightProperty());
			youtube_root.maxWidthProperty().bind(pane.maxWidthProperty());
			youtube_root.maxHeightProperty().bind(pane.maxHeightProperty());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	private void instantiateVideoPlayer(BasicItem item, Pane pane) {
		System.out.println("Called");
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files (x86)/VideoLAN/VLC");
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
		System.out.println(item.getPath());
		String [] paths = {item.getPath()};
		System.out.println(paths[0]);
		Player player = new Player((int) pane.getWidth(),(int) pane.getHeight());
		video_controllers.add(player);
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

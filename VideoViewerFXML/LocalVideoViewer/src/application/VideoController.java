package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer.Status;

/**
 * Controller for the VideoPlayer.fxml file. Initialises media player and UI components.
 * Instantiates UI action listeners.
 * 
 * Date created: 13/03/2019
 * Date last edited: 13/03/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden
 *
 */
public class VideoController extends BorderPane implements Initializable {
	
	// UI component Id's from fxml file
	@FXML BorderPane root_pane;
	@FXML StackPane media_pane;
	@FXML HBox bar_box;
	@FXML MediaView media_view;
	@FXML JFXButton play_button;
	@FXML Slider time_slider;
	@FXML Label volume_label;
	@FXML Slider volume_slider;
	// Create Media
	Media media;
	// Create Media Player
	MediaPlayer media_player;
	
	// Local file to be played
	String path = "1280.mp4";
	
	// Components for displaying error when incorrect file path is loaded
	private Image error_image;
	private ImageView error_viewer;
	private Boolean error_found;
	
	private Stage primaryStage;
	
	/**
	 * Method for initialising VideoController. Instantiates UI components and prepares Media Player for display.
	 * This class also sets up the listeners which listen for the user input.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {		
		// assume no error initially
		error_found = false;
		// Load error image from directory
		error_image = new Image(new File("ErrorTest.jpg").toURI().toString());
		error_viewer = new ImageView(error_image);
		
		// UI Media Bar Components
		bar_box.setAlignment(Pos.CENTER); // Set bar alignment to centre
		play_button.setPrefWidth(30);
		volume_slider.setPrefWidth(70); 
        volume_slider.setMinWidth(30); 
        volume_slider.setValue(100);
		HBox.setHgrow(time_slider, Priority.ALWAYS); // Make box grow if slider is long
		bar_box.setStyle("-fx-background-color:#bfc2c7");
		media_pane.setStyle("-fx-background-color:BLACK");
		bar_box.setPadding(new Insets(5, 10, 5, 10));
		StackPane.setAlignment(media_view, Pos.CENTER);
		
		// File of local video to be played. File must be in project directory.
		File file = new File(path);
		
		// Ensure media bar is not clipped when resizing window
		media_pane.setMinHeight(0);
		
		// Check that file exists to avoid crashing
		if (file.exists() && !file.isDirectory()){
			// Load file into media
			Media media = new Media(file.toURI().toString());
			// Create media player and load media
			media_player = new MediaPlayer(media);
			// Set media player to media view
			media_view.setMediaPlayer(media_player);
		
			// Adjust media view with window, preserve original aspect ratio
			media_view.setPreserveRatio(true);
			media_view.fitWidthProperty().bind(Bindings.selectDouble(media_view.sceneProperty(), "width"));
			media_view.fitHeightProperty().bind(Bindings.selectDouble(media_view.sceneProperty(), "height"));
		
			// Play media
			media_player.setAutoPlay(true);
		
			// Initialise action listeners for time slider and volume slider
			UpdateTime();
			JumpTime();
			VolumeChange();
		}
		// If file does not exist
		else{
			// Set error as found
			error_found = true;
			
			// Display error image
			media_pane.getChildren().add(error_viewer);
			
			// Resize image with window, preserve original aspect ratio
			media_view.setPreserveRatio(false);
			error_viewer.fitWidthProperty().bind(Bindings.selectDouble(error_viewer.sceneProperty(), "width"));
			error_viewer.fitHeightProperty().bind(Bindings.selectDouble(error_viewer.sceneProperty(), "height"));
			
			// Print error message to console
			System.out.println("Error: Movie not Found");
		}
	}
	
	@FXML
	/**
	 * Action listener for clicking the screen to Play/Pause video or double-click to set player to fullscreen
	 * @param action
	 */
	public void ScreenPressed(MouseEvent action){
		// Only accept left-click
		if(action.getButton().equals(MouseButton.PRIMARY)) {
			// If double-click
			if(action.getClickCount() == 2) {
				// Set fullscreen
    			primaryStage = (Stage)root_pane.getScene().getWindow();
    			primaryStage.setFullScreen(true);
    		}
		    // If playing then pause
		    if (media_player.getStatus().equals(Status.PLAYING)){
		    	media_player.pause();
		    	// Update button text
		    	play_button.setText(">"); 
		    }
		    // If paused then play
		    else {
		    	media_player.play();
		    	// Update button text
		    	play_button.setText("||"); 
		    }
    	}
	}
	
	@FXML
	/**
	 * Action listener for pausing player when Play/Pause button is pressed
	 * @param action
	 */
	public void PlayPressed(MouseEvent action){
		// Ignore if error has previously been found with filepath
		if (error_found == false){
			Status status = media_player.getStatus(); // To get the status of Player 
			// If the status is Video playing 
			if (status == Status.PLAYING) { 
				// If the player is at the end of video 
	            if (media_player.getCurrentTime().greaterThanOrEqualTo(media_player.getTotalDuration())) { 
	            	media_player.seek(media_player.getStartTime()); // Restart the video 
	                media_player.play(); 
	            } 
	            else { 
	                // Pausing the player 
	            	media_player.pause(); 
	                play_button.setText(">"); 
	            } 
	        } // If the video is stopped, halted or paused 
	        if (status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) { 
	        	media_player.play(); // Start the video 
	            play_button.setText("||"); 
	        } 
		}
	}
	
	/**
	 * Method for initialising action listener to update position of the time slider
	 */
	public void UpdateTime() {
		media_player.currentTimeProperty().addListener(new InvalidationListener() { 
            public void invalidated(Observable ov) 
            { 
            	Platform.runLater(new Runnable() { 
                    public void run() 
                    { 
                        // Updating to the new time value 
                        // This will move the slider while running the video 
                    	 time_slider.setValue(media_player.getCurrentTime().toMillis()/media_player.getTotalDuration().toMillis()*100);                 	
                    } 
                }); 
            } 
        }); 
	}
	
	/**
	 * Method for initialising action listener to skip to respective video time when slider position is changed
	 */
	public void JumpTime() {
		// In order to jump to the certain part of video 
        time_slider.valueProperty().addListener(new InvalidationListener() { 
            public void invalidated(Observable ov) 
            { 
                if (time_slider.isPressed()) { 
                    // play selected position of video
                    media_player.seek(media_player.getMedia().getDuration().multiply(time_slider.getValue()/100)); 
                } 
            } 
        });
	}
	
	/**
	 * Method for initialising action listener to change volume when slider position is changed
	 */
	public void VolumeChange() {
		// providing functionality to volume slider 
		volume_slider.valueProperty().addListener(new InvalidationListener() { 
			public void invalidated(Observable ov){ 
				if (volume_slider.isPressed()) { 
					media_player.setVolume(volume_slider.getValue()/100); // set the volume 
			    } 
			} 
		}); 
	}
}

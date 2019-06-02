package spotifyplayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
/**
 * Controls is the  class in the player package. This class handles the generation
 * of a resizable transport control used to control a spotify player object. This object
 * extends a HBox.
 * 
 * Date created: 27/04/2019
 * Date last edited 30/05/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson 
 */

public class Controls extends HBox {
	
	public Button play_pause;
	private Label time_label = new Label("Time:");
	private Slider time_slider;
	// Text to show current time
	private Text current_time_text = new Text("0.00");
	
	EmbeddedMediaPlayer media_player;
	Renderer renderer;
	protected boolean update_scrubber = true;
	
	private Text no_preview_text = new Text("No preview available.");

	/**
	 * Constructor for a video player's controls.
	 * @param renderer - the renderer where the controls will be added.
	 */
	public Controls(Renderer renderer){
		this.renderer = renderer;
		this.media_player = renderer.getPlayer();

		
		play_pause = new Button("||");
		time_slider = new Slider();
		time_slider.setValue(0);
		//time_slider.setPrefWidth(100);
		
		setAlignment(Pos.BOTTOM_CENTER);
		
		getChildren().add(play_pause);
		getChildren().add(time_label);
		getChildren().add(time_slider);
		getChildren().add(current_time_text);
		setStyle("-fx-background-color: #FFFFFF;");
		
		
		// Add Listener for play/pause button
        // Set up event handler to enable button press to pause video
        play_pause.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	// If playing then pause
            	if(media_player.status().isPlaying() == true){
            		media_player.controls().pause();
            		play_pause.setText(">"); 
            	}
            	
                // If the video paused then play 
                else if (media_player.status().isPlaying() == false) { 
                	media_player.controls().play();
                	play_pause.setText("||"); 
                	
                } 
            	
   
            } 
        });
        
        // Add listner for mouse click
        renderer.getPlayerHolder().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
 			
 		    @Override
 		    // If mouse is pressed check if player is currently paused or running
 		    public void handle(MouseEvent mouseEvent) {
 		    	// Make sure its a left mouse click
 				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
 					// Do not pause when onScreen time slider is pressed
 				    if (mouseEvent.getPickResult().getIntersectedNode().toString().contains("Canvas")) {
 				   	   // If playing then pause
 		            	if(media_player.status().isPlaying() == true){
 		            		media_player.controls().pause();
 		            		play_pause.setText(">"); 
 		            	}
 		            	
 		                // If the video paused then play 
 		                else if (media_player.status().isPlaying() == false) { 
 		                	media_player.controls().play();
 		                	play_pause.setText("||"); 
 		                	
 		                } 
 					}
 		    	}
 			}
 		});
      
        //adds event listener for when the video's time changes
        media_player.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter(){
        	/**
            *
            * @param mediaPlayer play video
            * @param newTime get every millisecond changed in the mediaplayer
            * when is in playing mode
            */
        	 @Override
			public void timeChanged (MediaPlayer  media_player, long new_time) {
				Float time = (float) new_time;
				System.out.println("New time: " + time);
				Float length = (float) (media_player.status().length());
				System.out.println("Fraction: " + time/length);
				// Update the scrubber, providing a fractional value of (current time / length)
				updatesValues(time/length);
		

			}
			
		}); 
        

		// This event happens when the user begins a mouse click on the scrubber
        time_slider.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				update_scrubber = false; // Prevent the scrubber position being updated by any playing audio
			}
		});
		// This event happens when the user begins a mouse click on the scrubber and drags it
        time_slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				update_scrubber = false; // Prevent the scrubber position being updated by any playing audio
			}
		});
		// This event happens when the user releases a mouse click from the scrubber
        time_slider.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				seek((float) time_slider.getValue()); // Set the play position to the new scrubber position
				
				update_scrubber = true; // Allow the scrubber position to be updated by any playing audio
				
			}
		});
        
	}
		
    /** 
     * Method for seeking to a different time in the video.
     * @param fraction fraction of the video we are interested in skipping too
     */
	public void seek(Float fraction) {
		// If slider has been moved to the end then set to value just below 100, this prevents a VLC glitch where end of video is not 
		// recognised
		if (fraction > 99.99) {
			fraction = (float) 99.99;	
		}
		// Convert fraction into actual time and move to that area of the track
		float time = (float) (media_player.status().length()) * fraction/100;
		media_player.controls().skipTime((long) time - media_player.status().time());

	}
	
	/**
	 * This protected method is used to update the timer slider of the video as
	 * the time changes.
	 */
    protected void updatesValues(Float fraction) { 
  
        // Updating to the new time value 
    	// This will move the slider while running the video 
    
    	if (update_scrubber) {
    		time_slider.setValue(fraction * 100);	
		    // Get current time of video and the length of the video, convert to seconds and minutes
		    long length_millis = media_player.status().length();
		    long length_minutes = (length_millis / 1000)  / 60;
		    // Subtract one off the length in seconds otherwise timer will usually be one second longer than
		    // length of video due to rounding error
        	int length_seconds = (int)((length_millis / 1000) % 60);
        	
        	long current_millis =  media_player.status().time();
        	long current_minutes = (current_millis / 1000)  / 60;
        	int current_seconds = (int)((current_millis / 1000) % 60);
        	
        	String current_minutes_text = new String();
        	String current_seconds_text = new String();
        	
        	String length_video_minutes_text = new String();
        	String length_video_seconds_text = new String();
        	
        	// Prefix a zero to minutes and seconds if necessary to ensure mm:ss format 
        	if (current_minutes < 10) {
        		current_minutes_text = "0" + Long.toString(current_minutes);
        	}
        	else {
        		current_minutes_text = Long.toString(current_minutes);
        	}
        	if (current_seconds< 10) {
        		current_seconds_text = "0" + Long.toString(current_seconds);
        	}
        	else {
        		current_seconds_text = Long.toString(current_seconds);
        		
        	}

        	// Repeat the string manipulation for the string storing length of video
        	if (length_minutes < 10) {
        		length_video_minutes_text = "0" + Long.toString(length_minutes);
        	}
        	else {
        		length_video_minutes_text = Long.toString(length_minutes);
        	}
        	
        	if (length_seconds< 10) {
        		length_video_seconds_text = "0" + Long.toString(length_seconds);
        	}
        	else {
        		length_video_seconds_text = Long.toString(length_seconds);
        	}
        	current_time_text.setText(current_minutes_text + ":" + current_seconds_text + "/" + length_video_minutes_text + ":" + length_video_seconds_text);
    	}
    	  
    		
    }
    
    /** If there is no preview available then display this to the user 
     * 
     */
    public void noPreviewScreen(){
        getChildren().remove(play_pause); 
        getChildren().remove(time_label);
        getChildren().remove(time_slider);
        getChildren().remove(current_time_text);
        // Add no preview message
        getChildren().add(no_preview_text );
    	
    }


    
}
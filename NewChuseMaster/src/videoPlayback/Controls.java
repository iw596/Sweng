package videoPlayback;

import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 * Controls is the  class in the player package. This class handles the generation
 * of a resizable transport control used to control a video player object. This object
 * extends a HBox.
 * 
 * Date created: 18/03/2019
 * Date last edited 15/04/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson 
 */
public class Controls extends HBox {
	
	//Instantiate Buttons
	Button play_pause;


	//Instantiate sliders
	Slider time_scrubber;
	
	
	//Labels

	Label time_label = new Label("Time: "); 
	
	// Text to show current time
	Text current_time_text = new Text("0.00");
	
	DecimalFormat format = new DecimalFormat("#.##"); // To format time
	
	// Boolean to stop blank end screen being played.
	boolean over = false;
	
	DirectMediaPlayerComponent media_player_component;
	boolean updateScrubber = true;

	/**
	 * Constructor for a video player's controls.
	 * @param player the video player the controls are for.
	 */
	public Controls (Player player) {
		setFillHeight(true);
		this.media_player_component = player.getMediaPlayerComponent();
		media_player_component.getMediaPlayer().setRepeat(true);
		//Create buttons
		play_pause = new Button("||");  
	
	
		//Create sliders
		time_scrubber = new Slider();
		time_scrubber.setValue(0);
		time_scrubber.setPrefWidth(100);
		
        

        
        // Place graphical change in runnable to prevent javafx screen rendering error
        Platform.runLater(new Runnable() {
        	
        	
 
            @Override
            public void run() {
                setAlignment(Pos.CENTER); // setting the HBox to center 
                // Adding the components to the bottom 
                getChildren().add(play_pause); 
                getChildren().add(time_label);
                getChildren().add(time_scrubber);
                HBox.setHgrow(time_scrubber, Priority.ALWAYS);
                getChildren().add(current_time_text);
                HBox.setHgrow(current_time_text, Priority.ALWAYS);
            }
        });

        // Add Listener for play/pause button
        // Set up event handler to enable button press to pause video
        play_pause.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	// If playing then pause
                if (media_player_component.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Playing && over == false) { 
                	media_player_component.getMediaPlayer().pause(); 
                	play_pause.setText(">"); 
                
                } 
                // If the video paused then play 
                else if (media_player_component.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Paused && over == false) { 
                	media_player_component.getMediaPlayer().play();
                	play_pause.setText("||"); 
                	
                } 
            } 
        });


        // Add listner for mouse click
		player.getPlayer_holder().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			
		    @Override
		    // If mouse is pressed check if player is currently paused or running
		    public void handle(MouseEvent mouseEvent) {
		    	// Make sure its a left mouse click
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					// Do not pause when onScreen time slider is pressed
				    if (!mouseEvent.getPickResult().getIntersectedNode().toString().contains("HBox")) {
		            	// If playing then pause
		                if (media_player_component.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Playing && over == false) { 
		                	media_player_component.getMediaPlayer().pause(); 
		                	play_pause.setText(">"); 
		                
		                } 
		                // If the video paused then play 
		                else if (media_player_component.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Paused && over == false) { 
		                	media_player_component.getMediaPlayer().play();
		                	play_pause.setText("||"); 
		                	
		                } 
					}
		    	}
			}
		});
        

        
        
        //adds event listener for when the video's time changes
        media_player_component.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            /**
            *
            * @param mediaPlayer play video
            * @param newTime get every millisecond changed in the mediaplayer
            * when is in playing mode
            */
			public void timeChanged (uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer, long newTime) {
				Float time = (float) newTime;
				Float length = (float) ((uk.co.caprica.vlcj.player.MediaPlayer) mediaPlayer).getLength();

				// Update the scrubber, providing a fractional value of (current time / length)
				updatesValues(time/length);
		

			}

			
		

		}); 
        

		// This event happens when the user begins a mouse click on the scrubber
        time_scrubber.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				updateScrubber = false; // Prevent the scrubber position being updated by any playing audio
			}
		});
		// This event happens when the user begins a mouse click on the scrubber and drags it
        time_scrubber.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				updateScrubber = false; // Prevent the scrubber position being updated by any playing audio
			}
		});
		// This event happens when the user releases a mouse click from the scrubber
        time_scrubber.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("Scrubber value: " + time_scrubber.getValue());
				seek((float) time_scrubber.getValue()); // Set the play position to the new scrubber position
				
				updateScrubber = true; // Allow the scrubber position to be updated by any playing audio
					
		
				
			
				
			}
		});
        
	}
	
	/**
	 * This protected method is used to update the timer slider of the video as
	 * the time changes.
	 */
    protected void updatesValues(Float fraction) { 
  
        // Updating to the new time value 
    	// This will move the slider while running the video 
    
    	if (updateScrubber) {
    		// Place everything in a runlater runnable in order to prevent Javafx rendering error when
    		// screen is resized
    	     Platform.runLater(new Runnable() {
    	    	 @Override
    	         public void run() {
		    		time_scrubber.setValue(fraction * 100);	
		    		// Get current time of video and the length of the video, convert to seconds and minutes
		        	long lengtht_millis = media_player_component.getMediaPlayer().getLength();
		        	long length_minutes = (lengtht_millis / 1000)  / 60;
		        	// Subtract one off the length in seconds otherwise timer will usually be one second longer than
		        	// length of video due to rounding error
		        	int length_seconds = (int)((lengtht_millis / 1000) % 60);
		        	
		        	long current_millis = media_player_component.getMediaPlayer().getTime();
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
		    	     });
    		
    	}

    }
    
    /** 
     * Method for seeking to a different time in the video.
     * @param fraction
     */
	public void seek(Float fraction) {
		// If slider has been moved to the end then set to value just below 100, this prevents a VLC glitch where end of video is not 
		// recognised
		if (fraction > 99.99) {
			System.out.println("In frac > 99 ");
			fraction = (float) 99.99;
			
		}
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Convert fraction into actual time

		float time = media_player_component.getMediaPlayer().getLength() * fraction/100;
		System.out.println("Current time: " + media_player_component.getMediaPlayer().getTime());
		media_player_component.getMediaPlayer().skip((long) time - media_player_component.getMediaPlayer().getTime());
		System.out.println(media_player_component.getMediaPlayer().getMediaPlayerState());
		if (media_player_component.getMediaPlayer().getMediaPlayerState().equals("libvlc_Ended")){
			//media_player_component.getMediaPlayer();
			System.out.println(media_player_component.getMediaPlayer().getMediaPlayerState());
		}
	}

	

    
    
}
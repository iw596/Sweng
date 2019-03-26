package player;

import java.util.concurrent.TimeUnit;

import com.sun.media.jfxmedia.MediaPlayer;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;

public class Controls extends HBox {
	
	//Instantiate Buttons
	Button play_pause;
	Button next;
	Button previous;
	Button Stop;

	//Instantiate sliders
	Slider time_scrubber;
	Slider volume;
	
	//Labels
	Label volume_label = new Label("Volume: "); 
	Label time_label = new Label("Time: "); 
	
	DirectMediaPlayerComponent media_player_component;
	boolean updateScrubber = true;
	
	// Variable used to allow seeking while the media player is stopped
	// It stores the value of where the the media player should begin its next playback
	private Float nextPlayPosition = (float) 0;
	
	// Store Player obect
	private Player player;
	
	
	public Controls (Player player) {
		this.player = player;
		this.media_player_component = player.getMediaPlayerComponent();
		//Create buttons
		play_pause = new Button("||");  
		next = new Button(">>");
		previous = new Button("<<");
		//Create sliders
		time_scrubber = new Slider();
		time_scrubber.setValue(0);
		time_scrubber.setPrefWidth(100);
		volume = new Slider();
        
		// Setting the preference for volume bar 
        volume.setPrefWidth(70); 
        volume.setMinWidth(30); 
        volume.setValue(100);  // Set volume to be max
		
        setAlignment(Pos.CENTER); // setting the HBox to center 
        //setPadding(new Insets(5, 10, 5, 10));  // Add some padding between bar and video
		
        
        // Adding the components to the bottom 
        getChildren().add(play_pause); 
        getChildren().add(next); 
        getChildren().add(previous); 
        getChildren().add(volume_label);
        getChildren().add(volume);
        getChildren().add(time_label);
        getChildren().add(time_scrubber);
        
        
        // Add Listener for play/pause button
        // Set up event handler to enable button press to pause video
        play_pause.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	// If playin then pause
                if (media_player_component.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Playing) { 
                	media_player_component.getMediaPlayer().pause(); 
                	play_pause.setText(">"); 
                
                } 
                // If the video paused then play 
                else {
                	media_player_component.getMediaPlayer().play();
                	play_pause.setText("||"); 
                	
                } 
            } 
           });
        
        // Add Listener for volume slider
        volume.valueProperty().addListener(new InvalidationListener() { 
            public void invalidated(Observable ov) 
            { 
                if (volume.isPressed()) { 
                	System.out.println(media_player_component.getMediaPlayer().getLength());
                	// Small multiply factor to ensure that at low levels there is still some sound
                	media_player_component.getMediaPlayer().setVolume((int) (volume.getValue() * 1.25));  
                } 
            } 
        });
        
        
        
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
			
			public void mediaStateChanged(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer, int newState) {
				System.out.println("new state is: " + newState);
				
			}
			
			
			
			
			/** Method to listen for end of video. If video ends load next video in
			 *  the path array. If no more videos to load then disable next buton
			 *  and display black screen.
			 * 
			 */
			
		 /*@Override
			public void finished(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer) {
				// Prevent a JavaFX thread error by calling runLater
				Platform.runLater(new Runnable(){
					@Override
					public void run() {
						int nextIndex = player.getCurrentIndex() + 1;
						// If more videos to be loaded then load
						if (nextIndex < player.sizePaths()) {
							player.loadVideo(nextIndex);
							player.setCurrentIndex(nextIndex);
							
							
						}
						// Else display a black screen
						else {
							// Code to load in black screen image
						}
						
						
						
					}
				});
			}	*/
			 @Override
				public void finished(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer) {
					// Prevent a JavaFX thread error by calling runLater
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							if (mediaPlayer.getTime() < 50) {
								System.out.println("False trigger");
							}
							else {
								int nextIndex = player.getCurrentIndex() + 1;
								// If more videos to be loaded then load
								if (nextIndex < player.sizePaths()) {
									player.loadVideo(nextIndex);
									player.setCurrentIndex(nextIndex);
									
									
								}
								// Else display a black screen
								else {
									// Code to load in black screen image
								}
								
							}

							
							
							
						}
					});
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
				System.out.println("The fraction when released is: " + time_scrubber.getValue());
				seek((float) time_scrubber.getValue()); // Set the play position to the new scrubber position
				updateScrubber = true; // Allow the scrubber position to be updated by any playing audio
			}
		});
        

        
        
        
		
		
		
		
	}
	
	/**
	 * This protected method is used to update the timer slider of the video as
	 * the time changes
	 */
    protected void updatesValues(Float fraction) { 
  
        // Updating to the new time value 
                // This will move the slider while running your video 
    	System.out.println("Function called. Fraction is: " + fraction);
    	if (updateScrubber) {
    		time_scrubber.setValue(fraction * 100);	
    		
    	}
    	
        
        System.out.println("The scrubber value is: " + time_scrubber.getValue());
    }
    
	public void seek(Float fraction) {
		System.out.println("The fraction is: " + fraction);
		
		// Convert fraction into actual time
		float time = media_player_component.getMediaPlayer().getLength() * fraction/100;
		media_player_component.getMediaPlayer().skip((long) time - media_player_component.getMediaPlayer().getTime());

		/*
		// If the media player is not stopped, set its playback position
		if (media_player_component.getMediaPlayer().isPlaying()) {
			media_player_component.getMediaPlayer().skip((long) time - media_player_component.getMediaPlayer().getTime());
			System.out.println("In if statement, time of video is: " + media_player_component.getMediaPlayer().getTime());
			//media_player_component.getMediaPlayer().play();
		}
		else {
			// If the media player is stopped, setting its playback position won't work
			// Instead we must store the value until the player is restarted
			System.out.println("In else");
			nextPlayPosition = time;
		}*/
	}
    
    
}

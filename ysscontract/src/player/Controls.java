package player;

import java.text.DecimalFormat;
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
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
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
	
	// Text to show current time
	Text current_time_text = new Text("0.00");
	
	DecimalFormat format = new DecimalFormat("#.##"); // To format time
	
	// Boolean to stop blank end screen being played.
	boolean over = false;
	
	DirectMediaPlayerComponent media_player_component;
	boolean updateScrubber = true;
	
	// Variable used to allow seeking while the media player is stopped
	// It stores the value of where the the media player should begin its next playback
	private Float nextPlayPosition = (float) 0;
	
	// Store Player obect
	private Player player;
	
	
	public Controls (Player player) {
		//HBox.setHgrow(time_scrubber, null);
		setFillHeight(true);
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
        Platform.runLater(new Runnable() {
            @Override
                public void run() {
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
                HBox.setHgrow(time_scrubber, Priority.ALWAYS);
                getChildren().add(current_time_text);
                HBox.setHgrow(current_time_text, Priority.ALWAYS);
                    // draw stuff
                }
            });

      
        
        
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
                else if (media_player_component.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Paused && over == false) { 
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
        
        next.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) {
            	
            	if (player.getCurrentIndex() < player.sizePaths() -1) {
            		Platform.runLater(new Runnable(){
						@Override
						public void run() {
							int nextIndex = player.getCurrentIndex() + 1;
							// If more videos to be loaded then load
							player.loadVideo(nextIndex);
							player.setCurrentIndex(nextIndex);
						}
            		});
									
            	}
            	
            	
            }
        
        });
        
        previous.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) {
            	
            	if (player.getCurrentIndex() > 0 && over == false)  {
            		Platform.runLater(new Runnable(){
						@Override
						public void run() {
							int nextIndex = player.getCurrentIndex() - 1;
							// If more videos to be loaded then load
							player.loadVideo(nextIndex);
							player.setCurrentIndex(nextIndex);
						}
            		});
									
            	}
            	
            	else if (over == true) {
            		Platform.runLater(new Runnable(){
						@Override
						public void run() {
							int nextIndex = player.getCurrentIndex() ;
							// If more videos to be loaded then load
							player.loadVideo(nextIndex);
							player.setCurrentIndex(nextIndex);
							over = false;
							updateScrubber = true;
							
						}
            		});
            		
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

			
			/** Method to listen for end of video. If video ends load next video in
			 *  the path array. If no more videos to load then disable next buton
			 *  and display black screen.
			 * 
			 */
			
			 @Override
				public void finished(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer) {
					// Prevent a JavaFX thread error by calling runLater
				 	if (over == false) {
						Platform.runLater(new Runnable(){
							@Override
							public void run() {
								// Youtube videos and error screen initially start with zero time, so need to check time
								// before confirming that video is actually over
								if (mediaPlayer.getTime() < 50 && player.in_error == false) {
									// Set in_error to true as we want to be able to play next video eventually
									if (player.in_error == false) {
										player.in_error = true;
									}
									System.out.println("False trigger");
								}
								else {
									int nextIndex = player.getCurrentIndex() + 1;
									// If more videos to be loaded then load
									if (nextIndex < player.sizePaths()) {
										player.loadVideo(nextIndex);
										player.setCurrentIndex(nextIndex);
										player.in_error = false;
										
										
									}
									// Else display a black screen and alert user no videos left
									else {
										player.loadEndScreen();
									
										mediaPlayer.pause();
										
										
										
										
										
									}
									
								}
	
								
								
								
							}
						});
			 		}
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
    	//System.out.println("Function called. Fraction is: " + fraction);
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

    //	System.out.println("The scrubber value is: " + time_scrubber.getValue());
    }
    
	public void seek(Float fraction) {
	//	System.out.println("The fraction is: " + fraction);
		if (fraction > 99.9) {
			fraction = (float) 99.9;
		}
		
		// Convert fraction into actual time
		float time = media_player_component.getMediaPlayer().getLength() * fraction/100;
		media_player_component.getMediaPlayer().skip((long) time - media_player_component.getMediaPlayer().getTime());
		

	}
	
	
	protected void loadingText() {
		this.current_time_text.setText("Loading next video.");
	}
	protected void endText() {
		this.current_time_text.setText("No more videos to play.");
	}
	

    
    
}

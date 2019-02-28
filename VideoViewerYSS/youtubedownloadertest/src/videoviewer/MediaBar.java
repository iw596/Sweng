package videoviewer;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/** MediaBar is a class in the videoviewer package, this is a back end class which
 * adds a volume slider, time slider and play/pause button to a media player. The code
 * for this class was adapted from https://www.geeksforgeeks.org/javafx-building-a-media-player/
 * 
 * Date created: 21/02/2019
 * Date last edited: 21/02/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson
 *
 */
public class MediaBar extends HBox {
	// Initalise sliders button and a label
	Slider time = new Slider(); 
	Slider volume = new Slider();
	Button play_button = new Button("||");
	Button next_button = new Button(">>");
	Button prev_button = new Button("<<");
	Label volume_label = new Label("Volume: ");
	DecimalFormat df = new DecimalFormat("#.##");
	Text text_current_time = new Text("0.00");

	
	// Create the player 
	MediaPlayer player; 
	
	/** This is the constructor for the MediaBar class. This class takes a media player
	 * and adds buttons and sliders to the player which allow it to be controlled. This
	 * class also sets up the listeners which listen for the user input.
	 * 
	 * @param  player- MediapPlayer object which we want to add controls to 
	 */
	public MediaBar(VideoViewer video_viewer) {
		this.player = video_viewer.media_player;
        setAlignment(Pos.CENTER); // setting the HBox to center 
        setPadding(new Insets(5, 10, 5, 10));  // Add some padding between bar and video
        // Settih the preference for volume bar 
        volume.setPrefWidth(70); 
        volume.setMinWidth(30); 
        volume.setValue(100);  // Set volume to be max
        HBox.setHgrow(time, Priority.ALWAYS); // Make box grow if slider is long
        play_button.setPrefWidth(30); 
        next_button.setPrefWidth(35);
        prev_button.setPrefWidth(35); 
        
    	text_current_time.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	text_current_time.setFill(Color.WHITE);

        // Adding the components to the bottom 
        getChildren().add(play_button); // Playbutton 
        getChildren().add(next_button); // Nextbutton
        getChildren().add(prev_button); // Nextbutton 
        getChildren().add(time); // time slider 
        getChildren().add(text_current_time);
        getChildren().add(volume_label);
        getChildren().add(volume); // volume slider 
        
        
        // Set up event handler to enable button press to pause video
        play_button.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                Status status = player.getStatus(); // To get the status of Player 
                if (status == status.PLAYING) { 

                    // If the status is Video playing 
                    if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) { 

                        // If the player is at the end of video 
                        player.seek(player.getStartTime()); // Restart the video 
                        player.play(); 
                    } 
                    else { 
                        // Pausing the player 
                        player.pause(); 
                        play_button.setText(">"); 
                    } 
                } // If the video is stopped, halted or paused 
                if (status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) { 
                    player.play(); // Start the video 
                    play_button.setText("||"); 
                } 
            } 
        });
        
        next_button.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	video_viewer.playNextVideo();
            } 
        });
        
        prev_button.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	video_viewer.playPrevVideo();
            } 
        });
        
     // Providing functionality to time slider 
        player.currentTimeProperty().addListener(new InvalidationListener() { 
            public void invalidated(Observable ov) 
            { 
                updatesValues(); 
            } 
        }); 

        // Inorder to jump to the certain part of video 
        time.valueProperty().addListener(new InvalidationListener() { 
            public void invalidated(Observable ov) 
            { 
                if (time.isPressed()) { // It would set the time 
                    // as specified by user by pressing 
                    player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100)); 
                } 
            } 
        });
        // providing functionality to volume slider 
        volume.valueProperty().addListener(new InvalidationListener() { 
            public void invalidated(Observable ov) 
            { 
                if (volume.isPressed()) { 
                    player.setVolume(volume.getValue() / 100); // It would set the volume 
                    // as specified by user by pressing 
                } 
            } 
        }); 
        
        
	}
	
	/**
	 * This protected method is used to update the timer slider of the video as
	 * the time changes
	 */
    protected void updatesValues() 
    { 
        Platform.runLater(new Runnable() { 
            public void run() 
            { 
                // Updating to the new time value 
                // This will move the slider while running your video 
            	 time.setValue(player.getCurrentTime().toMillis()/                                      
            	player.getTotalDuration().toMillis()*100);
            	 
            	 Duration currentTime = player.getCurrentTime();
            	 //double currentTimeMin = currentTime;
            	 System.out.println(player.getCurrentTime());
            	
            	 //text_current_time.setText("" + df.format(currentTime) + "");
            } 
        }); 
    }
    

	

}

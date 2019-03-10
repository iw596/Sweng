package videoviewer;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/** MediaBar is a class in the videoviewer package, this is a back end class which
 * adds time slider to a media player. The code
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
	// Initalise slider
	Slider time = new Slider(); 

	// Create the player 
	MediaPlayer player; 
	
	/** This is the constructor for the MediaBar class. This class takes a media player
	 * and adds buttons/sliders to the player which allow it to be controlled. This
	 * class also sets up the listeners which listen for the user input.
	 * 
	 * @param  player- MediapPlayer object which we want to add controls to 
	 */
	public MediaBar(MediaPlayer player) {
		this.player = player;
        setAlignment(Pos.BOTTOM_CENTER); // setting the HBox to the bottom 
        setPadding(new Insets(5, 10, 5, 10));  // Add some padding between bar and video

        HBox.setHgrow(time, Priority.ALWAYS); // Make box grow if slider is long
        
        // Adding the components to the bottom
        getChildren().add(time); // time slider 

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
            	 time.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
            } 
        }); 
    }
}

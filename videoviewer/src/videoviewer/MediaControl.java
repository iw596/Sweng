package videoviewer;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/*** MediaControl is a public class which exteneds the BorderPane class. This class creates 
 * a object which adds controls to the VideoViewer class, These controls are
 * video scrubbing, volume control and a play/pause button. Large amounts
 * of code taken from https://www.geeksforgeeks.org/javafx-building-a-media-player/
 * 
 * Date created: 21/02/2019
 * Date last edited: 21/02/2019
 * Last edited by: Isaac Watson 
 * @author Isaac Watson
 *
 */

public class MediaControl extends BorderPane {
   // Objects assocaited with the media player
	private MediaPlayer media_player;
    private MediaView media_viewer;
    
    // St up sliders,labels etc
    private final boolean repeat = false;
    private boolean stop_requested = false;
    private boolean at_end_of_media = false;
    private Duration duration;
    private Slider time_slider;
    private Label play_time;
    private Slider volume_slider;
    private HBox media_bar;

    /**
     * 
     * @param mp - mediaplayer object which we want to add controls to
     */
    public MediaControl(final MediaPlayer mp) {
        this.media_player = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        media_viewer = new MediaView(mp);
        // Set up a mvPane
        Pane mvPane = new Pane() {                };
        // Add viewer to the pane and the set the back ground colour
        mvPane.getChildren().add(media_viewer);
        mvPane.setStyle("-fx-background-color: black;"); 
        setCenter(mvPane);
        // Create a horizontal box to store controls and add a bit of padding
        media_bar = new HBox();
        media_bar.setAlignment(Pos.CENTER);
        media_bar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(media_bar, Pos.CENTER);
        
        // Create a play button event handler which when pressed plays the video an
        final Button playButton  = new Button(">");
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Status status = mp.getStatus();
         
                if (status == Status.UNKNOWN  || status == Status.HALTED)
                {
                   // don't do anything in these states
                   return;
                }
         
                  if ( status == Status.PAUSED
                     || status == Status.READY
                     || status == Status.STOPPED)
                  {
                     // If at end of the video then pressing play button rewinds the video
                     if (at_end_of_media) {
                        media_player.seek(media_player.getStartTime());
                        at_end_of_media = false;
                     }
                     media_player.play();
                     } else {
                    	 media_player.pause();
                     }
                 }
           });
        
        // Add listener which monitors time of video
        media_player.currentTimeProperty().addListener(new InvalidationListener() 
        {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });
 
        media_player.setOnPlaying(new Runnable() {
            public void run() {
                if (stop_requested) {
                	media_player.pause();
                    stop_requested = false;
                } else {
                    playButton.setText("||");
                }
            }
        });
 
        media_player.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                playButton.setText(">");
            }
        });
 
        media_player.setOnReady(new Runnable() {
            public void run() {
                duration = media_player.getMedia().getDuration();
                updateValues();
            }
        });
 
        media_player.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        media_player.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setText(">");
                    stop_requested = true;
                    at_end_of_media = true;
                }
            }
       });
        
        
        
        media_bar.getChildren().add(playButton);
     // Add spacer
        Label spacer = new Label("   ");
        media_bar.getChildren().add(spacer);
         
        // Add Time label
        Label timeLabel = new Label("Time: ");
        media_bar.getChildren().add(timeLabel);
         
        // Add time slider
        time_slider = new Slider();
        HBox.setHgrow(time_slider,Priority.ALWAYS);
        time_slider.setMinWidth(50);
        time_slider.setMaxWidth(Double.MAX_VALUE);
        time_slider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (time_slider.isValueChanging()) {
               // multiply duration by percentage calculated by slider position
                  mp.seek(duration.multiply(time_slider.getValue() / 100.0));
               }
            }
        });
        volume_slider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (volume_slider.isValueChanging()) {
                   mp.setVolume(volume_slider.getValue() / 100.0);
               }
            }
        });
        media_bar.getChildren().add(time_slider);

        // Add Play label
        play_time = new Label();
        play_time.setPrefWidth(130);
        play_time.setMinWidth(50);
        media_bar.getChildren().add(play_time);
         
        // Add the volume label
        Label volumeLabel = new Label("Vol: ");
        media_bar.getChildren().add(volumeLabel);
         
        // Add Volume slider
        volume_slider = new Slider();        
        volume_slider.setPrefWidth(70);
        volume_slider.setMaxWidth(Region.USE_PREF_SIZE);
        volume_slider.setMinWidth(30);
         
        media_bar.getChildren().add(volume_slider);
        setBottom(media_bar); 
     }
    
    protected void updateValues() {
    	  if (play_time != null && time_slider != null && volume_slider != null) {
    	     Platform.runLater(new Runnable() {
    	        public void run() {
    	          Duration currentTime = media_player.getCurrentTime();
    	          play_time.setText(formatTime(currentTime, duration));
    	          time_slider.setDisable(duration.isUnknown());
    	          if (!time_slider.isDisabled() 
    	            && duration.greaterThan(Duration.ZERO) 
    	            && !time_slider.isValueChanging()) {
    	              time_slider.setValue(currentTime.divide(duration).toMillis()
    	                  * 100.0);
    	          }
    	          if (!volume_slider.isValueChanging()) {
    	            volume_slider.setValue((int)Math.round(media_player.getVolume() 
    	                  * 100));
    	          }
    	        }
    	     });
    	  }
    	}
    
    private static String formatTime(Duration elapsed, Duration duration) {
    	   int intElapsed = (int)Math.floor(elapsed.toSeconds());
    	   int elapsedHours = intElapsed / (60 * 60);
    	   if (elapsedHours > 0) {
    	       intElapsed -= elapsedHours * 60 * 60;
    	   }
    	   int elapsedMinutes = intElapsed / 60;
    	   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
    	                           - elapsedMinutes * 60;
    	 
    	   if (duration.greaterThan(Duration.ZERO)) {
    	      int intDuration = (int)Math.floor(duration.toSeconds());
    	      int durationHours = intDuration / (60 * 60);
    	      if (durationHours > 0) {
    	         intDuration -= durationHours * 60 * 60;
    	      }
    	      int durationMinutes = intDuration / 60;
    	      int durationSeconds = intDuration - durationHours * 60 * 60 - 
    	          durationMinutes * 60;
    	      if (durationHours > 0) {
    	         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
    	            elapsedHours, elapsedMinutes, elapsedSeconds,
    	            durationHours, durationMinutes, durationSeconds);
    	      } else {
    	          return String.format("%02d:%02d/%02d:%02d",
    	            elapsedMinutes, elapsedSeconds,durationMinutes, 
    	                durationSeconds);
    	      }
    	      } else {
    	          if (elapsedHours > 0) {
    	             return String.format("%d:%02d:%02d", elapsedHours, 
    	                    elapsedMinutes, elapsedSeconds);
    	            } else {
    	                return String.format("%02d:%02d",elapsedMinutes, 
    	                    elapsedSeconds);
    	            }
    	        }
    	    }
}

package player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
	EmbeddedMediaPlayer media_player;
	Renderer renderer;

	public Controls(Renderer renderer){
		this.renderer = renderer;
		this.media_player = renderer.getPlayer();
		
		play_pause = new Button("||");
		getChildren().add(play_pause);
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
 				    if (!mouseEvent.getPickResult().getIntersectedNode().toString().contains("HBox")) {
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
		
		
	}
}

package player;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;

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
	
	
	public Controls (DirectMediaPlayerComponent media_player_component) {
		this.media_player_component = media_player_component; 
		//Create buttons
		play_pause = new Button("||");  
		next = new Button(">>");
		previous = new Button("<<");
		//Create sliders
		time_scrubber = new Slider();
		volume = new Slider();
		
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

        
       // getChildren().add(time); // time slider 
        //getChildren().add(volume_label);
        //getChildren().add(volume); // volume slider 
		
		
		
		
	}
}

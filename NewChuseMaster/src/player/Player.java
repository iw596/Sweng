package player;

import java.nio.ByteBuffer;

import com.sun.jna.NativeLibrary;

import javafx.application.Platform;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;

/**
 * Player is the  class in the player package. This class handles the generation
 * of a resizeable VLC based media player capable of loading in local videos and 
 * Youtube videos if passed a file path or URL respectively.
 * 
 * Date created: 18/03/2019
 * Date last edited 18/03/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson 
 */
public class Player extends BorderPane {

		private ImageView image_view;
	    private DirectMediaPlayerComponent media_player_component;
	    private WritableImage writable_image;
	    private Pane player_holder;
	    private WritablePixelFormat<ByteBuffer> pixel_format;
	    private FloatProperty video_source_ratio_property;
	    // Store height and width
	    private int height;
	    private int width;
	    // Store coordinates where 
	    
	    public Player(Canvas canvas) {
	    	// Add location of VLC, this may need to be changed depending on where VLC is installed
	    	NativeLibrary.addSearchPath("libvlc", "C:/Program Files (x86)/VideoLAN/VLC");
	    	NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
	    	this.height = (int) canvas.getHeight();
	    	this.width = (int) canvas.getWidth();
	    	System.out.println("The height is: " + height + " The width is: " + width);
	    	
	    	// Create pane to add player on
	    	player_holder = new Pane();
	    	// Define source ratio of video
	        video_source_ratio_property = new SimpleFloatProperty(0.4f);
	        // Define layout of pixels and initalise the players display
	        pixel_format = PixelFormat.getByteBgraPreInstance();

	        initializeImageView();
	    	media_player_component = new CanvasComponent(video_source_ratio_property, writable_image, pixel_format, width, height);
	    	setCenter(player_holder); 
	        // Needs to be true to play Youtube videos, do not set to False!!
	        media_player_component.getMediaPlayer().setPlaySubItems(true);
	        
	        // Add controls to player
	        Controls controls = new Controls(media_player_component);
			setBottom(controls); // Setting the MediaBar at bottom
			setStyle("-fx-background-color:#bfc2c7");
	        
	        media_player_component.getMediaPlayer().prepareMedia("https://www.youtube.com/watch?v=Jqs5EaAaueA");
	        // Play media
	        media_player_component.getMediaPlayer().start();

	    }
	    
	    private void initializeImageView() {
	        writable_image = new WritableImage(width, height);

	        image_view = new ImageView(writable_image);
	        player_holder.getChildren().add(image_view);

	        player_holder.widthProperty().addListener((observable, oldValue, newValue) -> {
	            fitImageViewSize(newValue.floatValue(), (float) player_holder.getHeight());
	        });

	        player_holder.heightProperty().addListener((observable, oldValue, newValue) -> {
	            fitImageViewSize((float) player_holder.getWidth(), newValue.floatValue());
	        });

	        video_source_ratio_property.addListener((observable, oldValue, newValue) -> {
	            fitImageViewSize((float) player_holder.getWidth(), (float) player_holder.getHeight());
	        });
	    }
	    
	    /**
	     * 
	     * @param width
	     * @param height
	     */
	    
	    private void fitImageViewSize(float width, float height) {
	        Platform.runLater(() -> {
	            float fitHeight = video_source_ratio_property.get() * width;
	            if (fitHeight > height) {
	                image_view.setFitHeight(height);
	                double fitWidth = height / video_source_ratio_property.get();
	                image_view.setFitWidth(fitWidth);
	                image_view.setX((width - fitWidth) / 2);
	                image_view.setY(0);
	            }
	            else {
	                image_view.setFitWidth(width);
	                image_view.setFitHeight(fitHeight);
	                image_view.setY((height - fitHeight) / 2);
	                image_view.setX(0);
	            }
	        });
	    }
	    
}

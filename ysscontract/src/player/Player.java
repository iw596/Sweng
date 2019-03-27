package player;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sun.jna.NativeLibrary;

import javafx.application.Platform;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.AudioOutput;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

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

		private String[] paths; 
		private ImageView image_view;
	    private DirectMediaPlayerComponent media_player_component;
	    private WritableImage writable_image;
	    private Pane player_holder;
	    private WritablePixelFormat<ByteBuffer> pixel_format;
	    private FloatProperty video_source_ratio_property;
	    // Store height and width
	    private int height;
	    private int width;
	    private int current_video_index = 0;
	    // Store coordinates where 
	    
	    public Player(Canvas canvas) {
	    	// Add location of VLC, this may need to be changed depending on where VLC is installed
	    	NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "c:/program files (x86)/videolan/vlc");

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
	        Controls controls = new Controls(this);
			setBottom(controls); // Setting the MediaBar at bottom
			setStyle("-fx-background-color:#bfc2c7");
	        
	        //media_player_component.getMediaPlayer().prepareMedia("https://www.youtube.com/watch?v=FzLPQNMSWZA");
	        // Play media
	        //media_player_component.getMediaPlayer().start();
	      //  System.out.println(media_player_component.getMediaPlayer().getLength());

	    }
	    
	    /** Allows the player to be resized by adding listeners which
	     * listen for a change in screen size. They then rescale the size of the 
	     * video to fit in the pane.
	     * 
	     */
	    private void initializeImageView() {
	        writable_image = new WritableImage(width, height);

	        image_view = new ImageView(writable_image);
	        player_holder.getChildren().add(image_view);

	        player_holder.widthProperty().addListener((observable, oldValue, newValue) -> {
	            fitImageViewSize(newValue.floatValue(), (float) player_holder.getHeight());
	            //this.width =  (int) newValue;
	        });

	        player_holder.heightProperty().addListener((observable, oldValue, newValue) -> {
	            fitImageViewSize((float) player_holder.getWidth(), newValue.floatValue());
	           // this.height =  (int) newValue;
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
	    
	    /** This methods takes an array of strings which are file paths to videos
	     *  or links to Youtube videos. This method stores this array and loads the
	     *  first video into the player.
	     * 
	     * @param paths - list of strings containing links and filepaths to videos
	     */
	    public void loadPaths (String paths[]) {
	    	// Store the paths
	    	this.paths = paths;
	    	System.out.println("Size of paths array is:" + paths.length);
	    	System.out.println("Current index is:" + this.current_video_index);
	    	checkVideo();
	    	// Load first video
	    	loadVideo(this.current_video_index);
	    	
	    }
	    
	    /** This method loads in a video which is stored in the paths list. If the 
	     *  video is a local video it first checks that the file path is valid and that
	     *  the file of type  .mp4. If it is a URL then the URL is checked
	     *  to ensure it leads to a youtube video. If the URL/file path is not valid
	     *  then the video is not loaded. If it is valid then the video is loaded into
	     *  the player. Method returns true if file/url is valid and false if not.
	     *  
	     * @param index_video - Index of the video in the paths list.
	     */
	    protected void  loadVideo (int index_video) {
	    	
	    	System.out.println(this.paths[index_video]);
	    	this.media_player_component.getMediaPlayer().pause();
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	List<AudioOutput> audioOutputs = media_player_component.getMediaPlayerFactory().getAudioOutputs();
	    	media_player_component.getMediaPlayer().setAudioOutput(audioOutputs.get(4).getName());
	    	System.out.println(audioOutputs.get(4).getDescription());
	    	media_player_component.getMediaPlayer().prepareMedia(this.paths[index_video]);

	    	media_player_component.getMediaPlayer().play();
	    	
	    	System.out.println("New video time: " + media_player_component.getMediaPlayer().getTime());
	  
	    }
	    
	    /** This method checks through all
	     * 
	     */
	    private void checkVideo() {
	    	// First check if youtube or local
	    	
	    	//if (paths[this.current_video_index].substring(0, 3).str) {
	    		
	    	//}
	    }
	    
	    /** This getter returns the DirectMediaPlayerComponent used by the
	     *  player to play video
	     * 
	     * @return
	     */
		protected DirectMediaPlayerComponent getMediaPlayerComponent() {
			// TODO Auto-generated method stub
			return this.media_player_component;
		}

		/** This getter returns the index of the video currently being played
		 * 
		 * @return current video index
		 */
		protected int getCurrentIndex() {
			// TODO Auto-generated method stub
			return this.current_video_index;
		}
		
		/** This method returns the size of the path array.
		 * 
		 */
		
		protected int sizePaths() {
			return this.paths.length;
		}

		public void setCurrentIndex(int nextIndex) {
			// TODO Auto-generated method stub
			this.current_video_index = nextIndex;
			
		}
	    


}

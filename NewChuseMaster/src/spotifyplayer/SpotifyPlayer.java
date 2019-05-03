package spotifyplayer;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.NativeLibrary;

import javafx.application.Platform;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import uk.co.caprica.vlcj.binding.internal.libvlc_marquee_position_e;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.AudioOutput;
import uk.co.caprica.vlcj.player.Marquee;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * Player is the  class in the player package. This class handles the generation
 * of a resizeable VLC based media player capable of loading in spotify 
 * preview tracs if passed a valid  URL .
 * 
 * Date created: 27/04/2019
 * Date last edited 29/04/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson and Harry Ogden 
 */
public class SpotifyPlayer extends BorderPane {
	
	
		private String[] paths; 
		private ImageView image_view;
	    private DirectMediaPlayerComponent media_player_component;
	    private WritableImage writable_image;
	    private Pane player_holder;
	    private WritablePixelFormat<ByteBuffer> pixel_format;
	    private FloatProperty video_source_ratio_property;
	    // Store height and width of screen
	    private int screen_height;
	    private int screen_width;
	    private int current_video_index = 0;
	    // Store loading image path
	    private String audio_output_name =  new String();
	    
	    private Controls controls;
	    
	    // Store window width and height
	    private int window_width;
	    private int window_height;
	    private Image test;
	    
	    private Text text_artist;
	    private Text text_album;
	    private Text text_song;
	    //Stores if media player is in error screen
	    boolean in_error = false;
	    
	    /**
	     * Constructor for the video player. Instantiates the media player and its controls, and fits them to the canvas
	     * they exist on.
	     * 
	     * @param canvas
	     * @param x_screen_position
	     * @param y_screen_position
	     */
	    public SpotifyPlayer(int width, int height) {
	    	// Add location of VLC, this may need to be changed depending on where VLC is installed
	    	NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
	    	NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files (x86)/VideoLAN/VLC"); // This is one used on uni Machines
	    	// Vbox to display metadata
	    	VBox metadata_vbox = new VBox();
	    	setTop(metadata_vbox);
	    	text_song = new Text("Song: ");
	    	text_song.setFont(Font.font("verdana", FontPosture.REGULAR, 15));
	    	metadata_vbox.setAlignment(Pos.CENTER_LEFT);
	    	text_artist = new Text("Arist: ");   
	    	text_artist.setFont(Font.font("verdana",FontPosture.REGULAR, 15));
	    	text_album = new Text("Album: ");
	    	text_album.setFont(Font.font("verdana",FontPosture.REGULAR, 15));

	    	metadata_vbox.getChildren().add(text_song);
	    	metadata_vbox.getChildren().add(text_artist);
	    	metadata_vbox.getChildren().add(text_album);
	    	test = new Image(new File("endScreen.jpg").toURI().toString());
	    	
	    	// Find and store the actual dimensions of the user screen
	    	this.screen_height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	    	this.screen_width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	   
	    	// Store the window height and window width
	    	this.window_width = width ;
	    	this.window_height = height;

	    	// Create pane to add player on
	    	setPlayer_holder(new Pane());
	    	
	    	// Define source ratio of video
	        video_source_ratio_property = new SimpleFloatProperty(0.4f);
	        // Define layout of pixels and initalise the players display
	        pixel_format = PixelFormat.getByteBgraPreInstance();
	        initializeImageView();
	        // Create the actual VLC player
	    	media_player_component = new CanvasComponent(video_source_ratio_property, writable_image, pixel_format, screen_width, screen_height);
	        // Add controls to player
	        this.controls = new Controls(this);
	       // Enable this so Youtube videos can be played
	        media_player_component.getMediaPlayer().setPlaySubItems(true);
	        media_player_component.getMediaPlayer().setRepeat(true);
	        // Add controls
	        Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
	            	setBottom(controls); // Setting the MediaBar at bottom
	            	setStyle("-fx-background-color:#bfc2c7");
	    		    setCenter(getPlayer_holder());
	           }
	        });

	        // Get directX audio output driver name
	    	List<AudioOutput> audioOutputs = media_player_component.getMediaPlayerFactory().getAudioOutputs();
	    	this.audio_output_name = audioOutputs.get(4).getName();
			// Stops playing audio when screen is closed.
	

	    }
	    
	    /** Allows the player to be resized by adding listeners which
	     * listen for a change in screen size. They then rescale the size of the 
	     * video to fit in the pane.
	     * 
	     */
	    private void initializeImageView() {
	        writable_image = new WritableImage(screen_width, screen_height);

	        image_view = new ImageView(writable_image);
	        image_view.setImage(test);
	        getPlayer_holder().getChildren().add(image_view);
	        
	        // Add listners for size of screen changing
	        getPlayer_holder().widthProperty().addListener((observable, oldValue, newValue) -> {
	        	   Platform.runLater(new Runnable() {
	        		    @Override
        		        public void run() {
        		            // draw stuff
        		    	fitImageViewSize(newValue.floatValue(), (float) getPlayer_holder().getHeight());	            
        		    	}
	        	   });
	            
		    	try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

	        });

	        getPlayer_holder().heightProperty().addListener((observable, oldValue, newValue) -> {
	        	   Platform.runLater(new Runnable() {
	        		    @Override
	        		        public void run() {
	        		            // draw stuff
	        		    		fitImageViewSize((float) getPlayer_holder().getWidth(), newValue.floatValue());

	        		    }
	        	   });
	            
		    	try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            
	        });
	        // Add listner for video ratio changing
	        video_source_ratio_property.addListener((observable, oldValue, newValue) -> {
	        	   Platform.runLater(new Runnable() {
	        		    @Override
	        		        public void run() {
	        		    	fitImageViewSize((float) getPlayer_holder().getWidth(), (float) getPlayer_holder().getHeight());
	        		        }
	        	   });
	        }); 
	    }
	    
	    /**
	     * Method to fit the video player to the size of the canvas it exists on.
	     * 
	     * @param width - the width of the canvas
	     * @param height - the height of the canvas
	     */
	    private void fitImageViewSize(float width, float height) {
	    	// Place in run later to avoid crashes
     	   Platform.runLater(new Runnable() {
   		    @Override
   		        public void run() {
   		    	// Change height of window
	            float fitHeight = video_source_ratio_property.get() * width;
	            if (fitHeight > height) {
	                image_view.setFitHeight(height);
			    	try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	                double fitWidth = height / video_source_ratio_property.get();
	                image_view.setFitWidth(fitWidth);
	                image_view.setX((width - fitWidth) / 2);
			    	try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	                image_view.setY(0);
	               
	            }
	            // Change height of width
	            else {
	                image_view.setFitWidth(width);
			    	try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	                image_view.setFitHeight(fitHeight);
	                image_view.setY((height - fitHeight) / 2);
			    	try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	                image_view.setX(0);
	            }
   		    }
     	   });
	    }
	    
	    /** This methods takes an array of strings which are file paths to spotify 
	     *  tracks.
	     * 
	     * @param paths - list of strings containing links and filepaths to videos
	     */
	    public void loadPaths (String paths[]) {
	    	// Store the paths
	    	this.paths = paths;
	    	// Load first video
	    	loadVideo(this.current_video_index);
	    	
	    }
	    
	    /** This method loads in a spotify link which is stored in the paths list. If the 
	     *  track is valid then it is loaded into the player and played. Otherwise
	     *  the controls are notified that the link is invalid.
	     *  
	     * @param index_video - Index of the video in the paths list.
	     */
	    protected void  loadVideo (int index_video) {
	    	
	    	this.media_player_component.getMediaPlayer().pause();
	        Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
			    	// Reinitalise drivers to prevent VLCJ codec error 
			    	media_player_component.getMediaPlayer().setAudioOutput(audio_output_name);
			    	int video_check_result = checkVideo(index_video);
			    	if (video_check_result == 1) {
			    		// Need to reset marquee text due to VLCJ bug where disable marquee request is ignored
						 Marquee.marquee()
					    	.text("")
					    	.size(20)
					    	.opacity(0.7f)
					    	.position(libvlc_marquee_position_e.centre)
					    	 .colour(Color.WHITE)
					    	.location(5,5)
					    	.timeout(9000)
					    	.enable()
					    	.apply(media_player_component.getMediaPlayer()); 
			    		//media_player_component.getMediaPlayer().enableMarquee(false);
						if (paths[index_video] != null){
				    		media_player_component.getMediaPlayer().prepareMedia(paths[index_video]);
				    		media_player_component.getMediaPlayer().play();	
						}
			    	} 
			    	// Load invalid video screen
			    	else if (video_check_result == 2){
			    		controls.noPreview();
			    	}
			
	            }
	        });
	    	
	    }
	    
		/** This method checks that the string in the paths array is either a valid 
		 * spotify playback link or a null. If the link is valid then a 1 is returned.
		 * Otherwise a 2 is returned to indicate no preview available.
	     *  
	     * @param index_video - the index of the path array that is being checked
	     * 
	     */
	    private int checkVideo(int index_video) {
	    	// If name of filepath is less than three characters then must be an errorneous filepath so disregard straight away
	    	// to prevent buffer overflow in rest of checks
	    	
	    	if(this.paths[index_video] == null){
	    		return 2;
	    	}
	    	return 1;
	    }
	    
	    
	    /** This getter returns the DirectMediaPlayerComponent used by the
	     *  player to play video
	     * 
	     * @return the media player component
	     */
		protected DirectMediaPlayerComponent getMediaPlayerComponent() {
			return this.media_player_component;
		}

		/** This getter returns the index of the video currently being played
		 * 
		 * @return current video index
		 */
		protected int getCurrentIndex() {
			return this.current_video_index;
		}
		
		/** 
		 * This method returns the size of the array of video paths.
		 */
		protected int sizePaths() {
			return this.paths.length;
		}
		
		/** Method to set the index of the player
		 * 
		 * @param nextIndex- index that current index will be changed too
		 */
		public void setCurrentIndex(int nextIndex) {
			this.current_video_index = nextIndex;
			
		}
	
		/** Get the player holder
		 * @return the player_holder
		 */
		public Pane getPlayer_holder() {
			return player_holder;
		}

		/** Set the holder where the video player will be displayed
		 * @param player_holder the player_holder to set
		 */
		public void setPlayer_holder(Pane player_holder) {
			this.player_holder = player_holder;
		}

		/**
		 * Method to get the width of the window.
		 * @return window_width the width of the window
		 */
		public double getWindowWidth() {
			return this.window_width;
		}

		/**
		 * Method to get the height of the window.
		 * @return window_height	the height of the window
		 */
		public double getWindowHeight() {
			return this.window_height;
		}
		
		/** This method changes the preview which the player is playing. It first
		 * checks if the preview if valid. If it is not then the no preview method
		 * in the controls is called. If it is then the media of the player is change
		 * and the new track played. The controls are also notifed that the track
		 * has a preview available
		 * @param track_path - link to preview track
		 */
		public void changeSong(String track_path) {
			if(track_path != null){
				this.media_player_component.getMediaPlayer().prepareMedia(track_path);
				this.media_player_component.getMediaPlayer().play();
				controls.previewAvailable();
			}
			
			// If no preview then update controls
			else{
				// If currently playing a track then pause
				if (controls.no_preview == false && media_player_component.getMediaPlayer().getMediaPlayerState() != libvlc_state_t.libvlc_Playing)
				controls.noPreview();
				
				
			}
			
		}
		/** This method is used to stop the media playing from playing music 
		 *	 if it is not already paused
		 * 
		 */
		public void exit() {
			// If still playing then pause the player
			if (media_player_component.getMediaPlayer().isPlaying() == true){
				this.media_player_component.getMediaPlayer().pause();
			}
			
		}
		/** This sets the metadata which will be displayed by the player
		 * 
		 * @param metadata - metadata to show
		 */
		public void setMeta(ArrayList<String> metadata){
	    	text_song.setText("Song: " + metadata.get(0));
	    	text_artist.setText("Artist: " + metadata.get(1));  
	    	text_album.setText("Album: " + metadata.get(2));
	    
			
		}
		/** This sets the artwork which will be displayed by the player
		 * 
		 * @param art - artwork to show
		 */
		public void setArt(Image art){
			this.image_view.setImage(art);
			
		}
	    


}
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
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.caprica.vlcj.binding.internal.libvlc_marquee_position_e;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.AudioOutput;
import uk.co.caprica.vlcj.player.Marquee;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * Player is the  class in the player package. This class handles the generation
 * of a resizeable VLC based media player capable of loading in local videos and 
 * Youtube videos if passed a file path or URL respectively.
 * 
 * Date created: 18/03/2019
 * Date last edited 30/03/2019
 * Last edited by: Dan Jackson
 *
 * @author Isaac Watson 
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
	    
	    // Store coordinates where screen will be displayer
	    private int x_screen_position;
	    private int y_screen_position;
	    
	    // Store window width and height
	    private int window_width;
	    private int window_height;
	    private Stage stage;
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
	    	//NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
	    	NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files (x86)/VideoLAN/VLC"); // This is one used on uni Machines
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
	    
	    
	    	// Store the coordinates where the window will be opened
	        this.x_screen_position = x_screen_position;
	        this.y_screen_position = y_screen_position;

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
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                double fitWidth = height / video_source_ratio_property.get();
	                image_view.setFitWidth(fitWidth);
	                image_view.setX((width - fitWidth) / 2);
			    	try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                image_view.setX(0);
	            }
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
						else{
							System.out.println("null");
						}

				    	//System.out.println("The height of the player is: " + media_player_component.getMediaPlayer().getVideoDimension().getHeight());
			    		
			    	} 
			    	// Load invalid video screen
			    	else if (video_check_result == 2){
			    		loadInvalidFileScreen();
			    	}
			    	// Load file too large screen
			
	            }
	        });
	    	
	    }
	    


		/** This method checks that the string in the paths array is either a Yotube watch link or a valid path to an 
	     *  .mp4 file. If the link or path is valid then 1 is returned. If the filepath
	     *  or link is not valid then a 2 is returned. If the size of the video
	     *  is over 20 Mb then 3 is returned
	     *  
	     * @param index_video - the index of the path array that is being checked
	     * 
	     */
	    private int checkVideo(int index_video) {
	    	// If name of filepath is less than three characters then must be an errorneous filepath so disregard straight away
	    	// to prevent buffer overflow in rest of checks
	    	
	    	return 1;
	    }
	    
	    
	    /** This getter returns the DirectMediaPlayerComponent used by the
	     *  player to play video
	     * 
	     * @return the media player component
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
			// TODO Auto-generated method stub
			this.current_video_index = nextIndex;
			
		}
	
		
		/**
		 *  Method to load an error screen if an invalid video path has been provided. If there is another valid video queued,
		 *  then play this after displaying the error message.
		 */
		private void loadInvalidFileScreen() {
			// Error so set in_error to be true
			in_error = true;
			// Short sleep to prevent memory access error
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	     	Platform.runLater(new Runnable() {
	     		@Override
	      		 public void run() {
	     			media_player_component.getMediaPlayer().setAudioOutput(audio_output_name);
	     			// Set error message to be displayed
					 Marquee.marquee()
			    	.text("Playback error: " + "\"" + paths[current_video_index] + "\"" +" is not a valid path")
			    	.size(30)
			    	.opacity(0.7f)
			    	.position(libvlc_marquee_position_e.centre)
			    	 .colour(Color.WHITE)
			    	.location(5,5)
			    	.timeout(12000)
			    	.enable()
			    	.apply(media_player_component.getMediaPlayer()); 
				    try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				    // Enable the error message and play the error screen
			    	media_player_component.getMediaPlayer().enableMarquee(true);
			    	media_player_component.getMediaPlayer().prepareMedia("endscreen.jpg");
			    	media_player_component.getMediaPlayer().play();
					
	     		}
	     	});
		     	
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

		public double getWindowWidth() {
			// TODO Auto-generated method stub
			return this.window_width;
		}

		public double getWindowHeight() {
			// TODO Auto-generated method stub
			return this.window_height;
		}

		public void changeSong(String track_path) {
			// TODO Auto-generated method stub
			if(track_path != null){
				this.media_player_component.getMediaPlayer().prepareMedia(track_path);
				this.media_player_component.getMediaPlayer().play();
			}
			
		}

		public void exit() {
			// TODO Auto-generated method stub
			if (media_player_component.getMediaPlayer().isPlaying() == true){
				this.media_player_component.getMediaPlayer().pause();
			}
			
		}
		
		public void setMeta(ArrayList<String> metadata){
	    	text_song.setText("Song: " + metadata.get(0));
	    	text_artist.setText("Artist: " + metadata.get(1));  
	    	text_album.setText("Album: " + metadata.get(2));
	    
			
		}
		
		public void setArt(Image art){
			this.image_view.setImage(art);
			
		}
	    


}
package videoviewer;

import java.io.File;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;


/*** VideoViewer is a public class which exteneds the BorderPane class. This class creates 
 * a object which is capable of displaying a video to a user. The video is dynamically resizable.  
 * 
 * Date created: 21/02/2019
 * Date last edited: 28/02/2019
 * Last edited by: Isaac Watson and Harry Ogden
 * @author isaac
 *
 */
public class VideoViewer extends BorderPane  {
	
	// String to store filepath
	private String path;
	
	protected  ArrayList video_list;
	
	// Create media player
	MediaPlayer media_player;// = new MediaPlayer(media);
	
	// Create media view class
	private MediaView media_view; //= new MediaView(media_player);
	
	// Create media object
	private Media media;
	
	private Pane mpane;
	
	private int current_index = 0;
	MediaBar bar;
	
	
	/** This is the constructor for the videoViewer class. It takes a string which
	 *  is the file path of the video that is going to be played . The constructor then
	 *  builds a VideoViewer object
	 *  
	 *  @param filePath - String which indicates where a file is located
	 * 
	 */
	public  VideoViewer(ArrayList Videos) {
		
		// Store path and load file into media
		this.video_list = Videos;
		// Note we need to convert string to file, start by loading the first
		// video
		this. media = new Media(new File(Videos.get(current_index).toString()).toURI().toString());
		this.media_player = new MediaPlayer(media);
		this.media_view= new MediaView(media_player);
		
		// Create a pane and add the viewer to the pain
		mpane = new Pane(); 
		mpane.getChildren().add(media_view);
		setCenter(mpane); 
		bar = new MediaBar(this);
		setBottom(bar); // Setting the MediaBar at bottom
		setStyle("-fx-background-color:#bfc2c7");
		media_player.setAutoPlay(true);	
		// Call method to create listener which listens for screen press
		pressedScreen(); 
	
	}
	/** This is a getter for the media_view
	 *  
	 * @return media_view - returns the media viewer
	 */
	public MediaView getMediaView() {

		return this.media_view;
	}
	
	/** This is a getter for the media_player
	 * 
	 * @return media_player - returns the media player
	 */
	public MediaPlayer getMediaPlayer(){
		return this.media_player;
	}
	

	/** This method sets the size of the media player window to scale as the user 
	 * changes the size of the window. This ensures that the movie the user is trying
	 * to watch always fits the size of the window.
	 * 
	 */
	public void setSize() {
		media_view.setPreserveRatio(false);
		media_view.fitWidthProperty().bind(mpane.widthProperty());
		media_view.fitHeightProperty().bind(mpane.heightProperty());
		
	} 
	
	/** Method to detect if user has pressed on the screen, doing so
	 *  will pause the video. Works by creating action listener which
	 *  listens for mouse click on media_player
	 */
	public void pressedScreen(){
		// Create the listener
		media_view.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    @Override
		    // If mouse is pressed check if player is currently paused or running
		    public void handle(MouseEvent mouseEvent) {
		    	// If playing then pause
		    	if (media_player.getStatus().equals(Status.PLAYING)){
		    		media_player.pause();	
		    	}
		    	// If paused then play
		    	else{
		    		media_player.play();
		    	}
		    }
		});
	}
	/** This method is called when the video neeed to change. It creates a new
	 * media,player and viewer and adds this to the screen and then 
	 * sets the listener to listen for a user pressing the screen and adds
	 * resizing function
	 * 
	 */
	public void playNextVideo(){
		// Get size of list of videos
		int size_list = video_list.size();
		
		if (current_index < size_list -1 ){
			//Increment index  
			current_index += 1;	
			// play new video
			this.media = new Media(new File(video_list.get(current_index).toString()).toURI().toString());
			this.media_player = new MediaPlayer(media);
			this.media_view= new MediaView(media_player);
			
			// Create a pane and add the viewer to the pain
			mpane = new Pane(); 
			mpane.getChildren().add(media_view);
			setCenter(mpane); 
			bar = new MediaBar(this);
			setBottom(bar); // Setting the MediaBar at bottom
			setStyle("-fx-background-color:#bfc2c7");
			media_player.setAutoPlay(true);	
			// Call method to create listener which listens for screen press
			pressedScreen(); 
			setSize();
		}
				

	}
	public void playPrevVideo(){
		// Get size of list of videos
		int size_list = video_list.size();
		
		if (current_index != 0  ){
			//Increment index  
			current_index -= 1;	
			// play new video
			this.media = new Media(new File(video_list.get(current_index).toString()).toURI().toString());
			this.media_player = new MediaPlayer(media);
			this.media_view= new MediaView(media_player);
			
			// Create a pane and add the viewer to the pain
			mpane = new Pane(); 
			mpane.getChildren().add(media_view);
			setCenter(mpane); 
			bar = new MediaBar(this);
			setBottom(bar); // Setting the MediaBar at bottom
			setStyle("-fx-background-color:#bfc2c7");
			media_player.setAutoPlay(true);	
			// Call method to create listener which listens for screen press
			pressedScreen(); 
			setSize();
		}
				

	}
	
	
	
	
	
	
	

}

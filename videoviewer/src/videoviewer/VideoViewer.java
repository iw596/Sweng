package videoviewer;

import java.io.File;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


/*** VideoViewer is a public class which exteneds the BorderPane class. This class creates 
 * a object which is capable of displaying a video to a user. The video is dynamically resizable.  
 * 
 * Date created: 21/02/2019
 * Date last edited: 21/02/2019
 * Last edited by: Isaac Watson
 * @author isaac
 *
 */
public class VideoViewer extends BorderPane  {
	
	// String to store filepath
	private String path;
	

	
	// Create media player
	private MediaPlayer media_player;// = new MediaPlayer(media);
	
	// Create media view class
	private MediaView media_view; //= new MediaView(media_player);
	
	private Pane mpane;
	MediaBar bar;
	
	/** This is the constructor for the videoViewer class. It takes a string which
	 *  is the file path of the video that is going to be played . The constructor then
	 *  builds a VideoViewer object
	 *  
	 *  @param filePath - String which indicates where a file is located
	 * 
	 */
	public  VideoViewer(String filePath) {
		
		// Store path and load file into media
		this.path = filePath;
		// Note we need to convert string to file
		Media media = new Media(new File(path).toURI().toString());
		this.media_player = new MediaPlayer(media);
		this.media_view= new MediaView(media_player);
		
		// Create a pane and add the viewer to the pain
		mpane = new Pane(); 
		mpane.getChildren().add(media_view);
		setCenter(mpane); 
		bar = new MediaBar(media_player);
		setBottom(bar); // Setting the MediaBar at bottom
		setStyle("-fx-background-color:#bfc2c7");
		media_player.setAutoPlay(true);	
	
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
	
	
	
	
	
	

}

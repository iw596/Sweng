package videoviewer;

import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


/*** VideoViewer is a public class which extends the BorderPane class. This class creates 
 * a object which is capable of displaying a video to a user. The video is dynamically resizable.  
 * 
 * Date created: 21/02/2019
 * Date last edited: 26/02/2019
 * Last edited by: Isaac Watson and Harry Ogden
 * @author Isaac
 *
 */
public class VideoViewer extends BorderPane  {
	
	// String to store filepath
	private String path;
	
	private Stage stage;
	
	// Create media player
	private MediaPlayer media_player;// = new MediaPlayer(media);
	
	// Create media view class
	private MediaView media_view; //= new MediaView(media_player);
	
	private StackPane pane;

	private Image error_image;
	private ImageView error_viewer;
	MediaBar bar;
	
	/** This is the constructor for the videoViewer class. It takes a string which
	 *  is the file path of the video that is going to be played . The constructor then
	 *  builds a VideoViewer object
	 *  
	 *  @param filePath - String which indicates where a file is located
	 * 
	 */
	public  VideoViewer(String filePath, Stage stage) {
		
		// Create a pane and add the viewer to the pain
		pane = new StackPane();
		this.stage = stage;
		
		// Image to be displayed to the user if a file error occurs
		this.error_image = new Image(new File("ErrorTest.jpg").toURI().toString());
		this.error_viewer = new ImageView(error_image);
		
		// Store path and load file into media
		this.path = filePath;
		
		this.centerProperty();
		
		// Create a file from file path
		File file = new File(path);
		
		// Check that file exists
		if (file.exists() && !file.isDirectory()){
			// Note we need to convert string to file
			Media media = new Media(file.toURI().toString());
			this.media_player = new MediaPlayer(media);
			this.media_view= new MediaView(media_player);
			
			media_view.setManaged(true);
			
			bar = new MediaBar(media_player);
			
			pane.getChildren().add(media_view);
			pane.getChildren().add(bar);
			
			StackPane.setAlignment(media_view, Pos.CENTER);

			setStyle("-fx-background-color:BLACK");
			
			media_player.setAutoPlay(true);	
			// Call method to create listener which listens for screen press
			pressedScreen();
			setSize();
		}
		// Display error
		else{
			pane.getChildren().add(error_viewer);
			setErrorImageSize();
			System.out.println("Error: Movie not Found");
		}
		
		setCenter(pane); 
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
	private void setSize() {
		media_view.setPreserveRatio(true);
		media_view.fitWidthProperty().bind(Bindings.selectDouble(media_view.sceneProperty(), "width"));
		media_view.fitHeightProperty().bind(Bindings.selectDouble(media_view.sceneProperty(), "height"));
		
	} 
	
	private void setErrorImageSize(){
		error_viewer.setPreserveRatio(true);
		error_viewer.fitWidthProperty().bind(Bindings.selectDouble(error_viewer.sceneProperty(), "width"));
		error_viewer.fitHeightProperty().bind(Bindings.selectDouble(error_viewer.sceneProperty(), "height"));
	}
	
	/** Method to detect if user has pressed on the screen, doing so
	 *  will pause the video. Works by creating action listener which
	 *  listens for mouse click on media_player
	 */
	private void pressedScreen(){
		// Create the listener
		pane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			
		    @Override
		    // If mouse is pressed check if player is currently paused or running
		    public void handle(MouseEvent mouseEvent) {
		    	
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					// Do not pause when onScreen time slider is pressed
				    if (!mouseEvent.getPickResult().getIntersectedNode().toString().equals("StackPane@640e9eff[styleClass=track]")) {
				    	if(mouseEvent.getClickCount() == 2) {
		    				setFullscreen();
		    			}
				    	// If playing then pause
				    	if (media_player.getStatus().equals(Status.PLAYING)){
				    		media_player.pause();	
				    	}
				    	// If paused then play
				    	else {
				    		media_player.play();
				    	}
					}
		    	}
			}
		});
	}
	
	/** When called this method sets the size of the video to fill the screen
	 * 
	 * @param stage- stage video is being displayed on
	 */
	public void setFullscreen(){
		this.stage.setFullScreen(true);
	}
}

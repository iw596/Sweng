import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.Observable;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AudioPlayer extends Application {

	public static void main(String[] args) {	
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Initialise button for playing/pausing the current song
		Button PlayPause = new Button("Play/Pause");
		// Initialise button to skip to the beginning of the current song
		Button SkipBack = new Button("SkipBack");
		// Initialise volume slider
		Slider Volume = new Slider();
		// Initialise time progress slider
		Slider Time = new Slider();
		
		
		// The title of the window.
		stage.setTitle("Audio Player");
		
		//Vboxes are needed to arrange text and sliders on the screen.
		VBox slider = new VBox();
		VBox text_Metadata = new VBox();
		VBox text_Volume = new VBox();
		VBox text_CurrentTime = new VBox();
		//All vboxes are added to the group below.
		Group root = new Group();
		
		// Read media file from  directory and initialise media player
		Media media = new Media(new File("10 Gloria.mp3").toURI().toString());
		MediaPlayer player = new MediaPlayer(media);
		MediaView view = new MediaView(player);
		
		// Initialise variables for displaying metadata.
		//Title
    	Text text_title = new Text();
    	text_title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
    	text_title.setFill(Color.WHITE);
    	//Artist
    	Text text_artist = new Text();
    	text_artist.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
    	text_artist.setFill(Color.WHITE);
    	//Album
    	Text text_album = new Text();
    	text_album.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	text_album.setFill(Color.WHITE);
    	
    	//Initialising variables for displaying volume label and current time stamp.
    	//Volume
    	Text text_volume = new Text("Volume");
    	text_volume.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	text_volume.setFill(Color.WHITE);
    	//Current Time Stamp
    	//#.## defines format for displaying time, in this case minutes.seconds.
    	DecimalFormat df = new DecimalFormat("#.##");
    	Text text_currentTime = new Text("0.00");
    	text_currentTime.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	text_currentTime.setFill(Color.WHITE);
    	//Initialising variable to display album cover as background image.
    	ImageView IV = new ImageView();
		
    	
    	//When the media player is ready it runs this code.
		player.setOnReady(new Runnable() {

	        @Override
	        public void run() {
	        	// Set boundaries for time progress bar.
	        	Time.setMin(0);
	        	Time.setMax(player.getTotalDuration().toSeconds());
	        	
	        	// Retrieve metadata and assign to variables for Title, Artist, Album and Image.
	        	String title = (String)media.getMetadata().get("title");
	        	text_title.setText("Title: " + title);
	        	String artist = (String)media.getMetadata().get("artist");
	        	text_artist.setText("Artist: " + artist);
	        	String album = (String)media.getMetadata().get("album");
	        	text_album.setText("Album: " + album);
	        	Image image = (Image)media.getMetadata().get("image");
	        	//Album cover is set as background image.
	        	IV.setImage(image);
	        	
	        	// Play song
	            player.play();
	        }
	    });
		
		// Set initial volume to song volume
		Volume.setValue(player.getVolume()*100);
		
		// Change song volume when slider value is changed
		Volume.valueProperty().addListener(new InvalidationListener() {
			@Override
			//Listens to slider change and changes volume.
			public void invalidated(javafx.beans.Observable arg0) {
				player.setVolume(Volume.getValue()/100);
			}
		 });
		
		// Move to corresponding song time when progress slider is changed
		InvalidationListener sliderChangeListener = o-> {
			//Listens for change in time slider and plays song from that point.
		    Duration seekTo = Duration.seconds(Time.getValue());
		    player.seek(seekTo);
		};
		Time.valueProperty().addListener(sliderChangeListener);

		//Moves the dot on the audio slider as song progresses.
		player.currentTimeProperty().addListener(l-> {
		    // Temporarily remove the listener on the slider, so it doesn't respond to the change in playback time
		    Time.valueProperty().removeListener(sliderChangeListener);

		    //Gathers current play time for display.
		    Duration currentTime = player.getCurrentTime();
		    int value = (int) currentTime.toSeconds();
		    //Sets the slider to current time of song.
		    Time.setValue(value);
		    //Prints it to the screen.
		    text_currentTime.setText("" + df.format((currentTime.toMinutes())) + "/" + df.format(player.getTotalDuration().toMinutes()));

		    // Re-add the slider listener
		    Time.valueProperty().addListener(sliderChangeListener);
		});
		
		// Play/Pause song when button is pressed
		PlayPause.setOnAction(e -> {
				if (player.getStatus().equals(Status.PLAYING)) {
					player.pause();	
				}
				else {
					player.play();
				}
		});
		
		// Skip to beginning of song when button is pressed
		SkipBack.setOnAction(e -> {
				player.stop();
				player.play();
		});
		
		//Changes the size of the image as the window size changes
		IV.fitHeightProperty().bind(stage.heightProperty());
		IV.fitWidthProperty().bind(stage.widthProperty());
		
		//All components are added to root (Group object).
		//The Background image.
		root.getChildren().add(IV);
		//Time Slider
		slider.getChildren().add(Time);
		root.getChildren().add(slider);
		//Information about audio file e.g. Title, Artist...
		text_Metadata.getChildren().addAll(text_title,text_artist,text_album);
		root.getChildren().add(text_Metadata);
		//Current time of song.
		text_CurrentTime.getChildren().add(text_currentTime);
		root.getChildren().add(text_CurrentTime);
		//Volume label
		text_Volume.getChildren().add(text_volume);
		root.getChildren().add(text_Volume);
		//Buttons and volume slider
		root.getChildren().add(PlayPause);
		root.getChildren().add(SkipBack);
		root.getChildren().add(Volume);
		//Window size and colour if no background is available.
		Scene scene = new Scene(root, 400, 400, Color.BLACK);
		stage.setMinWidth(415);
		stage.setMinHeight(450);
		stage.setMaxWidth(415);
		stage.setMaxHeight(450);
		stage.setScene(scene);
		
		//Position of Buttons
		PlayPause.setLayoutX(170);
		PlayPause.setLayoutY(370);
		SkipBack.setLayoutX(100);
		SkipBack.setLayoutY(370);
		//Position of Volume slider and label.
		Volume.setLayoutX(250);
		Volume.setLayoutY(380);
		text_Volume.setLayoutX(255);
		text_Volume.setLayoutY(370);
		//Position of time stamp.
		text_CurrentTime.setLayoutX(10);
		text_CurrentTime.setLayoutY(370);
		//Position of time slider. The width of it is set to the limits of the window.
		slider.setMinSize(scene.getWidth(), scene.getHeight());
		slider.setLayoutY(350);
		//Position of the information of the song.
		text_Metadata.setLayoutX(10);
		text_Metadata.setLayoutY(10);
		//Displaying everything.
		stage.show();
	}

}

package audioPlayback;

import java.io.File;
import java.nio.file.Paths;

import javax.swing.JPanel;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class AppController {
	
	private AudioController audioController;
	
	// Boolean to keep track of whether the scrub position should be updated as the song is playing
	// This is used to prevent the scrubber snapping back to position while the user is dragging it
	private Boolean updateScrubber = true;
	
	// FXML UI objects links, required to dynamically control them
	@FXML private Button playPauseBtn;
	@FXML private Button stopBtn;
	@FXML private Slider scrubSld;
	@FXML private SwingNode swingNode;
	@FXML private JPanel panel;
	@FXML private GridPane root;
	
	String filepath;
	
	public AppController(String audio_filepath) {
		filepath = audio_filepath;
	}
	
	/*
	 * Does extra set up of UI elements that cannot be done in FXML (must be in initialize, not a contructor,
	 * otherwise the FXML will not have loaded). Also instantiates an AudioController and loads an audio file.
	 */
	public void initialize() {
		// The SwingNode is used to house Swing inside JavaFX, so that a VLCJ media player can be used
		// It does not need to be visible to the user
		swingNode.setVisible(false);
		
		// Create a VLCJ EmbeddedMediaPlayerComponent and add it to the Swing panel inside the SwingNode
		EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		panel.add(mediaPlayerComponent);
		
		// Instatiate a AudioController, passing it this controller and the new media player
		audioController = new AudioController(this, mediaPlayerComponent);
		
		// Create event handlers for mouse interaction with the scrubber...
		// (This must be in 'initialize', not the constructor, so that the FXML slider has been instantiated)
		
		// This event happens when the user begins a mouse click on the scrubber
		scrubSld.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				updateScrubber = false; // Prevent the scrubber position being updated by any playing audio
			}
		});
		// This event happens when the user begins a mouse click on the scrubber and drags it
		scrubSld.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				updateScrubber = false; // Prevent the scrubber position being updated by any playing audio
			}
		});
		// This event happens when the user releases a mouse click from the scrubber
		scrubSld.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				audioController.seek((float) scrubSld.getValue()); // Set the play position to the new scrubber position
				updateScrubber = true; // Allow the scrubber position to be updated by any playing audio
			}
		});
		
		// Call the function that handles loading audio files, providing the path to a chosen file
		// Note: MUST USE DOUBLE BACKSLASH FOR DRIVE ('\' is already
		// an escape sequence, so '\\' is equivalent to a single backslash)
		loadFile(filepath);
	}
	
	
	/*
	 * Used to set the JavaFX Stage exit event handler
	 * @param stage		the JavaFX stage that contains the scene relevant to this controller
	 */
	public void setCloseAction(Stage stage) {
		// The event handler must be used to exit the audio controller, otherwise the media player
		// is not released from memory and may carry on playing audio
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				// Notify the audio controller of the exit request
				audioController.exit();
			}
		});
	}
	
	/*
	 * Called when the play/pause button is pressed. Starts or pauses audio playback and reflects
	 * the change in the UI.
	 * 
	 * @param event		Action event provided by the button
	 */
	@FXML protected void playPauseBtnPressed(ActionEvent event) {
		// If audio is not already playing, start playback
		if (!audioController.isPlaying) {
			audioController.play();
			// Change the playPause button text to reflect the new pause functionality
			playPauseBtn.setText("Pause");
			// Enable the stop button now that audio is playing
			stopBtn.setDisable(false);
		}
		// Otherwise, pause the audio playback and change the
		// playPause button text to reflect the new play functionality
		else {
			audioController.pause();
			playPauseBtn.setText("Play");
		}
    }
	
	/*
	 * Called when the stop button is pressed. Stops audio playback and reflects this in the UI
	 * 
	 * @param event		Action event provided by the button
	 */
	@FXML protected void stopBtnPressed(ActionEvent event) {
		// Stop the audio (pauses playback and sets the position back to the beginning)
		audioController.stop();
		// Ensure the playPause button reads 'Play'
		playPauseBtn.setText("Play");
		// Disable the stop button
		stopBtn.setDisable(true);
		// Reset the scrubber position to the start
		updateScrubber((float) 0);
	}
	
	/*
	 * Similar functionality to stopBtnPressed, but this can be called by
	 * external objects to make the UI reflect a stop in audio playback
	 */
	public void stop() {
		// Ensure the playPause button reads 'Play'
		playPauseBtn.setText("Play");
		// Disable the stop button
		stopBtn.setDisable(true);
		// Reset the scrubber position to the start
		updateScrubber((float) 0);
	}
	
	/*
	 * This handles loading an audio file, with error checking for file type, invalid paths and non-existent files
	 * 
	 * @param filePath		the absolute path to the audio file to be loaded
	 */
	public void loadFile(String filePath) {
		try {
			// Extract the file extension from the file path, using '.' as a separator
			String fileType = filePath.substring(filePath.lastIndexOf("."));
			
			// Check that the file extension is '.mp3', '.mp4', '.wav' or '.flac'
			if (fileType.equals(".mp3") || fileType.equals(".m4a") || fileType.equals(".wav") || fileType.equals(".flac")) {
				
				// Check that a file exists at the given location
				File file = new File(filePath);
				if(file.exists()) { 
					audioController.loadFile(Paths.get(filePath).toUri().toString());
				}
				else {
					// Print an error message
					System.out.println("Error: file does not exist");
				}
			}
			else {
				// Print an error message
				System.out.println("Error: invalid file type");
			}	
		}
		catch (Exception e) {
			// Print an error message
			System.out.println("Error: invalid file path");
		}
	}
	
	/*
	 * Function to be called by the audio controller to indicate successful loading of audio file
	 */
	public void fileLoaded() {
		// Now that audio can be played, enable the playPause button and scrubber
		playPauseBtn.setDisable(false);
		scrubSld.setDisable(false);
	}
	
	/*
	 * Function to be called by the audio controller to update the position of the scrubber
	 * The scrub slider's range is 0 to 1 - fractional values are used to control scrubbing so that
	 * it is independent of the length of an audio file
	 * 
	 * @param fraction		the fractional position which the scrubber should move to
	 */
	public void updateScrubber(Float fraction) {
		// If the updateScrubber boolean is true, set the slider position to the fractional value provided
		if (updateScrubber) {
			scrubSld.setValue(fraction);
		}
	}
	
	/**
	 * ****ADDED BY Dan Jackson AND Isaac Watson OF COMPANY WeTech****
	 * Returns the audio controller.
	 * @return audioController
	 */
	public AudioController getAudioController() {
		return this.audioController;
	}
	
	/**
	 * ****ADDED BY Dan Jackson AND Isaac Watson OF COMPANY WeTech****
	 * Method for when the 
	 */
	public void exit() {
		root.getChildren().removeAll();
		this.scrubSld.setVisible(false);
		this.playPauseBtn.setVisible(false);
		this.stopBtn.setVisible(false);
		audioController.stop();
		audioController.exit();
	}
}

package audioPlayback;

import javafx.application.Platform;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayer;

public class AudioController {
	private AppController appController;
	
	// Boolean to keep track of whether audio is currently being played
	public Boolean isPlaying = false;
	
	// Boolean to keep track of whether an audio file has been loaded ready for playback
	private Boolean fileLoaded = false;
	
	// VLCJ media player object used for handling audio playback
	private EmbeddedMediaPlayer mediaPlayer;
	
	// Variable used to allow seeking while the media player is stopped
	// It stores the value of where the the media player should begin its next playback
	private Float nextPlayPosition = (float) 0;
	
	/*
	 * Constructor. Sets up the media player event listeners.
	 * 
	 * @param appController		The AppController object that instantiated this one
	 * @param mediaPlayerComponent		The VLC media player component to be controlled
	 */
	public AudioController(AppController appController, EmbeddedMediaPlayerComponent mediaPlayerComponent) {
		this.appController = appController;
		this.mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		
		// Add event listeners to the media player so that the program
		// can react to events as the audio file is played
		mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			// This function is called when playback reaches the end of the audio file
			@Override
			public void finished(MediaPlayer mediaPlayer) {
				// Prevent a JavaFX thread error by calling runLater
				Platform.runLater(new Runnable(){
					@Override
					public void run() {
						// Stop the playback and notify the app controller
						stop();
						appController.stop();
					}
				});
			}
			
			// This function is called every time playback reaches a new sample buffer
			@Override
			public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
				Float time = (float) newTime;
				Float length = (float) mediaPlayer.getLength();
				// Update the scrubber, providing a fractional value of (current time / length)
				appController.updateScrubber(time/length);
			}
		});
		
	}
	
	/*
	 * Plays the audio file (if it is loaded) from the current seek position
	 */
	public void play() {
		// If an audio file is loaded ready for playback, start the media player
		if (fileLoaded) {
			mediaPlayer.play();
			
			// Check if the media player should start playback from a different point in the file
			if (nextPlayPosition > 0) {
				// If so, set the position
				mediaPlayer.setPosition(nextPlayPosition);
				// Reset the variable so that this effect doesn't repeat itself
				nextPlayPosition = (float) 0;
			}
			
			// Reflect the change in the isPlaying boolean
			isPlaying = true;
		}
	}
	
	/*
	 * Pauses audio playback
	 */
	public void pause() {
		// Pause audio playback and reflect the change in the isPlaying boolean
		mediaPlayer.pause();
		isPlaying = false;
	}
	
	/*
	 * Stops audio playback (pauses playback and sets the position back to the beginning)
	 */
	public void stop() {
		// Stop audio playback and reflect the change in the isPlaying boolean
		mediaPlayer.stop();
		isPlaying = false;
	}
	
	/*
	 * Function to be called by external objects to load a new audio file ready for playback
	 * 
	 * @param filePath		a string containing the absolute path to the audio file to be loaded
	 */
	public void loadFile(String filePath) {
		// Check that the file path provided is not empty
		if (filePath == null) {
			//System.out.println("Error: no file path provided");
		}
		else {
			try {
				// Load the media at the given file path ready for playback
				mediaPlayer.prepareMedia(filePath);
				// Stop the media player to prevent an error where playback finishes before the end of the file
				mediaPlayer.stop();
				// Upon successful loading, reflect the change in the file loaded property
				fileLoaded = true;
				// Notify the app controller that the file has been loaded
				appController.fileLoaded();
			}
			// If loading fails, print an error message
			catch(Exception e) {
				//System.out.println("Error: couldn't load audio file.");
			}
		}
	}
	
	/*
	 * Function to be called by external objects to set the position of audio playback
	 * Fractional values are used to control scrubbing so that
	 * it is independent of the length of an audio file
	 * 
	 * @param fraction		the fractional position that audio playback should be set to
	 */
	public void seek(Float fraction) {
		// If the media player is not stopped, set its playback position
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.setPosition(fraction);
		}
		else {
			// If the media player is stopped, setting its playback position won't work
			// Instead we must store the value until the player is restarted
			nextPlayPosition = fraction;
		}
	}
	
	/*
	 * Function called when the JavaFX window is exited
	 * This is needed so that the media player does not carry on processing audio after exit
	 */
	public void exit() {
		stop();
		mediaPlayer.release();
	}
}
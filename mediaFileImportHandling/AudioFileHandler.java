package mediaFileImportHandling;

import java.util.ArrayList;

import javafx.stage.Stage;
import listDataStructure.AudioItem;
import listDataStructure.BasicItem;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * Abstract class used for importing audio source files from a local file path. This class
 * extends the generic FileImportHandler.
 * 
 * Date created: 14/02/2018
 * Date last edited: 01/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public abstract class AudioFileHandler extends FileImportHandler{
	
	/**
	 * Method for opening multiple local audio files and returning an array list of audio items
	 * containing the local audio file path and metadata.
	 * @param stage - the JavaFX stage the files are opened from
	 * @return audio_items - the array list of audio items
	 */
	public static ArrayList<BasicItem> openMultipleAudioFiles(Stage stage) {
		
		//uses the JavaFX file chooser to open files and saves paths in array
		ArrayList<String> file_paths = openMultipleFiles(stage, "audio");
		
		ArrayList<BasicItem> audio_items = new ArrayList<BasicItem>();
		
		//needed for getting the metadata through VLCj
		new NativeDiscovery().discover();

		//loops through every file path in the list 
		for(String file_path : file_paths) {
			//adds every file path to the audio item array list
	        audio_items.add(new AudioItem(file_path));
		}
		
		return audio_items;

	}
	
	/**
	 * Method for getting the metadata of an audio file, given the file path of the audio file.
	 * 
	 * @param file_path - the path to the audio file
	 * @return track_metadata - an array list containing all of the file's relevant metadata
	 */
	public static ArrayList<String> getMetadata(String file_path) {
		
		//needed for getting the metadata through VLCj
		new NativeDiscovery().discover();
		
		//creates a media player
		EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		
		//adds the audio file to the media player and parses it
		mediaPlayerComponent.getMediaPlayer().prepareMedia(file_path);
        mediaPlayerComponent.getMediaPlayer().parseMedia();
        
        ArrayList<String> track_metadata = new ArrayList<String>();
        
        //gets the media player and the metadata for the track
        MediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
        MediaMeta metadata = mediaPlayer.getMediaMeta();

        //adds the relevant metadata to the metadata array list
        track_metadata.add(metadata.getTitle());
        track_metadata.add(metadata.getArtist());
        track_metadata.add(metadata.getAlbum());
        track_metadata.add(metadata.getDate());
        track_metadata.add(metadata.getGenre());
        
        return track_metadata;
		
	}
	
}

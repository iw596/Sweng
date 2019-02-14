package mediaFileImportHandling;

import java.util.ArrayList;

import javafx.stage.Stage;
import listDataStructure.AudioItem;
import listDataStructure.BasicItem;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayer;

public abstract class AudioFileHandler extends FileImportHandler{

	public static AudioItem openAudioFile(Stage stage) {
		
		String file_path = openFile(stage);
		
		ArrayList<String> track_metadata = new ArrayList<String>();
		
		new NativeDiscovery().discover();
		
    	EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    	
        mediaPlayerComponent.getMediaPlayer().prepareMedia(file_path);
        mediaPlayerComponent.getMediaPlayer().parseMedia();
        
        MediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
        
        MediaMeta metadata = mediaPlayer.getMediaMeta();
        
        track_metadata.add(metadata.getTitle());
        track_metadata.add(metadata.getArtist());
        track_metadata.add(metadata.getAlbum());
        track_metadata.add(metadata.getDate());
        track_metadata.add(metadata.getGenre());
        
        AudioItem new_audio_item = new AudioItem(file_path, track_metadata);
        
        return new_audio_item;
        
	}
	
	public static ArrayList<BasicItem> openMultipleAudioFiles(Stage stage) {
		
		ArrayList<String> file_paths = openMultipleFiles(stage);
		
		ArrayList<BasicItem> multi_metadata = new ArrayList<BasicItem>();
		
		new NativeDiscovery().discover();
		
		EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		
		for(String file_path : file_paths) {
			mediaPlayerComponent.getMediaPlayer().prepareMedia(file_path);
	        mediaPlayerComponent.getMediaPlayer().parseMedia();
	        
	        ArrayList<String> track_metadata = new ArrayList<String>();
	        
	        MediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
	        MediaMeta metadata = mediaPlayer.getMediaMeta();

	        track_metadata.add(metadata.getTitle());
	        track_metadata.add(metadata.getArtist());
	        track_metadata.add(metadata.getAlbum());
	        track_metadata.add(metadata.getDate());
	        track_metadata.add(metadata.getGenre());
	        
	        multi_metadata.add(new AudioItem(file_path, track_metadata));
		}
		
		return multi_metadata;

	}
	
}

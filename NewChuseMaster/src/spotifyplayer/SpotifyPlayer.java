package spotifyplayer;


import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import videoPlayback.NanoTimer;

/**
 * Player is the  class in the player package. This class handles the generation
 * of a resizeable VLC based media player capable of loading in spotify 
 * preview tracks if passed a valid  URL .
 * 
 * Date created: 27/04/2019
 * Date last edited 31/05/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson and Harry Ogden 
 */
public class SpotifyPlayer extends Renderer {

	    private Pane player_holder;
	    private Boolean no_preview = false;
		    
	   //Stores if media player is in error screen
	    boolean in_error = false;
	    // set the frame rate
	    private static final double FPS = 60.0;
	
	   /** Method to use nanoTimer, this code has been taken from VLCJ github page
	   *
	   */
	    private final NanoTimer nanoTimer = new NanoTimer(1000.0 / FPS) {
	       @Override
	       protected void onSucceeded() {
	          renderFrame();
	       }
	    };

	    	@Override
	       /** Method to start nanotimer once player begins playing media
	        * 
	        */
	       protected void startTimer() {
	           nanoTimer.start();
	       }
	       /** Method to stop nanotimer
	        * 
	        */
	       @Override
	       protected void stopTimer() {
	           nanoTimer.cancel();
	       }
	       
	       /** This method allows the player to play a video, it initally sets the video
	        * to play in order to load video in, then pauses it in order to stop both
	        * videos on comparison screen playing at once.
	        * 
	        * Also starts the nanotimer and sets it so that videos replay once complete
	        * 
	        */
	       public void play(){
	    	   if (no_preview == false){
		           media_player.controls().setRepeat(true);
		           media_player.controls().play();
		           //pause video so does not instantly play
		           media_player.controls().setPause(true);
		           System.out.println("Controls: " + controls);
		           System.out.println("Play/Pause: " + controls.play_pause);
		           controls.play_pause.setText(">");
		           startTimer();
	    	   }
	       }
	       /** Getter for diplay_pane containing controls and player
	        * 
	        * @return display_pane - pane containing controls and player
	        */
	       public BorderPane getPane(){
	       	return display_pane;
	       }
	       
	       /** Method to set the media that the player will play. Does this by setting the media in the media
	        *  player. For spotify also need to check to see if track path is valid.
	        * 
	        * @params file_name - the filename of the media 
	        */
	       public void setMedia (String file_name){
		    	if (file_name != null){
		    		media_player.media().prepare(file_name);
		    	}
		    	else{
		    		no_preview = true;
		    		controls.noPreviewScreen();
		    		
		    	}
	       	
	       }

	
		/** Get the player holder
		 * @return the player_holder
		 */
		public Pane getPlayer_holder() {
			return player_holder;
		}


		/** Method to exit from spotify player, 
	     * releases the media and player to free memory
	     */
		public void exit() {
			media_player.controls().stop();
			this.media_player.release();
			stopTimer();
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
			image_view.setImage(art);
			
		}
	    


}
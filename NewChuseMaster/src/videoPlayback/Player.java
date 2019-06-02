
package videoPlayback;

import javafx.scene.layout.BorderPane;

/** Player class extends renderer, this class handles the actual playing of a video and
 * 	any track changes and exit from the player
 * 
 * Date created: 27/04/2019
 * Date last edited 30/05/2019
 * Last edited by: Isaac Watson
 * 
 * @author Isaac Watson 
 */
public class Player extends Renderer  {

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
        media_player.controls().setRepeat(true);
        media_player.controls().play();
        //pause video so does not instantly play
        media_player.controls().setPause(true);
        controls.play_pause.setText(">");
        startTimer();
    }
    /** Getter for diplay_pane containing controls and player
     * 
     * @return display_pane - pane containing controls and player
     */
    public BorderPane getPane(){
    	return diplay_pane;
    }
    
    /** Method to set the media that the player will play. Does this by setting the media in the media
     *  player
     * 
     * @params file_name - the filename of the media 
     */
    public void setMedia (String file_name){
    	media_player.media().prepare(file_name);
    	
    }
    /** Method to exit from video player, releases the media and player to free memory
     * @throws InterruptedException 
     * 
     */
	public void exit() {
		System.out.println("Player: " + media_player);
		media_player.controls().setPause(true);
		media_player.controls().stop();
		this.media_player.release();
		stopTimer();
	}

    
   


}

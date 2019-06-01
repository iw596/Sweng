/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009, 2010, 2011, 2012, 2013, 2014, 2015 Caprica Software Limited.
 */

package player;

import javafx.scene.layout.BorderPane;

/**
 * Implementation of a JavaFX direct rendering media player that uses the {@link NanoTimer}.
 * <p>
 * In principle, this should be better performing than the corresponding JavaFX Timeline example.
 */
public class Player extends Renderer  {

    // set the frame rate
    private static final double FPS = 60.0;

    /**
     *
     */
    private final NanoTimer nanoTimer = new NanoTimer(1000.0 / FPS) {
        @Override
        protected void onSucceeded() {
            renderFrame();
        }
    };

    @Override
    protected void startTimer() {
        nanoTimer.start();
    }

    @Override
    protected void stopTimer() {
        nanoTimer.cancel();
    }
    
    /** This method makes the player play the media file currently stored in the media player
     * 
     */
    public void play(){
        media_player.controls().setRepeat(true);

        media_player.controls().play();

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
    
    
   


}

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

import java.nio.ByteBuffer;
import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CallbackVideoSurface;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapters;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat;

/** Modified version of software provided by VLCJ to render video using javafx. This class
 * handles the rendering of video and creates a borderpane to add controls and the actual
 * video too.
 * 
 * Date created: 30/05/2019
 * Date last edited 30/05/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson 
 */

public abstract class Renderer {

    /**
     * Filename of the video to play.
     */
    protected static  String VIDEO_FILE = "https://www.youtube.com/watch?v=jhAXPnm8BP0";

    /**
     * Lightweight JavaFX canvas, the video is rendered here.
     */
    private final Canvas canvas;

    /**
     * Pixel writer to update the canvas.
     */
    private PixelWriter pixelWriter;

    /**
     * Pixel format.
     */
    private final WritablePixelFormat<ByteBuffer> pixelFormat;

   // Borderpane to store player and controls
    public final BorderPane diplay_pane;
    // Pane to store player
    private Pane pane;

    private MediaPlayerFactory mediaPlayerFactory;

    /**
     * The vlcj direct rendering media player component.
     */
    public final EmbeddedMediaPlayer media_player;

    /**
     *
     */
    private Stage stage;

    /**
     *
     */
    private Scene scene;

    /**
     *
     */
    private WritableImage img;

    /** Constructor which creates renderer. It handles creating a media player and adding
     *  media player and controls to a border pane which can be displayed to users
     *
     */
    public Renderer() {
    	diplay_pane = new BorderPane();
        canvas = new Canvas();

        pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        pixelFormat = PixelFormat.getByteBgraInstance();

        mediaPlayerFactory = new MediaPlayerFactory();
        media_player = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();

        media_player.videoSurface().set(new JavaFxVideoSurface());
        pane = new Pane();
        Controls controls = new Controls(this);
        pane.getChildren().add(canvas);
        
        diplay_pane.setCenter(pane);
        diplay_pane.setBottom(controls);
        diplay_pane.setStyle("-fx-background-color: rgb(0, 0, 0);");


        canvas.widthProperty().bind(diplay_pane.widthProperty());
        canvas.heightProperty().bind(diplay_pane.heightProperty());
    }

    /*
    @Override
    public final void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        stage.setTitle("vlcj JavaFX Direct Rendering Test");

        scene = new Scene(borderPane, Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.show();

        mediaPlayer.controls().setRepeat(true);

        mediaPlayer.media().play(VIDEO_FILE);

//        mediaPlayer.controls().setPosition(0.9f);

        startTimer();
    } */

    
    /*
    @Override
    public final void stop() throws Exception {
        stopTimer();

        mediaPlayer.controls().stop();
        mediaPlayer.release();
        mediaPlayerFactory.release();
    } */
   
    /** Method created by VLCJ which handles making video surface compatible with javafx
     * 
     */
    private class JavaFxVideoSurface extends CallbackVideoSurface {

        JavaFxVideoSurface() {
            super(new JavaFxBufferFormatCallback(), new JavaFxRenderCallback(), true, VideoSurfaceAdapters.getVideoSurfaceAdapter());
        }

    }
    /** Method created by VLCj to handle Buffer call back
     * 
     */
    private class JavaFxBufferFormatCallback implements BufferFormatCallback {
        @Override
        public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
        	Renderer.this.img = new WritableImage(sourceWidth, sourceHeight);
        	Renderer.this.pixelWriter = img.getPixelWriter();

            Platform.runLater(new Runnable () {
                @Override
                public void run() {
                    stage.setWidth(sourceWidth);
                    stage.setHeight(sourceHeight);
                }
            });
            return new RV32BufferFormat(sourceWidth, sourceHeight);
        }
    }

    // Semaphore used to prevent the pixel writer from being updated in one thread while it is being rendered by a
    // different thread
    private final Semaphore semaphore = new Semaphore(1);

    // This is correct as far as it goes, but we need to use one of the timers to get smooth rendering (the timer is
    // handled by the demo sub-classes)
    private class JavaFxRenderCallback implements RenderCallback {
        @Override
        public void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat) {
            try {
                semaphore.acquire();
                pixelWriter.setPixels(0, 0, bufferFormat.getWidth(), bufferFormat.getHeight(), pixelFormat, nativeBuffers[0], bufferFormat.getPitches()[0]);
                semaphore.release();
            }
            catch (InterruptedException e) {
            }
        }
    }

    /** Method created by VLCJ to render each frame of video 
     * 
     */
    protected final void renderFrame() {
        GraphicsContext g = canvas.getGraphicsContext2D();

        double width = canvas.getWidth();
        double height = canvas.getHeight();

        g.setFill(new Color(0, 0, 0, 1));
        g.fillRect(0, 0, width, height);

        if (img != null) {
            double imageWidth = img.getWidth();
            double imageHeight = img.getHeight();

            double sx = width / imageWidth;
            double sy = height / imageHeight;

            double sf = Math.min(sx, sy);

            double scaledW = imageWidth * sf;
            double scaledH = imageHeight * sf;

            Affine ax = g.getTransform();

            g.translate(
                (width - scaledW) / 2,
                (height - scaledH) / 2
            );

            if (sf != 1.0) {
                g.scale(sf, sf);
            }

            try {
                semaphore.acquire();
                g.drawImage(img, 0, 0);
                semaphore.release();
            }
            catch (InterruptedException e) {
            }

            g.setTransform(ax);
        }
    }

    /**Abstract method to start timer
     *
     */
    protected abstract void startTimer();

    /** Abstract method to stop timer
     *
     */
    protected abstract void stopTimer();
    
    /** Abstract method to set video to be played 
     * 
     */
    
    public abstract void setMedia (String  file_name);
    
    /** Returns the embedded media player
     * 
     * @return media_player - the vlcj player
     */
    public EmbeddedMediaPlayer getPlayer(){
    	return this.media_player;
    }
    
    /** Returns the borderpane  which stores the media player and controls
     * 
     * @return BorderPane - returns pane containg controls and video player
     */
	public BorderPane getPlayerHolder() {
		// TODO Auto-generated method stub
		return diplay_pane;
	}
}

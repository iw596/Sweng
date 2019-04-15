package player;

import java.nio.ByteBuffer;

import com.sun.jna.Memory;

import javafx.application.Platform;
import javafx.beans.property.FloatProperty;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;

/**
 * The CanvasComponent class extends the DirectMediaPlayerComponent class, it adds functionality
 * to make the player compatible with JavaFx 
 * 
 * Date created: 18/03/2019
 * Date last edited 18/03/2019
 * Last edited by: Isaac Watson
 * 
 * @author Isaac Watson
 *
 */
public class CanvasComponent extends DirectMediaPlayerComponent{
	private WritableImage writable_image;
	private WritablePixelFormat<ByteBuffer> pixel_format;
	private PixelWriter pixel_writer = null;

   
	/** This constructor creates a canvas compent
	 * 
	 * @param video_source_ratio_property - The  source ratio of the video
	 * @param writable_image - The 
	 * @param pixel_format - The pixel format used by the player
	 */
	public CanvasComponent(FloatProperty video_source_ratio_property,WritableImage writable_image,WritablePixelFormat<ByteBuffer> pixel_format,int width,int height) {
        super(new CanvasBuffer(video_source_ratio_property, width, height));
        this.writable_image = writable_image;
        this.pixel_format =  pixel_format;

    }

    private PixelWriter getPW() {
        if (pixel_writer == null) {
            pixel_writer = writable_image.getPixelWriter();
        }
        return pixel_writer;
    }
   
    /** This method was taken from:  https://github.com/caprica/vlcj-javafx
     * It controls how the player displays each frame.
     */
    @Override
    public void display(DirectMediaPlayer mediaPlayer, Memory[] nativeBuffers, BufferFormat bufferFormat) {
        if (writable_image == null) {
            return;
        }
        Platform.runLater(() -> {
            Memory nativeBuffer = mediaPlayer.lock()[0];
            try {
                ByteBuffer byteBuffer = nativeBuffer.getByteBuffer(0, nativeBuffer.size());
                getPW().setPixels(0, 0, bufferFormat.getWidth(), bufferFormat.getHeight(), pixel_format, byteBuffer, bufferFormat.getPitches()[0]);
            }
            finally {
                mediaPlayer.unlock();
            }
        });
    }
}


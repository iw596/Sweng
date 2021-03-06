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

public class CanvasComponent extends DirectMediaPlayerComponent{
	private WritableImage writable_image;
	private WritablePixelFormat<ByteBuffer> pixel_format;
	private int width;
	private int height;
   
	public CanvasComponent(FloatProperty video_source_ratio_property,WritableImage writable_image,WritablePixelFormat<ByteBuffer> pixel_format,int width,int height) {
        super(new CanvasBuffer(video_source_ratio_property, width, height));
        this.writable_image = writable_image;
        this.pixel_format =  pixel_format;
        this.width = width;
        this.height = height;
    }

    PixelWriter pixelWriter = null;

    private PixelWriter getPW() {
        if (pixelWriter == null) {
            pixelWriter = writable_image.getPixelWriter();
        }
        return pixelWriter;
    }

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


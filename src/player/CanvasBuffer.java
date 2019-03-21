package player;

import javafx.application.Platform;
import javafx.beans.property.FloatProperty;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;

public class CanvasBuffer implements BufferFormatCallback {
	private FloatProperty video_source_ratio_property;
	private int width;
	private int height;
	
	public CanvasBuffer(FloatProperty video_source_ratio_property,int width,int height) {
		this.video_source_ratio_property = video_source_ratio_property;
		this.width = width;
		this.height = height;
	
	}
	
    @Override
    public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
        Platform.runLater(() -> video_source_ratio_property.set((float) sourceHeight / (float) sourceWidth));
        return new RV32BufferFormat(width, height);
    }
}


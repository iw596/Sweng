package listDataStructure;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageItem extends ListItem implements ListInterface {

	private Image image;
	
	public ImageItem(String image_url) {
		super(image_url);
		
		try {
			this.image = ImageIO.read(new File(this.name));
		} catch(IOException ioe) {
			ioe.printStackTrace();
			System.exit(0);
		}

	}

}

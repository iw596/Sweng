package application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class imageProfile {

	// Global Variables

	// Set default size of image

	private double imageFit = 100;
	
	private double imageRatio;
	private double imageNodeHeight;
	private ImageView imageNode;
	private HBox imageContainer;
	
	/**
	 * Constructor for an imageProfile for a specified image
	 * @param image chosen image to be displayed
	 */
	public imageProfile(Image image){
		
		// Store the ratio of the height of width of the original image
		this.imageRatio = image.getHeight()/image.getWidth();
		
		// Create a new ImageView node
		
		this.imageNode =  new ImageView(image);
		
		// Preserve the width and height ratio when either value is changed
		imageNode.setPreserveRatio(true);

		// Set true to use quality filtering algorithm for image transforming

		imageNode.setSmooth(true);

		// Scale the image to fit the scene using the default fit value

		imageNode.setFitWidth(imageFit);

		// Calculate and store the ImageView node height after default scaling

		this.imageNodeHeight = this.imageRatio * imageNode.getFitWidth();
		
		// Set the image container to hold the ImageView node
		this.imageContainer = new HBox(imageNode);
	}
	
	/**
	 * Get the value of the HBox container
	 * @return imageContainer the container the image node is displayed in
	 */

	public HBox getImageContainer(){
		return this.imageContainer;
	}
	
	/**
	 * Set the position of the image on the scene
	 * If values are outside the scene bounds, the image will not be repositioned relative to
	 * the invalid parameter.
	 * @param xPos the horizontal position for the image. Must be within scene bounds.
	 * @param yPos the vertical position for the image. Must be within scene bounds.
	 */

	public void setPosition(double xPos, double yPos, Scene scene){
		
		// Check if new x position is within the scene's width bounds

		if (xPos > 0 && xPos < scene.getWidth()){
			this.imageContainer.setLayoutX(xPos -(this.imageFit/2));
		}

		// Check if new y position is within the scene's width bounds

		if (yPos > 0 && yPos < scene.getHeight()){
			this.imageContainer.setLayoutY(yPos - (this.imageNodeHeight/2));
		}
		
	}

	/**
	 * Increase or decrease the image size by a specified increment 
	 * @param delta value for the image to be scaled with. Can be positive or negative.
	 */

	public void scaleImage(double delta){
		
		// Store the image fits value once scaled by the value's delta

		this.imageFit = this.imageFit + delta/2;
		
		// Scale the image by the new image fit value
		
		this.imageNode.setFitWidth(this.imageFit);
		
		//Update the image nodes height for new fit size
		
		this.imageNodeHeight = this.imageRatio * imageNode.getFitWidth();	
	}
	
	/**
	 * Resize the image node by a new value. Only changed the width as ratio is preserved.
	 * @param newSize new size of image nodes width
	 */

	public void resizeImage(double newSize){
		this.imageNode.setFitWidth(newSize);
	}
	
	/**
	 * Reset the position and size of the image to the default values
	 * @param scene the scene the image is displayed on
	 */

	public void resetImage(Scene scene){
		
		// Restore image fit size to default

		this.imageFit = 100;
		this.imageNode.setFitWidth(imageFit);

		// Restore image nodes height for new fit size
		this.imageNodeHeight = this.imageRatio * imageNode.getFitWidth();

		// Restore the position of the image to the centre of the scene

		this.imageContainer.setLayoutX(scene.getWidth()/2 -(this.imageFit/2));
		this.imageContainer.setLayoutY(scene.getHeight()/2 - (this.imageNodeHeight/2));
	}

}

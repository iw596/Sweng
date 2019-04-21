package imageDisplay;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

/** Controller for the Image Display module. This class handles initalizing the viewer and adding listeners
 * 	which listen for user input.
 
 * Date created: 15/04/2019
 * Date last edited: 15/04/2019
 * Last edited by: Dan Jackson and Isaac Watson
 * 
 * @author Dan Jackson and Isaac Watson 
 *
 */
public class ImageDisplayController  implements Initializable {

    @FXML
    private BorderPane root;
	
    Image image;
    /** Constructor which calls method to load in the image file
     * 
     * @param image_path - path to the image file
     */
	public ImageDisplayController(String image_path) {
		loadImageFile(image_path);
	}
	
	/**
	 * Attempt to load image file from specified file path 
	 * @param filePath chosen file path for image file to be loaded
	 * @return 0 is successful, 1 if not successful
	 */
	
	public int loadImageFile(String filePath){
		
		// The specific image file to be used

		File imgFile = new File(filePath);

		// Store the imgFile extension from the file path

		String fileExt = imgFile.getName().substring( imgFile.getName().lastIndexOf(".") + 1);
		
		// Check if file path specified is a file, not a directory 

		if (!(imgFile.isFile())){
			// If imgFile is not a file

			// Display error alert

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid File Path");
			alert.setContentText("File not found.\nEnter new file path.");
			System.out.println("Invalid File Path");
			alert.showAndWait();
			//Returns 1, meaning loading was unsuccessful
			return 1;
	
		// Check if file extension matches the chosen extensions 

		}else if (!(fileExt.equals("jpg")||fileExt.equals("png")||fileExt.equals("gif")||fileExt.equals("jpeg")||fileExt.equals("JPG")||fileExt.equals("PNG")||fileExt.equals("GIF")||fileExt.equals("JPEG"))){
			// If imgFile extension does not match chosen extensions
			// Display error alert

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid File Extension");
			alert.setContentText("Only .jpg .jpeg .png .gif file types.");
			alert.showAndWait();

			// Returns 1, meaning loading was unsuccessful
			return 1;
		}
		
		// If file path and file type are valid, load the image file and return 0

		image = new Image("file:" + imgFile.getAbsolutePath());
		return 0;
	}
	
	/** 
	 * Initialise method adds image to pane and sets up listeners which monitor user input
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// Create a new image profile using the wanted image and then add the image_prodiles container
		// to the BorderPane
		imageProfile image_profile = new imageProfile(image);
		root.getChildren().add(image_profile.getImageContainer());

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				image_profile.resizeImage(root.getWidth());
			}

		});
		// Listener which listens for user dragging the image and moves the image to the wanted locaton
		image_profile.getImageContainer().setOnMouseDragged(e->{
			
			//Check if the cursor position is with in the scene's bounds
			if ((e.getSceneX() > 0 && e.getSceneX() < root.getScene().getWidth())
					&&(e.getSceneY() > 0 && e.getSceneY() < root.getScene().getHeight())){
				
				// If cursor within width and height bounds, move image to cursor's position
				image_profile.setPosition(e.getX(), e.getY(), root.getScene());

			}
		});
		
		
		// Listener which listens for user scrolling the mouse wheel. This will zoom im/out on the image
		image_profile.getImageContainer().setOnScroll(e->{

			// Get the value of the scroll amount

			double delta = e.getDeltaY();
			
			// Call function to scale the image size

			// Reduced delta value to improve scaling range
			image_profile.scaleImage(delta);
		});
		
		
	}
	
	/**
	 * ****ADDED BY Dan Jackson OF COMPANY WeTech****
	 * Method for when the image handler is closed. Removes all images from the screen.
	 */
	public void exit() {
		root.getChildren().removeAll();
		root.setVisible(false);
	}
	
}

// Copyright © 2019 York Software Solutions. All Rights Reserved.
//
// The recipient may not distribute, license, loan or sell any part
// of the code contained herein under the Copyright, Designs and Patents Act 1988.
//
// Removal, modification or obfuscation of this trademark shall be considered a 
// breach of the terms of the license and may result in legal action.
//
// Module Name: Single Image Display Handler
//
// Description: 
//
// A module for loading a single image file and manipulating that image
//
// Authors: Cameron Ellwood
//
// Date Created: 27/02/19

package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.File;
import application.imageProfile;
import javafx.scene.layout.Pane;

public class Main extends Application {

	// Declare global variables

	Image image;
	Pane root;
	Scene scene;
	
	public void start(Stage stage) {
		try {
			
			// JavaFX Containers

			// Pane to contain HBox

			root = new Pane();

			// Add pane to scene and set default size

			scene = new Scene(root,800,600);

			// Add scene to stage

			stage.setScene(scene);

			// Set stage title and show stage

			stage.setTitle("Image Display Application");
			stage.show();
			
			// Try to load image file from file path
			
			//Example file path:"C:/New folder/image file.jpg"
			int isSucc = loadImageFile("****");
			
			// Check if the image loaded successfully

			if (isSucc == 0){
				//If image loaded successfully, call main program function

				runProgram();
			}
			else {
				//If image did not load successfully, display error message

				Label error = new Label("Error loading image!");
				error.setStyle("-fx-font-size: 20;");
				root.getChildren().add(error);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Handles image manipulation
	 */
	public void runProgram(){
		
		
		// Create an imageProfile for the loaded image

		imageProfile imgPro = new imageProfile(image);

		// Set position of HBox to centre of scene

		// With adjustment to centre of image

		imgPro.setPosition(scene.getWidth()/2, scene.getHeight()/2, scene);

		// Add HBox to root pane

		root.getChildren().add(imgPro.getImageContainer());
		
		// Move image position

		// When HBox is clicked on and dragged...

		imgPro.getImageContainer().setOnMouseDragged(e->{
			
			//Check if the cursor position is with in the scene's bounds
			if ((e.getSceneX() > 0 && e.getSceneX() < scene.getWidth())
					&&(e.getSceneY() > 0 && e.getSceneY() < scene.getHeight())){
				
				// If cursor within width and height bounds, move image to cursor's position

				imgPro.setPosition(e.getSceneX(), e.getSceneY(), scene);
			}
		});
		
		// Scale image

		// When cursor is on HBox and the scroll wheel is use
		imgPro.getImageContainer().setOnScroll(e->{

			// Get the value of the scroll amount

			double delta = e.getDeltaY();

			// Call function to scale the image size

			// Reduced delta value to improve scaling range
			imgPro.scaleImage(delta);
		});
		
		// Reset image defaults

		// When the enter key is pressed, restore the images start size and position

		scene.setOnKeyPressed(e->{
			if (e.getCode() == KeyCode.ENTER){
				imgPro.resetImage(scene);
			}
		});
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

		}else if (!(fileExt.equals("jpg")||fileExt.equals("png")||fileExt.equals("gif"))){
			// If imgFile extension does not match chosen extensions
			// Display error alert

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid File Extension");
			alert.setContentText("Only .jpg .png .gif file types.");
			alert.showAndWait();

			// Returns 1, meaning loading was unsuccessful
			return 1;
		}
		
		// If file path and file type are valid, load the image file and return 0

		image = new Image("file:"+imgFile.getAbsolutePath());
		return 0;
	}
}

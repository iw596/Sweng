package resultsScreenGUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import analytics.AnalyticsController;
import backEnd.BackEndContainer;
import cloudInteraction.UploadingScreenController;
import comparisonScreenGUI.ComparisonScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import searchScreenGUI.NotLoggedInScreenController;

/**
 * Controller for the ResultsScreen.fxml file. Initialises results screen and UI components.
 * 
 * Date created: 14/03/2019
 * Date last edited: 21/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Harry Ogden & Aeri Olsson
 *
 */
public class ResultsScreenController implements Initializable {
	// UI Elements
	@FXML 
	BorderPane root;
	
	@FXML 
	VBox results_pane;
	
	@FXML 
	JFXButton define_button;
	
	@FXML 
	JFXButton save_button;
	
	@FXML 
	private JFXButton save_as_button;
	
	@FXML 
	private ScrollPane scroll_pane;
	
    @FXML
    private AnchorPane statistics_pane;

    @FXML
    private HBox bottom_container;
	
    @FXML
    private VBox results_box_container;
    
    @FXML
    private VBox check_box_container;
	
    @FXML
    private JFXCheckBox save_online_toggle;
    
    private Boolean save_list_online = false;

    @FXML
    private JFXCheckBox make_public_toggle;
    
    @FXML
    private JFXCheckBox share_results_toggle;
    
    private Boolean share_results = false;
    
    private Boolean share_list_publicly = false;
    
    private Boolean list_has_been_shared = false;
	
	// Array list containing list items ranked in order
	// All items from rankinglist are sorted into the ranked items arraylist.
	private ArrayList<ResultItem> rankedItems;
	
	// list position of current item in ranking list. (first, second, third...)
	private String position;

	private BackEndContainer back_end;
	
	/**
	 * Constructor for the results screen controller.
	 * 
	 * @param back_end
	 */
	public ResultsScreenController(BackEndContainer back_end) {
		this.back_end = back_end;
	}
	

    @FXML
    /**
     * Method to toggle whether or not the list should be saved on the user's
     * online account.
     * 
     * @param event
     */
    void toggleOnlineSaving(ActionEvent event) {
    	
    	//toggles a boolean variable
    	if(save_list_online) {
    		save_list_online = false;
    		//disables the public sharing check box if unchecked as impossible to share
    		//publicly if not stored online
    		make_public_toggle.setDisable(true);
    		make_public_toggle.selectedProperty().set(false);
    		share_list_publicly = false;
    	} else {
    		save_list_online = true;
    		make_public_toggle.setDisable(false);
    	}
    	
    }

    @FXML
    /**
     * Method to toggle whether or not the list should be saved in a user's 
     * public area using a check box.
     * 
     * @param event
     */
    void togglePublicSharing(ActionEvent event) {
    	
    	if(share_list_publicly) {
    		share_list_publicly = false;
    	} else {
    		share_list_publicly = true;
    	}
    	
    }
    
    @FXML
    /**
     * Method to toggle whether or not the results of a public list should be shared
     * using a check box.
     * 
     * @param event
     */
    void toggleResultsSharing(ActionEvent event) {
    	
    	//toggles a boolean variable
    	if(share_results) {
    		share_results = false;
    	} else {
    		share_results = true;
    	}
    	
    }
	
	@Override
	/**
	 * Method for initialising results screen. Sorts RankingList passed from algorithm into a
	 * rankedItems array list for display on screen.
	 */
	public void initialize(URL url, ResourceBundle rb){
		// Forces the V-box inside the scroll pane to fit the size of the scroll pane. 
		scroll_pane.setFitToHeight(true);
		scroll_pane.setFitToWidth(true);

		// Array list of results items to be displayed on screen
		// Results items consist of item position (e.g. 1st, 2nd) and item title (e.g. cat)
		rankedItems = new ArrayList<ResultItem>();
		
		// Cycle through ranked results list passed into controller from algorithm
		// Store each item into resultItem variable for display
		// Loops for amount of items within ranked list.
		for (int i=0;i<back_end.getRankedResults().getSize();i++){
			// Check to see if rank value is equal to previous item (two items have same list position)
			if (!(i>0 && back_end.getRankedResults().get(i).getRanking() == back_end.getRankedResults().get(i-1).getRanking())){
				// If adjacent items are not of same position set position of current item to index in list
				position = i + 1 + ".";
				// If adjacent items are of same position, index is retained for next item
			}
			// Create new resultItem containing title and position of current item
			ResultItem item = new ResultItem(back_end.getRankedResults().get(i).getWrappedItem().getTitle(),position);
			// Add item to array list
			rankedItems.add(item);
			// Display item on screen
			results_pane.getChildren().addAll(item.getHBox());
			
			if(back_end.getCurrentListSize() < 2) {
				define_button.setMouseTransparent(true);
				define_button.setVisible(false);
				define_button.setManaged(false);
			}
			
		}
		
		//if logged in and the list is owned by the logged in account
		if(back_end.getLocalAccount() != null && (back_end.getListOwner() == null || back_end.getLocalAccount().getUsername().equals(back_end.getListOwner()))) {
			//display the check boxes for cloud storage and public sharing
			save_online_toggle.setVisible(true);
			make_public_toggle.setVisible(true);
			make_public_toggle.setDisable(true);
		} else {
			//remove the check boxes for cloud storage and public sharing
			bottom_container.getChildren().remove(check_box_container);
		}

		//if logged in and the list is owned by a different account to the one logged in
		if(back_end.getLocalAccount() != null && back_end.getListOwner() != null && !back_end.getLocalAccount().getUsername().equals(back_end.getListOwner())) {
			//show the share results check box
			share_results_toggle.setVisible(true);
		} else {
			//remove the share results check box
			bottom_container.getChildren().remove(results_box_container);
		}
		
		this.save_as_button.setLayoutX(bottom_container.getWidth());
		this.save_button.setLayoutX(bottom_container.getWidth());
		
		if(back_end.getLocalAccount() == null) {
			try {
				loadNotLoggedInScreen();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return;
		}
		
		try {
			loadStatisticsScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	/**
	 * Action listener for "Update Defined List" button.
	 * @param action
	 */
	public void defineList(MouseEvent action){
		// Trigger algorithm again
		
		back_end.getLosers();
		back_end.compareLosers();
		back_end.setComparingLosers(true);
		
		try {
			FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
        	ComparisonScreenController controller = new ComparisonScreenController(back_end);
        	loader.setController(controller);
        	BorderPane new_pane = loader.load();
        	showInSelf(new_pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	/**
	 * Action listener for "Save List" button.
	 * @param action
	 */
	public void saveList(MouseEvent action){

    	if(back_end.getCurrentListFileName() != null) {
    		
    		if(!list_has_been_shared) {
    			back_end.updateSaveListToXML(back_end.getCurrentListFileName());
    		}
    		
    		if(back_end.getLocalAccount() != null && save_list_online) {
    			showListUploadScreen();
    			//back_end.uploadList(back_end.getCurrentListFileName(), shareListPublicly);
    		} else if(back_end.getLocalAccount() != null && share_results) {
    			if(list_has_been_shared == false) { 
    				System.out.println("In sharing clause");
    				back_end.uploadResults();
    				list_has_been_shared = true;
    			}
    		}
    		
    	} else {
    		save_as_button.fireEvent(action);
    	}

	}
	
	/**
	 * Method to load the list upload splash screen.
	 */
	private void showListUploadScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(cloudInteraction.UploadingScreenController.class.getResource("UploadingScreen.fxml"));
			UploadingScreenController controller = new UploadingScreenController(back_end, back_end.getCurrentListFileName(), share_list_publicly);
			loader.setController(controller);
			BorderPane new_pane = loader.load();
			showInSelf(new_pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	/**
	 * Action listener for "Save As List" button.
	 * @param action
	 */
    void saveAsList(MouseEvent event) {
		// Save current list
    	Stage stage = (Stage)root.getScene().getWindow();
    	FileChooser file_chooser = new FileChooser();
    	
    	file_chooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/saves"));
    	
    	file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Chuse Comparison List", "*.xml"));
    	File new_file = file_chooser.showSaveDialog(stage);

    	if(new_file != null) {
    		back_end.saveCurrentListToXML(new_file.getAbsolutePath());

    		if(back_end.getLocalAccount() != null && save_list_online) {
    			showListUploadScreen();
    			//back_end.uploadList(new_file.getAbsolutePath(), shareListPublicly);
    		}
    		
    	}
    	
    	root.requestFocus();
    	
    }
	
	/**
	 * Method to load the statistics screen in the analytics pane.
	 * @throws IOException
	 */
    private void loadStatisticsScreen() throws IOException {
    	
		FXMLLoader loader = new FXMLLoader(analytics.AnalyticsController.class.getResource("Analytics.fxml"));
		AnalyticsController controller = new AnalyticsController(back_end, back_end.getListOwner());
		loader.setController(controller);
		ScrollPane new_pane = loader.load();
		
    	new_pane.prefWidthProperty().bind(statistics_pane.widthProperty());
    	new_pane.prefHeightProperty().bind(statistics_pane.heightProperty());
    	new_pane.minWidthProperty().bind(statistics_pane.minWidthProperty());
    	new_pane.minHeightProperty().bind(statistics_pane.minHeightProperty());
    	new_pane.maxWidthProperty().bind(statistics_pane.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(statistics_pane.maxHeightProperty());
		
		statistics_pane.getChildren().add(new_pane);
		
    }
    
    /**
     * Method to load the not logged in screen in the analytics pane.
     * @throws IOException
     */
    private void loadNotLoggedInScreen() throws IOException {

		FXMLLoader loader = new FXMLLoader(analytics.AnalyticsController.class.getResource("NotLoggedInAnalyticsScreen.fxml"));
		NotLoggedInScreenController controller = new NotLoggedInScreenController(back_end, null);
		loader.setController(controller);
		BorderPane new_pane = loader.load();
		
    	new_pane.prefWidthProperty().bind(statistics_pane.widthProperty());
    	new_pane.prefHeightProperty().bind(statistics_pane.heightProperty());
    	new_pane.minWidthProperty().bind(statistics_pane.minWidthProperty());
    	new_pane.minHeightProperty().bind(statistics_pane.minHeightProperty());
    	new_pane.maxWidthProperty().bind(statistics_pane.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(statistics_pane.maxHeightProperty());
		
		statistics_pane.getChildren().add(new_pane);
		
	}
	
    /**
     * Method to display another .fxml file within the current screen.
     * @param new_pane
     */
    private void showInSelf(Pane new_pane) {
    	
    	new_pane.prefWidthProperty().bind(root.widthProperty());
    	new_pane.prefHeightProperty().bind(root.heightProperty());
    	new_pane.minWidthProperty().bind(root.minWidthProperty());
    	new_pane.minHeightProperty().bind(root.minHeightProperty());
    	new_pane.maxWidthProperty().bind(root.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(root.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	root.setCenter(new_pane);
    	
    	root.requestFocus();
    	
    	System.gc();
	
    }
	
}

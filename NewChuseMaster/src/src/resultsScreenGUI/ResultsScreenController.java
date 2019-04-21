package resultsScreenGUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import comparisonScreenGUI.ComparisonScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller for the ResultsScreen.fxml file. Initialises results screen and UI components.
 * 
 * Date created: 14/03/2019
 * Date last edited: 14/03/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden & Aeri Olsson
 *
 */
public class ResultsScreenController implements Initializable {
	// UI Elements
	@FXML BorderPane root;
	@FXML VBox results_pane;
	@FXML JFXButton define_button;
	@FXML JFXButton save_button;
	@FXML private JFXButton save_as_button;
	@FXML private ScrollPane scroll_pane;
	
	// Array list containing list items ranked in order
	// All items from rankinglist are sorted into the ranked items arraylist.
	private ArrayList<ResultItem> rankedItems;
	
	// list position of current item in ranking list. (first, second, third...)
	private String position;

	private BackEndContainer back_end;
	
	public ResultsScreenController(BackEndContainer back_end) {
		this.back_end = back_end;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	/**
	 * Action listener for "Save List" button.
	 * @param action
	 */
	public void saveList(MouseEvent action){
		
		String file_path_check = System.getProperty("user.dir") + "/saves" + "/cool" + ".xml";
		// need to redo XML bulider as Chuselist as name of list dose not apper (is null)
		//String file_path_check = System.getProperty("user.dir") + "/saves" + back_end.getCurrentList().getName() + ".xml";
    	
    	File file = new File(file_path_check);
    	
    	// check is it exists
    	if(file.exists()) {
    		System.out.println("exists");
    		
    		back_end.updateSaveListToXML(file_path_check);
    		
    	}else {
    		// send them to save as function
    		// not sure if you can call save as function
    		
    		System.out.println("does not exists");
    		
    		Stage stage = (Stage)root.getScene().getWindow();
        	FileChooser file_chooser = new FileChooser();
        	
        	file_chooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/saves"));
        	
        	file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Chuse Comparison List", "*.xml"));
        	File new_file = file_chooser.showSaveDialog(stage);

        	if(new_file != null) {
        		back_end.saveCurrentListToXML(new_file.getAbsolutePath());
        	}
        	
        	root.requestFocus();
    		
    	}
    	//System.out.println(file_chooser.showSaveDialog(stage).);
		
    	
	}
	
	@FXML
	/**
	 * Action listener for "Save As List" button.
	 * @param action
	 */
    void saveAsList(MouseEvent event) {
		// Save current list
		System.out.println("Save List Pressed");
		
    	Stage stage = (Stage)root.getScene().getWindow();
    	FileChooser file_chooser = new FileChooser();
    	
    	file_chooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/saves"));
    	
    	file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Chuse Comparison List", "*.xml"));
    	File new_file = file_chooser.showSaveDialog(stage);

    	if(new_file != null) {
    		back_end.saveCurrentListToXML(new_file.getAbsolutePath());
    	}
    	
    	root.requestFocus();
    	
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
	
    }
	
}

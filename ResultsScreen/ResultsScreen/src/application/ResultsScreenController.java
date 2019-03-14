package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import listDataStructure.BasicItem;
import listDataStructure.RankingItem;
import listDataStructure.RankingList;

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
	@FXML BorderPane root_pane;
	@FXML VBox results_pane;
	@FXML JFXButton define_button;
	@FXML JFXButton save_button;
	@FXML private ScrollPane scroll_pane;
	
	// Ranking list of items passed from algorithm
	private RankingList results;
	// Array list containing list items ranked in order
	// All items from rankinglist are sorted into the ranked items arraylist.
	private ArrayList<resultItem> rankedItems;
	
	// Sample items for example results list
	private BasicItem item01;
	private BasicItem item02;
	private BasicItem item03;
	private BasicItem item04;
	private RankingItem rankItem01;
	private RankingItem rankItem02;
	private RankingItem rankItem03;
	private RankingItem rankItem04;
	
	// list position of current item in ranking list. (first, second, third...)
	private String position;
		
	@Override
	/**
	 * Method for initialising results screen. Sorts RankingList passed from algorithm into a
	 * rankedItems array list for display on screen.
	 */
	public void initialize(URL url, ResourceBundle rb){
		// Forces the V-box inside the scroll pane to fit the size of the scroll pane. 
		scroll_pane.setFitToHeight(true);
		scroll_pane.setFitToWidth(true);
		
		// EXAMPLE RESULTS LIST
		results = new RankingList();
		item01 = new BasicItem("cat");
		item02 = new BasicItem("girafe");
		item03 = new BasicItem("dog");
		item04 = new BasicItem("pingu");
		rankItem01 = new RankingItem(item01,1);
		rankItem02 = new RankingItem(item02,3);
		rankItem03 = new RankingItem(item03,8);
		rankItem04 = new RankingItem(item04,8);
		results.addItem(rankItem01);
		results.addItem(rankItem02);
		results.addItem(rankItem03);
		results.addItem(rankItem04);
		// EXAMPLE RESULTS LIST
		
		// Array list of results items to be displayed on screen
		// Results items consist of item position (e.g. 1st, 2nd) and item title (e.g. cat)
		rankedItems = new ArrayList<resultItem>();
		
		// Cycle through ranked results list passed into controller from algorithm
		// Store each item into resultItem variable for display
		// Loops for amount of items within ranked list.
		for (int i=0;i<results.getSize();i++){
			// Check to see if rank value is equal to previous item (two items have same list position)
			if (!(i>0 && results.get(i).getRanking() == results.get(i-1).getRanking())){
				// If adjacent items are not of same position set position of current item to index in list
				position = i + 1 + ".";
				// If adjacent items are of same position, index is retained for next item
			}
			// Create new resultItem containing title and position of current item
			resultItem item = new resultItem(results.get(i).getWrappedItem().getTitle(),position);
			// Add item to array list
			rankedItems.add(item);
			// Display item on screen
			results_pane.getChildren().addAll(item.getHBox());
		}
	}
	
	@FXML
	/**
	 * Action listener for "Update Defined List" button.
	 * @param action
	 */
	public void DefineList(MouseEvent action){
		// Trigger algorithm again
		System.out.println("Further Define List Pressed");
	}
	
	@FXML
	/**
	 * Action listener for "Save List" button.
	 * @param action
	 */
	public void SaveList(MouseEvent action){
		// Save current list
		System.out.println("Save List Pressed");
	}
	
	/**
	 * Method for passing RankingList from algorithm into controller.
	 * @param resultsList
	 */
	public void setResultsList(RankingList resultsList){
		// Pass algorithm results list into controller
		results = resultsList;
	}
}

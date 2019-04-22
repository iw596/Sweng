package resultsScreenGUI;

import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Class for result item, consisting of item title and item position (1s, 2nd etc).
 * Stores item details in HBox for display on screen.
 * 
 * @author Harry Ogden & Aeri Olsson
 *
 */
public class ResultItem {
	HBox newResult = new HBox();
	Text title = new Text();
	Text position = new Text();
	
	/**
	 * Constructor for resultItem, sets style and layout of item.
	 * @param ItemTitle
	 * @param ItemPosition
	 */
	ResultItem(String ItemTitle, String ItemPosition){
		// Set style for HBox display
		newResult = new HBox();
		newResult.setPadding(new Insets(15,12,15,12));
		newResult.setSpacing(10);
		newResult.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
		        + "-fx-border-radius: 5;" + "-fx-border-color: black;");
		newResult.setAlignment(Pos.BASELINE_LEFT);
		// Store title passed into constructor
		title.setText(ItemTitle);
		title.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 15));
		// Store position passed into constructor
		position.setText(ItemPosition);
		// Add title/position to HBox for display
		newResult.getChildren().addAll(position,title);
	}
	
	/**
	 * Get item position (1st, 2nd etc)
	 * @return
	 */
	public Text getItemPosition(){
		return this.position;
	}
	
	/**
	 * Get item title
	 * @return
	 */
	public Text getItemTitle(){
		return this.title;
	}
	
	/**
	 * Get HBox (required for adding to results_pane VBox)
	 * @return
	 */
	public HBox getHBox(){
		return this.newResult;
	}
}

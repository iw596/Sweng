package previewListScreenGUI;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PublicListItem {

	HBox newItem = new HBox();
	Text title = new Text();
	Text position = new Text();
	
	/**
	 * Constructor for resultItem, sets style and layout of item.
	 * @param ItemTitle
	 * @param ItemPosition
	 */
	PublicListItem(String ItemTitle, String ItemPosition){
		// Set style for HBox display
		newItem = new HBox();
		newItem.setPadding(new Insets(15,12,15,12));
		newItem.setSpacing(10);
		newItem.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
		        + "-fx-border-radius: 5;" + "-fx-border-color: black;");
		newItem.setAlignment(Pos.BASELINE_LEFT);
		// Store title passed into constructor
		title.setText(ItemTitle);
		title.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 15));
		// Store position passed into constructor
		position.setText(ItemPosition);
		// Add title/position to HBox for display
		newItem.getChildren().addAll(position,title);
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
		return this.newItem;
	}
	
}

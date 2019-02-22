import java.io.File;
import java.net.URL;
import java.util.Map.Entry;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class ImageDisplay extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Image Display");
		VBox root = new VBox();
		
		ImageView IV = new ImageView();
		Image image = new Image(new File("BBC-test-card-F-featuring-008.jpg").toURI().toString());
		IV.setImage(image);
		
		//IV.fitHeightProperty().bind(stage.heightProperty());
		//IV.fitWidthProperty().bind(stage.widthProperty());
		
		root.getChildren().add(IV);
		Scene scene = new Scene(root, image.getWidth(), image.getHeight(), Color.BLACK);
		stage.setScene(scene);
		stage.show();
	}

}

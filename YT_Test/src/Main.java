import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public  void start(Stage stage) throws Exception {
		WebView myWebView = new WebView();
		WebEngine engine = myWebView.getEngine();
		engine.load("https://www.youtube.com/embed/RcP5jDFd0aY");
		
		VBox root = new VBox();
		root.getChildren().addAll(myWebView);
		
		Scene scene = new Scene(root,500,200);
		stage.setScene(scene);
		
		stage.show();
	}

}

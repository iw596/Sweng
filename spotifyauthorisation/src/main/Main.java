package main;

import auth.AuthURI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viewer.Viewer;

public class Main extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		AuthURI auth_uri = new AuthURI();
		Viewer viewer =  new Viewer(auth_uri.authorizationCodeUri_Sync(),stage, auth_uri);
		
		Scene scene = new Scene(viewer);
		
		stage.setScene(scene);
		stage.show();
		viewer.fetchToken();
		/* while (viewer.token.equals("a")) {
			
		} */
		System.out.println("The fetched token is:" + viewer.token);
		//stage.close();
		
		
		
		
	}

}

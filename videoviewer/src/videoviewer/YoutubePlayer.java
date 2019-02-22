package videoviewer;


import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * YoutubePLayer is the class in the videoviewer project. This handles the creation
 * of an object which can create an embedded web browser which loads up a youtube
 * video and plays it
 * 
 * Date created: 21/02/2019
 * Date last edited: 21/02/2019
 * Last edited by: Isaac Watson
 *
 *@author Isaac Watson
 */

public class YoutubePlayer extends BorderPane {
	private WebView web_view;
	private WebEngine web_engine;
	private String path = "https://www.youtube.com/embed/RcP5jDFd0aY";
	private Pane mpane;
	

	/** Youtube player constructor which generates the webviewer which displays
	 * the browser and the web engine which handles the loading of videos.
	 * The size of the webview is also set.
	 * 
	 */
	public YoutubePlayer() {
		// Creates a web viewer and a webengine 
		this.web_view = new WebView();
		this.web_engine = web_view.getEngine();
		// Create new pane and add webviewer to it
		mpane = new Pane(); 
		mpane.getChildren().add(web_view);
		setCenter(mpane); 
		
		// Set the size of the web view window
		// Note need to make this resizeable at some point
		web_view.setPrefSize(640, 390);
		
	}
	
	/*** This method is a getter for the web_view variable
	 * 
	 * @return web_view - the viewer for the embedded webpage
	 */
	public WebView getWebView() {
		return this.web_view;
	}
	
	/*** This method loads the page the web_engine has been linked too
	 * 
	 */
	public void loadPage() {
		web_engine.load(path);
	}
	

	
	

}

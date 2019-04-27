package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import com.wrapper.spotify.SpotifyApi;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import listDataStructure.BasicItem;
import spotifyPlayback.GetAvailableDevices;
import spotifyPlayback.PlaybackPause;

public class SampleController03 implements Initializable {

    @FXML private BorderPane chrome_view;
    ArrayList<BasicItem> spotify_items;
    private SpotifyApi spotifyApi;
    private PlaybackPause pause;
    
    @FXML private Text track_title;
    @FXML private Text track_artist;
    @FXML private Text track_album;
    
    String Track01_url;
    String Track02_url;
    ChromeDriver Driver;
    
    int Track01_index = 0;
    int Track02_index = 1;
    
    @FXML
    private ImageView IV;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			System.setProperty("webview.chrome.driver", "H://Sweng/SpotifyPlayer/SpotifyPlayer/chromedriver.exe");
			Driver = new ChromeDriver();
			pause = new PlaybackPause(spotifyApi);
			
			Track01_url = spotify_items.get(Track01_index).getPath();
			Track02_url = spotify_items.get(Track02_index).getPath();
			
			String Email = "harry.o1998@hotmail.co.uk";
			String Password = "buckethead";
			
			Driver.get("https://accounts.spotify.com/en/login");
			
			Driver.findElement(By.xpath("//*[@id='login-username']")).sendKeys(Email);
			Driver.findElement(By.xpath("//*[@id=\"login-password\"]")).sendKeys(Password);
			Driver.findElement(By.xpath("//*[@id='login-button']")).click();
			while (Driver.getCurrentUrl().equals("https://accounts.spotify.com/en/login")) {
				System.out.println(Driver.getCurrentUrl());
			}
			
			Driver.get("https://open.spotify.com/track/" + Track01_url);
			
			com.wrapper.spotify.model_objects.specification.Image art = spotify_items.get(Track01_index).getAlbumArt();
			Image image = new Image(art.getUrl());
			IV.setImage(image);
		});
	}
	
	public void setItems( ArrayList<BasicItem> items){
		spotify_items = items;
	}
	
	public void setAPI(SpotifyApi spotifyApi) {
		// TODO Auto-generated method stub
		this.spotifyApi = spotifyApi;
	}
	
	  @FXML
	  void track01_selected(MouseEvent event) {
		  Driver.get("https://open.spotify.com/track/" + Track01_url);
		  track_title.setText("Title: " + spotify_items.get(Track01_index).getMetadata().get(0));
		  track_artist.setText("Artist: " + spotify_items.get(Track01_index).getMetadata().get(1));
		  track_album.setText("Artist: " + spotify_items.get(Track01_index).getMetadata().get(2));
	  }
	  
	  @FXML
	  void track02_selected(MouseEvent event) {
		  Driver.get("https://open.spotify.com/track/" + Track02_url);
		  track_title.setText("Title: " + spotify_items.get(Track02_index).getMetadata().get(0));
		  track_artist.setText("Artist: " + spotify_items.get(Track02_index).getMetadata().get(1));
		  track_album.setText("Artist: " + spotify_items.get(Track01_index).getMetadata().get(2));
	  }
	  
	  @FXML
	  void PlayPause(MouseEvent event) {
		  //pause.pauseUsersPlayback_Sync();
		  Driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[5]/footer/div/div[2]/div/div[1]/button[3]")).click();
	  }
	
	
}

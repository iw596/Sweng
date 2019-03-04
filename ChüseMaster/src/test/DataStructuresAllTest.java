package test;
/**
 * Test class to test if the all data structures can be added to the same list and work correctly
 * with the tournament algorithms already made.
 * 
 * Date created: 03/03/2019
 * Date last edited 03/03/2019
 * Last edited by: Ho Ting Lok 
 *
 * @author Ho Ting Lok 
 */
import java.io.IOException;
import java.util.ArrayList;

import algorithms.TournamentAlgorithms;
import apiHandlers.YouTubeAPIHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.RankingList;
import mediaFileImportHandling.AudioFileHandler;
import mediaFileImportHandling.ImageFileHandler;
import mediaFileImportHandling.TextFileHandler;
import xmlHandling.XMLHandler;

public class DataStructuresAllTest extends Application {
	/** This method is a revision of the TournamentAlgorithmsTest which gathers items from each different
	 *  type of file allowed in a ChuseList so far instead of just creating new items within the class.
	 * @throws Exception
	 */
	@Override
	public void start(Stage stage) throws Exception {
		//Initialises file browsers and sets file handlers
		ArrayList<BasicItem> text = TextFileHandler.openTextFile(stage);	//Changed to my own function for test purposes.
		ArrayList<BasicItem> image = ImageFileHandler.openMultipleImageFiles(stage);
		ArrayList<BasicItem> audio = AudioFileHandler.openMultipleAudioFiles(stage);
    	ChuseList videos = null;
		try {
			videos = YouTubeAPIHandler.getPlaylistData("https://www.youtube.com/watch?v=q6EoRBvdVPQ&list=PLFsQleAWXsj_4yDeebiIADdH5FMayBiJo");
		} catch (IOException e1) {
			// TODO Auto-generated catch block;
			e1.printStackTrace();
		}
		
    	ArrayList<BasicItem> video_array = new ArrayList<BasicItem>();
    	
    	for(int i = 0; i < videos.getSize(); i++) {
    		video_array.add(videos.get(i));
    	}
    	
    	//Mixed list with items of every type of data structure currently implemented.
        ChuseList list = new ChuseList("name");
        list.addItemArray(video_array);
        list.addItemArray(text);
        list.addItemArray(image);
        list.addItemArray(audio);
        XMLHandler.buildXMLFromList(list, "new xml image file");
        list.printList();

        //Test to check the size to confirm the right amount of items and right type
        int size = list.getSize();
        System.out.println(size);
		for(int i = 0; i < list.getSize(); i++) {
			System.out.println(list.get(i).getType());
			System.out.println(list.get(i).getPath());
		} 
       
		//Ranking algorithm
        ArrayList<ChuseList> result = TournamentAlgorithms.rankingAlgorithm(list);
        RankingList ranks = new RankingList(result);
        
		list = TournamentAlgorithms.findLosers(result);
		result = TournamentAlgorithms.rankingAlgorithm(list);
		ranks.addRankedResults(result);



	}

	//Launches file choosers
	public static void main(String[] args) {
		launch(args);
	}
}

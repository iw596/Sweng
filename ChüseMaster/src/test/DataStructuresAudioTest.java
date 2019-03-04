package test;

import java.util.ArrayList;

import algorithms.TournamentAlgorithms;
import javafx.application.Application;
import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.RankingList;
import mediaFileImportHandling.AudioFileHandler;
import xmlHandling.XMLHandler;

public class DataStructuresAudioTest extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		//Initialises file browser and sets file handler
		ArrayList<BasicItem> audio = AudioFileHandler.openMultipleAudioFiles(stage);
		
		//Initialisation of Chuse list, adding items gotten from file
		ChuseList list = new ChuseList("name");
        list.addItemArray(audio);
        list.printList();
        XMLHandler.buildXMLFromList(list, "new xml image file");
       
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
		list = TournamentAlgorithms.findLosers(result);
		result = TournamentAlgorithms.rankingAlgorithm(list);
		ranks.addRankedResults(result);

	}

	//Launches file chooser
	public static void main(String[] args) {
		launch(args);
	}

}

package analytics;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import cloudInteraction.CloudInteractionHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Text;
import listDataStructure.StatisticsDataStructure;


public class AnalyticsController implements Initializable {

    @FXML
    private BarChart<?, ?> barchart;
    
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private AreaChart<?, ?> areachart;

    @FXML
    private BarChart<?, ?> barchart3;

    @FXML
    private BarChart<?, ?> barchart4;
    
    @FXML
    private CategoryAxis barchart_x;

    @FXML
    private NumberAxis barchart_y;
  
    @Override
    public void initialize (URL url, ResourceBundle rb) {
    
    // Needed to access the cloud
    // get rid of this when integrating 
    new CloudInteractionHandler();	
    // Creates the data structure
 	StatisticsDataStructure aa = new StatisticsDataStructure(System.getProperty("user.dir") + "\\saves\\Test_12_XML.xml");
   
 	// aa is the information from the cloud to the statistics.
    chart1(aa);
    chart2(aa);
    chart3(aa);
    chart4(aa);   
    
    //Scroll bar to fit size of window.
    scroll.setFitToHeight(true);
    scroll.setFitToWidth(true);

   }
    
    /***
     * Method to create the Area chart on the top of the page
     * This chart is checking popularity of list over the last 7 days and displaying it by gender.
     * 
     * @param orignal
     * Authors: Jack Small, Luke Fisher, Aeri Olsson
     */
    public void chart1(StatisticsDataStructure orignal){
    	
    	//AREA CHART (Popularity over the last 7 days)
    	//Preparing two series for male and female entries.
    	XYChart.Series series1 = new XYChart.Series();  
    	series1.setName("Male"); 
    	XYChart.Series series2 = new XYChart.Series(); 
    	series2.setName("Female"); 
    	
    	//Setting the format for displaying the dates.
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	// Used for upper and lower bounds for date searching.
        Date date1;
        Date date2;
        
        //Gets results within a certain date range.
        StatisticsDataStructure Result; // = aa.getDataForGivenTimeRange(date1, date2);
        //Gets female and male results within results.
        StatisticsDataStructure Male;
        StatisticsDataStructure Female;
        
        
        // gets todays date
    	date2 = new Date();

    	// get date 7 days ago
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE, -7);
    	
    	
    	//Loops through each date for the past week to get the information from each day.
    	for(int i = 0; i < 7 ; i++){
    		
    		cal.add(Calendar.DATE, +1);
    		date1 = cal.getTime();
    		
    		try {
    			Result = orignal.getDataForGivenTimeRange(sdf.parse(sdf.format(cal.getTime())), sdf.parse(sdf.format(cal.getTime())));
    			Male = Result.getDataForGivenGender("Male");
    			Female = Result.getDataForGivenGender("Female");
    			
    			//Adding the information to the chart.
    			series1.getData().add(new XYChart.Data(cal.getTime().toString().substring(0, 10), Male.getNumberOfResults())); 
    			series2.getData().add(new XYChart.Data(cal.getTime().toString().substring(0, 10), Female.getNumberOfResults()));
    		} catch (ParseException e) {
    			// Catch 
    			e.printStackTrace();
    		} 
    		
    		
    		
    	}
    	
    	//Adding the two series to the chart.
    	areachart.getData().addAll(series1,series2); 
    	//Setting location of legend to the right hand side of chart.
    	areachart.setLegendSide(Side.RIGHT);
    	//Setting the title at the top of the chart.
    	areachart.setTitle("Popularity over last 7 days:");
    	
    }
    
    /***
     * Method to create the first bar chart(chart 2).
     * The score is given by Length of List - Item Index.
     * The average is displayed.
     * 
     * @param orignal (original data on the list)
     * Authors: Jack Small, Luke Fisher, Aeri Olsson
     */
    public void chart2(StatisticsDataStructure orignal){
    	
    	//Gives object to store data
        XYChart.Series set1 = new XYChart.Series<>();
        //ABCD = Name and number is value.
        int i;
        for(i = 0; i < orignal.getList().getSize(); i++){
        	
        	set1.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), orignal.getAverageScore(orignal.getList().get(i).getTitle())));
        	
        }
        
        //Displays a title at the top of the chart.
        barchart.setTitle("Favourite");
        //Gives name to data set, this name will be in the legend on the bar chart.
        set1.setName("Score");
        //bc.getData connects the data to the bar chart.
        barchart.getData().addAll(set1);
        barchart.setLegendSide(Side.RIGHT);
    }
    
    /***
     * Method to create the second bar chart.
     * This one is arranged by female and male.
     * The score is given by Length of List - Item Index.
     * The average is displayed.
     * 
     * @param orignal
     * 
     * Authors: Jack Small, Luke Fisher, Aeri Olsson
     */
    public void chart3(StatisticsDataStructure orignal){
    	
    	//Declaring the list storing the data for females.
    	XYChart.Series series4 = new XYChart.Series();
    	series4.setName("Female"); 
    	
    	//Declaring the list storing the data for males.
    	XYChart.Series series3 = new XYChart.Series(); 
    	series3.setName("Male"); 
    	
    	//Accessing the original data and sorting the data for female and male.
    	StatisticsDataStructure FemalesResults = orignal.getDataForGivenGender("Female");
    	StatisticsDataStructure MaleResults = orignal.getDataForGivenGender("Male");
    	int i;
    	
    	// Appending all the data into their series.
    	for(i = 0; i < orignal.getList().getSize(); i++){
    		series4.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), FemalesResults.getAverageScore(FemalesResults.getList().get(i).getTitle())));
    		series3.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), MaleResults.getAverageScore(MaleResults.getList().get(i).getTitle()))); 
    		
    	}
    	
    	//Setting legend on right hand side of chart.
    	barchart3.setLegendSide(Side.RIGHT);
    	//Adding the two series to the chart.
    	barchart3.getData().addAll(series4,series3);
    	//Title of chart
    	barchart3.setTitle("Favourite by gender");
    	
    }
    	
    /***
     * Method to assign data to the 3rd bar chart. 
     * This method is arranging the data in age blocks.
     * 
     * @param orignal
     */
    public void chart4(StatisticsDataStructure orignal){
    		
    	//Getting the original data and arranging it by age.
    	StatisticsDataStructure under18Results = orignal.getDataForGivenAgeRange(0, 18);
    	StatisticsDataStructure Results18_24 = orignal.getDataForGivenAgeRange(18, 24);
    	StatisticsDataStructure Results25_34 = orignal.getDataForGivenAgeRange(25, 34);
    	StatisticsDataStructure Results35_44 = orignal.getDataForGivenAgeRange(35, 44);
    	StatisticsDataStructure Results45_54 = orignal.getDataForGivenAgeRange(45, 54);
    	StatisticsDataStructure Results55_64 = orignal.getDataForGivenAgeRange(55, 64);
    	StatisticsDataStructure Results65over = orignal.getDataForGivenAgeRange(65, 200);
    	
    	//Assigning series for all the data.
    	XYChart.Series series5 = new XYChart.Series();  
    	series5.setName("Under 18"); 
    	XYChart.Series series6 = new XYChart.Series(); 
    	series6.setName("18-24"); 
    	XYChart.Series series7 = new XYChart.Series(); 
    	series7.setName("25-34"); 
    	XYChart.Series series8 = new XYChart.Series(); 
    	series8.setName("35-44"); 
    	XYChart.Series series9 = new XYChart.Series(); 
    	series9.setName("45-54"); 
    	XYChart.Series series10 = new XYChart.Series(); 
    	series10.setName("55-64"); 
    	XYChart.Series series11 = new XYChart.Series(); 
    	series11.setName("Over 65"); 
    	
    	// Adds all the data from the original data into the series created above.
    	for(int i = 0; i < orignal.getList().getSize(); i++){
    		series5.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), under18Results.getAverageScore(under18Results.getList().get(i).getTitle())));
    		series6.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), Results18_24.getAverageScore(Results18_24.getList().get(i).getTitle())));
    		series7.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), Results25_34.getAverageScore(Results25_34.getList().get(i).getTitle())));
    		series8.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), Results35_44.getAverageScore(Results35_44.getList().get(i).getTitle())));
    		series9.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), Results45_54.getAverageScore(Results45_54.getList().get(i).getTitle())));
    		series10.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), Results55_64.getAverageScore(Results55_64.getList().get(i).getTitle())));
    		series11.getData().add(new XYChart.Data(orignal.getList().get(i).getTitle(), Results65over.getAverageScore(Results65over.getList().get(i).getTitle())));
    	}
    	
    	
    	//Setting legend to right hand side of chart.
    	barchart4.setLegendSide(Side.RIGHT);
    	//Adding all series to the chart.
    	barchart4.getData().addAll(series5,series6, series7,series8,series9, series10,series11);
    	//Title of Chart
    	barchart4.setTitle("Favourite by age");
    	// Size of gap between categories (i.e. Gap between Banana and Cake) to make sure data is not too close.
    	barchart4.setCategoryGap(20);
    	
    	}
    

}
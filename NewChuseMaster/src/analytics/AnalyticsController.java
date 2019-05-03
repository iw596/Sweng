package analytics;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import backEnd.BackEndContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
  
    private BackEndContainer back_end;
    
    public AnalyticsController(BackEndContainer back_end, String username) {
    	this.back_end = back_end;
    	
	// check if user is logged 
    	if(username != null && !username.equals("Local (Not Logged In)")) {
		// set up statistics data structure on current list 
    		back_end.createCurrentListStatistics(username);
    	}
    	
    }
	
    /**
     * initial all the 4 charts of statistics
     *
     * @param url
     * @param rb
     *
     */
    @Override
    public void initialize (URL url, ResourceBundle rb) {
    
	    //Scroll bar to fit size of window.
	    scroll.setFitToHeight(true);
	    scroll.setFitToWidth(true);
    		
	    StatisticsDataStructure statistics = back_end.getCurrentListStatistics();
	    
	    if(back_end.getCurrentListStatistics() == null) {
	    	Text error_msg = new Text("This list has not been shared. Please share the list to view statistics about the responses!"); 
	    	error_msg.setStyle("-fx-font-size: 18;");
	    	error_msg.setTextAlignment(TextAlignment.CENTER);
	    	VBox container = new VBox(error_msg);
	    	container.setAlignment(Pos.CENTER);
	    	scroll.setContent(container);
	    	return;
	    }
	     
	    // aa is the information from the cloud to the statistics.
	    chart1(statistics);
	    chart2(statistics);
	    chart3(statistics);
	    chart4(statistics);   
   }
    
    /***
     * Method to create the Area chart on the top of the page
     * This chart is checking popularity of list over the last 7 days and displaying it by gender.
     * 
     * @param orignal
     * Authors: Jack Small, Luke Fisher, Aeri Olsson
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void chart1(StatisticsDataStructure orignal){
    	
    	//AREA CHART (Popularity over the last 7 days)
    	//Preparing two series for male and female entries.
    	XYChart.Series series1 = new XYChart.Series();  
    	series1.setName("Male"); 
    	XYChart.Series series2 = new XYChart.Series(); 
    	series2.setName("Female"); 
    	
    	//Setting the format for displaying the dates.
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        //Gets results within a certain date range.
        StatisticsDataStructure Result; // = aa.getDataForGivenTimeRange(date1, date2);
        //Gets female and male results within results.
        StatisticsDataStructure Male;
        StatisticsDataStructure Female;

    	// get date 7 days ago
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE, -7);
    	
    	
    	//Loops through each date for the past week to get the information from each day.
    	for(int i = 0; i < 7 ; i++){
    		
    		cal.add(Calendar.DATE, +1);
    		
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
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

package refactor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import systemFixPackage.timeReport;

public class InOutController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
	}

	@FXML
    private Pane inOut;

    @FXML
    private Button inButton, outButton;

    @FXML
    private TableView<timeToObList> timeReportTableView;

    @FXML
    private TableColumn<timeToObList, String> dateColumn, inColumn, outColumn, hoursColumn, absentColumn;

  //Method to set up the columns for the table view that displays shifts in the stamp in/out function
  	private void setUpTableView(){
  		dateColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("date"));
  		inColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("in"));
  		outColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("out"));
  		hoursColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("hours"));
  		absentColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("absent"));
  	}

  	public void initializeInOutWindow(){
  		setUpTableView();
  		setStatusOfStampButtons();
  		timeReportTableView.setItems(mainC.displayTimeReport(-1));
  	}

    @FXML
    void in(ActionEvent event) {
		try {
			this.timeReporter.stampIn(mainC.getUser().getUserId());
			setStatusOfStampButtons();
			timeReportTableView.setItems(mainC.displayTimeReport(-1));

			System.out.println("inOut");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("messageWindow.fxml"));
			Parent root = (Parent) loader.load();

			MessageWindowControler message = loader.getController();
			message.setMessage("You have stamped in");

			Stage stageMessage = new Stage();
			stageMessage.initStyle(StageStyle.UNDECORATED);
			stageMessage.setScene(new Scene(root));
			stageMessage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void out(ActionEvent event) {
    	//Code to stamp out
    	try {
    		this.timeReporter.stampOut(mainC.getUser().getUserId());
    		setStatusOfStampButtons();
    		timeReportTableView.setItems(mainC.displayTimeReport(-1));

    		FXMLLoader loader = new FXMLLoader(getClass().getResource("messageWindow.fxml"));
    		Parent root = (Parent) loader.load();

    		MessageWindowControler message = loader.getController();
    		message.setMessage("You have stamped out");

    		Stage stageMessage = new Stage();
    		stageMessage.initStyle(StageStyle.UNDECORATED);
    		stageMessage.setScene(new Scene(root));
    		stageMessage.showAndWait();

    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }

	private void setStatusOfStampButtons(){
		inButton.setDisable(statusOfStampInButton());
		outButton.setDisable(statusOfStampOutButton());
	}

	private boolean statusOfStampInButton(){
		ArrayList <String []> info = this.timeReporter.getTimeReport(mainC.getUser().getUserId());
		//ArrayList <String []> info = user.getTimeReport();

		boolean isTrue = false;

		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

		for(int i = 0; i<info.size(); i++){
			if(!info.get(i)[5].equals(ft.format(dNow))){
				isTrue = false;
			}
			else if((info.get(i)[2] == null) || (info.get(i)[3] != null && info.get(i)[2] != null) ){
				isTrue = false;
			}
			else{
				isTrue = true;
			}
		}

		return isTrue;
	}

	public boolean statusOfStampOutButton(){
		//ArrayList <String []> info = this.test.getTimeReport(1);
		ArrayList <String []> info = this.timeReporter.getTimeReport(mainC.getUser().getUserId());
		boolean isTrue = true;

		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

		for(int i = 0; i<info.size(); i++){
			if(!info.get(i)[5].equals(ft.format(dNow))){
				isTrue = true;
			}
			else if(info.get(i)[3] == null && info.get(i)[2] != null){
				isTrue = false;
			}
			else{
				isTrue = true;
			}
		}

		return isTrue;
	}
}

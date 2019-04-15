package application;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import systemFixPackage.User;
import systemFixPackage.UserFactory;
import systemFixPackage.timeReport;


public class menuController implements Initializable {



	//Used to make stage dragable
	private Node node;
	private Stage stage;
	private double xOffSet = 0;
	private double yOffSet = 0;

	//Fixa så rätt user används och att jag kan använda user factory
	//private DBConnection test = new DBConnection();
	private UserFactory userFac;
	private User user;
	private timeReport timeReporter = new timeReport();

	//Main AnchorPane everything is painted on
	@FXML
	private AnchorPane parent;

	//Label to display username and date + time
	@FXML
	private Label nameLabel, dateAndTimeLabel;

	//Pane to display main menu
	@FXML
	private Pane mainMenuPain;
	//Panes to display buttons for choices done in the main menu
	@FXML
	private Pane timePane, schedulePane, salarySlipPane, ManageAccountsPane, projectPane;

	//Pane to hide content
	@FXML
	private Pane hideMainScreen;

	//Panes to display content to the right when sub menu options is choosen
	@FXML
	private Pane  reportASickDay, applyForVaccation, editWorkingHours, inOutPane, generateSalarySlip;

	//FXML items relating to generateSalarySlipPane
	@FXML
	private Button generateSalarySlipButton;


	//FXML items relating to inOutPane
	@FXML
	private Button inButton, outButton;
	@FXML
	private TableView<timeToObList> timeReportTableView;
	@FXML
	private TableColumn<timeToObList, String> dateColumn, inColumn, outColumn, hoursColumn, absentColumn;

	//FXML items relating to reportASickDay
	@FXML
	private TextArea commentOnWhySick;

	@FXML
	private Button CallInSickButton;

	//FXML items relating to editWorkingHours

	@FXML
	private Button SaveEditWorkingHours;
	@FXML
	private TableView<timeToObList> timeReportTableView1;
	@FXML
	private TableColumn<timeToObList, String> dateColumn1, inColumn1, outColumn1, hoursColumn1, absentColumn1;




	//Set up at launch
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		hideMainScreen.toFront();

		setUpTableView();
		setUpTableViewEditable();
		displayTime();
		makeStageDragable();

	}

	private void setUpTableView(){
		dateColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("date"));
		inColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("in"));
		outColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("out"));
		hoursColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("hours"));
		absentColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("absent"));
	}

	private void setUpTableViewEditable(){
		dateColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("date"));
		inColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("in"));
		outColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("out"));
		hoursColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("hours"));
		absentColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("absent"));

		timeReportTableView1.setEditable(true);
		inColumn1.setCellFactory(TextFieldTableCell.forTableColumn());
		outColumn1.setCellFactory(TextFieldTableCell.forTableColumn());
	}


	private void displayTime() {
		Clock time = new Clock();
		dateAndTimeLabel.textProperty().bind(time.messageProperty());
		new Thread(time).start();

	}

	private void makeStageDragable(){
		parent.setOnMousePressed((event) -> {
			node = (Node) event.getSource();
	        stage = (Stage) node.getScene().getWindow();
			xOffSet = event.getSceneX();
			yOffSet = event.getSceneY();
		});

		parent.setOnMouseDragged((event) -> {
	        stage.setX(event.getScreenX() - xOffSet);
	        stage.setY(event.getScreenY() - yOffSet);
	        stage.setOpacity(0.8f);
		});

		parent.setOnDragDone((event) -> {
	        stage.setOpacity(1.0f);
		});

		parent.setOnMouseReleased((event) -> {
	        stage.setOpacity(1.0f);
		});


	}

	//Ändra så det är setUser , skapa och initiera en user
	public void setUser(String userName){

		userFac = UserFactory.initiateUserFactory(userName);
		user = userFac.getUser("WORKER");
		//

		nameLabel.setText(userName);

	}

	@FXML
	private void minimize_stage(MouseEvent event){
        //stage.setMaximized(true);

        stage.setIconified(true);
	}

	@FXML
	private void close_app(MouseEvent event){
		System.exit(0);

	}

	@FXML
	private void time(ActionEvent event){
		timePane.toFront();
	}

	@FXML
	private void applyForVaccation(ActionEvent event){
		applyForVaccation.toFront();
	}

	@FXML
	private void inOut(ActionEvent event){
		//Code here to stamp in or out
		inOutPane.toFront();
		setStatusOfStampButtons();
		timeReportTableView.setItems(displayTimeReport());

	}

	private ObservableList<timeToObList> displayTimeReport(){
		ObservableList<timeToObList> timeReport = FXCollections.observableArrayList();
		ArrayList <String []> info = this.timeReporter.getTimeReport(user.getUserId());
		String absent = "no";


		for(int i = 0; i<info.size(); i++){
			//Replace k with hours worked
			String k = Integer.toString(i+2);
			System.out.println(info.get(i)[4] + "----------------------------------");
			if(info.get(i)[4] != null){
				absent = info.get(i)[4];
			}

			timeReport.add(new timeToObList(info.get(i)[2], info.get(i)[3], info.get(i)[5], k, absent));
		}

		return timeReport;
	}

	private void setStatusOfStampButtons(){
		inButton.setDisable(stampInIsTrue());
		outButton.setDisable(stampOutIsTrue());
	}

	private boolean stampInIsTrue(){
		ArrayList <String []> info = this.timeReporter.getTimeReport(user.getUserId());
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

	private boolean stampOutIsTrue(){
		//ArrayList <String []> info = this.test.getTimeReport(1);
		ArrayList <String []> info = this.timeReporter.getTimeReport(user.getUserId());
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

	@FXML
	private void in(ActionEvent event){
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("messageWindow.fxml"));
			Parent root = (Parent) loader.load();

			MessageWindowControler message = loader.getController();
			message.setMessage("You have stamped in");

			Stage stageMessage = new Stage();
			stageMessage.initStyle(StageStyle.UNDECORATED);
			stageMessage.setScene(new Scene(root));
			stageMessage.showAndWait();

			//Test code
			//this.test.stampIn(1);
			this.timeReporter.stampIn(user.getUserId());
			setStatusOfStampButtons();
			timeReportTableView.setItems(displayTimeReport());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void out(ActionEvent event){
		//Code to stamp out
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("messageWindow.fxml"));
			Parent root = (Parent) loader.load();

			MessageWindowControler message = loader.getController();
			message.setMessage("You have stamped out");

			Stage stageMessage = new Stage();
			stageMessage.initStyle(StageStyle.UNDECORATED);
			stageMessage.setScene(new Scene(root));
			stageMessage.showAndWait();

			//Test code
			this.timeReporter.stampOut(user.getUserId());
			//this.test.stampOut(1);
			setStatusOfStampButtons();
			timeReportTableView.setItems(displayTimeReport());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void editWorkingHours(ActionEvent event){
		editWorkingHours.toFront();
		timeReportTableView1.setItems(displayTimeReport());
	}

	@FXML
	public void changeInCellEvent(CellEditEvent edittedCell)
    {
		timeToObList row =  timeReportTableView1.getSelectionModel().getSelectedItem();
		if(edittedCell.getNewValue().toString().matches("\\d{2}:\\d{2}:\\d{2}"))
			row.setIn((edittedCell.getNewValue().toString()));
		else
		{
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText("Wrong format");
			enterAlert.setContentText("Please use to following format 00:00:00");
			enterAlert.showAndWait();
		}
    }

	@FXML
	public void changeOutCellEvent(CellEditEvent edittedCell)
    {
		timeToObList row =  timeReportTableView1.getSelectionModel().getSelectedItem();
		if(edittedCell.getNewValue().toString().matches("\\d{2}:\\d{2}:\\d{2}"))
			row.setOut((edittedCell.getNewValue().toString()));
		else
		{
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText("Wrong format");
			enterAlert.setContentText("Please use to following format 00:00:00");
			enterAlert.showAndWait();
		}
    }

	@FXML
    void SaveEditWorkingHoursAction(ActionEvent event) {

		//Spara ändrinar i tiderna
		System.out.println("test");
		ArrayList <String []> info = new ArrayList<String[]>();
		for(int i = 0; i < timeReportTableView1.getItems().size(); i++){
			timeToObList row = timeReportTableView1.getItems().get(i);
			System.out.println(row.getIn());
		}

    }



	@FXML
	private void reportASickDay(ActionEvent event){
		reportASickDay.toFront();
		commentOnWhySick.clear();
		setStatusOfSickDayButton();
	}
	private void setStatusOfSickDayButton(){
		CallInSickButton.setDisable(CallInSickIsTrue());
	}

	private boolean CallInSickIsTrue(){

		ArrayList <String []> info = this.timeReporter.getTimeReport(user.getUserId());
		boolean isTrue = false;

		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

		for(int i = 0; i<info.size(); i++){
			if(!info.get(i)[5].equals(ft.format(dNow))){
				isTrue = false;
			}
			else if(info.get(i)[4] == null){
				isTrue = false;
			}
			else{
				isTrue = true;
			}
		}

		return isTrue;
	}

	@FXML
	private void callInSick(ActionEvent event){
		System.out.println(commentOnWhySick.getText());
		this.timeReporter.recordAbsence(user.getUserId(), "Sick", commentOnWhySick.getText());
		commentOnWhySick.clear();
		//if(!stampOutIsTrue()){
			//this.timeReporter.stampOut(user.getUserId());
			//this.test.stampOut(1);
		//}
		//this.timeReporter.recordAbsence(user.getUserId(), commentOnWhySick.getText());
		//Uppdatera proceduren i databasen

	}

	@FXML
	private void schedule(ActionEvent event){
		schedulePane.toFront();
	}

	@FXML
	private void salarySlip(ActionEvent event){
		salarySlipPane.toFront();
	}

	@FXML
	private void generateSalarySlip(ActionEvent event){
		generateSalarySlip.toFront();
		String[] salarySlip = this.timeReporter.generateSalarySlip(user.getUserId());

	}

	@FXML
	private void changePassword(ActionEvent event){
		ManageAccountsPane.toFront();
	}

	@FXML
	private void project(ActionEvent event){
		projectPane.toFront();
	}

	@FXML
	private void back(ActionEvent event){
		mainMenuPain.toFront();
		hideMainScreen.toFront();
	}


}

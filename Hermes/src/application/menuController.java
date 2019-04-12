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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.DBConnection;
import system.User;

public class menuController implements Initializable {

	//Provisoriskt
	private String userName;

	//Used to make stage dragable
	private Node node;
	private Stage stage;
	private double xOffSet = 0;
	private double yOffSet = 0;

	//Fixa så rätt user används och att jag kan använda user factory
	private DBConnection test = new DBConnection();

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
	private Pane  reportASickDay, applyForVaccation, editWorkingHours, inOutPane;


	//FXML items relating to inOutPane
	@FXML
	private Button inButton, outButton;
	@FXML
	private TableView<timeToObList> timeReportTableView;
	@FXML
	private TableColumn<timeToObList, String> dateColumn, inColumn, outColumn, hoursColumn;

	//FXML items relating to reportASickDay
	@FXML
	private TextArea commentOnWhySick;

	@FXML
	private Button CallInSickButton;


	//Set up at launch
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		hideMainScreen.toFront();

		setUpTableView();
		displayTime();
		makeStageDragable();

	}

	private void setUpTableView(){
		dateColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("date"));
		inColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("in"));
		outColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("out"));
		hoursColumn.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("hours"));
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
	public void setUserName(String userName){
		this.userName = userName;
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
		ArrayList <String []> info = this.test.getTimeReport(1);

		for(int i = 0; i<info.size(); i++){
			//Replace k with hours worked
			String k = Integer.toString(i+2);
			timeReport.add(new timeToObList(info.get(i)[2], info.get(i)[3], info.get(i)[5], k));
		}

		return timeReport;
	}

	private void setStatusOfStampButtons(){
		inButton.setDisable(stampInIsTrue());
		outButton.setDisable(stampOutIsTrue());
	}

	private boolean stampInIsTrue(){
		ArrayList <String []> info = this.test.getTimeReport(1);
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
		ArrayList <String []> info = this.test.getTimeReport(1);
		boolean isTrue = false;

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
			this.test.stampIn(1);
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
			this.test.stampOut(1);
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
	}

	@FXML
	private void reportASickDay(ActionEvent event){
		reportASickDay.toFront();
		setStatusOfSickDayButton();
	}
	private void setStatusOfSickDayButton(){
		CallInSickButton.setDisable(CallInSickIsTrue());
	}

	private boolean CallInSickIsTrue(){
		ArrayList <String []> info = this.test.getTimeReport(1);
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
		if(!stampOutIsTrue()){
			this.test.stampOut(1);
		}
		//kalla på funktion som lägger in att personen är sjuk

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

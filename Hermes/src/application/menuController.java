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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.Login;
import systemFixPackage.ManageEmployees;
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
	private ManageEmployees manageEmployee;

	//Main AnchorPane everything is painted on
	@FXML
	private AnchorPane parent;
	@FXML
	private HBox top;

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
	@FXML
    private Label nameLabelSalary, adressLabelSalary, socialLabelSalary, periodLabelSalary, employeeIdLabelSalary;
	@FXML
    private Label descriptionSalary, amountSalary, hWageSalary, sumSalary;
	@FXML
	private Label grossEarningsSalary, taxesSalary, netEarningsSalary;

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

	//Items relating to delete user
	@FXML
	private ComboBox<String> cBoxFilterDeleteUser, cBoxOfUsersDelete;

	//Items relating to edit user
	//@FXML
	//private ListView<String> listViewDisplayUsers;

	@FXML
	private ComboBox<String> cBoxFilterEditUser, cBoxOfUsersEdit;
	@FXML
	private TextField idLabelUserEdit, nameLabelUserEdit, adressLabelUserEdit, phoneLabelUserEdit, socialLabelUserEdit,
	shiftLabelUserEdit, roleLabelUserEdit, managerIdLabelUserEdit, managerNameLabelUserEdit, hWageLabelUserEdit, authorityLabelUserEdit;
	@FXML
	private Button saveUserEditsButton;


	//FXML items relating to manage accounts
	@FXML
    private Button newUserButton,editUserButton,deleteUserButton,changePasswordButton;
	@FXML
    private Pane newUserPane,editUserPane,deleteUserPane,changePasswordPane, changePasswordManagerPane;

	//FXML items relating to change pw
	@FXML
    private TextField newPassword, oldPassword1, oldPassword2;

	//FXML items relating to change pw manager

	//Set up at launch
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hideMainScreen.toFront();
		mainMenuPain.toFront();
		
		this.timePane.setVisible(false);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(true);

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
		this.manageEmployee = new ManageEmployees(user);
		//

		nameLabel.setText(user.getName());

	}

	@FXML
	private void minimize_stage(MouseEvent event){



        if(stage.isMaximized()){
        	stage.setMaximized(false);
        }
        else{
        	stage.setMaximized(true);
        }

        //stage.setIconified(true);

	}

	@FXML
	private void close_app(MouseEvent event){
		System.exit(0);

	}

	@FXML
	private void time(ActionEvent event){
		timePane.toFront();
		this.timePane.setVisible(true);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(false);
		
		
		
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
		timeReportTableView.setItems(displayTimeReport(-1));

	}

	private ObservableList<timeToObList> displayTimeReport(int limit){
		ObservableList<timeToObList> timeReport = FXCollections.observableArrayList();
		ArrayList <String []> info = this.timeReporter.getTimeReport(user.getUserId());


		if(limit > 0 && info.size() > limit)
		{
			for(int i = (info.size()-limit); i<info.size(); i++){
				//Replace k with hours worked

				String absent = "no";
				if(info.get(i)[4] != null){
					absent = info.get(i)[4];
				}

				timeReport.add(new timeToObList(info.get(i)[2], info.get(i)[3], info.get(i)[5], info.get(i)[7], absent, Integer.parseInt(info.get(i)[0])));
			}
		}
		else
		{
			for(int i = 0; i<info.size(); i++){
				//Replace k with hours worked

				String absent = "no";
				if(info.get(i)[4] != null){
					absent = info.get(i)[4];
				}

				timeReport.add(new timeToObList(info.get(i)[2], info.get(i)[3], info.get(i)[5], info.get(i)[7], absent, Integer.parseInt(info.get(i)[0])));
			}
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
			timeReportTableView.setItems(displayTimeReport(-1));

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
			timeReportTableView.setItems(displayTimeReport(-1));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void editWorkingHours(ActionEvent event){
		editWorkingHours.toFront();
		timeReportTableView1.setItems(displayTimeReport(7));
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

		//fixa så vi inte kan spara om input är fel
		System.out.println("test");
		ArrayList <String []> info = new ArrayList<String[]>();
		for(int i = 0; i < timeReportTableView1.getItems().size(); i++){
			timeToObList row = timeReportTableView1.getItems().get(i);
			this.timeReporter.editTimeReport(row.getIn(), row.getOut(), row.getDate(), row.getTimeRowId(), "");
		}
		timeReportTableView1.setItems(displayTimeReport(7));

    }



	@FXML
	private void reportASickDay(ActionEvent event){
		reportASickDay.toFront();
		commentOnWhySick.clear();
		setStatusOfSickDayButton();
	}
	private void setStatusOfSickDayButton(){
		CallInSickButton.setDisable((CallInSickIsTrue()));
	}

	private boolean CallInSickIsTrue(){

		ArrayList <String []> info = this.timeReporter.getTimeReport(user.getUserId());
		boolean isTrue = false;

		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

		for(int i = 0; i<info.size(); i++){
			if(info.get(i)[5].equals(ft.format(dNow)) && info.get(i)[4] != null){
				isTrue = true;
			}
			if(!stampOutIsTrue()){
				isTrue = false;
			}
			/*
			else if(info.get(i)[4] == null){
				isTrue = false;
			}
			else{
				isTrue = true;
			}
			*/
		}

		return isTrue;
	}

	@FXML
	private void callInSick(ActionEvent event){
		System.out.println(commentOnWhySick.getText());
		this.timeReporter.recordAbsence(user.getUserId(), "Sick", commentOnWhySick.getText());
		commentOnWhySick.clear();
		setStatusOfSickDayButton();
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
		
		this.timePane.setVisible(false);
		this.schedulePane.setVisible(true);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(false);
	}

	@FXML
	private void salarySlip(ActionEvent event){
		salarySlipPane.toFront();
		
		this.timePane.setVisible(false);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(true);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(false);
	}

	@FXML
	private void generateSalarySlip(ActionEvent event){
		generateSalarySlip.toFront();
		String[] salarySlip = this.timeReporter.generateSalarySlip(user.getUserId());
		//System.out.println(salarySlip[0] + " " + salarySlip[1] + " " + salarySlip[2] + " " + salarySlip[3] + " " + salarySlip[4] + " " + salarySlip[5] + " " + salarySlip[6]);

		this.nameLabelSalary.setText(user.getName());
		this.adressLabelSalary.setText(user.getAdress());
		this.socialLabelSalary.setText(user.getSocials());
		this.periodLabelSalary.setText(salarySlip[2]);
		this.employeeIdLabelSalary.setText(Integer.toString(user.getUserId()));
		//Bör lägga in if statement för att avgöra vad som ska visas med ob och så
		this.descriptionSalary.setText("Basic Pay");
		this.amountSalary.setText(salarySlip[0]);
		this.hWageSalary.setText(salarySlip[1]);
		this.sumSalary.setText(salarySlip[3]);

		this.grossEarningsSalary.setText(salarySlip[3]);
		this.taxesSalary.setText(salarySlip[5]);
		double netEarnings = Double.parseDouble(salarySlip[3])-Double.parseDouble(salarySlip[5]);
		this.netEarningsSalary.setText(Double.toString(netEarnings));
	}

	@FXML
	private void changePassword(ActionEvent event){
		if(Integer.parseInt(this.user.getClassificationID()) == 3){
			//this.changePasswordManagerPane.toFront();
			this.changePasswordPane.toFront();
		}
		else
			this.changePasswordPane.toFront();
	}

	@FXML
	private void saveNewPassword(ActionEvent event){
		Login validate = new Login(this.user.getUserName(), this.oldPassword1.getText());
		System.out.println(this.user.getUserName()+ "---" + this.user.getUserId() + "---" + this.newPassword.getText());
		if(this.oldPassword1.getText().equals(this.oldPassword2.getText()) && validate.authorizePassword() && this.newPassword.getText().length() > 7){
			this.manageEmployee.changeYourOwnPassword(this.user.getUserId(), this.newPassword.getText());
			this.newPassword.clear();
			this.oldPassword1.clear();
			this.oldPassword2.clear();

			Alert enterAlert = new Alert(AlertType.INFORMATION);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("Password is changed");
			enterAlert.showAndWait();
		}
		else if(!validate.authorizePassword()){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("error");
			enterAlert.showAndWait();
		}
		else if(this.oldPassword1.getText().equals(this.oldPassword2.getText())){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("error");
			enterAlert.showAndWait();
		}
		else if(this.newPassword.getText().length() < 7){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("error");
			enterAlert.showAndWait();
		}

	}

	@FXML
	private void deleteUser(ActionEvent event){
		this.deleteUserPane.toFront();
		setDeleteUserComboBoxFilter();
		updateComboBoxOfUsersDelete();
	}

	private void setDeleteUserComboBoxFilter(){
		this.cBoxFilterDeleteUser.getItems().addAll("No filter", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
	}

	@FXML
	private void filterComboBoxOfUsersDelete(ActionEvent event){
		updateComboBoxOfUsersDelete();
	}

	private void updateComboBoxOfUsersDelete(){
		ArrayList<String[]> employeeList = this.manageEmployee.getAllUsers();
		cBoxOfUsersDelete.getItems().clear();

		if(cBoxFilterDeleteUser.getSelectionModel().getSelectedIndex() != -1)
		{
			for(int i = 0; i<employeeList.size(); i++)
			{
				if(cBoxFilterDeleteUser.getSelectionModel().getSelectedItem().equalsIgnoreCase("No filter") || employeeList.get(i)[1].trim().substring(0, 1).equalsIgnoreCase(cBoxFilterDeleteUser.getSelectionModel().getSelectedItem()))
				{
					cBoxOfUsersDelete.getItems().add("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]);
				}
			}
		}
		//Shows all users in DropDown box when no filter is selected
		else{
			for(int i = 0; i<employeeList.size(); i++)
			{
				cBoxOfUsersDelete.getItems().add("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]);
			}
		}

	}


	@FXML
	private void deleteSelectedUser(ActionEvent event){
		ArrayList<String[]> employeeList = this.manageEmployee.getAllUsers();
		int idRemove = -1;
		for(int i = 0; i<employeeList.size(); i++){
			if(("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]).equals(cBoxOfUsersDelete.getSelectionModel().getSelectedItem()))
				idRemove = i;
		}


		if(idRemove != -1 && Integer.parseInt(employeeList.get(idRemove)[0]) != this.user.getUserId()) {

			System.out.println("Remove");
			this.manageEmployee.deleteUser(idRemove);
			//updateListViewForDeleteUser();
		}
		else if(idRemove == -1){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("You need to select a user before deleting");
			enterAlert.showAndWait();
		}
		else if(Integer.parseInt(employeeList.get(idRemove)[0]) == this.user.getUserId()){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("You can't delete your own account");
			enterAlert.showAndWait();
		}

	}

	@FXML
	private void editUser(ActionEvent event){
		this.editUserPane.toFront();

		setEditUserComboBoxFilter();
		updateComboBoxOfUsersEdit();
	}

	private void setEditUserComboBoxFilter(){
	this.cBoxFilterEditUser.getItems().addAll("No filter", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
	}

	@FXML
	private void filterComboBoxOfUsersEdit(ActionEvent event){
		updateComboBoxOfUsersEdit();
	}

	private void updateComboBoxOfUsersEdit(){
		ArrayList<String[]> employeeList = this.manageEmployee.getAllUsers();
		cBoxOfUsersEdit.getItems().clear();

		this.idLabelUserEdit.clear();
    	this.nameLabelUserEdit.clear();
    	this.adressLabelUserEdit.clear();
    	this.phoneLabelUserEdit.clear();
    	this.socialLabelUserEdit.clear();
    	this.shiftLabelUserEdit.clear();
    	this.roleLabelUserEdit.clear();
    	this.managerIdLabelUserEdit.clear();
    	this.managerNameLabelUserEdit.clear();
    	this.hWageLabelUserEdit.clear();
    	this.authorityLabelUserEdit.clear();

		if(cBoxFilterEditUser.getSelectionModel().getSelectedIndex() != -1)
		{
			for(int i = 0; i<employeeList.size(); i++)
			{
				if(cBoxFilterEditUser.getSelectionModel().getSelectedItem().equalsIgnoreCase("No filter") || employeeList.get(i)[1].trim().substring(0, 1).equalsIgnoreCase(cBoxFilterEditUser.getSelectionModel().getSelectedItem()))
				{
					cBoxOfUsersEdit.getItems().add("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]);
				}
			}
		}
		//Shows all users in DropDown box when no filter is selected
		else{
			for(int i = 0; i<employeeList.size(); i++)
			{
				cBoxOfUsersEdit.getItems().add("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]);
			}
		}

	}

	@FXML
	private void saveUserEdits(ActionEvent event){
		ArrayList<String[]> employeeList = this.manageEmployee.getAllUsers();
		int selected = -1;
		for(int i = 0; i<employeeList.size(); i++){
			if(("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]).equals(cBoxOfUsersEdit.getSelectionModel().getSelectedItem()))
				selected = i;
		}

		if(selected != -1){
			//Byt metod sen
			this.manageEmployee.changeUserInformationProvisorisk(Integer.parseInt(this.idLabelUserEdit.getText()), this.nameLabelUserEdit.getText(), this.adressLabelUserEdit.getText(),
					this.phoneLabelUserEdit.getText(), this.socialLabelUserEdit.getText(), this.shiftLabelUserEdit.getText(), this.roleLabelUserEdit.getText(),
					this.managerNameLabelUserEdit.getText(), this.hWageLabelUserEdit.getText(), this.authorityLabelUserEdit.getText(), this.managerIdLabelUserEdit.getText());

			updateComboBoxOfUsersEdit();
		}
		else{
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("You need to select a user before saving");
			enterAlert.showAndWait();
		}

	}

    @FXML
    void fillInInformation(ActionEvent event) {

    	ArrayList<String[]> employeeList = this.manageEmployee.getAllUsers();
		int selected = -1;
		for(int i = 0; i<employeeList.size(); i++){
			if(("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]).equals(cBoxOfUsersEdit.getSelectionModel().getSelectedItem()))
				selected = i;
		}
		if(selected != -1){
			this.idLabelUserEdit.setText(employeeList.get(selected)[0]);
	    	this.nameLabelUserEdit.setText(employeeList.get(selected)[1]);
	    	this.adressLabelUserEdit.setText(employeeList.get(selected)[2]);
	    	this.phoneLabelUserEdit.setText(employeeList.get(selected)[3]);
	    	this.socialLabelUserEdit.setText(employeeList.get(selected)[4]);
	    	this.shiftLabelUserEdit.setText(employeeList.get(selected)[5]);
	    	this.roleLabelUserEdit.setText(employeeList.get(selected)[6]);
	    	this.managerIdLabelUserEdit.setText(employeeList.get(selected)[7]);
	    	this.managerNameLabelUserEdit.setText(employeeList.get(selected)[8]);
	    	this.hWageLabelUserEdit.setText(employeeList.get(selected)[9]);
	    	this.authorityLabelUserEdit.setText(employeeList.get(selected)[10]);
		}

    }

	@FXML
	private void newUser(ActionEvent event){
		this.newUserPane.toFront();
	}


    //private Button newUserButton,editUserButton,deleteUserButton,changePasswordButton;

	@FXML
	private void manageAccounts(ActionEvent event){
		ManageAccountsPane.toFront();
		
		this.timePane.setVisible(false);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(true);
		this.mainMenuPain.setVisible(false);
		
		this.newUserButton.setDisable(!this.manageEmployee.checkClassificationID());
		this.deleteUserButton.setDisable(!this.manageEmployee.checkClassificationID());
		this.editUserButton.setDisable(!this.manageEmployee.checkClassificationID());
	}

	@FXML
	private void project(ActionEvent event){
		projectPane.toFront();
		this.timePane.setVisible(false);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(true);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(false);
	}

	@FXML
	private void back(ActionEvent event){
		mainMenuPain.toFront();
		hideMainScreen.toFront();
		this.timePane.setVisible(false);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(true);
	}


}

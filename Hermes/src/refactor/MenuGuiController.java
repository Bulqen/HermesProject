package refactor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import systemFixPackage.Login;
import systemFixPackage.ManageEmployees;
import systemFixPackage.User;
import systemFixPackage.UserFactory;
import systemFixPackage.timeReport;

//public class menuControllerR implements Initializable {
public class MenuGuiController {

	// Nested controllers
	@FXML
	private EditUserController editUserController;
	@FXML
	private DeleteUserController deleteUserController;
	@FXML
	private ChangePasswordController changePasswordController;
	@FXML
	private ReportSickController reportSickController;
	@FXML
	private InOutController inOutController;
	@FXML
	private ApplyVacationController applyVacationController;
	@FXML
	private NewUserController newUserController;
	@FXML
	private NewProjectController newProjectController;
	@FXML
	private EditProjectController editProjectController;
	@FXML
	private AddOrEditActivityToProjectController addOrEditActivityToProjectController;
	@FXML
	private ShowActivitiesController showActivitiesController;
	@FXML
	private ShowScheduleController showScheduleController;

	// Node used to get the current scene
	private Node node;
	private Stage stage;
	// Used to make stage dragable
	private double xOffSet = 0;
	private double yOffSet = 0;

	// User factory to create different users
	private UserFactory userFac;
	// The user that is currently logged in
	private User user;
	// TimeReporter is used for sending and getting data from the database
	// relating to the time use cases
	private timeReport timeReporter = new timeReport();
	// ManageEmployess is used for sending and getting data from the database
	// relating to the manage user usecases
	private ManageEmployees manageEmployee;

	// Main AnchorPane everything is painted on
	@FXML
	private AnchorPane parent;
	// Hbox that contains exit and minimize options for the window
	@FXML
	private HBox top;

	// Label to display username and date + time
	@FXML
	private Label nameLabel, dateAndTimeLabel;

	@FXML
	private ImageView profilePic;

	// Pane to display main menu
	@FXML
	private Pane mainMenuPain;

	// Panes to display buttons for choices done in the main menu
	@FXML
	private Pane timePane, schedulePane, salarySlipPane, ManageAccountsPane, projectPane;

	// Pane to hide content
	@FXML
	private Pane hideMainScreen;

	// Panes to display content to the right when sub menu options is chosen
	@FXML
	private Pane reportASickDay, applyForVaccation, editWorkingHours, inOutPane, generateSalarySlip;

	// FXML items relating to generateSalarySlipPane
	@FXML
	private Button generateSalarySlipButton;
	@FXML
	private Label nameLabelSalary, adressLabelSalary, socialLabelSalary, periodLabelSalary, employeeIdLabelSalary;
	@FXML
	private Label descriptionSalary, amountSalary, hWageSalary, sumSalary;
	@FXML
	private Label grossEarningsSalary, taxesSalary, netEarningsSalary;

	// FXML items relating to editWorkingHours
	@FXML
	private Button SaveEditWorkingHours;
	@FXML
	private TableView<timeToObList> timeReportTableView1;
	@FXML
	private TableColumn<timeToObList, String> dateColumn1, inColumn1, outColumn1, hoursColumn1, absentColumn1;

	// FXML items relating to manage accounts
	@FXML
	private Button newUserButton, editUserButton, deleteUserButton, changePasswordButton;
	@FXML
	private Pane newUserPane, editUserPane, deleteUserPane, changePasswordPane, changePasswordManagerPane;

	// FXML items relating to project
	@FXML
	private Button newProjectButton, editProjectButton, addOrEditActivityToProjectButton, showActivitiesButton;

	@FXML
	private Pane newProjectPane, editProjectPane, addOrEditActivityToProjectPane, showActivitiesPane;

	//Pane that shows schedule
	@FXML
	private Pane showSchedulePane;

	// Set up at launch

	@FXML
	private void initialize() {
		// Injects MenuGuiController to nested controllers
		injectMenuGuiController();

		// Makes sure that the correct view is displayed when program starts
		hideMainScreen.toFront();
		mainMenuPain.toFront();
		this.timePane.setVisible(false);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(true);

		makeStageDragable();

		// Ska refactoras bort
		setUpTableViewEditable();
		displayTime();
	}

	// Sets the user and initiates it as a worker
	// Initiates the manageEmployee
	public void setUser(String userName) {

		userFac = UserFactory.initiateUserFactory(userName);
		user = userFac.getUser("WORKER");
		this.manageEmployee = new ManageEmployees(user);
		//

		nameLabel.setText(user.getName());
	}

	// Method to create and display the date and time
	private void displayTime() {
		Clock time = new Clock();
		dateAndTimeLabel.textProperty().bind(time.messageProperty());
		new Thread(time).start();

	}

	// Injects MenuGuiController to nested controllers
	private void injectMenuGuiController(){
		editUserController.injectMainController(this);
		deleteUserController.injectMainController(this);
		changePasswordController.injectMainController(this);
		reportSickController.injectMainController(this);
		inOutController.injectMainController(this);
		applyVacationController.injectMainController(this);
		newUserController.injectMainController(this);
		newProjectController.injectMainController(this);
		editProjectController.injectMainController(this);
		addOrEditActivityToProjectController.injectMainController(this);
		showActivitiesController.injectMainController(this);
		showScheduleController.injectMainController(this);
	}

	// Makes the stage dragable when you klick and hold on the stage
	private void makeStageDragable() {
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

	// Method to minimize the stage
	@FXML
	private void minimize_stage(MouseEvent event) {

		System.out.println("lllll");
		stage.setIconified(true);
	}

	// Method to maximize the stage
	@FXML
	private void maximize_stage(MouseEvent event) {

			 if(stage.isMaximized())
			 	stage.setMaximized(false);
			 else
			 	stage.setMaximized(true);

		}

	// Exit
	@FXML
	private void close_app(MouseEvent event) {
		System.exit(0);
	}

	// Method that is called by other subControllers to add the alphabet to a
	// cBox
	public ComboBox<String> setComboBoxFilter(ComboBox<String> cBox) {
		cBox.getItems().addAll("No filter", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
				"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
		return cBox;
	}

	// Method that is called by subControllers to get timereport
	public ObservableList<timeToObList> displayTimeReport(int limit) {
		ObservableList<timeToObList> timeReport = FXCollections.observableArrayList();
		ArrayList<String[]> info = this.timeReporter.getTimeReport(user.getUserId());
		DecimalFormat format = new DecimalFormat("###.00");

		if (limit > 0 && info.size() > limit) {}
		else{limit = info.size();}

		for (int i = (info.size() - limit); i < info.size(); i++) {
			String absent = "no";
			String workedH = ".00";
			if (info.get(i)[4] != null) {
				absent = info.get(i)[4];
			}
			if(info.get(i)[7] != null){
				workedH = format.format(Double.parseDouble(info.get(i)[7]));
				//System.out.println(info.get(i)[7] + " : " + format.format(Double.parseDouble(info.get(i)[7])));
			}
			timeReport.add(new timeToObList(info.get(i)[2], info.get(i)[3], info.get(i)[5], workedH, absent,
					Integer.parseInt(info.get(i)[0])));
		}
		return timeReport;
	}

	// Getters
	public ManageEmployees GetManageEmployee() {
		return this.manageEmployee;
	}

	public User getUser() {
		return this.user;
	}

	public boolean getStatusOfStampOutButton() {
		return inOutController.statusOfStampOutButton();
	}

	@FXML
	private void logOut(MouseEvent event){
		Alert enterAlert = new Alert(AlertType.INFORMATION);
		enterAlert.setHeaderText(null);
		enterAlert.setContentText("Do you want to Log Out");
		enterAlert.showAndWait();

		//add you loading or delays - ;-
        stage.close();

        try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
			Parent root = (Parent) loader.load();

			Stage stage1 = new Stage();
			stage1.initStyle(StageStyle.UNDECORATED);
			stage1.setScene(new Scene(root));
			stage1.show();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@FXML
	private void setProfilePic(MouseEvent event){
		/*
		String path = null;

		FileChooser fileChooser = new FileChooser();
		//Stage stageFile = (Stage) parent.getScene().getWindow();
		File newFile = fileChooser.showOpenDialog(stage);
		//path = newFile.getAbsolutePath();

		Image newPic = new Image(javax.imageio.ImageIO.read(newFile));
		System.out.println(path);

		if(path != null)
			profilePic.setImage(newPic);
		*/
	}

	//*************************************************Methods to show different panes when buttons are clicked********************************************************//

	@FXML
	private void time(ActionEvent event) {
		timePane.toFront();
		this.timePane.setVisible(true);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(false);
	}

	@FXML
	private void applyForVaccation(ActionEvent event) {
		applyForVaccation.toFront();
	}

	@FXML
	private void newProject(ActionEvent event) {
		newProjectPane.toFront();;
	}

	@FXML
	private void editProject(ActionEvent event) {
		editProjectPane.toFront();
		editProjectController.setup();
	}

	@FXML
	private void addOrEditActivityToProject(ActionEvent event) {
		addOrEditActivityToProjectPane.toFront();
		this.addOrEditActivityToProjectController.setUp();
	}

	@FXML
	private void showActivities(ActionEvent event) {
		showActivitiesPane.toFront();
		this.showActivitiesController.setup();
	}

	@FXML
	private void inOut(ActionEvent event) {
		// Code here to stamp in or out
		inOutPane.toFront();
		inOutController.initializeInOutWindow();
	}

	// 7an borde vara en variabel som initieras i b�rjan
	@FXML
	private void editWorkingHours(ActionEvent event) {
		editWorkingHours.toFront();
		timeReportTableView1.setItems(displayTimeReport(7));
	}

	@FXML
	private void reportASickDay(ActionEvent event) {
		reportASickDay.toFront();
		reportSickController.setStatusOfSickDayButton();
	}

	@FXML
	private void schedule(ActionEvent event) {
		schedulePane.toFront();

		this.timePane.setVisible(false);
		this.schedulePane.setVisible(true);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(false);
	}

	@FXML
	private void showSchedule(ActionEvent event){
		showSchedulePane.toFront();
		this.showScheduleController.test();
	}

	@FXML
	private void salarySlip(ActionEvent event) {
		salarySlipPane.toFront();

		this.timePane.setVisible(false);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(true);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(false);
	}

	@FXML
	private void changePassword(ActionEvent event) {
		if (Integer.parseInt(this.user.getClassificationID()) == 3) {
			// this.changePasswordManagerPane.toFront();
			this.changePasswordPane.toFront();
		} else
			this.changePasswordPane.toFront();
	}

	@FXML
	private void deleteUser(ActionEvent event) {
		this.deleteUserPane.toFront();
		deleteUserController.updateComboBoxOfUsersDelete();
	}

	@FXML
	private void editUser(ActionEvent event) {
		this.editUserPane.toFront();
		editUserController.updateComboBoxOfUsersEdit();
	}

	@FXML
	private void newUser(ActionEvent event) {
		this.newUserPane.toFront();
		this.newUserController.setup();
	}

	@FXML
	private void manageAccounts(ActionEvent event) {
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
	private void project(ActionEvent event) {
		projectPane.toFront();
		this.timePane.setVisible(false);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(true);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(false);

		this.newProjectButton.setDisable(!this.manageEmployee.checkClassificationID());
		this.editProjectButton.setDisable(!this.manageEmployee.checkClassificationID());
		this.addOrEditActivityToProjectButton.setDisable(!this.manageEmployee.checkClassificationID());
	}

	@FXML
	private void back(ActionEvent event) {
		mainMenuPain.toFront();
		hideMainScreen.toFront();
		this.timePane.setVisible(false);
		this.schedulePane.setVisible(false);
		this.projectPane.setVisible(false);
		this.salarySlipPane.setVisible(false);
		this.ManageAccountsPane.setVisible(false);
		this.mainMenuPain.setVisible(true);
	}

//*************************************************Kommer flyttas till andra klasser********************************************************//
	// Method to set up the columns for the table view that displays shifts in
	// the edit working hours
	// Makes in and out time editable
	private void setUpTableViewEditable() {
		dateColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("date"));
		inColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("in"));
		outColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("out"));
		hoursColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("hours"));
		absentColumn1.setCellValueFactory(new PropertyValueFactory<timeToObList, String>("absent"));

		timeReportTableView1.setEditable(true);
		inColumn1.setCellFactory(TextFieldTableCell.forTableColumn());
		outColumn1.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	private boolean checkFormatForTime(String input) {
		if (input.matches("\\d{2}:\\d{2}:\\d{2}")) {
			return true;
		} else {
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText("Wrong format");
			enterAlert.setContentText("Please use to following format 00:00:00");
			enterAlert.showAndWait();
			return false;
		}
	}

	@FXML
	public void changeInCellEvent(CellEditEvent edittedCell) {
		timeToObList row = timeReportTableView1.getSelectionModel().getSelectedItem();
		// if(edittedCell.getNewValue().toString().matches("\\d{2}:\\d{2}:\\d{2}"))
		if (checkFormatForTime(edittedCell.getNewValue().toString()))
			row.setIn((edittedCell.getNewValue().toString()));
	}

	@FXML
	public void changeOutCellEvent(CellEditEvent edittedCell) {
		timeToObList row = timeReportTableView1.getSelectionModel().getSelectedItem();
		// if(edittedCell.getNewValue().toString().matches("\\d{2}:\\d{2}:\\d{2}"))
		if (checkFormatForTime(edittedCell.getNewValue().toString()))
			row.setOut((edittedCell.getNewValue().toString()));
	}

	@FXML
	private void SaveEditWorkingHoursAction(ActionEvent event) {

		// fixa s� vi inte kan spara om input �r fel
		//Man m�ste �ven trycka enter f�r att �ndra fixa detta
		System.out.println("test");
		ArrayList <String []> info = new ArrayList<String[]>();

		for (int i = 0; i < timeReportTableView1.getItems().size(); i++) {
			timeToObList row = timeReportTableView1.getItems().get(i);
			this.timeReporter.editTimeReport(row.getIn(), row.getOut(), row.getDate(), row.getTimeRowId(), "");
			System.out.println(row.getOut());
		}
		timeReportTableView1.setItems(displayTimeReport(7));
	}

	@FXML
	private void generateSalarySlip(ActionEvent event) {
		generateSalarySlip.toFront();
		String[] salarySlip = this.timeReporter.generateSalarySlip(user.getUserId());
		// System.out.println(salarySlip[0] + " " + salarySlip[1] + " " +
		// salarySlip[2] + " " + salarySlip[3] + " " + salarySlip[4] + " " +
		// salarySlip[5] + " " + salarySlip[6]);

		this.nameLabelSalary.setText(user.getName());
		this.adressLabelSalary.setText(user.getAdress());
		this.socialLabelSalary.setText(user.getSocials());
		this.periodLabelSalary.setText(salarySlip[2]);
		this.employeeIdLabelSalary.setText(Integer.toString(user.getUserId()));
		// B�r l�gga in if statement f�r att avg�ra vad som sa visas med ob och
		// s�
		this.descriptionSalary.setText("Basic Pay");
		this.amountSalary.setText(salarySlip[0]);
		this.hWageSalary.setText(salarySlip[1]);
		this.sumSalary.setText(salarySlip[3]);

		this.grossEarningsSalary.setText(salarySlip[3]);
		this.taxesSalary.setText(salarySlip[5]);
		double netEarnings = Double.parseDouble(salarySlip[3]) - Double.parseDouble(salarySlip[5]);
		this.netEarningsSalary.setText(Double.toString(netEarnings));
	}
}

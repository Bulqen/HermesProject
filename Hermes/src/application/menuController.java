package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
	private Node node;
	private Stage stage;

	//Fixa så rätt user används och att jag kan använda user factory
	private DBConnection test = new DBConnection();

	@FXML
	private AnchorPane parent;

	@FXML
	private Label nameLabel, dateAndTimeLabel;

	@FXML
	private Pane timePane, mainMenuPain, schedulePane, salarySlipPane, changePasswordPane, projectPane, reportASickDay, hideMainScreen,
	applyForVaccation, editWorkingHours, inOutPane;

	@FXML
	private HBox top;

	private double xOffSet = 0;
	private double yOffSet = 0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		hideMainScreen.toFront();
		displayTime();
		makeStageDragable();

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
		//if(InThisDayIsTrue)
		//inButton.setDisable(true)
		//outButton.setDisable(false)
		//else
		//inButton.setDisable(false)
		//outButton.setDisable(true)
		System.out.println("In/Out");
	}

	@FXML
	private void in(ActionEvent event){
		//Code to stamp in
		//if(inIsTrue)
		try {

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
		changePasswordPane.toFront();
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

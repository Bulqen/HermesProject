package refactor;

import systemFixPackage.timeReport;
import systemFixPackage.DBConnection;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;


public class EditProjectController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();
	private DBConnection db = new DBConnection();
	private int pr;



	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (EditProject)");
	}



	@FXML
	private Pane editProject;

	@FXML
	private TextField projectIdLabel, nameLabel, startDateLabel, endDateLabel, budgetLabel, statusLabel;

	@FXML
	private Button editProjectButton, AddUserButton, removeUserButton, finishProjectButton;

	@FXML
	private TextArea goalText;

	@FXML
	private ComboBox<String> cBoxAddUsers, cBoxremoveUsers;


	public void setup () {

		pr = db.getProjectByManager(mainC.getUser().getUserId());
		if(pr !=-2) {
			String [] project = db.getProjectInfo(pr);
			projectIdLabel.setText(project[0]);
			startDateLabel.setText(project[1]);
			endDateLabel.setText(project[2]);
			goalText.setText(project[3]);
			budgetLabel.setText(project[4]);
			statusLabel.setText(project[5]);
			nameLabel.setText(project[6]);

			projectIdLabel.setEditable(false);

			updateCBoxes();



		}
		else {
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("Create a project first");
			enterAlert.showAndWait();
		}

	}

	public void updateCBoxes() {
		cBoxremoveUsers.getItems().clear();
		cBoxAddUsers.getItems().clear();




		ArrayList<String[]> remUsers = db.getUsersByProject(pr);
		for(int i = 0; i < remUsers.size(); i++) {
			cBoxremoveUsers.getItems().add(("Name: " + remUsers.get(i)[1] + ", Employee ID: " + remUsers.get(i)[0]));


		}


		ArrayList<String[]> addUsers = db.getUsersNotInProject(pr);




		for(int i = 0; i < addUsers.size() ; i++) {
			cBoxAddUsers.getItems().add(("Name: " + addUsers.get(i)[1] + ", Employee ID: " + addUsers.get(i)[0]));


		}





	}

	@FXML
	void addUser(ActionEvent event) {

		ArrayList<String[]> addUsers = db.getUsersNotInProject(pr);
		for(int i = 0; i < addUsers.size(); i++) {
			if((("Name: " + addUsers.get(i)[1] + ", Employee ID: " + addUsers.get(i)[0]).equals(cBoxAddUsers.getSelectionModel().getSelectedItem()))) {
				db.addUserToProject(pr, Integer.parseInt(addUsers.get(i)[0]));
			}
		}
		updateCBoxes();

	}
	
	@FXML
	void finishProject(ActionEvent event) {
		Alert enterAlert = new Alert(AlertType.CONFIRMATION);
		enterAlert.setHeaderText(null);
		enterAlert.setContentText("Are you sure you want to finish your project?");
		enterAlert.showAndWait();
		
		if(enterAlert.getResult() != ButtonType.CANCEL) {
			db.finishProject(pr);
			pr = -2;
			
			projectIdLabel.clear();
			nameLabel.clear();
			startDateLabel.clear();
			endDateLabel.clear();
			budgetLabel.clear();
			statusLabel.clear();
			goalText.clear();
			
			
			updateCBoxes();
		}
		
		
		
		
	}

    @FXML
    void cBoxAdd(ActionEvent event) {

    }

    @FXML
    void cBoxRemove(ActionEvent event) {

    }





	@FXML
	void editProject(ActionEvent event) {
		
		if(pr != -2) {
		
		int man = mainC.getUser().getUserId();
		db.editProject(pr, startDateLabel.getText(), endDateLabel.getText(), goalText.getText(), Integer.parseInt(budgetLabel.getText()), statusLabel.getText(),
					nameLabel.getText(), man);
		

    	Alert enterAlert = new Alert(AlertType.INFORMATION);
		enterAlert.setHeaderText(null);
		enterAlert.setContentText("Successfully edited!");
		enterAlert.showAndWait();
		}
		else {
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("you don´t manage any projects, please create one");
			enterAlert.showAndWait();
		}

	}

	@FXML
	void removeUser(ActionEvent event) {
		
		ArrayList<String[]> remUsers = db.getUsersByProject(pr);
		for(int i = 0; i < remUsers.size(); i++) {
			if((("Name: " + remUsers.get(i)[1] + ", Employee ID: " + remUsers.get(i)[0]).equals(cBoxremoveUsers.getSelectionModel().getSelectedItem()))) {
				db.removeUserToProject(pr, Integer.parseInt(remUsers.get(i)[0]));
			}
		}
		updateCBoxes();

	}
}

package refactor;




import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import systemFixPackage.DBConnection;
import systemFixPackage.timeReport;
import javafx.scene.control.ComboBox;
public class AddOrEditActivityToProjectController {
	private MenuGuiController mainC;
	private DBConnection c = new DBConnection();
	private timeReport timeReporter = new timeReport();
	//asdx
	@FXML
	private Button addActivity;
	@FXML
	private Button editActivity;

	@FXML 
	private Button finalizeEditActivity;

	@FXML
	private Button finalizeAddActivity;

	@FXML
	private Label LblProject;
	
	@FXML 
	private TextField startTimeAnswer,endTimeAnswer,dateAnswer,
						activityDescriptionAnswer;
	@FXML
	private Pane hiddenPane;

	@FXML
	private Pane hiddenPane1;

	@FXML
	private ComboBox <String> cBoxOfActivities;


	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (AddOrEditActivityToProject)");
		hiddenPane.setVisible(false);
	}

	@FXML
	private void addActivity(ActionEvent event) {

		int p = c.getProjectByManager(mainC.getUser().getUserId());
		if(p >= 0){
			hiddenPane.setVisible(true);
			hiddenPane.toFront();
			LblProject.setText(String.valueOf(p));
		}
		else{
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("No projects found");
			enterAlert.showAndWait();
		}
	}

	private void editActivity(ActionEvent event) {
		ArrayList <String []> info = new ArrayList<String []>();
		int k = c.getProjectByManager(mainC.getUser().getUserId());

		info = c.getProcjectActivities(k);

		if(!info.isEmpty()) {
			for(int i = 0; i<info.size(); i++)
				cBoxOfActivities.getItems().add(info.get(i)[0]);		
		}
	}

	private void finalizeAddActivity(ActionEvent event) {

		// int projectId, String starts, String stops, String currDate
		
		ArrayList <String []> info = new ArrayList<String []>();
		int k = c.getProjectByManager(mainC.getUser().getUserId());
		
		c.addScheduledActivities(k, startTimeAnswer.getText(), endTimeAnswer.getText(),
					dateAnswer.getText(),activityDescriptionAnswer.getText());
	}

	private void finalizeEditActivity(ActionEvent event) {

		ArrayList <String []> info = new ArrayList<String []>();
		int k = c.getProjectByManager(mainC.getUser().getUserId());
		
		


	}
	/*
	 * Your code should be below this
	 */


}

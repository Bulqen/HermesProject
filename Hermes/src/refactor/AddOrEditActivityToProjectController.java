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
	private Label LblProject,LblProject2;

	@FXML
	private TextField startTimeAnswer,endTimeAnswer,dateAnswer, activityDescriptionAnswer;

	@FXML
	private TextField startTimeAnswer1,endTimeAnswer1,dateAnswer1, activityDescriptionAnswer1;

	@FXML
	private Pane hiddenPane;

	@FXML
	private Pane hiddenPane1;

	@FXML
	private ComboBox <String> cBoxOfActivities;


	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (AddOrEditActivityToProject)");
	}

	public void setUp(){
		hiddenPane.setVisible(false);
		hiddenPane1.setVisible(false);
	}

	@FXML
	private void addActivity(ActionEvent event) {

		int p = c.getProjectByManager(mainC.getUser().getUserId());
		if(p >= 0){
			hiddenPane1.setVisible(false);
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


	@FXML
	private void editActivity(ActionEvent event) {

		hiddenPane1.setVisible(true);
		hiddenPane1.toFront();
		hiddenPane.setVisible(false);


		ArrayList <String []> info = new ArrayList<String []>();
		int k = c.getProjectByManager(mainC.getUser().getUserId());

		info = c.getProjectActivities(k);

		this.cBoxOfActivities.getItems().clear();

		if(!info.isEmpty()) {
			for(int i = 0; i<info.size(); i++)
				cBoxOfActivities.getItems().add(info.get(i)[0]);
		}
	}

	@FXML
	private void finalizeAddActivity(ActionEvent event) {

		// int projectId, String starts, String stops, String currDate

		ArrayList <String []> info = new ArrayList<String []>();
		int k = c.getProjectByManager(mainC.getUser().getUserId());

		c.addScheduledActivities(k, startTimeAnswer.getText(), endTimeAnswer.getText(),
					dateAnswer.getText(),activityDescriptionAnswer.getText());

		Alert enterAlert = new Alert(AlertType.CONFIRMATION);
		enterAlert.setHeaderText(null);
		enterAlert.setContentText("Activity Sucsesfully created");
		enterAlert.showAndWait();

		startTimeAnswer.clear();
		endTimeAnswer.clear();
		dateAnswer.clear();
		activityDescriptionAnswer.clear();


	}
	@FXML
	private void finalizeEditActivity(ActionEvent event) {

		ArrayList <String []> info = new ArrayList<String []>();
		int k = c.getProjectByManager(mainC.getUser().getUserId());
			//[0] id, [1] startDate, [2] endDate, [3] currentDate, [4] projectID, [5] description
		info = c.getProjectActivities(k);

		String activityId = cBoxOfActivities.getSelectionModel().getSelectedItem();

		int i = 0;
		boolean found = false;
		while(i<info.size() && found == false) {
			if(info.get(i)[0].equals(activityId))
				found = true;
			else
				i++;
		}


		String activityId2 = info.get(i)[0];
		String startTime = info.get(i)[1];
		String endTime = info.get(i)[2];
		String date = info.get(i)[3];
		String projectId = info.get(i)[4];
		String description = info.get(i)[5];

		//startTimeAnswer,endTimeAnswer,dateAnswer, activityDescriptionAnswer;

		LblProject2.setText(projectId);
		startTimeAnswer1.setText(startTime);
		endTimeAnswer1.setText(endTime);
		dateAnswer1.setText(date);
		activityDescriptionAnswer1.setText(description);

		c.editScheduledActivities(Integer.parseInt(projectId),startTimeAnswer1.getText(),endTimeAnswer1.getText(),
				dateAnswer1.getText(), Integer.parseInt(activityId), activityDescriptionAnswer1.getText());

	//int projectId, String starts, String stops, String currDate, int scheduleId, String description



		Alert enterAlert = new Alert(AlertType.CONFIRMATION);
		enterAlert.setHeaderText(null);
		enterAlert.setContentText("Activity Sucsesfully edited");
		enterAlert.showAndWait();

		startTimeAnswer1.clear();
		endTimeAnswer1.clear();
		dateAnswer1.clear();
		activityDescriptionAnswer1.clear();


	}

	@FXML
	private void setCBox(ActionEvent event){
		ArrayList <String []> info = new ArrayList<String []>();
		int k = c.getProjectByManager(mainC.getUser().getUserId());
			//[0] id-activity, [1] start time, [2] description, [3] end time, [4] date, [5] projectID
		info = c.getProjectActivities(k);

		int index = cBoxOfActivities.getSelectionModel().getSelectedIndex();
		System.out.println(info.get(0)[0] + info.get(0)[1] +info.get(0)[2] +info.get(0)[3]+info.get(0)[4]+info.get(0)[5]);

		System.out.println(index);

		String activityId = info.get(index)[0];
		String startTime = info.get(index)[1];
		String endTime = info.get(index)[3];
		String date = info.get(index)[4];
		String projectId = info.get(index)[5];
		String description = info.get(index)[2];

		//startTimeAnswer,endTimeAnswer,dateAnswer, activityDescriptionAnswerd

		LblProject2.setText(projectId);
		startTimeAnswer1.setText(startTime);
		endTimeAnswer1.setText(endTime);
		dateAnswer1.setText(date);
		activityDescriptionAnswer1.setText(description);
	}
	/*
	 * Your code should be below this
	 */


}

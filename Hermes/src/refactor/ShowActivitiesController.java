package refactor;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import systemFixPackage.DBConnection;
import systemFixPackage.timeReport;

public class ShowActivitiesController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();
	private DBConnection db = new DBConnection();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (ShowActivities)");
	}

	@FXML
	private Pane showActivities;

	@FXML
	private ComboBox<String> cBoxProjects;

	@FXML
	private ComboBox<String> cBoxActivities;

	@FXML
	private TextField ActivityIdLabel;

	@FXML
	private TextField dateLabel;

	@FXML
	private TextField startLabel;

	@FXML
	private TextField endLabel;

	@FXML
	private TextArea descriptionLabel;
	
	
	public void setup() {
		ActivityIdLabel.setEditable(false);
		dateLabel.setEditable(false);
		startLabel.setEditable(false);
		endLabel.setEditable(false);
		descriptionLabel.setEditable(false);
		
		ActivityIdLabel.clear();
		startLabel.clear();
		descriptionLabel.clear();
		endLabel.clear();
		dateLabel.clear();
		
		
		ArrayList<String[]> projects = db.getAllProjects();
		cBoxProjects.getItems().clear();
		cBoxActivities.getItems().clear();


		for(int i = 0; i < projects.size(); i++) {
			String row = projects.get(i)[0]+","+projects.get(i)[6];
			cBoxProjects.getItems().add(row);

		}




	}

	@FXML
	void activitySelected(ActionEvent event) {

		int activity = Integer.parseInt(cBoxActivities.getSelectionModel().getSelectedItem());

		String [] activityInfo = db.getScheduledActiviry(activity);

		ActivityIdLabel.setText(activityInfo[0]);
		startLabel.setText(activityInfo[1]);
		descriptionLabel.setText(activityInfo[2]);
		endLabel.setText(activityInfo[3]);
		dateLabel.setText(activityInfo[4]);


	}
 
	@FXML
	void projectSelected(ActionEvent event) {

		cBoxActivities.getItems().clear();
		String [] words = cBoxProjects.getSelectionModel().getSelectedItem().split(",");
		int pr = Integer.parseInt(words[0]);
		ArrayList<String[]> activities = db.getProjectActivities(pr);
		if(!activities.isEmpty()) {

			for(int i = 0; i < activities.size(); i++) {

				cBoxActivities.getItems().add(activities.get(i)[0]);
			}
		}


	}


}

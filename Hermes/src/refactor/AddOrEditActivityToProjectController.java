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
	private Label LblProject;


	@FXML
	private Pane hiddenPane;


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
	@FXML
	private void editActivity(ActionEvent event) {


	}


	/*
	 * Your code should be below this
	 */


}

package refactor;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import systemFixPackage.timeReport;

public class AddOrEditActivityToProjectController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();
	//asdx
	@FXML
	private Button addActivity;
	@FXML
	private Button editActivity;

	@FXML
	private Pane hiddenPane;


	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (AddOrEditActivityToProject)");
		hiddenPane.setVisible(false);
	}

	@FXML
	private void addActivity(ActionEvent event) {
		hiddenPane.setVisible(true);


	}
	@FXML
	private void editActivity(ActionEvent event) {


	}

	/*
	 * Your code should be below this
	 */


}

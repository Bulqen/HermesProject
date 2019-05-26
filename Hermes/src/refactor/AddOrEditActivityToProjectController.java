package refactor;

import java.awt.Button;
import javafx.scene.layout.Pane;
import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import systemFixPackage.timeReport;

public class AddOrEditActivityToProjectController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();
	
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
	
	private void addActivity(ActionEvent event) {
		hiddenPane.setVisible(true);
		
		
		
	}
	
	private void editActivity(ActionEvent event) {
		
		
	}

	/*
	 * Your code should be below this
	 */
	
	
}

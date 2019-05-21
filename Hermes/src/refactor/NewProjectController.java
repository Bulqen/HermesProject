package refactor;

import systemFixPackage.timeReport;
import systemFixPackage.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class NewProjectController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();
	private DBConnection db = new DBConnection();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (NewProject)");
	}
		@FXML
	    private Pane newProject;

	    @FXML
	    private TextField nameLabel, startDateLabel,budgetLabel,statusLabel,endDateLabel;

	  
	    @FXML
	    private TextArea goalTextArea;

	    @FXML
	    private Button buttonCreateProject;

	    @FXML
	    void createProject(ActionEvent event) {
	    	
	    	db.projectCreate(startDateLabel.getText(), endDateLabel.getText(), goalTextArea.getText(), Integer.parseInt(budgetLabel.getText())
	    			, statusLabel.getText(), nameLabel.getText(), mainC.getUser().getUserId());

	    }
}

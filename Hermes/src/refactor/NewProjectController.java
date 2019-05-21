package refactor;

import systemFixPackage.timeReport;
import systemFixPackage.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	    	int pr; 
	    	pr = db.getProjectByManager(mainC.getUser().getUserId());
	    	System.out.println(pr);
	    	
	    	if(pr == -2) {
	    	db.projectCreate(startDateLabel.getText(), endDateLabel.getText(), goalTextArea.getText(), Integer.parseInt(budgetLabel.getText())
	    			, statusLabel.getText(), nameLabel.getText(), mainC.getUser().getUserId());
	    	
	  
	    
	    	Alert enterAlert = new Alert(AlertType.INFORMATION);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("Successfully created new project!");
			enterAlert.showAndWait();
			nameLabel.clear();
	    	startDateLabel.clear();
	    	budgetLabel.clear();
	    	statusLabel.clear();
	    	endDateLabel.clear();
	    	goalTextArea.clear();
	    	} else {
	    		Alert enterAlert = new Alert(AlertType.ERROR);
				enterAlert.setHeaderText(null);
				enterAlert.setContentText("You are already maniging a project");
				enterAlert.showAndWait();
	    	}

	    }
}

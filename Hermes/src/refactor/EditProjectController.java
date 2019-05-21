package refactor;

import systemFixPackage.timeReport;
import systemFixPackage.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class EditProjectController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();
	private DBConnection db = new DBConnection();
	
	int pr = db.getProjectByManager(mainC.getUser().getUserId());
	String [] project = db.getProjectInfo(pr);

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (EditProject)");
	}
	
	 public void setUp() {
		   
	   }

	@FXML
    private Pane editProject;

    @FXML
    private TextField projectIdLabel, nameLabel, startDateLabel, endDateLabel, budgetLabel, statusLabel;

    @FXML
    private Button editProjectButton, AddUserButton, removeUserButton;

    @FXML
    private ComboBox<?> cBoxAddUsers, cBoxremoveUsers;

   
  
    
    


    @FXML
    void addUser(ActionEvent event) {

    }

    @FXML
    void cBoxAdd(ActionEvent event) {

    }

    @FXML
    void cBoxRemove(ActionEvent event) {

    }

    @FXML
    void editProject(ActionEvent event) {

    }

    @FXML
    void removeUser(ActionEvent event) {

    }
}

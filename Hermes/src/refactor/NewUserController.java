package refactor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import systemFixPackage.timeReport;

public class NewUserController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (NewUser)");
	}


    @FXML
    private Pane newUser;

    @FXML
    private TextField nameLabel;

    @FXML
    private TextField adressLabel;

    @FXML
    private TextField phoneLabel;

    @FXML
    private TextField socialLabel;

    @FXML
    private TextField shiftLabel;

    @FXML
    private TextField roleLabel;

    @FXML
    private TextField managerIdLabel;

    @FXML
    private TextField hWageLabel;

    @FXML
    private TextField authorityLabel;

    @FXML
    private PasswordField passwordLabel1;

    @FXML
    private PasswordField passwordLabel2;

    @FXML
    private Button createUserButton;

    @FXML
    void createUser(ActionEvent event) {

    }
}

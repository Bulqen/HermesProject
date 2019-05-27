package refactor;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import systemFixPackage.DBConnection;
import systemFixPackage.timeReport;

public class NewUserController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();
	private DBConnection db = new DBConnection();


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
	private ComboBox<String> cBoxShiftLabel, cBoxAuthority, cBoxManager;


	@FXML
	private TextField userNameLabel;

	@FXML
	private TextField hWageLabel;


	@FXML
	private PasswordField passwordLabel1;

	@FXML
	private PasswordField passwordLabel2;

	@FXML
	private Button createUserButton;

	@FXML
	void createUser(ActionEvent event) {



			if(passwordLabel2.getText().equals(passwordLabel1.getText())) {
				String [] auth = cBoxAuthority.getSelectionModel().getSelectedItem().split(",");
				String [] shif = cBoxShiftLabel.getSelectionModel().getSelectedItem().split(",");
				int pay = Integer.parseInt(hWageLabel.getText());
				String [] name = nameLabel.getText().trim().split(" ");
				String [] man = cBoxManager.getSelectionModel().getSelectedItem().split(",");
				
				System.out.println("auth:"+auth[0]);
				System.out.println("auth:"+man[0]);
				System.out.println("auth:"+shif[0]);





				int user = db.userCreate(Integer.parseInt(auth[0]), Integer.parseInt(shif[0]),
						pay, Integer.parseInt(man[0]), name[0], name[1], adressLabel.getText(),
						phoneLabel.getText(), socialLabel.getText());

				db.loginCreate(user, userNameLabel.getText(), passwordLabel1.getText());
				setup();


			} else {
				Alert enterAlert = new Alert(AlertType.ERROR);
				enterAlert.setHeaderText(null);
				enterAlert.setContentText("password doesn´t match");
				enterAlert.showAndWait();
			}




	}
	public void setup() {
		cBoxAuthority.getItems().add("1,worker");
		cBoxAuthority.getItems().add("2,project manager");
		cBoxAuthority.getItems().add("3,department manager");

		cBoxShiftLabel.getItems().add("0,2-shift");
		cBoxShiftLabel.getItems().add("1,förmiddag");
		cBoxShiftLabel.getItems().add("2,natt");
		cBoxShiftLabel.getItems().add("3,helg");

		ArrayList<String[]> managers = db.getManagers(); 

		cBoxManager.getItems().add("0,no manager");

		for(int i = 0; i < managers.size(); i++) {
			String [] rows = managers.get(i);
			String row = rows[0] + ","+rows[1];
			cBoxManager.getItems().add(row);

		}



	}
}

package refactor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import systemFixPackage.Login;

//public class changePasswordController implements Initializable{
public class ChangePasswordController {

    @FXML
    private Pane changePassword;

    @FXML
    private PasswordField newPassword, oldPassword1, oldPassword2;

	private MenuGuiController mainC;

	/*
	@Override
   	public void initialize(URL arg0, ResourceBundle arg1) {

   	}
	*/

    public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		
	}
    @FXML
	private void saveNewPassword(ActionEvent event){
		Login validate = new Login(mainC.getUser().getUserName(), this.oldPassword1.getText());
		//System.out.println(mainC.getUser().getUserName()+ "---" + mainC.getUser().getUserId() + "---" + this.newPassword.getText());
		if(this.oldPassword1.getText().equals(this.oldPassword2.getText()) && validate.authorizePassword() && this.newPassword.getText().length() > 7){
			mainC.GetManageEmployee().changeYourOwnPassword(mainC.getUser().getUserId(), this.newPassword.getText());
			this.newPassword.clear();
			this.oldPassword1.clear();
			this.oldPassword2.clear();

			Alert enterAlert = new Alert(AlertType.INFORMATION);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("Password is changed");
			enterAlert.showAndWait();
		}
		else if(!validate.authorizePassword()){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("error");
			enterAlert.showAndWait();
		}
		else if(this.oldPassword1.getText().equals(this.oldPassword2.getText())){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("error");
			enterAlert.showAndWait();
		}
		else if(this.newPassword.getText().length() < 7){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("error");
			enterAlert.showAndWait();
		}

	}
}

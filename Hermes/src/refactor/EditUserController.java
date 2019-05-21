package refactor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class EditUserController {
//public class editUserController{
	@FXML
    private Pane editUser;

    @FXML
    private ComboBox<String> cBoxFilterEditUser;

    @FXML
    private ComboBox<String> cBoxOfUsersEdit;

    @FXML
    private TextField idLabelUserEdit, nameLabelUserEdit, adressLabelUserEdit, phoneLabelUserEdit, socialLabelUserEdit, shiftLabelUserEdit,
    roleLabelUserEdit, managerIdLabelUserEdit, managerNameLabelUserEdit, hWageLabelUserEdit, authorityLabelUserEdit;

    @FXML
    private Button saveUserEditsButton;

    private MenuGuiController mainC;

    /*
    public EditUserController(){

    }
    /*
    /*
	@FXML
	private void initialize() {
		//updateComboBoxOfUsersEdit();

	}
	*/

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		this.cBoxFilterEditUser = mainC.setComboBoxFilter(this.cBoxFilterEditUser);
	}

	@FXML
	private void filterComboBoxOfUsersEdit(ActionEvent event){
		updateComboBoxOfUsersEdit();
	}

	public void updateComboBoxOfUsersEdit(){
		ArrayList<String[]> employeeList = mainC.GetManageEmployee().getAllUsers();
		cBoxOfUsersEdit.getItems().clear();

		this.idLabelUserEdit.clear();
	    this.nameLabelUserEdit.clear();
	    this.adressLabelUserEdit.clear();
	    this.phoneLabelUserEdit.clear();
	    this.socialLabelUserEdit.clear();
	    this.shiftLabelUserEdit.clear();
	    this.roleLabelUserEdit.clear();
	    this.managerIdLabelUserEdit.clear();
	    this.managerNameLabelUserEdit.clear();
	    this.hWageLabelUserEdit.clear();
	    this.authorityLabelUserEdit.clear();

		if(cBoxFilterEditUser.getSelectionModel().getSelectedIndex() != -1)
		{
			for(int i = 0; i<employeeList.size(); i++)
			{
				if(cBoxFilterEditUser.getSelectionModel().getSelectedItem().equalsIgnoreCase("No filter") || employeeList.get(i)[1].trim().substring(0, 1).equalsIgnoreCase(cBoxFilterEditUser.getSelectionModel().getSelectedItem()))
				{
					cBoxOfUsersEdit.getItems().add("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]);
				}
			}
		}
		//Shows all users in DropDown box when no filter is selected
		else{
			for(int i = 0; i<employeeList.size(); i++)
			{
				cBoxOfUsersEdit.getItems().add("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]);
			}
		}

	}

	@FXML
	private void saveUserEdits(ActionEvent event){
		ArrayList<String[]> employeeList = mainC.GetManageEmployee().getAllUsers();
		int selected = -1;
		for(int i = 0; i<employeeList.size(); i++){
			if(("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]).equals(cBoxOfUsersEdit.getSelectionModel().getSelectedItem()))
				selected = i;
		}

		if(selected != -1){
			//Byt metod sen
			try{
				String str = this.socialLabelUserEdit.getText().replace("-", "");
				str = str.replace("e", "r");
				Double.parseDouble(str);
				System.out.println(str);
			} catch(Exception e){
				Alert enterAlert = new Alert(AlertType.ERROR);
				enterAlert.setHeaderText(null);
				enterAlert.setContentText("Social security number should be entered with numbers in one of the two following formats 0000000000, 000000-0000");
				enterAlert.showAndWait();
				return;
			}
			mainC.GetManageEmployee().changeUserInformationProvisorisk(Integer.parseInt(this.idLabelUserEdit.getText()), this.nameLabelUserEdit.getText(), this.adressLabelUserEdit.getText(),
			this.phoneLabelUserEdit.getText(), this.socialLabelUserEdit.getText(), this.shiftLabelUserEdit.getText(), this.roleLabelUserEdit.getText(),
			this.managerNameLabelUserEdit.getText(), this.hWageLabelUserEdit.getText(), this.authorityLabelUserEdit.getText(), this.managerIdLabelUserEdit.getText());

			updateComboBoxOfUsersEdit();
		}
		else{
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("You need to select a user before saving");
			enterAlert.showAndWait();
		}

	}

	@FXML
    void fillInInformation(ActionEvent event) {

    	ArrayList<String[]> employeeList = mainC.GetManageEmployee().getAllUsers();
		int selected = -1;
		for(int i = 0; i<employeeList.size(); i++){
			if(("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]).equals(cBoxOfUsersEdit.getSelectionModel().getSelectedItem()))
				selected = i;
		}
		if(selected != -1){
			this.idLabelUserEdit.setText(employeeList.get(selected)[0]);
	    	this.nameLabelUserEdit.setText(employeeList.get(selected)[1]);
	    	this.adressLabelUserEdit.setText(employeeList.get(selected)[2]);
	    	this.phoneLabelUserEdit.setText(employeeList.get(selected)[3]);
	    	this.socialLabelUserEdit.setText(employeeList.get(selected)[4]);
	    	this.shiftLabelUserEdit.setText(employeeList.get(selected)[5]);
	    	this.roleLabelUserEdit.setText(employeeList.get(selected)[6]);
	    	this.managerIdLabelUserEdit.setText(employeeList.get(selected)[7]);
	    	this.managerNameLabelUserEdit.setText(employeeList.get(selected)[8]);
	    	this.hWageLabelUserEdit.setText(employeeList.get(selected)[9]);
	    	this.authorityLabelUserEdit.setText(employeeList.get(selected)[10]);
		}

    }
}

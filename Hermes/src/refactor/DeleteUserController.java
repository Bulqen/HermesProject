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
import javafx.scene.layout.Pane;

//public class deleteUserController implements Initializable{
public class DeleteUserController{
	@FXML
    private Pane deleteUser;

    @FXML
    private ComboBox<String> cBoxFilterDeleteUser, cBoxOfUsersDelete;

    @FXML
    private Button deleteUserEditsButton;

    private MenuGuiController mainC;

    /*
    @Override
   	public void initialize(URL arg0, ResourceBundle arg1) {
    	this.cBoxFilterDeleteUser = mainC.setComboBoxFilter();
    	updateComboBoxOfUsersDelete();
   	}
	*/

    public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		this.cBoxFilterDeleteUser = mainC.setComboBoxFilter(this.cBoxFilterDeleteUser);
	}

	@FXML
	private void filterComboBoxOfUsersDelete(ActionEvent event){
		updateComboBoxOfUsersDelete();
	}

	public void updateComboBoxOfUsersDelete(){
		ArrayList<String[]> employeeList = mainC.GetManageEmployee().getAllUsers();
		cBoxOfUsersDelete.getItems().clear();

		if(cBoxFilterDeleteUser.getSelectionModel().getSelectedIndex() != -1)
		{
			for(int i = 0; i<employeeList.size(); i++)
			{
				if(cBoxFilterDeleteUser.getSelectionModel().getSelectedItem().equalsIgnoreCase("No filter") || employeeList.get(i)[1].trim().substring(0, 1).equalsIgnoreCase(cBoxFilterDeleteUser.getSelectionModel().getSelectedItem()))
				{
					cBoxOfUsersDelete.getItems().add("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]);
				}
			}
		}
		//Shows all users in DropDown box when no filter is selected
		else{
			for(int i = 0; i<employeeList.size(); i++)
			{
				cBoxOfUsersDelete.getItems().add("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]);
			}
		}

	}

	@FXML
	private void deleteSelectedUser(ActionEvent event){
		ArrayList<String[]> employeeList = mainC.GetManageEmployee().getAllUsers();
		int idRemove = -1;
		for(int i = 0; i<employeeList.size(); i++){
			if(("Name: " + employeeList.get(i)[1] + ", Employee ID: " + employeeList.get(i)[0]).equals(cBoxOfUsersDelete.getSelectionModel().getSelectedItem()))
				idRemove = i;
		}


		if(idRemove != -1 && Integer.parseInt(employeeList.get(idRemove)[0]) != mainC.getUser().getUserId()) {

			System.out.println("Remove");
			mainC.GetManageEmployee().deleteUser(idRemove);
			//updateListViewForDeleteUser();
		}
		else if(idRemove == -1){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("You need to select a user before deleting");
			enterAlert.showAndWait();
		}
		else if(Integer.parseInt(employeeList.get(idRemove)[0]) == mainC.getUser().getUserId()){
			Alert enterAlert = new Alert(AlertType.ERROR);
			enterAlert.setHeaderText(null);
			enterAlert.setContentText("You can't delete your own account");
			enterAlert.showAndWait();
		}

	}
}

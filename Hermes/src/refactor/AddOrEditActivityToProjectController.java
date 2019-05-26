package refactor;




import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import systemFixPackage.DBConnection;
import systemFixPackage.timeReport;
public class AddOrEditActivityToProjectController {
	private MenuGuiController mainC;
	private DBConnection c = new DBConnection();
	private timeReport timeReporter = new timeReport();
	//asdx
	@FXML
	private Button addActivity;
	@FXML
	private Button editActivity;
	
	@FXML
	private ComboBox cBoxOfProjects;
	
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
	
	public void updateComboBoxOProjects(){
		//ArrayList<String[]> employeeList = mainC.GetManageEmployee().getAllUsers();
		ArrayList<String> projects = c.getProjectByManager();
		cBoxOfProjects.getItems().clear();

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
    void fillInInformation(ActionEvent event) {
		
		
		
    	ArrayList<String[]> employeeList = mainC.GetManageEmployee().getAllUsers();
    	ArrayList<String[]> projectList = c.getProcjectActivities();
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

	/*
	 * Your code should be below this
	 */
	
	
}

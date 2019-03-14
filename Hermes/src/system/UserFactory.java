package system;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class UserFactory {

	public UserFactory(){

	}

	//connecta till db
	public User getUserType(String userName) throws SQLException{

		DBConnection DB = new DBConnection(userName);
		return DB.getEmployeeType(); // 1=Worker, 2=ProjectManager & 3=DepartmentManager
	}

	public <E> E createUser(String userName, int userType){
		if (userType == 1){
			Worker w = new Worker(userName);
			return (E) w;
		}
		else if (userType == 2){
			ProjectManager pm = new ProjectManager(userName);
			return (E) pm;
		}
		else if (userType == 3){
			DepartmentManager dm = new DepartmentManager(userName);
			return (E) dm;
		}
		else{
			//Ska tas bort innan vi skickar in
			JOptionPane.showInputDialog("ERROR, ange userType 1, 2 eller 3 Förfan! Usern sätts som Worker nu istället");
			Worker standardUser = new Worker(userName);		
			return (E) standardUser;

			}
		
		
		
	}




}

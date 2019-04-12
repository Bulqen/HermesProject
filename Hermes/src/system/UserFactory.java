package system;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class UserFactory {
	
	// start index 0 is userid, 1 name, 2 adress, 3 number, 4 socials, 5 shift, 6 role, 7 mangerid, 8 mangerName
	
	
	 
	
	public UserFactory(String userName){

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
			JOptionPane.showInputDialog("ERROR, ange userType 1, 2 eller 3 F�rfan! Usern s�tts som Worker nu ist�llet");
			Worker standardUser = new Worker(userName);		
			return (E) standardUser;
			}
		
		
		
	}




}

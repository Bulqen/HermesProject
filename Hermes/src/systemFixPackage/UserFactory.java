package systemFixPackage;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class UserFactory {

	// start index 0 is userid, 1 name, 2 adress, 3 number, 4 socials, 5 shift, 6 role, 7 mangerid, 8 mangerName

	private String userName;
	private DBConnection DBC = new DBConnection();


	private static UserFactory obj = null;

	private UserFactory(String userName){
		this.userName = userName;
	}

	public static UserFactory initiateUserFactory(String userName){
		//if(obj==null){
		    obj= new UserFactory(userName);

	        return obj;
	   }
	//Ändra så dom inte skapas om varje gång;

	public User getUser(String employeeType){
		int userID = DBC.getUserIdByUsername(this.userName);

		String [] info  = DBC.getUserInfo(userID);

		/*
		System.out.println("userId:" +info[0] + " name:" + info[1] + " adress:" + info[2] + " number:" + info[3] + " socials:" + info[4]);
		System.out.println("-------------------");
		System.out.println(" shiftId:" + info[5]);
		System.out.println("-------------------");
		System.out.println(" role:" + info[6]);
		System.out.println("-------------------");
		System.out.println(" managerName:" + info[7]);
		System.out.println("-------------------");
		System.out.println(" null bs wtf:" + info[8]);
		System.out.println("-------------------");
		System.out.println("hourlySalary:" + info[9]);
		System.out.println("-------------------");
		System.out.println(" classificationID:" + info[10]);
		*/

	    if(employeeType == null){
	    	return null;
	    }
	    if(employeeType.equalsIgnoreCase("WORKER"))
	    {
	    	return new Worker(userID, info[1], info[2], info[3], info[4], info[5], info[6], info[8], info[9], info[10], info[7], this.userName);

	    }
	    else if(employeeType.equalsIgnoreCase("DMANAGER"))
	    {
	    	return new DepartmentManager(userID, info[1], info[2], info[3], info[4], info[5], info[6], info[8], info[9], info[10], info[7], this.userName);

	    }
	    else if(employeeType.equalsIgnoreCase("PMANAGER"))
	    {
	    	return new ProjectManager(userID, info[1], info[2], info[3], info[4], info[5], info[6], info[8], info[9], info[10], info[7], this.userName);
	    }
	     	return null;
	}


}

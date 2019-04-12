package system;

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
		if(obj==null){
		    obj= new UserFactory(userName);
		}
	        return obj;
	   }

	public Worker createWorker(){
		//Null point
		int userID = DBC.getUserIdByUsername(this.userName);
		String [] info  = DBC.getUserInfo(userID);
		Worker currentUser = new Worker(Integer.parseInt(info[0]),info[1],info[2],Integer.parseInt(info[3]),info[4],Integer.parseInt(info[5])
				,Integer.parseInt(info[6]),info[7],Double.parseDouble(info[8]),Integer.parseInt(info[9]));

		return currentUser;


	}
	public ArrayList <String []> getTimeReport(){
		//System.out.println(currentUser.getUserId());
		//return DBC.getTimeReport(currentUser.getUserId());
		return null;
	}
}

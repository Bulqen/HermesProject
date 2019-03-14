package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	
	private String userName;
	private String PW;
	
	DBConnection() throws SQLException{
		
		
	}
	
	DBConnection(String userName){
		this.userName = userName;
	}
	
	public String getPW(String userName) throws SQLException {
		
		Connection myConn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshop", "user", "pass");
		String password = String.valueOf(myConn.prepareStatement("SELECT * FROM tabelNamn WHERE userName = this.userName "));
		
		return password;
		
	}
	
	//databas måste implimenteras först
		public int getEmployeeType(){
			int userType = (?= CALL procedure_employeeType); // 1=Worker, 2=ProjectManager & 3=DepartmentManager
			return userType; 
		}
}

	
	


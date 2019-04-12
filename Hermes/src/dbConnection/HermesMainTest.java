/**
 * 
 */
package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import system.DBConnection;

/**
 * @author Justad
 *
 */
public class HermesMainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		Connection myConn;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hermes", "user", "pass");
		
		
		PreparedStatement preMyStmt = myConn.prepareStatement("Select password FROM login WHERE username = ?");
		preMyStmt.setString(1, "user");

		ResultSet myRs = preMyStmt.executeQuery();
		myRs.next();

		String password = myRs.getString("password");
		System.out.println(password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnection db = new DBConnection();
		
		String [] mySalary = db.generateSalaryslip(1);
		
		for (String val : mySalary) {
			System.out.print(val + " |");
			
		}

		
		
		

	}

}

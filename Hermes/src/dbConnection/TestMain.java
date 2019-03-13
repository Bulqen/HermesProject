package dbConnection;

import java.sql.*;

public class TestMain {

	public static void main(String[] args) {
		
		try {
			
			// connect to database
			//jdbc:<driver protocol>:<driver connection detils><database name>, username ,password
			//local
			Connection myConn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshop", "user", "pass");
			
			
			
			// statement
			
			Statement myStmt = myConn.createStatement();
			
			
			// execute tge statement with SQL query
			
			ResultSet myRs = myStmt.executeQuery("Select * from kund");
			
			// process result set
			
		
			while(myRs.next()) {
				System.out.println(myRs.getString("fornamn") + " "+ (myRs.getString("efternamn")));
			}
		
			// test
			System.out.println(myRs.getArray(1));
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
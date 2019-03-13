package dbConnection;
import java.sql.*;


public class TestConectSchoolMain {

	public static void main(String[] args) {


		try {

			// connect to database
			//jdbc:<driver protocol>:<driver connection detils><database name>, username ,password

			//skolan

			Connection myConn  = DriverManager.getConnection("jdbc:mysql://blu-ray.student.bth.se:3306/jaju15", "jaju15", "XXXXX");


			// statement

			Statement myStmt = myConn.createStatement();


			// execute tge statement with SQL query

			ResultSet myRs = myStmt.executeQuery("Select * FROM Plagg");

			// process result set


			while(myRs.next()) {
				System.out.println(myRs.getString("Tillverkare") + " "+ (myRs.getString("Modell")));
			}

			// test funkade inte
			//Array firstColumn = myRs.getArray(1);
			
			System.out.println("done");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}




}



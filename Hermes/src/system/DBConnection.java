package system;

import java.sql.*;

public class DBConnection {


	private String userName;
	private String PW;
	private Connection myConn;

	DBConnection() {

		try {
			myConn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/hermes", "user", "pass");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPW() {
		return PW;
	}

	public void setPW(String pW) {
		PW = pW;
	}

	public Connection getMyConn() {
		return myConn;
	}

	public void setMyConn(Connection myConn) {
		this.myConn = myConn;
	}

	DBConnection(String userName){
		this.userName = userName;
	}

	public String getPW(String userName) throws SQLException {
<<<<<<< HEAD
		
<<<<<<< HEAD
		String password = String.valueOf(myConn.prepareStatement("SELECT * FROM tabelNamn WHERE userName = " + this.userName));
=======
=======

>>>>>>> branch 'master' of https://github.com/Bulqen/HermesProject.git
//		String password = String.valueOf(myConn.prepareStatement("SELECT * FROM tabelNamn WHERE userName = this.userName "));
		PreparedStatement preMyStmt = myConn.prepareStatement("Select password FROM login WHERE username = ?");
		preMyStmt.setString(1, userName);

		ResultSet myRs = preMyStmt.executeQuery();
		myRs.next();

		String password = myRs.getString("password");
<<<<<<< HEAD
		
		
>>>>>>> branch 'master' of https://github.com/Bulqen/HermesProject.git
		
=======



>>>>>>> branch 'master' of https://github.com/Bulqen/HermesProject.git
		return password;

	}

	//databas måste implimenteras först
		public int getEmployeeType() throws NumberFormatException, SQLException{
			int userType = Integer.valueOf(String.valueOf(myConn.prepareStatement("SELECT classificationID FROM user WHERE userName = " + this.userName))); // 1=Worker, 2=ProjectManager & 3=DepartmentManager
					//(?= CALL procedure_employeeType);
			return userType;
		}
}





/**
 *
 */
package systemFixPackage;

import java.sql.SQLException;

/**
 * @author anton
 *
 */
// this class handels the login object, the enterd username and password is sent from the GUI where the object
// thereafter acquires the actual password for that username and thereafter gives the option to
// authorise if these two are the same in an authroizing manner
public class Login {

	DBConnection DB = new DBConnection();

	private String userName;
	private String enterdPassword;
	private String truePassword;

	public Login(String userName,String enterdPassword){

		this.userName = userName;
		this.enterdPassword = enterdPassword;
		try {
			this.truePassword = DB.getPW(this.userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean authorizePassword() {

		if(enterdPassword.equals(truePassword))
			return true;
		else
			return false;

	}
}

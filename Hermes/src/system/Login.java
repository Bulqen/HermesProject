/**
 * 
 */
package system;

/**
 * @author anton
 *
 */
public class Login {

	DBConnection DB = new DBConnection();
	
	private String getPW() {
		
		String password = DB.getPW(userName);
	
	}


}

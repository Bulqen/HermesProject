/**
 * 
 */
package system;

import java.util.ArrayList;

/**
 * @author anton
 *
 */
public class timeReport {
	private DBConnection DBC;
	
	public ArrayList <String []> getTimeReport(int userId){
		
		return DBC.getTimeReport(userId);
		
	}
	
	public void stampIn(int userId){
		DBC.stampIn(userId);
	}
	
	public void stampOut(int userId){
		DBC.stampOut(userId);
	}
}

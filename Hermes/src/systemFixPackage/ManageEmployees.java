/**
 * 
 */
package systemFixPackage;

import system.User;

/**
 * @author anton
 *
 */
public class ManageEmployees {

	private DBConnection DBC = new DBConnection();
	private User currentUser;
	private User targetUser;
	private int classificationID;

	public ManageEmployees(User currentUser) {

		this.currentUser = currentUser;
		this.classificationID = currentUser.getClassificationID();

	}

	public void createUser(int userId, String name, String adress, String number, String socials, String shiftId,
			String managerName, String hourlySalary, String classificationID,String managerID) {
		if (this.classificationID == 3) {
			String [] temp = name.split(" ");
			String firstName = temp[0];
			String lastName = temp[1];
			DBC.userCreate((int) Integer.parseInt(classificationID),(int) Integer.valueOf(shiftId),(int) Integer.valueOf(hourlySalary),
					(int) Integer.valueOf(managerID),firstName,lastName,adress,number,socials);
		}
//classId, shiftId, hourlyPay, managerId, firstName, lastName, adress, phone, socialSecurityNumber
	}

	public void deleteUser(int targetUserId) {
		if (this.classificationID == 3) {

			DBC.deletUser(targetUserId);
		}
	}

	public void changeUser(int userId, String name, String adress, String number, String socials, String shiftId,
			String role, String managerName, String hourlySalary, String classificationID,String managerId) {
		
		if (this.classificationID == 3) {
		
			String [] temp = name.split(" ");
			String firstName = temp[0];
			String lastName = temp[1];
			
			DBC.editUser(userId,(int) Integer.parseInt(classificationID),(int) Integer.parseInt(shiftId), 
					(int) Integer.parseInt(hourlySalary),(int) Integer.parseInt(managerId), 
					firstName, lastName, adress, number, socials);
		
		}
		
		
		
		// classification ID == 1,2,3
		// shiftID 1,2,3,4
		// ej user ID

	}
}

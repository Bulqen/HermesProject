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
			System.out.println("User sucsefully");
		}
		else 
			System.out.println("You are not authorized to perform the desired operation");
//classId, shiftId, hourlyPay, managerId, firstName, lastName, adress, phone, socialSecurityNumber
	}

	public void deleteUser(int targetUserId) {
		if (this.classificationID == 3) {

			DBC.deletUser(targetUserId);
			System.out.println("The user has been terminated");
		}
		else 
			System.out.println("You are not authorized to perform the desired operation");
	}

	public void changeUserInformation(int userId, String name, String adress, String number, String socials, String shiftId,
			String role, String managerName, String hourlySalary, String classificationID,String managerId) {
		
		if (this.classificationID == 3) {
		
			String [] temp = name.split(" ");
			String firstName = temp[0];
			String lastName = temp[1];
			
			if(checkClassID(classificationID) == true && checkshiftId(shiftId) == true )
			{
			DBC.editUser(userId,(int) Integer.parseInt(classificationID),(int) Integer.parseInt(shiftId), 
					(int) Integer.parseInt(hourlySalary),(int) Integer.parseInt(managerId), 
					firstName, lastName, adress, number, socials); 
			System.out.println("The user information was succsesfully changed");
			}
			else if(checkClassID(classificationID) == false)
				System.out.println("Invalid values of the classificationID");
			else if(checkshiftId(shiftId) == false)
				System.out.println("Invalid values of the shiftId");
		}
		else 
			System.out.println("You are not authorized to perform the desired operation");
		
		// classification ID is only allowed to be 1,2,3
		// shiftID is only allowed to have any of the following the values 1,2,3,4
	}
	public void changePassword(int userId,String newPassword){
		
		if(this.classificationID == 3) {
		DBC.changePW(userId,newPassword);
		}
		
	}

	private boolean checkshiftId(String shiftId) {
		// TODO Auto-generated method stub
		
		if(Integer.valueOf(shiftId) == 1 || Integer.valueOf(shiftId) == 2 || Integer.valueOf(shiftId) == 3 
				|| Integer.valueOf(shiftId) == 4 )
			return true;
		else 
			return false;		
	}

	private boolean checkClassID(String classificationID) {
		// TODO Auto-generated method stub
		
		if(Integer.valueOf(classificationID) == 1 || Integer.valueOf(classificationID) == 2 || 
				Integer.valueOf(classificationID) == 3) 
			return true;
		else
			return false;
	}
	private boolean checkClassificationID(){
		if(this.classificationID == 3)
			return true;
			else
				return false;
	}
}

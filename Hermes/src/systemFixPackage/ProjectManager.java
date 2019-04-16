package systemFixPackage;

public class ProjectManager extends Manager{

	public ProjectManager(int userId, String name, String adress, String number, String socials, String shiftId,
			String role, String managerName, String hourlySalary, String classificationID) {
		super(userId, name, adress, number, socials, shiftId, role, managerName, hourlySalary, classificationID);
		// TODO Auto-generated constructor stub
	}

	public ProjectManager(int userId, String name, String adress, String number, String socials, String shiftId,
			String role, String managerName, String hourlySalary, String classificationID, String managerID,
			String userName) {
		super(userId, name, adress, number, socials, shiftId, role, managerName, hourlySalary, classificationID, managerID,
				userName);
		// TODO Auto-generated constructor stub
	}
}

package systemFixPackage;

public abstract class Manager extends User {

	private String[] projects;
	private String[] employees;

	public Manager(int userId, String name, String adress, String number, String socials, String shiftId,
			String role, String managerName, String hourlySalary, String classificationID) {
		super(userId, name, adress, number, socials, shiftId, role, managerName, hourlySalary, classificationID);
		// TODO Auto-generated constructor stub
	}


	public Manager(int userId, String name, String adress, String number, String socials, String shiftId, String role,
			String managerName, String hourlySalary, String classificationID, String managerID, String userName) {
		super(userId, name, adress, number, socials, shiftId, role, managerName, hourlySalary, classificationID, managerID,
				userName);
		// TODO Auto-generated constructor stub
	}



	public String[] getProjects() {
		return projects;
	}

	public void setProjects(String[] projects) {
		this.projects = projects;
	}

	public String[] getEmployees() {
		return employees;
	}

	public void setEmployees(String[] employees) {
		this.employees = employees;
	}




}

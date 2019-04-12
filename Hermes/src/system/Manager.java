package system;

public abstract class Manager extends User {

	//Detta måste fixas
	private String[] projects;
	private String[] employees;

	public Manager(int userId, String name, String adress, int number, String socials, int shiftId, int role,
			String managerName, double hourlySalary, int classificationID) {
		super(userId, name, adress, number, socials, shiftId, role, managerName, hourlySalary, classificationID);
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

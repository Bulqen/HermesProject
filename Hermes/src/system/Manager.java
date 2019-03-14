package system;

public class Manager extends User {

	private String[] projects;
	private String[] employees;
	
	public Manager(String userName) {
		super(userName);
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

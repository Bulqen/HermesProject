package system;

public class DBConnection {


	
	public String getPW() {
		
		String password = "123"; // h�r ska givetvis password h�mtas fr�n databasen n�r de metoderna �r f�rdiga
		
		return password;
	}
	
	//databas måste implimenteras först
	public int getEmployeeType(){
		int userType = (?= CALL procedure_employeeType); // 1=Worker, 2=ProjectManager & 3=DepartmentManager
		return userType; 
	}
}

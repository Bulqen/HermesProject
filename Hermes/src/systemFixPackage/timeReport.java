/**
 *
 */
package systemFixPackage;

import java.util.ArrayList;


public class timeReport {
	private DBConnection DBC = new DBConnection();

	public ArrayList <String []> getTimeReport(int userId){

		return DBC.getTimeReport(userId);

	}

	public void editTimeReport(String start, String stop, String currentDate, int timeId, String comment){
		this.DBC.editTimeReport(start, stop, currentDate, timeId, comment);
	}
	public void stampIn(int userId){
		DBC.stampIn(userId);
	}

	public void stampOut(int userId){
		DBC.stampOut(userId);
	}
	public void recordAbsence(int uId, String type, String comment) {
		DBC.reportAbscence(uId, type, comment);
	}

	public String[] generateSalarySlip(int userId){
		//[0] totalHours, [1] hourlyPay, [2] YYYY-MM, [3] pay, [4] namn
		String[] salaryInfo = this.DBC.generateSalaryslip(userId);
		String[] salarySlip = new String[10];

		salarySlip[0] = salaryInfo[0];
		salarySlip[1] = salaryInfo[1];
		salarySlip[2] = salaryInfo[2];
		salarySlip[3] = salaryInfo[3];
		salarySlip[4] = salaryInfo[4];

		if(salaryInfo[0] == null)
			salarySlip[0] = "0";
		if(salaryInfo[3] == null)
			salarySlip[3] = "0";


		double tax;
		double nettoPay;

		//Här bor vi importera en skattetabell och skatta enligt den
		//Provisorisk lösning sålänge

		if(Integer.parseInt(salarySlip[3]) > 50000){
			tax = 0.3;
			nettoPay = Double.parseDouble(salarySlip[3]) * tax;
		}
		else if(Integer.parseInt(salarySlip[3]) > 40000){
			tax = 0.28;
			nettoPay = Double.parseDouble(salarySlip[3]) * tax;
		}
		else if(Integer.parseInt(salarySlip[3]) > 30000){
			tax = 0.26;
			nettoPay = Double.parseDouble(salarySlip[3]) * tax;
		}
		else if(Integer.parseInt(salarySlip[3]) > 20000){
			tax = 0.24;
			nettoPay = Double.parseDouble(salarySlip[3]) * tax;
		}
		else if(Integer.parseInt(salarySlip[3]) <= 20000){
			tax = 0.22;
			nettoPay = Double.parseDouble(salarySlip[3]) * tax;
		}
		else{
			tax = 0.22;
			nettoPay = 0;
		}
		salarySlip[5] = Double.toString(nettoPay);
		salarySlip[6] = Double.toString(tax);


		return salarySlip;
	}

}

package refactor;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import systemFixPackage.DBConnection;

public class SalaryBetweenDatesController {
	@FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Label salaryLabel;

    @FXML
    private Label hourLabel;

    private DBConnection db = new DBConnection();

    private MenuGuiController mainC;

    public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (ShowSchedule)");
	}

    @FXML
    private void showSalary(ActionEvent event){
    	ArrayList<String[]> reports = db.getTimeReportIntervall(mainC.getUser().getUserId(), startDate.getValue().toString(), endDate.getValue().toString());
    			System.out.println(startDate.getValue() + " " + endDate.getValue());
    			double pay = Double.parseDouble(mainC.getUser().getHourlySalary());
    			double sumHours = 0;

    			for(int i = 0; i < reports.size(); i++) {
    			    sumHours += Double.parseDouble(reports.get(i)[7]);

    			}

    			Double totalCash = sumHours * pay;

    			this.salaryLabel.setText("Gross earnigns: " + Double.toString(totalCash));
    			this.hourLabel.setText("Hours worked: " + Double.toString(sumHours));
    }
}

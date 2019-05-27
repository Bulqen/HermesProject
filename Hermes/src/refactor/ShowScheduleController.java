package refactor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import systemFixPackage.DBConnection;
import systemFixPackage.timeReport;

public class ShowScheduleController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();
	private DBConnection c = new DBConnection();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (ShowSchedule)");
	}


	@FXML
    private GridPane grid;
	public void test(){

		for(int i = 0; i <24; i++){
			Label time = new Label();
			time.setFont(new Font("System", 12));
			time.setTextFill(Color.web("#87cefa"));
			String text = i+":00";
			time.setText(text);
			grid.add(time,0,i);
		}

		String[] workSchedule = c.generateSchedule(mainC.getUser().getUserId());
		int startDay = Integer.parseInt(workSchedule[2]);
		int endDay = Integer.parseInt(workSchedule[3]);

		String[] startTime = workSchedule[0].split(":");
		String[] endTime = workSchedule[0].split(":");
		int start = Integer.parseInt(startTime[0]);
		int stop = Integer.parseInt(endTime[0]);

		System.out.println(start);

		for(int i = startDay-1; i < endDay; i++){
			for(int j = 0; j <24; j++){
				int check = j+1;
				if(check >= start && check <= stop){
					System.out.println(i + "," + j);
				}
			}
		}
	}
}

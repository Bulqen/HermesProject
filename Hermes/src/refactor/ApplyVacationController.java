package refactor;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import systemFixPackage.DBConnection;
import systemFixPackage.timeReport;

public class ApplyVacationController {
	private MenuGuiController mainC;
	private timeReport timeReporter = new timeReport();
	private DBConnection db = new DBConnection();

	public void injectMainController(MenuGuiController mainC){
		this.mainC = mainC;
		System.out.println("This message shows us that the controller is set up (ApplyVacation)");
	}
	
	@FXML
    private Pane applyVacation;

    @FXML
    private Button applyVacationButton;

    @FXML
    private TextField startLabel;

    @FXML
    private TextField endLabel;

    @FXML
    private TextArea CommentArea;

    @FXML
    void applyVacation(ActionEvent event) {
    	
    	String startDate = startLabel.getText();
    	String endDate = endLabel.getText();
    	String comment = CommentArea.getText();
    	
    	db.applyVacationDates(mainC.getUser().getUserId(), startDate, endDate, comment);

    }
	
	
}

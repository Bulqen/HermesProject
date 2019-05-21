package refactor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MessageWindowControler implements Initializable{

	@FXML
    private Label LBLDisplayMessage;

	private Node node;
	private Stage stage;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void close_app(MouseEvent event){
		node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.close();
	}

	public void setMessage(String message){
		LBLDisplayMessage.setText(message);
	}

	@FXML
	private void OK(ActionEvent event){
		node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.close();
	}

}

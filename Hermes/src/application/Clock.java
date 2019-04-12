package application;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.concurrent.Task;

public class Clock extends Task<String>{

	@Override
	protected String call() throws Exception {
		// TODO Auto-generated method stub
		while (true) {
	    	Date dNow = new Date( );
		    SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd '-' HH:mm:ss");
		    updateMessage(ft.format(dNow));
		    Thread.sleep(1000);
        }
	}

}

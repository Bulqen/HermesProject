package application;

public class timeToObList {
	private String date, in, out, hours, absent;
	private int timeRowId;

	public timeToObList(String in, String out, String date, String hours, String absent, int timeRowId){
		this.in = in;
		this.out = out;
		this.date = date;
		this.hours = hours;
		this.absent = absent;
		this.timeRowId = timeRowId;
	}

	public int getTimeRowId() {
		return timeRowId;
	}

	public void setTimeRowId(int timeRowId) {
		this.timeRowId = timeRowId;
	}

	public timeToObList(String in, String out, String date, String hours){
		this.in = in;
		this.out = out;
		this.date = date;
		this.hours = hours;
	}

	public String getAbsent() {
		return absent;
	}

	public void setAbsent(String absent) {
		this.absent = absent;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

}

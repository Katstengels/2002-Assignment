import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Schedule {
	private Calendar time = Calendar.getInstance();;
	private boolean available;
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM YYYY");
	
	public Schedule (int inDay, int inTime) {
		time.add(Calendar.DATE, inDay);
		time.set(Calendar.HOUR_OF_DAY, (9+inTime));
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		available = true;
	}
	
	public void changeAvail(boolean avail) {
		available = avail;
	}
	
	public String getDate() {
		return dateFormat.format(time.getTime());
	}
	public String getTime() {
		return timeFormat.format(time.getTime());
	}
	
	public Calendar getDateTime() {
		return time;
	}
	
	public boolean getAvail() {
		return available;
	}

}

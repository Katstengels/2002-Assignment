import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Schedule {
	private Calendar calendar = Calendar.getInstance();;
	private Date dateTime;
	private boolean available;
	private Appointment appointment;
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); // display time in the format 12:00 from Date data
	private SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM YYYY"); // display in the format "Friday, 01 Nov 2024" from Date data
	
	public Schedule (int inDay, int inTime) {
		calendar.add(Calendar.DATE, inDay);					//Today + inDay
		calendar.set(Calendar.HOUR_OF_DAY, (9+inTime));		//First apt at 9 am (one hour slot) + Nth appointment
		calendar.set(Calendar.MINUTE, 0);					//Ensure hour ends with :00
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		dateTime = calendar.getTime();						//Set dateTime for apt as the above
		available = false;									//default value
	}
	
	public void changeAvail(boolean avail) {
		available = avail;
	}
	
	public String getDate() {
		return dateFormat.format(dateTime.getTime());
	}
	public String getTime() {
		return timeFormat.format(dateTime.getTime());
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public boolean getAvail() {
		return available;
	}
	
	public void addAppointment(Appointment inApt) {
		appointment = inApt;
	}
	
	public Appointment getAppointment() {
		return appointment;
	}
	
}

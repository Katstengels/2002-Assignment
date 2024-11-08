package src;

public class RestockForm {
	private int restockID;
	private static int restockIDCounter=0;
    private String medicationName;
    private int restockAmount;
    public enum status {
        Pending_Admin, Fulfilled
    }
    private String restockStatus;


    public RestockForm(){
    	
    }
    //
}
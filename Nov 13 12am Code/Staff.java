package project_hospital_draft1;

public class Staff extends User {

    private String name;
    private String gender;
    private int age;


    public Staff(String userID, String name, String role, String gender, int age, String password) {
        super(userID, password, role);
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    //Getters

    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }
    public int getAge() {
        return age;
    }
    public String getID() {
    	return getUserID();
    }
    public String getRole() {
    	return super.getRole();
    }
    
    public void changeName(String newname) {
    	this.name = newname;
    	System.out.println("Name changed successfully!");
    	return;
    }
    public void changeAge(int newage) {
    	this.age = newage;
    	System.out.println("Age changed successfully!");
    	return;
    }
    public void changeGender(String newgender) {
    	this.gender = newgender;
    	System.out.println("Gender changed successfully!");
    	return;
    }
    public void changeRole(String newrole) {
    	super.setRole(newrole);
    	System.out.println("Role changed successfully!");
    	return;
    }

    @Override
    public String toString() { //FOR TESTING ONLY
        return "Staff {" +
                "StaffID='" + super.getUserID()+
                "', Password: " + super.getPassword()+ "\n"+
                "       Role: " + super.getRole()+
                " Name: " + this.name+
                " , Gender: " + this.gender+
                " , Age: " + this.age + "} \n";
    }
}
import java.util.Scanner;

public class User {
	Scanner sc = new Scanner(System.in);
	
  private String userID;
  private String password;
  private String role;
  private boolean firstLogin;
    
    // Constructor
	public User(String userID, String password, String role) {
		this.userID = userID;
    this.password = password;
    this.role = role;
    this.firstLogin = true;
  }

    // Login method using .equals for string comparison
  public boolean login(String user, String pass) {
    if (!user.equals(userID) || !pass.equals(password)) return false;
    	
  	String newPass;
  	String confirmPass;
    	
   	if (firstLogin) { // forces change password during first login
			System.out.println("Please change your password. ");
    	do {
  			System.out.println("Enter new password: ");
  			newPass = sc.nextLine();
				System.out.println("Confirm new password: ");
    		confirmPass = sc.nextLine();
    			if (!newPass.equals(confirmPass)) System.out.println("Passwords does not match! Please enter again.");
  		} while (!newPass.equals(confirmPass));
  		changePassword("password", newPass);
			firstLogin = false; 
    }    	
  	return true;
  }

    // Password change method using .equals for string comparison
  public boolean changePassword(String oldPass, String newPass) {
		if (password.equals(oldPass)) {
			password = newPass;
      System.out.println("Password changed!");
			return true;
      }
      return false;
  }


  public String getUserID() {
		return userID;
  }
  public String getPassword() {
		return password;
  }
  public String getRole() {
      return role;
  }
  public void setRole(String newrole) {
	  role = newrole;
	  return;
  }
}

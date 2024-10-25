public class User {
    private String userID;
    private String password;
    private String role;
    
    // Constructor
    public User(String userID, String password, String role) {
        this.userID = userID;
        this.password = password;
        this.role = role;
    }

    // Login method using .equals for string comparison
    public boolean login(String user, String pass) {
        return userID.equals(user) && password.equals(pass);
    }

    // Password change method using .equals for string comparison
    public boolean changePassword(String oldPass, String newPass) {
        if (password.equals(oldPass)) {
            password = newPass;
            return true;
        }
        return false;
    }

    // Get role method
    public String getRole() {
        return role;
    }
}

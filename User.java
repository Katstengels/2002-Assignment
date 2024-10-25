public class User {
    private String userID;
    private String password;
    private String role;
    
    public boolean login(String user, String pass) {
        if (userID == user && password == pass){
            return true;
        }
        else return false;
    };
    
    public boolean changePassword(String oldPass, String newPass){
        if (password == oldPass) {
            password = newPass;
            return true;
        }
        else return false;
    }
    
    public String getRole() {
    	return role;
    }
    
}

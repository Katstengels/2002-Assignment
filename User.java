
public class User {
    protected String userID;
    private String password;
    protected String name;
    protected String role;
    
    public boolean login(String user, String pass) {
        if (userID == user && password == pass){
            return True;
        }
        else return False;
    };
    
    public boolean changePassword(String oldPass, String newPass){
        if (password == oldPass) {
            password = newPass;
            return True;
        }
        else return False;
    }
    
}

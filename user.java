
public class user {
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
    
    public boolean changePassword(String oldpass, String newpass){
        if (password == oldpass) {
            password = newpass;
            return True;
        }
        else return False;
    }
    
}

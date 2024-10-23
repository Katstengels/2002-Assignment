
public class User {
    protected String UserID;
    private String Password;
    protected String Name;
    protected String Role;
    
    public boolean login(String User, String Pass) {
        if (UserID == User && Password == Pass){
            return True;
        }
        else return False;
    };
    
    public boolean changePassword(String OldPass, String NewPass){
        if (Password == OldPass) {
            Password = NewPass;
            return True;
        }
        else return False;
    }
    
}

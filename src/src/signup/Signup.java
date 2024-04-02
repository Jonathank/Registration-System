package signup;

public class Signup {

    private String username;
    private String password;
    private String confirmp;
    
    
    public Signup(String user,String pass,String confirm) {
    	this.username = user;
    	this.password = pass;
    	this.confirmp = confirm;
    	
    }
    
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmp() {
		return confirmp;
	}
	public void setConfirmp(String confirmp) {
		this.confirmp = confirmp;
	}
    

}

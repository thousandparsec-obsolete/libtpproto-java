/**
 * 
 * This is class should create a login frame.
 * @author Ishwor Gurung
 *
 */
public class LoginFrame {

	/*
	 * User name and password
	 */
	//private String username,password;
	
	private UserData user;
	private Buffer	 buf;
	private int type;
	
	public LoginFrame(String username, String password) {
		user = new UserData();
		if (username == null || password == null) {
			
			
			user.setUsername("demo"); //login with demo username
			user.setPassword("demo"); //login with demo password
			
		}
		
		user.setUsername(username);
		user.setPassword(password);
		buf = new Buffer();
	}	

	public void packUserNameAndPassword() {
		buf.packString(user.getUsername());
		buf.packString(user.getPassword());
		
		type = FrameType.FT.ft02_Login;
	}
	public boolean unpackBuffer(Buffer buf){
	    // should never receive a login frame
	    return false;
	  }
	
	//TODO
	//Do we need a factory method ?	
	/**
	 * Factory method to return new loginFrame
	 * @return return new login frame object set to default value
	 *//*
	public LoginFrame getLoginFrameFactory() {
		return new LoginFrame(null,null);
	}*/
	
	/** 
	 * Return buffer contents as string
	 * 
	 */
	public String toString() {
		return buf.getData();
	}

	
}
 

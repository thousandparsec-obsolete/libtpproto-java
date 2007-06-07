

//TODO
//Streamline other classes that need user info like username,
//password,email and comment to use this class in their implementation

public class UserData extends java.lang.Object {

	private String 	username,
					password,
					email,
					comment;
	
	public UserData() {}
	
	//set username
	public void setUsername(String username) {
		this.username = username;
	}
	
	//set password
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	public String getEmail() {
		// TODO Auto-generated method stub
		return this.email;
	}
	
	public String getComment() {
		// TODO Auto-generated method stub
		return this.comment;
	}
	
}

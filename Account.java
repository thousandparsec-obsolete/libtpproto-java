

public class Account extends Frame{

	private String username;
	private String password;
	private String comment;
	private String email;
	private int type; //frame type

	
	/** 
	 * Assign username, password, comment, email 
	 * to create and account in TP server.
	 * @param username User name
	 * @param password Password 
	 * @param comment
	 * @param email
	 * @throws Exception 
	 */
	public Account(String username, String password, String comment, String email)  {//throws Exception {
		if (username != null && password != null && email != null) { // comment CAN be null ?
			this.username = username;		
			this.password = password;
			this.comment  = comment;
			this.email    = email;
		}
		else {
			/*try {*/
			throw new NullPointerException("Username or Password or email is empty! \n Quitting!");
			/*}
			catch (Exception ex) {
				ex.printStackTrace();
			}*/
			
		}
	}
	/**
	 * Set user
	 */
	public void setUser(String username) {
		this.username = username;
	}
	
	/**
	 * Set password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Get get user name
	 * @return user name
	 */
	public String getUser() {
		return this.username;
	}
	
	/**
	 * Get user password
	 */
	public String getPassword() {
		return this.password;
	}
	/**
	 * Set email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Get email
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set email
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * Get email
	 */
	public String getComment() {
		return this.comment;
	}
	
	/**
	 * pack buf into this account
	 * (create an account)
	 * @param buf
	 */	
	public void packBuffer(Buffer buf) {
		buf.packString(this.username);
		buf.packString(this.password);
		buf.packString(this.email);
		buf.packString(this.comment);	
		//set type of this fram to Account create frame
		type = FrameType.FT.ft03_Account_Create;
	}
	
	
	public boolean unpackBuffer(Buffer buf){
	    // should never receive a login frame
	    return false;
	  }
	public void buildFrame(String frameHeader, int sequenceNumber, FrameType frameType, int frameLength, Buffer frameData) {
		// TODO Auto-generated method stub
		
	}
	public Buffer getFrameData() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getFrameLength() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getFrameType() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getProtocolVersion() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getSequenceNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setFrameData(Buffer data) {
		// TODO Auto-generated method stub
		
	}
	public void setFrameHeader(String frameHeader) {
		// TODO Auto-generated method stub
		
	}
	public void setFrameLength(int frameLength) {
		// TODO Auto-generated method stub
		
	}
	public void setFrameType(int frameType) {
		// TODO Auto-generated method stub
		
	}
	public void setProtocolVersion(int ver) {
		
		this.protocolVersion = ver;
		
	}
	public void setSequenceNumber(int sequenceNumber) {
		// TODO Auto-generated method stub
		
	}

}

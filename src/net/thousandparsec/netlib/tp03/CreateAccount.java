package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Create Account
 */
public class CreateAccount extends Request
{
	public static final int FRAME_TYPE=62;

	protected CreateAccount(int id)
	{
		super(id);
	}

	public CreateAccount()
	{
		super(FRAME_TYPE);
	}

	/**
	 * The username to create the account of.
	 */
	private String username=new String();

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String value)
	{
		this.username=value;
	}

	/**
	 * The password to get for the username.
	 */
	private String password=new String();

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String value)
	{
		this.password=value;
	}

	/**
	 * The email address of the player.
	 */
	private String email=new String();

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String value)
	{
		this.email=value;
	}

	/**
	 * A comment for the account.
	 */
	private String comment=new String();

	public String getComment()
	{
		return this.comment;
	}

	public void setComment(String value)
	{
		this.comment=value;
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in CreateAccount.java");
            visit((TP03Visitor)visitor);
        }
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	public int findByteLength()
	{
		return super.findByteLength()
			 + findByteLength(this.username)
			 + findByteLength(this.password)
			 + findByteLength(this.email)
			 + findByteLength(this.comment);
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeString(this.username);
		out.writeString(this.password);
		out.writeString(this.email);
		out.writeString(this.comment);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	CreateAccount(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.username=in.readString();
		this.password=in.readString();
		this.email=in.readString();
		this.comment=in.readString();
	}


	public String toString()
	{
		return "{CreateAccount"
                    +"; username: "
                    +String.valueOf(this.username)
                    +"; password: "
                    +String.valueOf(this.password)
                    +"; email: "
                    +String.valueOf(this.email)
                    +"; comment: "
                    +String.valueOf(this.comment)
                    +"; super:"
                    +super.toString()
                    +"}";
		
	}

}
package net.thousandparsec.netlib.tp04;

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

	@Override
	public void visit(TP04Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + findByteLength(this.username)
			 + findByteLength(this.password)
			 + findByteLength(this.email)
			 + findByteLength(this.comment);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
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

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{CreateAccount");
		buf.append("; username: ");
		buf.append(String.valueOf(this.username));
		buf.append("; password: ");
		buf.append(String.valueOf(this.password));
		buf.append("; email: ");
		buf.append(String.valueOf(this.email));
		buf.append("; comment: ");
		buf.append(String.valueOf(this.comment));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}

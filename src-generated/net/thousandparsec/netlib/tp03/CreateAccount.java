package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Create Account
 */
public class CreateAccount extends Request
{
	protected CreateAccount(int id)
	{
		super(id);
	}

	public CreateAccount()
	{
		super(62);
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
	public void visit(TP03Visitor visitor)
	{
		visitor.handle(this);
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
	public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
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
	@SuppressWarnings("unused")
	CreateAccount(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.username=in.readString();
		this.password=in.readString();
		this.email=in.readString();
		this.comment=in.readString();
	}

}

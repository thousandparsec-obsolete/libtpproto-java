package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Login
 */
public class Login extends Request
{
	protected Login(int id)
	{
		super(id);
	}

	public Login()
	{
		super(4);
	}

	/**
	 * The username to login with.
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
	 * The password for the username.
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

	@Override
	public void visit(TP03Visitor visitor)
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + findByteLength(this.username)
			 + findByteLength(this.password);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeString(this.username);
		out.writeString(this.password);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Login(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.username=in.readString();
		this.password=in.readString();
	}

}

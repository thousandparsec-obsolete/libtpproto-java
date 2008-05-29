package net.thousandparsec.netlib;

public class TPException extends Exception
{
	public TPException()
	{
		super();
	}

	public TPException(String message)
	{
		super(message);
	}

	public TPException(Throwable cause)
	{
		super(cause);
	}

	public TPException(String message, Throwable cause)
	{
		super(message, cause);
	}
}

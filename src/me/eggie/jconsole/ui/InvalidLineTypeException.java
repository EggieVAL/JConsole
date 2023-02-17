package me.eggie.jconsole.ui;

@SuppressWarnings("serial")
public class InvalidLineTypeException extends Exception
{
	public static final String DEFAULT_MSG = "Invalid line type.";
	
	public InvalidLineTypeException(String msg)
	{
		super(msg);
	}
	
	public InvalidLineTypeException()
	{
		super();
	}
}

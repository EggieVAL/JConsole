package me.eggie.jconsole.ui;

public class JLine
{
	public static final int COMMAND = 0x00;
	public static final int PRINT = 0x01;
	
	public int id;
	public String contents;
	
	public JLine(int id, String contents) throws InvalidLineTypeException
	{
		if (id != JLine.COMMAND && id != JLine.PRINT)
		{
			throw new InvalidLineTypeException();
		}
		this.id = id;
		this.contents = contents;
	}
	
	public JLine(int id) throws InvalidLineTypeException
	{
		this(id, "");
	}
	
	public JLine(String contents) throws InvalidLineTypeException
	{
		this(JLine.PRINT, contents);
	}
	
	public JLine() throws InvalidLineTypeException
	{
		this("");
	}
	
	@Override
	public String toString()
	{
		return this.contents;
	}
}

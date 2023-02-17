package me.eggie.jconsole.ui;

import java.util.ArrayList;
import java.util.List;

import me.eggie.jconsole.Utilities;

public class JLogDocument
{
	public static final int MAX_LOG_CAPACITY = 25000;
	public static final int MIN_LOG_CAPACITY = 1000;
	
	public JLogDocument(int logCapacity)
	{
		this.log = new ArrayList<JLine>();
		this.setLogCapacity(logCapacity);
	}
	
	public JLogDocument()
	{
		this(JLogDocument.MIN_LOG_CAPACITY);
	}
	
	public List<JLine> add(JLine line)
	{
		List<JLine> removed = new ArrayList<JLine>();
		if (line != null)
		{
			while (this.log.size() >= this.logCapacity)
			{
				removed.add(this.log.remove(0));
			}
			this.log.add(line);
		}
		return removed;
	}
	
	public List<JLine> add(int id, String contents) throws InvalidLineTypeException
	{
		return this.add(new JLine(id, contents));
	}
	
	public void clearLog()
	{
		this.log.clear();
	}
	
	public List<JLine> getLog()
	{
		return Utilities.copyOf(log);
	}
	
	public int getLogCapacity()
	{
		return this.logCapacity;
	}
	
	public void setLogCapacity(int logCapacity)
	{
		this.logCapacity = (logCapacity > JLogDocument.MAX_LOG_CAPACITY) ?
				JLogDocument.MAX_LOG_CAPACITY : logCapacity;
		this.logCapacity = (logCapacity < JLogDocument.MIN_LOG_CAPACITY) ?
				JLogDocument.MIN_LOG_CAPACITY : logCapacity;
	}
	
	private List<JLine> log;
	
	private int logCapacity;
}

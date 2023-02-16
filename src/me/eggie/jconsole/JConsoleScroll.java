package me.eggie.jconsole;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class JConsoleScroll extends JScrollPane
{
	public JConsoleScroll(JConsole console, JConsoleLog log)
	{
		super(log);
		this.console = console;		
		this.log = log;
		
		this.console.add(this);
		this.setPreferredSize(this.console.getSize());
		this.setBorder(null);
	}
	
	private JConsole console;
	
	private JConsoleLog log;
}

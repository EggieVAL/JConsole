package me.eggie.jconsole;

import java.awt.Dimension;

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
		this.setBorder(null);
		this.setPreferredSize(this.console.getSize());
		this.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
	}
	
	private JConsole console;
	
	private JConsoleLog log;
}

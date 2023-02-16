package me.eggie.jconsole;

import java.awt.Insets;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class JConsoleLog extends JTextArea
{
	public JConsoleLog(JConsole console)
	{
		super();
		this.console = console;
		
		final Insets MARGIN = new Insets(8, 8, 8, 8);
		
		this.setMargin(MARGIN);
		this.setPreferredSize(this.console.getSize());
		this.setLineWrap(true);
	}
	
	private JConsole console;
}

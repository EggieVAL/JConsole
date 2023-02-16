package me.eggie.jconsole;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JConsolePanel extends JPanel
{
	public JConsolePanel(JConsole console)
	{
		super();
		this.console = console;
		this.console.setContentPane(this);
		
		this.setBorder(null);
	}
	
	private JConsole console;
}

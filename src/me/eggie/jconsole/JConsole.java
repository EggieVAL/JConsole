package me.eggie.jconsole;

import java.awt.BorderLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class JConsole extends JFrame
{
	public static JConsole Instance = null;
	
	public static void load()
	{
		JConsole.Instance = new JConsole();
	}
	
	public JConsole()
	{
		super("Console");
		
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.contentPane = new JConsolePanel(this);
		this.log = new JConsoleLog(this);
		this.scrollPane = new JConsoleScroll(this, this.log);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private JConsolePanel contentPane;
	
	private JConsoleLog log;
	
	private JConsoleScroll scrollPane;
}

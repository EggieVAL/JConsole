package me.eggie.jconsole;

import java.awt.BorderLayout;
import java.awt.Dimension;

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
		
		this.setPreferredSize(new Dimension(700, 500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}

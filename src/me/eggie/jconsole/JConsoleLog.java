package me.eggie.jconsole;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class JConsoleLog extends JTextArea
{
	public JConsoleLog(JConsole console)
	{
		super();
		this.console = console;
		
		final Color BACKGROUND = this.console.getInnerColor();
		final Insets MARGIN = new Insets(8, 8, 8, 8);
		final Font FONT = this.console.getFont();
		final Color FONT_COLOR = this.console.getFontColor();
		
		this.setBackground(BACKGROUND);
		this.setMargin(MARGIN);
		this.setFont(FONT);
		this.setForeground(FONT_COLOR);
		this.setLineWrap(true);
	}
	
	private JConsole console;
}

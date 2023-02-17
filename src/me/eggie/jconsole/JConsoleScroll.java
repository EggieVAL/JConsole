package me.eggie.jconsole;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

@SuppressWarnings("serial")
public class JConsoleScroll extends JScrollPane
{
	public JConsoleScroll(JConsole console, JConsoleLog log)
	{
		super(log);
		this.console = console;		
		this.log = log;
		
		this.console.add(this);
		this.log.setContainer(this);
		
		this.setBorder(null);
		this.setPreferredSize(this.console.getSize());
		this.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
	}
	
	protected void shiftViewport()
	{
		int height = this.log.getPreferredScrollableViewportSize().height;
		JViewport view = this.getViewport();
		
		if (height > this.getHeight())
		{
			height += this.log.getMargin().bottom;
			view.setViewPosition(new Point(0, height));
		}
	}
	
	private JConsole console;
	
	private JConsoleLog log;
}

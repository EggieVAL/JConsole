package me.eggie.jconsole;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

@SuppressWarnings("serial")
public class JConsoleScrollPane extends JScrollPane
{
	public JConsoleScrollPane(JConsole console, JConsoleLog log)
	{
		super(log);
		this.console = console;		
		this.log = log;
		
		this.console.add(this);
		this.log.setContainer(this);
		
		this.setBorder(null);
		this.setPreferredSize(this.console.getSize());
		this.setScrollBarWidth(10);
		
		this.changeDefaultLook(this.console.getInnerColor(), this.console.getOuterColor());
	}
	
	public void setScrollBarWidth(int width)
	{
		if (width >= 0)
		{
			this.getVerticalScrollBar().setPreferredSize(new Dimension(width, 0));
		}
	}
	
	public void changeDefaultLook(Color inner, Color outer, int arcWidth, int arcHeight)
	{
		JScrollBar vertical = this.getVerticalScrollBar();
		vertical.setUI(new JConsoleScrollBar(outer, arcWidth, arcHeight));
		vertical.setBackground(inner);
	}
	
	public void changeDefaultLook(Color inner, Color outer)
	{
		JScrollBar vertical = this.getVerticalScrollBar();
		vertical.setUI(new JConsoleScrollBar(outer));
		vertical.setBackground(inner);
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

package me.eggie.jconsole;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * A {@code JConsoleScrollPane} is a container for {@code JConsoleLog}. It allows the user
 * to scroll through the log.
 * @author Eggie
 */
@SuppressWarnings("serial")
public class JConsoleScrollPane extends JScrollPane
{
	/**
	 * Creates a scroll pane for a {@code JConsoleLog}, and adding a scroll pane to a
	 * {@code JConsole}.
	 * @param console   the container of this {@code JConsoleScrollPane}.
	 * @param log       the {@code JConsoleLog} that will be added to this scroll pane.
	 */
	public JConsoleScrollPane(JConsole console, JConsoleLog log)
	{
		super(log);
		this.console = console;		
		this.log = log;
		
		this.console.add(this);
		this.log.container = this;
		
		this.setBorder(null);
		this.setPreferredSize(this.console.getSize());
		this.setScrollBarWidth(10);
		
		this.changeDefaultLook(this.console.getInnerColor(), this.console.getOuterColor());
	}
	
	/**
	 * Changes the default look of a {@code JConsoleScrollPane}.
	 * @param inner       the inner layer color.
	 * @param outer       the outer layer color.
	 * @param arcWidth    the arc width.
	 * @param arcHeight   the arc height.
	 */
	public void changeDefaultLook(Color inner, Color outer, int arcWidth, int arcHeight)
	{
		JScrollBar vertical = this.getVerticalScrollBar();
		vertical.setUI(new JConsoleScrollBar(outer, arcWidth, arcHeight));
		vertical.setBackground(inner);
	}
	
	/**
	 * Changes the default look of a {@code JConsoleScrollPane}.
	 * @param inner   the inner layer color.
	 * @param outer   the outer layer color.
	 */
	public void changeDefaultLook(Color inner, Color outer)
	{
		JScrollBar vertical = this.getVerticalScrollBar();
		vertical.setUI(new JConsoleScrollBar(outer));
		vertical.setBackground(inner);
	}
	
	/**
	 * Sets the vertical scroll bar width.
	 * @param width   an integer.
	 */
	public void setScrollBarWidth(int width)
	{
		if (width >= 0)
		{
			this.getVerticalScrollBar().setPreferredSize(new Dimension(width, 0));
		}
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

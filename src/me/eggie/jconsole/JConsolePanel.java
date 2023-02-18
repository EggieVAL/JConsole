package me.eggie.jconsole;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * A {@code JConsolePanel} is a content pane for a {@code JConsole}. This is a container
 * for a {@code JConsoleScrollPane} and a {@code JConsoleLog}.
 * @author Eggie
 */
@SuppressWarnings("serial")
public class JConsolePanel extends JPanel
{
	/**
	 * Creates a content pane for a {@code JConsole}.
	 * @param console   the container of this {@code JConsolePanel}
	 */
	public JConsolePanel(JConsole console)
	{
		super();
		this.renderTarget = null;
		this.console = console;
		this.console.setContentPane(this);
		this.changeDefaultLook(this.console.getOuterColor());
	}
	
	public void changeDefaultLook(Color outer)
	{
		final Color COLOR = outer;
		final Insets THICKNESS = new Insets(28, 16, 16, 16);
		final float INNER_ARC = 15f;
		final float OUTER_ARC = 30f;
		
		JRoundedBorder border = new JRoundedBorder(COLOR, THICKNESS, INNER_ARC, OUTER_ARC);
		this.setBorder(border);
	}
	
	/**
	 * Changes how a {@code JConsolePanel} is painted onto the screen:
	 * The render target is slightly smaller than the panel's actual
	 * size to make sure the background doesn't protrude out of the
	 * {@code JRoundedBorder}.
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		if (g instanceof Graphics2D)
		{
			final int WIDTH = this.getWidth();
			final int HEIGHT = this.getHeight();
			
			if (this.renderTarget == null)
			{
				Insets thickness = this.getBorder().getBorderInsets(this);
				int x = thickness.left;
				int y = thickness.top;
				int width = WIDTH - x - thickness.right;
				int height = HEIGHT - y - thickness.bottom;
				this.renderTarget = new Rectangle(x, y, width, height);
			}
			
			Graphics2D g2d = (Graphics2D) g;
			Paint paint = this.console.getInnerColor();
			g2d.setPaint(paint);
			g2d.fill(this.renderTarget);
		}
	}
	
	private Rectangle renderTarget;
	
	private JConsole console;
}

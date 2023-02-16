package me.eggie.jconsole;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JConsolePanel extends JPanel
{
	public JConsolePanel(JConsole console)
	{
		super();
		this.renderTarget = null;
		this.console = console;
		this.console.setContentPane(this);
		
		final Color COLOR = this.console.getOuterColor();
		final Insets THICKNESS = new Insets(28, 16, 16, 16);
		final float INNER_ARC = 15f;
		final float OUTER_ARC = 30f;
		
		JRoundedBorder border = new JRoundedBorder(COLOR, THICKNESS, INNER_ARC, OUTER_ARC);
		this.setBorder(border);
	}
	
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

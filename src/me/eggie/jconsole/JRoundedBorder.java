package me.eggie.jconsole;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class JRoundedBorder extends AbstractBorder
{
	public JRoundedBorder(final Color c, final Insets thickness,
			final float innerArc, final float outerArc)
	{
		this.color = c;
		this.thickness = thickness;
		this.innerArc = innerArc;
		this.outerArc = outerArc;
	}
	
	@Override
	public void paintBorder(final Component c, final Graphics g,
							final int x, final int y,
							final int width, final int height)
	{
		if (!(g instanceof Graphics2D))
		{
			return;
		}
		
		Graphics2D g2d = (Graphics2D) g;
		
		final Color oldColor = g2d.getColor();
		g2d.setColor(this.color);
		
		final Shape outer;
		final Shape inner;
		
		final int top = this.thickness.top;
		final int left = this.thickness.left;
		final int bot = this.thickness.bottom;
		final int right = this.thickness.right;
		
		final int west = x + left;
		final int north = y + top;
		final int east = width - left - right;
		final int south = height - top - bot;
		
		outer = new RoundRectangle2D.Float(x, y, width, height, this.outerArc, this.outerArc);
		inner = new RoundRectangle2D.Float(west, north, east, south, this.innerArc, this.innerArc);
		
		Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
		path.append(outer, true);
		path.append(inner, true);
		
		g2d.fill(path);
		g2d.setColor(oldColor);
	}
	
	@Override
	public Insets getBorderInsets(final Component c)
	{
		return this.thickness;
	}
	
	private Color color;
	
	private Insets thickness;
	
	private float innerArc;
	private float outerArc;
}

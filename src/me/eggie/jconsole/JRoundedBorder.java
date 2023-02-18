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

/**
 * A {@code JRoundedBorder} is a rounded border.
 * @author Eggie
 */
@SuppressWarnings("serial")
public class JRoundedBorder extends AbstractBorder
{
	/**
	 * Creates a rounded border.
	 * @param c           the color of the border.
	 * @param thickness   the thickness of the border.
	 * @param innerArc    the inner curve of the border.
	 * @param outerArc    the outer curve of the border.
	 */
	public JRoundedBorder(Color c, Insets thickness, float innerArc, float outerArc)
	{
		this.color = c;
		this.thickness = thickness;
		this.innerArc = innerArc;
		this.outerArc = outerArc;
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	{
		if (!(g instanceof Graphics2D))
		{
			return;
		}
		
		Graphics2D g2d = (Graphics2D) g;
		
		Color oldColor = g2d.getColor();
		g2d.setColor(this.color);
		
		Shape outer;
		Shape inner;
		
		int top = this.thickness.top;
		int left = this.thickness.left;
		int bot = this.thickness.bottom;
		int right = this.thickness.right;
		
		int west = x + left;
		int north = y + top;
		int east = width - left - right;
		int south = height - top - bot;
		
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

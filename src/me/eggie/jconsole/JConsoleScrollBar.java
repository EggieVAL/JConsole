package me.eggie.jconsole;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * A customized scroll bar tailored for a {@code JConsoleScrollPane}.
 * @author Eggie
 */
public class JConsoleScrollBar extends BasicScrollBarUI
{
	/**
	 * Creates a rounded scroll bar for a {@code JConsoleScrollPane}.
	 * @param color       the color of the scroll bar.
	 * @param arcWidth    the arc of the scroll bar in terms of width.
	 * @param arcHeight   the arc of the scroll bar in terms of height.
	 */
	public JConsoleScrollBar(Color color, int arcWidth, int arcHeight)
	{
		this.color = color;
		this.arcWidth = (arcWidth >= 0) ? arcWidth : 0;
		this.arcHeight = (arcHeight >= 0) ? arcHeight : 0;
	}
	
	/**
	 * Creates the defaulted rounded scroll bar for a {@code JConsoleScrollPane}.
	 * @param color   the color of the scroll bar.
	 */
	public JConsoleScrollBar(Color color)
	{
		this(color, 15, 6);
	}
	
	/**
	 * Get the color of the scroll bar.
	 * @return a {@code Color}.
	 */
	public Color getColor()
	{
		return this.color;
	}
	
	/**
	 * Get the arc width of the scroll bar.
	 * @return an integer.
	 */
	public int getArcWidth()
	{
		return this.arcWidth;
	}
	
	/**
	 * Get the arc height of the scroll bar.
	 * @return an integer.
	 */
	public int getArcHeight()
	{
		return this.arcHeight;
	}
	
	/**
	 * Set the color of the scroll bar.
	 * @param color   a {@code Color}.
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	/**
	 * Set the arc width of the scroll bar.
	 * @param arcWidth   an integer.
	 */
	public void setArcWidth(int arcWidth)
	{
		if (arcWidth >= 0)
		{
			this.arcWidth = arcWidth;
		}
	}
	
	/**
	 * Set the arc height of the scroll bar.
	 * @param arcHeight   an integer.
	 */
	public void setArcHeight(int arcHeight)
	{
		if (arcHeight >= 0)
		{
			this.arcHeight = arcHeight;
		}
	}
	
	@Override
	protected void configureScrollBarColors()
	{
		this.thumbColor = color;
	}
	
	@Override
	protected JButton createDecreaseButton(int orientation)
	{
		return this.zeroSizedButton();
	}
	
	@Override
	protected JButton createIncreaseButton(int orientation)
	{
		return this.zeroSizedButton();
	}
	
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
    {
        if(thumbBounds.isEmpty() || !this.scrollbar.isEnabled())
        {
            return;
        }
        
        int w = thumbBounds.width;
        int h = thumbBounds.height;
        
        g.translate(thumbBounds.x, thumbBounds.y);
        
        g.setColor(this.thumbDarkShadowColor);
        drawRect(g, 0, 0, w-1,h -1);
        g.setColor(this.thumbColor);
        g.fillRoundRect(0, 0, w-2, h-2, this.arcWidth, this.arcHeight);
        
        g.setColor(this.thumbHighlightColor);
        drawVLine(g, 1, 1, h-2);
        drawHLine(g, 2, w-3, 1);
        
        g.setColor(this.thumbLightShadowColor);
        drawHLine(g, 2, w-2, h-2);
        drawVLine(g, w-2, 1, h-3);
        
        g.translate(-thumbBounds.x, -thumbBounds.y);
    }
	
    private void drawRect(Graphics g, int x, int y, int w, int h)
    {
        if (w < 0 || h < 0)
        {
            return;
        }

        if (h == 0 || w == 0)
        {
            g.fillRoundRect(x, y, w+1, h+1, this.arcWidth, this.arcHeight);
        }
        else
        {
            g.fillRoundRect(x, y, w, 1, this.arcWidth, this.arcHeight);
            g.fillRoundRect(x+w, y, 1, h, this.arcWidth, this.arcHeight);
            g.fillRoundRect(x+1, y+h, w, 1, this.arcWidth, this.arcHeight);
            g.fillRoundRect(x, y+1, 1, h, this.arcWidth, this.arcHeight);
        }
    }
    
    private void drawVLine(Graphics g, int x, int y1, int y2)
    {
        if (y2 < y1)
        {
            final int temp = y2;
            y2 = y1;
            y1 = temp;
        }
        g.fillRoundRect(x, y1, 1, y2-y1+1, this.arcWidth, this.arcHeight);
    }
    
    private void drawHLine(Graphics g, int x1, int x2, int y)
    {
        if (x2 < x1)
        {
            final int temp = x2;
            x2 = x1;
            x1 = temp;
        }
        g.fillRoundRect(x1, y, x2-x1+1, 1, this.arcWidth, this.arcHeight);
    }
	
	@SuppressWarnings("serial")
	private JButton zeroSizedButton()
	{
		return new JButton()
		{
			@Override
			public Dimension getPreferredSize()
			{
				return new Dimension();
			}
		};
	}
	
	private Color color;
	
	private int arcWidth;
	private int arcHeight;
}

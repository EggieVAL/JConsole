package me.eggie.jconsole;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class JConsoleScrollBar extends BasicScrollBarUI
{
	public JConsoleScrollBar(Color color, int arcWidth, int arcHeight)
	{
		this.color = color;
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
	}
	
	public JConsoleScrollBar(Color color)
	{
		this(color, 20, 8);
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

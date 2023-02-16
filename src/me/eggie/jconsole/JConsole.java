package me.eggie.jconsole;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class JConsole extends JFrame
{
	public static JConsole Instance = null;
	
	public static final Color DARK_MODE_OUTER = new Color(24, 24, 24);
	public static final Color DARK_MODE_INNER = new Color(29, 29, 29);
	public static final Color DARK_MODE_FONT_COLOR = new Color(120, 120, 120);
	
	public static final Color LIGHT_MODE_OUTER = new Color(234, 235, 240);
	public static final Color LIGHT_MODE_INNER = new Color(247, 248, 250);
	public static final Color LIGHT_MODE_FONT_COLOR = new Color(145, 146, 165);
	
	public static final Font FONT = new Font("Cascadia Code", Font.PLAIN, 16);
	
	public static void load(boolean lightMode)
	{
		JConsole.Instance = new JConsole(lightMode);
	}
	
	public static void load()
	{
		JConsole.Instance = new JConsole();
	}
	
	public JConsole(boolean lightMode)
	{
		super("Console");
		this.lightMode(lightMode);
		this.font = JConsole.FONT;
		
		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 0));
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.contentPane = new JConsolePanel(this);
		this.log = new JConsoleLog(this);
		this.scrollPane = new JConsoleScroll(this, this.log);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public JConsole()
	{
		this(false);
	}
	
	public Color getOuterColor()
	{
		return this.outer;
	}
	
	public Color getInnerColor()
	{
		return this.inner;
	}
	
	public Color getFontColor()
	{
		return this.fontColor;
	}
	
	public Font getFont()
	{
		return this.font;
	}
	
	public void setOuterColor(Color color)
	{
		if (color.getAlpha() == 0)
		{
			return;
		}
		this.outer = color;
	}
	
	public void setInnerColor(Color color)
	{
		if (color.getAlpha() == 0)
		{
			return;
		}
		this.inner = color;
	}
	
	public void setFontColor(Color color)
	{
		if (color.getAlpha() == 0)
		{
			return;
		}
		this.fontColor = color;
	}
	
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	public void lightMode(boolean lightMode)
	{
		this.outer = (lightMode) ? LIGHT_MODE_OUTER : DARK_MODE_OUTER;
		this.inner = (lightMode) ? LIGHT_MODE_INNER : DARK_MODE_INNER;
		this.fontColor = (lightMode) ? LIGHT_MODE_FONT_COLOR : DARK_MODE_FONT_COLOR;
	}
	
	private JConsolePanel contentPane;
	private JConsoleLog log;
	private JConsoleScroll scrollPane;
	
	private Color outer;
	private Color inner;
	private Color fontColor;
	
	private Font font;
}

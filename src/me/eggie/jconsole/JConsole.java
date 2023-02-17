package me.eggie.jconsole;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import me.eggie.jconsole.cmd.Clear;
import me.eggie.jconsole.cmd.Command;
import me.eggie.jconsole.cmd.Help;
import me.eggie.jconsole.cmd.Quit;
import me.eggie.jconsole.ui.InvalidLineTypeException;
import me.eggie.jconsole.ui.JLine;

@SuppressWarnings("serial")
public class JConsole extends JFrame implements MouseListener, MouseMotionListener
{
	public static final Color DARK_MODE_OUTER = new Color(24, 24, 24);
	public static final Color DARK_MODE_INNER = new Color(29, 29, 29);
	public static final Color DARK_MODE_FONT_COLOR = new Color(120, 120, 120);
	
	public static final Color LIGHT_MODE_OUTER = new Color(234, 235, 240);
	public static final Color LIGHT_MODE_INNER = new Color(247, 248, 250);
	public static final Color LIGHT_MODE_FONT_COLOR = new Color(145, 146, 165);
	
	public static final Font FONT = new Font("Cascadia Code", Font.PLAIN, 16);
	
	public JConsole(boolean lightMode)
	{
		super("Console");
		this.lightMode(lightMode);
		this.font = JConsole.FONT;
		this.entry = ": ";
		this.tab = " >> ";
		this.user = System.getProperty("user.name");
		this.buttonsPressed = new HashSet<Integer>();
		this.prevPoint = null;
		
		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 0));
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		new JConsolePanel(this);
		this.log = new JConsoleLog(this);
		this.scrollPane = new JConsoleScrollPane(this, this.log);
		
		this.log.add(new Clear(this, "clear"));
		this.log.add(new Help(this, "help"));
		this.log.add(new Quit(this, "q"));
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public JConsole()
	{
		this(false);
	}
	
	public void clear()
	{
		this.log.clear();
	}
	
	public void log()
	{
		this.log("");
	}
	
	public void log(char c)
	{
		this.log(Character.toString(c));
	}
	
	public void log(int i)
	{
		this.log(Integer.toString(i));
	}
	
	public void log(long l)
	{
		this.log(Long.toString(l));
	}
	
	public void log(float f)
	{
		this.log(Float.toString(f));
	}
	
	public void log(double d)
	{
		this.log(Double.toString(d));
	}
	
	public void log(char[] arr)
	{
		this.log(Arrays.toString(arr));
	}
	
	public void log(String str)
	{
		try
		{
			this.log.add(new JLine(str));
		}
		catch (InvalidLineTypeException e)
		{
			e.printStackTrace();
		}
	}
	
	public void log(Object obj)
	{
		this.log(obj.toString());
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
	
	public String getEntry()
	{
		return this.entry;
	}
	
	public String getTab()
	{
		return this.tab;
	}
	
	public String getUser()
	{
		return this.user;
	}
	
	public JConsoleLog getLog()
	{
		return this.log;
	}
	
	public JConsoleScrollPane getScrollPane()
	{
		return this.scrollPane;
	}
	
	public List<Command> getCommands()
	{
		return this.log.getCommands();
	}
	
	public void setOuterColor(Color color)
	{
		if (color.getAlpha() != 0)
		{
			this.outer = color;
		}
	}
	
	public void setInnerColor(Color color)
	{
		if (color.getAlpha() != 0)
		{
			this.inner = color;
		}
	}
	
	public void setFontColor(Color color)
	{
		if (color.getAlpha() != 0)
		{
			this.fontColor = color;
		}
	}
	
	public void setFont(Font font)
	{
		if (font != null)
		{
			this.font = font;
		}
	}
	
	public void setEntry(String entry)
	{
		if (entry != null)
		{
			this.entry = entry;
		}
	}
	
	public void setTab(String tab)
	{
		if (tab != null)
		{
			this.tab = tab;
		}
	}
	
	public void setUser(String user)
	{
		if (user != null)
		{
			this.user = user;			
		}
	}
	
	public void setSpacing(float spacing)
	{
		this.log.setSpacing(spacing);
	}
	
	public void setScrollBarWidth(int width)
	{
		this.scrollPane.setScrollBarWidth(width);
	}
	
	public void lightMode(boolean lightMode)
	{
		this.outer = (lightMode) ? LIGHT_MODE_OUTER : DARK_MODE_OUTER;
		this.inner = (lightMode) ? LIGHT_MODE_INNER : DARK_MODE_INNER;
		this.fontColor = (lightMode) ? LIGHT_MODE_FONT_COLOR : DARK_MODE_FONT_COLOR;
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (this.buttonsPressed.contains(MouseEvent.BUTTON1))
		{
			Point currPoint = e.getLocationOnScreen();
			Point pos = this.getLocation();
			int dx = currPoint.x - this.prevPoint.x;
			int dy = currPoint.y - this.prevPoint.y;
			this.setLocation(pos.x + dx, pos.y + dy);
			this.prevPoint = currPoint;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		this.prevPoint = e.getLocationOnScreen();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) { }
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		this.buttonsPressed.add(e.getButton());
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		this.buttonsPressed.remove(e.getButton());
	}
	
	@Override
	public void mouseEntered(MouseEvent e) { }
	
	@Override
	public void mouseExited(MouseEvent e) { }
	
	private Color outer;
	private Color inner;
	private Color fontColor;
	
	private Font font;
	
	private String entry;
	private String tab;
	private String user;
	
	private JConsoleLog log;
	private JConsoleScrollPane scrollPane;
	
	private Set<Integer> buttonsPressed;
	private Point prevPoint;
}

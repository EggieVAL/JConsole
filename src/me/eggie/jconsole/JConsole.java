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

import me.eggie.jconsole.cmd.Command;
import me.eggie.jconsole.ui.InvalidLineTypeException;
import me.eggie.jconsole.ui.JLine;

/**
 * A {@code JConsole} is an alternative for System.out.println(). It does everything a
 * System.out.println() can do. However, everything is printed in a customizable GUI.
 * You can also add custom commands to a {@code JConsole}, such as a command to
 * configurate a class.
 * 
 * @author Eggie
 */
@SuppressWarnings("serial")
public class JConsole extends JFrame implements MouseListener, MouseMotionListener
{
	/**
	 * The outer layer color in dark mode.
	 */
	public static final Color DARK_MODE_OUTER = new Color(24, 24, 24);
	
	/**
	 * The inner layer color in dark mode.
	 */
	public static final Color DARK_MODE_INNER = new Color(29, 29, 29);
	
	/**
	 * The font color in dark mode.
	 */
	public static final Color DARK_MODE_FONT_COLOR = new Color(120, 120, 120);
	
	/**
	 * The outer layer color in light mode.
	 */
	public static final Color LIGHT_MODE_OUTER = new Color(234, 235, 240);
	
	/**
	 * The inner layer color in light mode.
	 */
	public static final Color LIGHT_MODE_INNER = new Color(247, 248, 250);

	/**
	 * The font color in light mode.
	 */
	public static final Color LIGHT_MODE_FONT_COLOR = new Color(145, 146, 165);
	
	/**
	 * The default font a JConsole uses.
	 */
	public static final Font FONT = new Font("Cascadia Code", Font.PLAIN, 16);
	
	/**
	 * Creates a JConsole in light mode or dark mode.
	 * @param lightMode   the theme of the console.
	 */
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
		
		this.contentPane = new JConsolePanel(this);
		this.log = new JConsoleLog(this);
		this.scrollPane = new JConsoleScrollPane(this, this.log);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Creates a JConsole in dark mode.
	 */
	public JConsole()
	{
		this(false);
	}
	
	/**
	 * Adds/registers a {@code Command} to this console.
	 * @param cmd   a {@code Command}.
	 * @return if the command was successfully added.
	 */
	public boolean add(Command cmd)
	{
		return this.log.add(cmd);
	}

	/**
	 * Changes the default look of a {@code JConsole}.
	 * @param inner       the inner layer color.
	 * @param outer       the outer layer color.
	 * @param fontColor   the font color.
	 * @param font        the font.
	 * @param arcWidth    the arc width.
	 * @param arcHeight   the arc height.
	 */
	public void changeDefaultLook(Color inner, Color outer, Color fontColor, Font font, int arcWidth, int arcHeight)
	{
		this.setInnerColor(inner);
		this.setOuterColor(outer);
		this.contentPane.changeDefaultLook(outer);
		this.log.changeDefaultLook(inner, fontColor, font);
		this.scrollPane.changeDefaultLook(inner, outer, arcWidth, arcHeight);
	}
	
	/**
	 * Changes the default look of a {@code JConsole}.
	 * @param inner       the inner layer color.
	 * @param outer       the outer layer color.
	 * @param fontColor   the font color.
	 * @param font        the font.
	 */
	public void changeDefaultLook(Color inner, Color outer, Color fontColor, Font font)
	{
		this.setInnerColor(inner);
		this.setOuterColor(outer);
		this.contentPane.changeDefaultLook(outer);
		this.log.changeDefaultLook(inner, fontColor, font);
		this.scrollPane.changeDefaultLook(inner, outer);
	}
	
	/**
	 * Clear the console log, including the log history.
	 */
	public void clear()
	{
		this.log.clear();
	}
	
	/**
	 * Get the outer layer color of the console.
	 * @return a {@code Color}.
	 */
	public Color getOuterColor()
	{
		return this.outer;
	}
	
	/**
	 * Get the inner layer color of the console.
	 * @return a {@code Color}.
	 */
	public Color getInnerColor()
	{
		return this.inner;
	}
	
	/**
	 * Get the font color of the console.
	 * @return a {@code Color}.
	 */
	public Color getFontColor()
	{
		return this.fontColor;
	}
	
	/**
	 * Get the font of the console.
	 * @return a {@code Font}.
	 */
	public Font getFont()
	{
		return this.font;
	}
	
	/**
	 * Get the entry of the console. An entry is the {@code String} that
	 * comes after the user {@code String}.
	 * @return a {@code String}.
	 */
	public String getEntry()
	{
		return this.entry;
	}
	
	/**
	 * Get the tab of the console. A tab is the {@code String} that comes
	 * before the printed message via {@code log}.
	 * @return a {@code String}.
	 */
	public String getTab()
	{
		return this.tab;
	}
	
	/**
	 * Get the user of the console. A user is the name the user goes by
	 * under the PC (the name you see when logging onto the computer).
	 * @return
	 */
	public String getUser()
	{
		return this.user;
	}
	
	/**
	 * Get the content pane of the console. Having the content pane
	 * allows for better customization than the ones {@code JConsolePanel}
	 * provides.
	 * @return a {@code JConsolePanel}.
	 */
	public JConsolePanel getConsolePanel()
	{
		return this.contentPane;
	}
	
	/**
	 * Get the log of the console. Having the log allows for better
	 * customization than the ones {@code JConsoleLog} provides.
	 * @return a {@code JConsoleLog}.
	 */
	public JConsoleLog getLog()
	{
		return this.log;
	}
	
	/**
	 * Get the spacing between the lines.
	 * @return a {@code float}.
	 */
	public float getSpacing()
	{
		return this.log.getSpacing();
	}
	
	/**
	 * Get the scroll pane of the console. This is the container of a
	 * {@code JConsoleLog}. Having the scroll pane allows for better
	 * customization than the ones {@code JConsoleScrollPane} provides.
	 * @return a {@code JConsoleScrollPane}.
	 */
	public JConsoleScrollPane getScrollPane()
	{
		return this.scrollPane;
	}
	
	/**
	 * Get all the commands registered on the console.
	 * @return a {@code List<Command>}.
	 */
	public List<Command> getCommands()
	{
		return this.log.getCommands();
	}
	
	/**
	 * The System.out.println() equivalent: It creates a new line
	 * in the console log.
	 */
	public void log()
	{
		this.log("");
	}
	
	/**
	 * Logs the {@code char} in the console.
	 * @param c   the character to be printed.
	 */
	public void log(char c)
	{
		this.log(Character.toString(c));
	}
	
	/**
	 * Logs the {@code int} in the console.
	 * @param i   the integer to be printed.
	 */
	public void log(int i)
	{
		this.log(Integer.toString(i));
	}
	
	/**
	 * Logs the {@code long} in the console.
	 * @param l   the long to be printed.
	 */
	public void log(long l)
	{
		this.log(Long.toString(l));
	}
	
	/**
	 * Logs the {@code float} in the console.
	 * @param f   the float to be printed.
	 */
	public void log(float f)
	{
		this.log(Float.toString(f));
	}
	
	/**
	 * Logs the {@code double} in the console.
	 * @param d   the double to be printed.
	 */
	public void log(double d)
	{
		this.log(Double.toString(d));
	}
	
	/**
	 * Logs the {@code char[]} in the console.
	 * @param arr   the character array to be printed.
	 */
	public void log(char[] arr)
	{
		this.log(Arrays.toString(arr));
	}
	
	/**
	 * Logs the {@code String} in the console.
	 * @param str   the string to be printed.
	 */
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
	
	/**
	 * Logs the {@code Object} in the console.
	 * @param obj   the object to be printed.
	 */
	public void log(Object obj)
	{
		this.log(obj.toString());
	}
	
	/**
	 * Set the outer layer color of the console.
	 * @param color   the outer layer color.
	 */
	public void setOuterColor(Color color)
	{
		if (color.getAlpha() != 0)
		{
			this.outer = color;
			this.contentPane.changeDefaultLook(this.outer);
		}
	}
	
	/**
	 * Set the inner layer color of the console.
	 * @param color   the inner layer color.
	 */
	public void setInnerColor(Color color)
	{
		if (color.getAlpha() != 0)
		{
			this.inner = color;
			this.contentPane.changeDefaultLook(this.outer);
		}
	}
	
	/**
	 * Set the font color of the console.
	 * @param color   the font color.
	 */
	public void setFontColor(Color color)
	{
		if (color.getAlpha() != 0)
		{
			this.fontColor = color;
		}
	}
	
	/**
	 * Set the font of the console.
	 * @param font   the font.
	 */
	public void setFont(Font font)
	{
		if (font != null)
		{
			this.font = font;
		}
	}
	
	/**
	 * Set the entry of the console.
	 * @param entry   the entry string.
	 */
	public void setEntry(String entry)
	{
		if (entry != null)
		{
			this.entry = entry;
		}
	}
	
	/**
	 * Set the tab of the console.
	 * @param tab   the tab string.
	 */
	public void setTab(String tab)
	{
		if (tab != null)
		{
			this.tab = tab;
		}
	}
	
	/**
	 * Set the user of the console.
	 * @param user   the user string.
	 */
	public void setUser(String user)
	{
		if (user != null)
		{
			this.user = user;			
		}
	}
	
	/**
	 * Set the spacing of the console.
	 * @param spacing   the space between the lines.
	 */
	public void setSpacing(float spacing)
	{
		this.log.setSpacing(spacing);
	}
	
	/**
	 * Set the width of the scroll bar
	 * @param width   the width.
	 */
	public void setScrollBarWidth(int width)
	{
		this.scrollPane.setScrollBarWidth(width);
	}
	
	/**
	 * Set the theme of the console.
	 * @param lightMode   the theme.
	 */
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
	
	private JConsolePanel contentPane;
	private JConsoleLog log;
	private JConsoleScrollPane scrollPane;
	
	private Set<Integer> buttonsPressed;
	private Point prevPoint;
}

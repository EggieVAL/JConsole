package me.eggie.jconsole;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextArea;

import me.eggie.jconsole.cmd.Command;
import me.eggie.jconsole.ui.InvalidLineTypeException;
import me.eggie.jconsole.ui.JLine;
import me.eggie.jconsole.ui.JLogDocument;

@SuppressWarnings("serial")
public class JConsoleLog extends JTextArea implements KeyListener
{
	public static final float MIN_SPACING = 1.25f;
	
	public JConsoleLog(JConsole console)
	{
		super();
		this.console = console;
		this.container = null;
		this.document = new JLogDocument();
		this.spacing = JConsoleLog.MIN_SPACING;
		this.keysPressed = new HashSet<Integer>();
		this.commands = new ArrayList<Command>();
		
		final Color BACKGROUND = this.console.getInnerColor();
		final Insets MARGIN = new Insets(8, 8, 8, 8);
		final Font FONT = this.console.getFont();
		final Color FONT_COLOR = this.console.getFontColor();
		
		String entry = this.console.getEntry();
		String user = this.console.getUser();
		String text = user + entry;
		
		this.savedDocument = text;
		this.inputStartPos = this.savedDocument.length();
		
		this.setBackground(BACKGROUND);
		this.setMargin(MARGIN);
		this.setFont(FONT);
		this.setForeground(FONT_COLOR);
		this.setLineWrap(true);
		this.setText(text);
		this.setCaretPosition(this.inputStartPos);
		this.addKeyListener(this);
	}
	
	public void add(JLine line)
	{
		String entry = this.console.getEntry();
		String user = this.console.getUser();
		String text = this.getText();
		
		int lineIdx = text.lastIndexOf("\n");
		if (lineIdx == -1)
		{
			text = "";
		}
		else
		{
			text = text.substring(0, lineIdx+1);
		}
		
		text += this.convertLineToString(line) + "\n";
		text += user + entry;
		
		List<JLine> removed = this.document.add(line);
		for (JLine l : removed)
		{
			String regex = this.convertLineToString(l) + "\n";
			text = text.replaceFirst(regex, "");
		}
		
		this.inputStartPos = text.length();
		this.savedDocument = text;
		this.setText(text);
	}
	
	public void add(int id, String multiLineText) throws InvalidLineTypeException
	{
		int begin = 0;
		int newLine = multiLineText.indexOf('\n');
		int end = multiLineText.length();
		
		while (newLine != -1)
		{
			String contents = multiLineText.substring(begin, newLine);
			this.add(new JLine(id, contents));
			
			begin = newLine;
			newLine = multiLineText.indexOf('\n');
		}
		
		if (begin != end)
		{
			String contents = multiLineText.substring(begin, end);
			this.add(new JLine(id, contents));
		}
	}
	
	public boolean add(Command cmd)
	{
		if (cmd == null)
		{
			return false;
		}
		
		for (Command c : this.commands)
		{
			if (c.equals(cmd))
			{
				return false;
			}
		}
		
		return this.commands.add(cmd);
	}
	
	public void clear()
	{
		String entry = this.console.getEntry();
		String user = this.console.getUser();
		String text = user + entry;
		
		this.inputStartPos = text.length();
		this.savedDocument = text;
		this.document.clearLog();
		
		this.setText(text);
	}
	
	public List<Command> getCommands()
	{
		return Utilities.copyOf(commands);
	}
	
	@Override
	public FontMetrics getFontMetrics(Font font)
	{
		return new FontMetrics(font)
		{
			@Override
			public int getHeight()
			{
				return (int)(this.font.getSize() * JConsoleLog.this.spacing);
			}
		};
	}
	
	public float getSpacing()
	{
		return this.spacing;
	}
	
	public void setSpacing(float spacing)
	{
		this.spacing = (spacing < JConsoleLog.MIN_SPACING) ?
				JConsoleLog.MIN_SPACING : spacing;
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		if (this.keysPressed.contains(KeyEvent.VK_ENTER))
		{
			String text = this.getText();
			text = text.substring(0, text.length()-1);
			
			if (text.length() != this.savedDocument.length())
			{
				this.setText(this.savedDocument);
				return;
			}
			this.setText(text);
			this.processInput(text.substring(this.inputStartPos));
			this.container.shiftViewport();
			return;
		}
		
		if (this.keysPressed.size() == 2 &&
			this.keysPressed.contains(KeyEvent.VK_CONTROL) &&
			this.keysPressed.contains(KeyEvent.VK_A))
		{
			return;
		}
		this.checkEditedDocument(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		this.keysPressed.add(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		this.keysPressed.remove(e.getKeyCode());
	}
	
	protected void setContainer(JConsoleScrollPane container)
	{
		this.container = container;
	}
	
	private String convertLineToString(JLine line)
	{
		String entry = this.console.getEntry();
		String tab = this.console.getTab();
		String user = this.console.getUser();
		
		if (line.id == JLine.COMMAND)
		{
			return user + entry + line.contents;
		}
		else if (line.id == JLine.PRINT)
		{
			return tab + line.contents;
		}
		return null;
	}
	
	private void checkEditedDocument(KeyEvent e)
	{
		String entry = this.console.getEntry();
		String user = this.console.getUser();
		String text = user + entry;
		String document = this.getEditedDocument(e);
		
		int textLength = text.length();
		int begin = this.inputStartPos - textLength;
		int end = this.inputStartPos;
		
		if (document.length() >= end &&
			document.substring(begin, end).equals(text))
		{
			this.savedDocument = document;
		}
		else
		{
			this.setText(this.savedDocument);
		}
	}
	
	private String getEditedDocument(KeyEvent e)
	{
		String document = this.getText();
		boolean selected = (this.getSelectedText() != null);
		
		int begin = (selected) ? this.getSelectionStart() : this.getCaretPosition();
		int end = (selected) ? this.getSelectionEnd() : this.getCaretPosition();
		
		char c = e.getKeyChar();
		String add = (c >= 32 && c <= 126) ? Character.toString(c) : "";
		
		String editedDocument = document.substring(0, begin);
		editedDocument += add;
		editedDocument += document.substring(end);
		return editedDocument;
	}
	
	private void processInput(String input)
	{
		try
		{
			input = input.strip();
			JLine line = (input.isEmpty()) ? new JLine(JLine.PRINT, input) :
											 new JLine(JLine.COMMAND, input);
			
			this.add(line);
			if (line.id == JLine.COMMAND)
			{
				boolean invalidCommand = true;
				for (Command c : this.commands)
				{
					if (c.isCommand(input))
					{
						c.run(input);
						invalidCommand = false;
						break;
					}
				}
				
				if (invalidCommand)
				{
					this.add(new JLine("'" + input + "' is not a valid command."));
				}
			}
		}
		catch (InvalidLineTypeException e)
		{
			e.printStackTrace();
		}
	}
	
	private JConsole console;
	private JConsoleScrollPane container;
	private JLogDocument document;
	
	private float spacing;
	
	private int inputStartPos;
	
	private String savedDocument;
	
	private Set<Integer> keysPressed;
	
	private List<Command> commands;
}

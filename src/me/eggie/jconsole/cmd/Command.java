package me.eggie.jconsole.cmd;

import me.eggie.jconsole.JConsole;

public abstract class Command
{
	protected JConsole console;
	
	protected String key;
	
	public Command(JConsole console, String key)
	{
		this.console = console;
		this.key = key.toLowerCase();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Command)
		{
			Command cmd = (Command) obj;
			return this.key.equals(cmd.key);
		}
		return false;
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	public boolean isCommand(String str)
	{
		str = str.toLowerCase();
		str = this.next(str, this.key);
		return (str == null) ? false : true;
	}
	
	public String next(String cmd, String remove)
	{
		cmd = cmd.strip();
		if (cmd.startsWith(remove))
		{
			cmd = cmd.replaceFirst(remove, "");
			if (cmd.isEmpty())
			{
				return cmd;
			}
			if (cmd.charAt(0) == ' ')
			{
				return cmd.stripLeading();
			}
		}
		return null;
	}

	public abstract String getDescription();
	
	public abstract void run(String cmd);
}

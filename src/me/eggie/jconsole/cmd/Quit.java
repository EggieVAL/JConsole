package me.eggie.jconsole.cmd;

import me.eggie.jconsole.JConsole;

public class Quit extends Command
{
	public Quit(JConsole console, String key)
	{
		super(console, key);
	}
	
	@Override
	public String getDescription()
	{
		return "Terminates the program.";
	}
	
	@Override
	public void run(final String cmd)
	{
		String copy = this.next(cmd, this.key);
		if (!copy.isEmpty())
		{
			this.console.log("'" + cmd + "' is an invalid command.");
			return;
		}
		System.exit(0);
	}
}

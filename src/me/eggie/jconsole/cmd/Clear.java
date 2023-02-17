package me.eggie.jconsole.cmd;

import me.eggie.jconsole.JConsole;

public class Clear extends Command
{
	public Clear(JConsole console, String key)
	{
		super(console, key);
	}
	
	@Override
	public String getDescription()
	{
		return "Clears the console log.";
	}
	
	@Override
	public void run(String cmd)
	{
		String copy = this.next(cmd, this.key);
		if (!copy.isEmpty())
		{
			this.console.log("'" + cmd + "' is an invalid command.");
			return;
		}
		this.console.clear();
	}
}
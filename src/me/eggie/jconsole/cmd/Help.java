package me.eggie.jconsole.cmd;

import java.util.List;

import me.eggie.jconsole.JConsole;

public class Help extends Command
{
	public Help(JConsole console, String key)
	{
		super(console, key);
	}
	
	@Override
	public String getDescription()
	{
		return "Displays a list of commands and their descriptions.";
	}
	
	@Override
	public void run(String cmd)
	{
		List<Command> commandList = this.console.getCommands();
		for (Command c : commandList)
		{
			this.console.log(c.getKey() + " -- " + c.getDescription());
		}
	}
}

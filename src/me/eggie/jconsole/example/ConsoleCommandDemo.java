package me.eggie.jconsole.example;

import me.eggie.jconsole.JConsole;
import me.eggie.jconsole.cmd.Command;
import me.eggie.jconsole.global.Console;

public class ConsoleCommandDemo
{
	public static void main(String[] args)
	{
		JConsole console = Console.GLOBAL;
		
		// Some commands may need to grab information from the console.
		// The String is the activation key of the command.
		console.add(new Command(console, "print")
		{
			@Override
			public String getDescription()
			{
				return "Prints a message to the console. Prints"
						+ " \"Hello World!\" if message not provided.";
			}

			@Override
			public void run(String cmd)
			{
				// Get the command line after the String key.
				// e.g.:
				//    key = "print"
				//    cmd = "print ur mom"
				//
				//    next(cmd, key) = "ur mom"
				//    next(cmd, "ur") = "mom"
				
				// NOTE: We already know the command's first word is the activation
				// key word. The console log looks at the first word before running
				// this command.
				String copy = this.next(cmd, this.key);
				
				// print message
				if (!copy.isEmpty())
				{
					this.console.log(copy);
				}
				// print Hello World!
				else
				{
					this.console.log("Hello World!");
				}
			}
		});
		
		// Now test out the newly added command!
		// Other implemented commands are:
		// - clear
		// - help
		// - q
	}
}

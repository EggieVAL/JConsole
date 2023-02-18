package me.eggie.jconsole.example;

import me.eggie.jconsole.JConsole;

/**
 * You can either instantiate a new JConsole, or you can use the global console.
 * 
 * - To log using the global console, use {@code Console.log}
 * 
 * - To reconfigure the global console, get its instance: {@code Console.GLOBAL}
 *      - e.g.: Console.GLOBAL.changeDefaultLook(...)
 *      
 * @author Eggie
 */
public class ConsoleDemo
{
	public static void main(String[] args)
	{	
		// false = DARK MODE
		// true = LIGHT MODE
		// NOTE: a new JConsole does not have any commands registered!
		JConsole console = new JConsole(false);
		
		try
		{
			// logging the classic print statement
			Thread.sleep(1000);
			console.log("Hello World!");
			
			// changing console theme from DARK to LIGHT using { changeDefaultLook }
			Thread.sleep(1000);
			console.changeDefaultLook(JConsole.LIGHT_MODE_INNER, JConsole.LIGHT_MODE_OUTER,
					JConsole.LIGHT_MODE_FONT_COLOR, JConsole.FONT);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}

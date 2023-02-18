package me.eggie.jconsole.global;

import me.eggie.jconsole.JConsole;
import me.eggie.jconsole.cmd.Clear;
import me.eggie.jconsole.cmd.Help;
import me.eggie.jconsole.cmd.Quit;

public class Console
{
	public static JConsole GLOBAL = globalConsole();
	
	public static void log()
	{
		Console.GLOBAL.log();
	}
	
	public static void log(char c)
	{
		Console.GLOBAL.log(c);
	}
	
	public static void log(int i)
	{
		Console.GLOBAL.log(i);
	}
	
	public static void log(long l)
	{
		Console.GLOBAL.log(l);
	}
	
	public static void log(float f)
	{
		Console.GLOBAL.log(f);
	}
	
	public static void log(double d)
	{
		Console.GLOBAL.log(d);
	}
	
	public static void log(char[] arr)
	{
		Console.GLOBAL.log(arr);
	}
	
	public static void log(String str)
	{
		Console.GLOBAL.log(str);
	}
	
	public static void log(Object obj)
	{
		Console.GLOBAL.log(obj);
	}
	
	private static JConsole globalConsole()
	{
		JConsole console = new JConsole();
		console.add(new Clear(console, "clear"));
		console.add(new Help(console, "help"));
		console.add(new Quit(console, "q"));
		return console;
	}
}

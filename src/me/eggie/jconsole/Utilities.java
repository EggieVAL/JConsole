package me.eggie.jconsole;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple utilities class for the {@code JConsole} project.
 * @author Eggie
 */
public class Utilities
{
	/**
	 * Copies a list.
	 * @param list   the list to be copied.
	 * @return a {@code List<T>}.
	 */
	public static <T> List<T> copyOf(List<T> list)
	{
		List<T> copy = new ArrayList<T>();
		for (T line : list)
		{
			copy.add(line);
		}
		return copy;
	}
}

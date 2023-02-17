package me.eggie.jconsole;

import java.util.ArrayList;
import java.util.List;

public class Utilities
{
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

package org.mahjong.matoso.util.comparator;

import java.util.Comparator;

import org.mahjong.matoso.util.NumberUtils;

public class NumberPercComparator implements Comparator<String> {

	public int compare(String o1, String o2) {
		int i = o1.indexOf('(');
		int j = o1.indexOf('%');
		int i2 = o2.indexOf('(');
		int j2 = o2.indexOf('%');
		String s = o1.substring(i + 1, j - 1);
		String s2 = o2.substring(i2 + 1, j2 - 1);
		if (s.length() == 0 && s2.length() == 0)
			return 0;
		else if (s.length() == 0 || s2.length() == 0)
			return s.length() - s2.length();
		else
			return Double.compare(NumberUtils.getDouble(s), NumberUtils.getDouble(s2));
	}
}
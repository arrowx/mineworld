package com.mineworld.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
	/**
	 * 
	 * @param value
	 * @param deCode
	 * @param enCode
	 * @return
	 */
	public static String decode(String value, String deCode, String enCode) {
		if (value == null)
			return value;
		try {
			return new String(value.getBytes(deCode), enCode);
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 获取给出的字符串首个非0数字字符的位置
	 * @param value
	 * @return 找不到返回-1
	 */
	public static int getFirstNumber(String value) {
		int len = value.length();
		int i = 0;
		while (i < len && value.charAt(i++) == '0')
			;

		i = i - 1;
		if (value.charAt(i) == '0')
			i = -1;

		return i;
	}

	/**
	 * 把给出的字符串加1
	 * 该字符串由0～9这10个字符组成
	 * @param value
	 * @return
	 */
	public static String increase(String value) {
		int i = getFirstNumber(value);
		if (i == 0) {
			value = String.valueOf(Long.parseLong(value) + 1);
		} else if (i == -1) {
			value = value.substring(0, value.length() - 1) + "1";
		} else {
			String pre = value.substring(0, i);

			int offset = value.length() - i;
			value = String.valueOf(Long.parseLong(value.substring(i, value.length())) + 1);
			if (value.length() != offset) {
				//进行了进1操作
				value = pre.substring(0, i - 1) + value;
			} else
				value = pre + value;
		}

		return value;
	}

	public static final String joinArray(final Object[] arr, String delim) {
		if (arr == null || arr.length == 0)
			return "";

		//		assume every object string is less than 5 chars
		StringBuffer bf = new StringBuffer(arr.length * 5);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == null)
				continue;

			String t = arr[i].toString().trim();
			if (t.length() == 0)
				continue;

			bf.append(t);

			if (i < arr.length - 1) {
				if (delim != null) {
					bf.append(delim);
				}
			}
		}

		return bf.toString();
	}

	public static final Map<String, String> split2map(String s, String delim1, String delim2) {
		if (s == null || s.trim().length() == 0) {
			return new HashMap<String, String>(0);
		}

		String[] level1 = s.split(delim1);
		Map<String, String> result = new HashMap<String, String>(level1.length);
		for (String tmp : level1) {
			String[] level2 = tmp.split(delim2);
			if (level2.length < 1)
				continue;

			result.put(level2[0], level2.length > 1 ? level2[1] : "");
		}

		return result;
	}

	public static final Integer[] split2num(String s) {
		return split2num(s, ",");
	}
	
	public static final Integer[] split2num(String s, String delim) {
		s = trim(s);
		if (s.trim().length() == 0) {
			return new Integer[0];
		}

		String[] level1 = s.split(delim);
		Integer[] result = new Integer[level1.length];
		
		int fillidx = 0;
		for (int i = 0; i < level1.length; i++) {
			String s2 = level1[i];
			if (s2.length()==0) {
				continue;
			}
			result[fillidx] = toInt(s2);
			fillidx+=1;
		}
		
		if (fillidx==level1.length) {
			return result;
		} else {
			Integer[] result2 = new Integer[fillidx];
			System.arraycopy(result, 0, result2, 0, fillidx);
			return result2;
		}
	}

	
	/**
	 * convert a map into "k1<delim1>v1<delim2>k2<delim1>v2", eg, "k1-v1,k2-v2"
	 * @param map
	 * @param delim1
	 * @param delim2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final String map2String(Map map, String delim1, String delim2) {
		if (map == null || map.isEmpty()) {
			return "";
		}

		StringBuffer bf = new StringBuffer(map.size() * 10);
		Iterator<Object> it = map.keySet().iterator();

		Object k = it.next();
		Object v = map.get(k);
		bf.append(trim(k)).append(delim1).append(trim(v));

		while (it.hasNext()) {
			k = it.next();
			v = map.get(k);
			bf.append(delim2).append(trim(k)).append(delim1).append(trim(v));
		}

		return bf.toString();
	}

	public static final String trim(Object source) {
		return source == null ? "" : source.toString().trim();
	}

	public static final String[] asStringArray(Object[] source) {
		if (source == null || source.length == 0)
			return new String[0];

		String[] result = new String[source.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = trim(source[i]);
		}
		return result;
	}

	/**
	 * parse the object into int, if fail, return -1
	 * @param source
	 * @return
	 */
	public static final int toInt(Object source) {
		return toInt(source, -1);
	}
	
	public static final float toFloat(Object source) {
		String x = trim(source);
		float result = 0;

		try {
			result = Float.parseFloat(x);
		} catch (Exception e) {
			//ignore
		}
		return result;
	}
	
	public static final double toDouble(Object source) {
		return toDouble(source, -1.0d);
	}
	
	public static final double toDouble(Object source, double defaultValue) {
		String x = trim(source);
		double result = defaultValue;

		try {
			result = Double.parseDouble(x);
		} catch (Exception e) {
			//ignore
		}
		return result;
	}
	
	/**
	 * parse the object into int, if fail, return 'defaultValue'
	 * @param source
	 * @param defaultValue
	 * @return
	 */
	public static final int toInt(Object source, int defaultValue) {
		String x = trim(source);
		int result = defaultValue;

		try {
			result = Integer.parseInt(x);
		} catch (Exception e) {
			//ignore
		}
		return result;
	}

	public static final Integer[] toIntArray(String values, String delim) {
		values = trim(values);
		if (values.length()<1) {
			return new Integer[0];
		}
		
		String[] pp = values.split(delim);
		Integer[] result = new Integer[pp.length];
		for (int i=0; i<pp.length; i++) {
			result[i] = toInt(pp[i]);
		}
		return result;
	}
	
	public static void trimLastChar(StringBuffer bf, char match) {
		if (bf == null || bf.length() == 0)
			return;

		char c = bf.charAt(bf.length() - 1);
		if (c == match) {
			bf.deleteCharAt(bf.length() - 1);
		}
	}

	public static String format(String content, Object... args) {
		return MessageFormat.format(content, args);
	}

	public static boolean isNull(String content) {
		return content == null || content.length() == 0;
	}

	/**
	 *  判断某个字符是否为中文
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		/**
		 *  1.GENERAL_PUNCTUATION 判断中文的“号
		 *  2.CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号  
		 *  3.HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
		 */
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean containsChinese(String strName) {

		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 该方法用于处理在HTML中显示的超长文字无法换行的问题, 采取在一段长文字中插入空格
	 * 来使得在HTML中可以被浏览器进行换行处理. 像遇到一些连续的数字或英文, 浏览器不会
	 * 对其打断换行, 导致页面显示混乱.
	 * @param content
	 * @param charCountPerRow 每行容纳的文字个数
	 * @return
	 */
	private static Set<Character> BREAKABLE_CHARS = new HashSet<Character>(Arrays
			.asList(new Character[] { '-', '*', '%', '!', ' ' }));

	public static String breakWords(String content, int charCountPerRow) {
		if (content == null || charCountPerRow < 1) {
			return content;
		}
		int unbreakableCount = 0, len = content.length();
		StringBuffer bf = new StringBuffer(len + 10);

		for (int i = 0; i < len; i++) {
			char achar = content.charAt(i);
			bf.append(achar);
			
			if (BREAKABLE_CHARS.contains(achar) || isChinese(achar)) {
				// 发现一个支持换行的字符, 则重新计算
				unbreakableCount = 0;
				continue;
			}

			unbreakableCount += 1;
			if (unbreakableCount >= charCountPerRow) {
				// 超过了要换号的字符数, 插入一个空格
				bf.append(' ');
				unbreakableCount = 0;
			}
		}
		return bf.toString();
	}
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	/**
	 * 取小数点后两位
	 * @param f
	 * @return
	 */
	public static String decFormat(float f) {
		return df.format(f);
	}
	
	public static double formatDouble2(double f) {
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

	public static void main(String[] args) {
		
		System.out.println(split2num(" 38,  5,23,5"));
		
		StringBuffer bf = new StringBuffer("ab,");
		trimLastChar(bf, ',');
		System.out.println(bf);

		Map<String, String> m = new HashMap<String, String>();
		m.put("a", "A ");
		m.put("b", "B ");
		System.out.println(map2String(m, "-", ","));

		String rowToBreak = "很多的汉字很多的很多的汉字很多的很多的汉字很多的";
		System.out.println(breakWords(rowToBreak, 10));
		
	}
	
	public static boolean isMobile(String str) {
		if( str == null) {
			return false;
		}
		
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
}

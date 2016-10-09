package com.web.util;

import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;


public final class StringHelper {

	private StringHelper() { }

	public static boolean checkstr(String username) {
		String indexStr = username.substring(0, 1);
		String numberStr = "abcdefghijklmnopqrstuvwxyz0123456789_";
		String emailStr = "@.!~#$%^&*()|";
		if (username.length() < 6)
			return true;
		for (int i = 0; i < username.length(); i++) {
			if (i == username.length())
				return true;
			if (emailStr.indexOf(username.substring(i, i + 1)) != -1)
				return true;
		}

		return numberStr.indexOf(indexStr) == -1;
	}

	public static String join(String seperator, String strings[]) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuffer buf = (new StringBuffer(length * strings[0].length()))
				.append(strings[0]);
		for (int i = 1; i < length; i++)
			buf.append(seperator).append(strings[i]);

		return buf.toString();
	}

	public static String join(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext())
			buf.append(objects.next());
		for (; objects.hasNext(); buf.append(seperator).append(objects.next()))
			;
		return buf.toString();
	}

	public static String[] add(String x[], String seperator, String y[]) {
		String result[] = new String[x.length];
		for (int i = 0; i < x.length; i++)
			result[i] = x[i] + seperator + y[i];

		return result;
	}

	public static String repeat(String string, int times) {
		StringBuffer buf = new StringBuffer(string.length() * times);
		for (int i = 0; i < times; i++)
			buf.append(string);

		return buf.toString();
	}

	public static String replace(String source, String old, String replace) {
		StringBuffer output = new StringBuffer();
		int sourceLen = source.length();
		int oldLen = old.length();
		int posStart;
		int pos;
		for (posStart = 0; (pos = source.indexOf(old, posStart)) >= 0; posStart = pos
				+ oldLen) {
			output.append(source.substring(posStart, pos));
			output.append(replace);
		}

		if (posStart < sourceLen)
			output.append(source.substring(posStart));
		return output.toString();
	}

	public static String replace(String template, String placeholder,
			String replacement, boolean wholeWords) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			boolean actuallyReplace = wholeWords
					|| loc + placeholder.length() == template.length()
					|| !Character.isJavaIdentifierPart(template.charAt(loc
							+ placeholder.length()));
			String actualReplacement = actuallyReplace ? replacement
					: placeholder;
			return template.substring(0, loc)
					+ actualReplacement
					+ replace(template.substring(loc + placeholder.length()),
							placeholder, replacement, wholeWords);
		}
	}

	public static String replaceOnce(String template, String placeholder,
			String replacement) {
		int loc = template.indexOf(placeholder);
		if (loc < 0)
			return template;
		else
			return template.substring(0, loc) + replacement
					+ template.substring(loc + placeholder.length());
	}

	public static String[] split(String list, String seperators) {
		return split(list, seperators, false);
	}

	public static String[] split(String list, String seperators, boolean include) {
		StringTokenizer tokens = new StringTokenizer(list, seperators, include);
		String result[] = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens())
			result[i++] = tokens.nextToken();
		return result;
	}

	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, ".");
	}

	public static String unqualify(String qualifiedName, String seperator) {
		return qualifiedName
				.substring(qualifiedName.lastIndexOf(seperator) + 1);
	}

	public static String qualifier(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		if (loc < 0)
			return "";
		else
			return qualifiedName.substring(0, loc);
	}

	public static String[] suffix(String columns[], String suffix) {
		if (suffix == null)
			return columns;
		String qualified[] = new String[columns.length];
		for (int i = 0; i < columns.length; i++)
			qualified[i] = suffix(columns[i], suffix);

		return qualified;
	}

	public static String suffix(String name, String suffix) {
		return suffix != null ? name + suffix : name;
	}

	public static String[] prefix(String columns[], String prefix) {
		if (prefix == null)
			return columns;
		String qualified[] = new String[columns.length];
		for (int i = 0; i < columns.length; i++)
			qualified[i] = prefix + columns[i];

		return qualified;
	}

	public static String prefix(String name, String prefix) {
		return prefix != null ? prefix + name : name;
	}

	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}

	public static String toString(Object array[]) {
		int len = array.length;
		if (len == 0)
			return "";
		StringBuffer buf = new StringBuffer(len * 12);
		for (int i = 0; i < len - 1; i++)
			buf.append(array[i]).append(", ");

		return buf.append(array[len - 1]).toString();
	}

	public static String[] multiply(String string, Iterator placeholders,
			Iterator replacements) {
		String result[];
		for (result = (new String[] { string }); placeholders.hasNext(); result = multiply(
				result, (String) placeholders.next(),
				(String[]) replacements.next()))
			;
		return result;
	}

	private static String[] multiply(String strings[], String placeholder,
			String replacements[]) {
		String results[] = new String[replacements.length * strings.length];
		int n = 0;
		for (int i = 0; i < replacements.length; i++) {
			for (int j = 0; j < strings.length; j++)
				results[n++] = replaceOnce(strings[j], placeholder,
						replacements[i]);

		}

		return results;
	}

	public static int count(String string, char character) {
		int n = 0;
		for (int i = 0; i < string.length(); i++)
			if (string.charAt(i) == character)
				n++;

		return n;
	}

	public static int countUnquoted(String string, char character) {
		if ('\'' == character)
			throw new IllegalArgumentException(
					"Unquoted count of quotes is invalid");
		int count = 0;
		int stringLength = string != null ? string.length() : 0;
		boolean inQuote = false;
		for (int indx = 0; indx < stringLength; indx++)
			if (inQuote) {
				if ('\'' == string.charAt(indx))
					inQuote = false;
			} else if ('\'' == string.charAt(indx))
				inQuote = true;
			else if (string.charAt(indx) == character)
				count++;

		return count;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return true;
		for (int i = 0; i < strLen; i++)
			if (!Character.isWhitespace(str.charAt(i)))
				return false;

		return true;
	}

	public static boolean isNotBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return false;
		for (int i = 0; i < strLen; i++)
			if (!Character.isWhitespace(str.charAt(i)))
				return true;

		return false;
	}

	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String qualify(String name, String prefix) {
		if (name.startsWith("'"))
			return name;
		else
			return (new StringBuffer(prefix.length() + name.length() + 1))
					.append(prefix).append('.').append(name).toString();
	}

	public static String[] qualify(String names[], String prefix) {
		if (prefix == null)
			return names;
		int len = names.length;
		String qualified[] = new String[len];
		for (int i = 0; i < len; i++)
			qualified[i] = qualify(prefix, names[i]);

		return qualified;
	}

	public static int firstIndexOfChar(String sqlString, String string,
			int startindex) {
		int matchAt = -1;
		for (int i = 0; i < string.length(); i++) {
			int curMatch = sqlString.indexOf(string.charAt(i), startindex);
			if (curMatch >= 0)
				if (matchAt == -1)
					matchAt = curMatch;
				else
					matchAt = Math.min(matchAt, curMatch);
		}

		return matchAt;
	}

	public static String truncate(String string, int length,
			boolean appendSuspensionPoints) {
		if (isEmpty(string) || length < 0)
			return string;
		if (length == 0)
			return "";
		int strLength = string.length();
		int byteLength = byteLength(string);
		length *= 2;
		boolean needSus = false;
		if (appendSuspensionPoints && byteLength >= length) {
			needSus = true;
			length -= 2;
		}
		StringBuffer result = new StringBuffer();
		int count = 0;
		for (int i = 0; i < strLength; i++) {
			if (count >= length)
				break;
			char c = string.charAt(i);
			if (isLetter(c)) {
				result.append(c);
				count++;
			} else if (count == length - 1) {
				result.append(" ");
				count++;
			} else {
				result.append(c);
				count += 2;
			}
		}

		if (needSus)
			result.append("...");
		return result.toString();
	}

	public static boolean isLetter(char c) {
		int k = 128;
		return c / k == 0;
	}

	public static int byteLength(String s) {
		char c[] = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++)
			if (isLetter(c[i]))
				len++;
			else
				len += 2;

		return len;
	}

	public static String truncate(String string, int length) {
		if (isEmpty(string))
			return string;
		if (string.length() <= length)
			return string;
		else
			return string.substring(0, length);
	}

	public static String leftTrim(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (!Character.isWhitespace(ch[i]))
				break;
			index = i;
		}

		if (index != -1)
			result = result.substring(index + 1);
		return result;
	}

	public static String rightTrim(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (!Character.isWhitespace(ch[i]))
				break;
			endIndex = i;
		}

		if (endIndex != -1)
			result = result.substring(0, endIndex);
		return result;
	}

	public static String N2S(String source) {
		return source == null ? "" : source;
	}

	public static String N2S(String source, String defaultStr) {
		return source == null ? defaultStr : source;
	}

	public static final String toGb2312(String source) {
		String temp = null;
		if (source == null || source.equals(""))
			return source;
		try {
			temp = new String(source.getBytes("8859_1"), "GB2312");
		} catch (Exception e) {
			logger.error("转换字符串为gb2312编码出错:" + e.getMessage());
		}
		return temp;
	}

	public static final String toGBK(String source) {
		String temp = null;
		if (source == null || source.equals(""))
			return source;
		try {
			temp = new String(source.getBytes("8859_1"), "GBK");
		} catch (Exception e) {
			logger.error("Convert code Error:" + e.getMessage());
		}
		return temp;
	}

	public static final String to8859(String source) {
		String temp = null;
		if (source == null || source.equals(""))
			return source;
		try {
			temp = new String(source.getBytes("GBK"), "8859_1");
		} catch (Exception e) {
			logger.error("Convert code Error:" + e.getMessage());
		}
		return temp;
	}

	public static String chineseToUnicode(String source) {
		if (isEmpty(source))
			return source;
		String unicode = null;
		String temp = null;
		for (int i = 0; i < source.length(); i++) {
			temp = "\\u" + Integer.toHexString(source.charAt(i));
			unicode = unicode != null ? unicode + temp : temp;
		}

		return unicode;
	}

	public static String toScript(String str) {
		if (str == null) {
			return null;
		} else {
			String html = new String(str);
			html = replace(html, "\"", "\\\"");
			html = replace(html, "\r\n", "\n");
			html = replace(html, "\n", "\\n");
			html = replace(html, "\t", "    ");
			html = replace(html, "'", "\\'");
			html = replace(html, "  ", " &nbsp;");
			html = replace(html, "</script>", "<\\/script>");
			html = replace(html, "</SCRIPT>", "<\\/SCRIPT>");
			return html;
		}
	}

	public static String trim(String s) {
		return s != null ? s.trim() : s;
	}

	public static int strTrim(String source, int defaultValue)
    {
        if(isEmpty(source))
            return defaultValue;
        int value;
        source = source.trim();
        value = (new Integer(source)).intValue();
        return value;
    }

	public static String strTrim(String source, String defaultValue)
    {
        if(isEmpty(source))
            return defaultValue;
        source = source.trim();
        return source;
    }

	public static String encodeHtml(String source) {
		if (source == null) {
			return null;
		} else {
			String html = new String(source);
			html = replace(html, "&", "&amp;");
			html = replace(html, "<", "&lt;");
			html = replace(html, ">", "&gt;");
			html = replace(html, "\"", "&quot;");
			return html;
		}
	}

	public static String decodeHtml(String source) {
		if (source == null) {
			return null;
		} else {
			String html = new String(source);
			html = replace(html, "&amp;", "&");
			html = replace(html, "&lt;", "<");
			html = replace(html, "&gt;", ">");
			html = replace(html, "&quot;", "\"");
			html = replace(html, "\r\n", "\n");
			html = replace(html, "\n", "<br>\n");
			html = replace(html, "\t", "    ");
			html = replace(html, "  ", " &nbsp;");
			return html;
		}
	}

	public static boolean isBoolean(String source) {
		return source.equalsIgnoreCase("true")
				|| source.equalsIgnoreCase("false");
	}

	public static String lastCharTrim(String str, String strMove) {
		if (isEmpty(str))
			return "";
		String newStr = "";
		if (str.lastIndexOf(strMove) != -1
				&& str.lastIndexOf(strMove) == str.length() - 1)
			newStr = str.substring(0, str.lastIndexOf(strMove));
		return newStr;
	}


	public static boolean chenckEmail(String str) {
		boolean result = false;
		String chen = null;
		String temp = "abcdefghijklmnopqrstuvwxyz0123456789@.-_";
		if (str != null && str.length() > 0) {
			chen = str.toLowerCase();
			for (int i = 0; i < str.length(); i++) {
				char ch = chen.charAt(i);
				if (temp.indexOf(chen.charAt(i)) == -1) {
					System.out.println("输入的email地址含有无效字符:" + chen);
					return false;
				}
			}

			int len = str.length();
			int temps = chen.indexOf("@");
			int tempd = chen.indexOf(".", temps);
			if (temps <= 1 || tempd - temps <= 2)
				return false;
			if (chen.indexOf("@.") > 1)
				return false;
			if (tempd == -1 || len - tempd < 2)
				return false;
		}
		return true;
	}

	public static String textFmtToHtmlFmt(String content) {
		content = replace(content, " ", "&nbsp;");
		content = replace(content, "\n", "<br>");
		return content;
	}

	public static String getFilterStr(String str) {
		String filterStr = str;
		if (filterStr != null && filterStr.length() > 0) {
			filterStr = replace(filterStr, "<", "&lt;");
			filterStr = replace(filterStr, ">", "&gt;");
			filterStr = replace(filterStr, "\"", "&quot");
			filterStr = replace(filterStr, "'", "&quot");
		}
		return filterStr;
	}


	private static Logger logger;
	public static final String EMPTY_STRING = "";
	public static final char DOT = 46;
	public static final char UNDERSCORE = 95;
	public static final String COMMA_SPACE = ", ";
	public static final String COMMA = ",";
	public static final String OPEN_PAREN = "(";
	public static final String CLOSE_PAREN = ")";
	public static final char SINGLE_QUOTE = 39;
	public static final String CRLF = "\r\n";

	static {
		logger = Logger.getLogger(com.web.util.StringHelper.class);
	}
}
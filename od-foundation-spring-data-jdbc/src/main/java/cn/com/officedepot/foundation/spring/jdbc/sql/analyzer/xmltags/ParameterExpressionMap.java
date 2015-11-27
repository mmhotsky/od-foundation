package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags;

import java.util.HashMap;

public class ParameterExpressionMap extends HashMap<String, String> {

	private static final long serialVersionUID = -2417552199605158680L;

	public ParameterExpressionMap(String expression) {
		int p = skipWS(expression, 0);
		if (expression.charAt(p) == '(') {
			expression(expression, p + 1);

		} else {
			property(expression, p);
		}
	}

	protected void expression(String expression, int left) {
		int match = 1;
		int right = left + 1;
		while (match > 0) {
			if (expression.charAt(right) == ')') {
				match--;
			} else if (expression.charAt(right) == '(') {
				match++;
			}
			right++;
		}
		put("expression", expression.substring(left, right - 1));
	}

	protected void property(String expression, int left) {
		if (left < expression.length()) {
			int right = skipUntil(expression, left, ",:");
			put("property", trimmedStr(expression, left, right));
		}
	}

	protected int skipWS(String expression, int p) {
		for (int i = p; i < expression.length(); i++) {
			if (expression.charAt(i) > 0x20) {
				return i;
			}
		}
		return expression.length();
	}

	protected int skipUntil(String expression, int p, final String endChars) {
		for (int i = p; i < expression.length(); i++) {
			char c = expression.charAt(i);
			if (endChars.indexOf(c) > -1) {
				return i;
			}
		}
		return expression.length();
	}

	protected void option(String expression, int p) {
		int left = skipWS(expression, p);
		if (left < expression.length()) {
			int right = skipUntil(expression, left, "=");
			String name = trimmedStr(expression, left, right);
			left = right + 1;
			right = skipUntil(expression, left, ",");
			String value = trimmedStr(expression, left, right);
			put(name, value);
			option(expression, right + 1);
		}
	}

	protected String trimmedStr(String str, int start, int end) {
		while (str.charAt(start) <= 0x20) {
			start++;
		}
		while (str.charAt(end - 1) <= 0x20) {
			end--;
		}
		return start >= end ? "" : str.substring(start, end);
	}

}

package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node;

import java.util.Properties;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags.GenericTokenParser;
import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags.TokenParser;

public class PropertyParser {

	public static String parse(String string, Properties variables) {
		VariableTokenHandler handler = new VariableTokenHandler(variables);
		TokenParser parser = new GenericTokenParser();
		return parser.parse(string);
	}

	private static interface TokenHandler {
		String handleToken(String content);
	}

	private static class VariableTokenHandler implements TokenHandler {
		private Properties variables;

		public VariableTokenHandler(Properties variables) {
			this.variables = variables;
		}

		@Override
		public String handleToken(String content) {
			if (variables != null && variables.containsKey(content)) {
				return variables.getProperty(content);
			}
			return "${" + content + "}";
		}
	}

}

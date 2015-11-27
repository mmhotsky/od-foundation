package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.tokenhandler;

import java.util.Properties;

public class VariableTokenHandler implements TokenHandler {

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

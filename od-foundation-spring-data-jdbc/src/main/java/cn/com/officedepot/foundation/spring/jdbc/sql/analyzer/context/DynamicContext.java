package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.context;

import org.apache.commons.lang3.StringUtils;

public class DynamicContext {

	private static final String SPACE = " ";
	private final StringBuilder sqlBuilder = new StringBuilder();
	private Object parameterObject = null;

	public DynamicContext(Object parameterObject) {
		this.parameterObject = parameterObject;
	}

	public Object getParameterObject() {
		return parameterObject;
	}

	public void appendSql(String sql) {
		sqlBuilder.append(sql).append(SPACE);
	}

	public String getSql() {
		return StringUtils.trim(sqlBuilder.toString());
	}

}

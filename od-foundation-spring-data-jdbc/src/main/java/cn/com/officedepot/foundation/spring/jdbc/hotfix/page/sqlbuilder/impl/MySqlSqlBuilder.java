package cn.com.officedepot.foundation.spring.jdbc.hotfix.page.sqlbuilder.impl;

import cn.com.officedepot.foundation.spring.jdbc.hotfix.page.sqlbuilder.SqlBuilder;

public class MySqlSqlBuilder implements SqlBuilder {

	@Override
	public String buildPageSql(String sql, int offset, int limit) {
		return sql == null || sql.trim().length() < 1 ? sql : (sql + " limit " + offset + ", " + limit);
	}

}

package cn.com.officedepot.foundation.spring.jdbc.hotfix.page.sqlbuilder;

import cn.com.officedepot.foundation.spring.jdbc.Dialect;
import cn.com.officedepot.foundation.spring.jdbc.hotfix.page.sqlbuilder.impl.MySqlSqlBuilder;
import cn.com.officedepot.foundation.spring.jdbc.hotfix.page.sqlbuilder.impl.OracleSqlBuilder;

public class SqlBuilderFactory {

	private static final SqlBuilder oralceBuilder = new OracleSqlBuilder();
	private static final SqlBuilder mysqlBuilder = new MySqlSqlBuilder();

	private static final SqlBuilder defaultSqlBuilder = oralceBuilder;

	public static SqlBuilder create(String dialect) {
		switch (Dialect.valueOf(dialect)) {
		case oracle:
			return oralceBuilder;
		case mysql:
			return mysqlBuilder;
		default:
			return defaultSqlBuilder;
		}
	}

}

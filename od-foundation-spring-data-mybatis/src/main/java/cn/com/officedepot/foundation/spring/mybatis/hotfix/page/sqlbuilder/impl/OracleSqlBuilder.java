package cn.com.officedepot.foundation.spring.mybatis.hotfix.page.sqlbuilder.impl;

import cn.com.officedepot.foundation.spring.mybatis.hotfix.page.sqlbuilder.SqlBuilder;

public class OracleSqlBuilder implements SqlBuilder {

	@Override
	public String buildPageSql(String sql, int offset, int limit) {
		String s = "";

		s += "select * from ( select __page_sql_temp.*, rownum __page_sql_rownum from ( ";
		s += sql;
		s += " ) __page_sql_temp where rownum<=";
		s += (offset + limit - 1);
		s += ") where __page_sql_rownum>";
		s += offset;

		return sql == null || sql.trim().length() < 1 ? sql : s;
	}

}

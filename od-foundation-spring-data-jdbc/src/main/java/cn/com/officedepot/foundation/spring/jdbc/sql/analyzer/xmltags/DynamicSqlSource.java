package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.context.DynamicContext;
import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node.SqlNode;

public class DynamicSqlSource {

	private SqlNode rootSqlNode = null;

	public DynamicSqlSource(SqlNode rootSqlNode) {
		this.rootSqlNode = rootSqlNode;
	}

	public BoundSql getBoundSql(Object parameterObject) {
		DynamicContext context = new DynamicContext(parameterObject);
		rootSqlNode.apply(context);

		SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder();
		SqlSource sqlSource = sqlSourceBuilder.parse(context.getSql());
		BoundSql boundSql = sqlSource.getBoundSql(parameterObject);

		return boundSql;
	}

}

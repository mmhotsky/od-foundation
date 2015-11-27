package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node;

import java.util.List;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.context.DynamicContext;

public class MixedSqlNode implements SqlNode {

	private List<SqlNode> contents;

	public MixedSqlNode(List<SqlNode> contents) {
		this.contents = contents;
	}

	@Override
	public boolean apply(DynamicContext context) {
		for (SqlNode sqlNode : contents) {
			sqlNode.apply(context);
		}
		return true;
	}

}

package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.context.DynamicContext;
import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags.ExpressionEvaluator;

public class IfSqlNode implements SqlNode {

	private ExpressionEvaluator evaluator;
	private String test;
	private SqlNode contents;

	public IfSqlNode(SqlNode contents, String test) {
		this.test = test;
		this.contents = contents;
		this.evaluator = new ExpressionEvaluator();
	}

	@Override
	public boolean apply(DynamicContext context) {
		if (evaluator.evaluateBoolean(test, context.getBindings())) {
			contents.apply(context);
			return true;
		}
		return false;
	}

}

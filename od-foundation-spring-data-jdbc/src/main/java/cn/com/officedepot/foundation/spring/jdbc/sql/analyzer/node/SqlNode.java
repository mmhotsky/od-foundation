package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.context.DynamicContext;

public interface SqlNode {

	public boolean apply(DynamicContext context);

}

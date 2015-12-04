package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node.SqlNode;

public abstract class AbstractSqlAnalyzer implements SqlAnalyzer, InitializingBean {

	protected static final String DEFAULT_NAMESPACE = "";

	protected static final Map<String, SqlNode> cache = new ConcurrentSqlCache<String, SqlNode>();

	protected PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

	@Override
	public <T> String get(String sqlId, T parameter) {
		return null;
	}

	@Override
	public <T> String get(String namespace, String sqlId, T parameter) {
		return null;
	}

}

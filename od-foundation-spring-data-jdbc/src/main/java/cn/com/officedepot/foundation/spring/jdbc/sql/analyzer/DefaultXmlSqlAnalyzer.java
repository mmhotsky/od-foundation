package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.CollectionUtils;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node.SqlGroup;
import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node.SqlNode;

public class DefaultXmlSqlAnalyzer implements SqlAnalyzer, InitializingBean {

	private static final Map<String, SqlNode> cache = new ConcurrentSqlCache<String, SqlNode>();

	private PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

	private String basePackages = null;

	private List<File> sqlFiles = new ArrayList<File>();

	private Map<String, SqlGroup> map = new LinkedHashMap<String, SqlGroup>();

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

	@Override
	public void afterPropertiesSet() {
		this.resolveBasePackages();
		this.resolveSqlFiles();
	}

	protected void resolveBasePackages() {
		if (StringUtils.isBlank(basePackages)) {
			throw new IllegalArgumentException();
		}

		String[] paths = StringUtils.split(basePackages, ",");
		for (String path : paths) {
			try {
				for (Resource resource : resourceResolver.getResources(StringUtils.trim(path))) {
					sqlFiles.add(resource.getFile());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void resolveSqlFiles() {
		if (CollectionUtils.isEmpty(sqlFiles)) {
			return;
		}

		for (File sqlFile : sqlFiles) {
			SqlGroup sqlGroup = this.resolveSqlGroup(sqlFile);

			String namespace = sqlGroup.getNamespace();
			List<SqlNode> sqls = sqlGroup.getSqls();
			for (SqlNode node : sqls) {
				cache.put(namespace + node, node);
			}
		}
	}

	protected SqlGroup resolveSqlGroup(File sqlFile) {
		return null;
	}

	@Override
	public <T> String get(String namespace, String sqlId, T parameter) {
		return null;
	}

}

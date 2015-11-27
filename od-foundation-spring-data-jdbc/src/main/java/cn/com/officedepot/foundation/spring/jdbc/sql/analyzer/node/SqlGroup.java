package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlGroup {

	private String namespace = null;

	private Map<String, SqlNode> mapper = new LinkedHashMap<String, SqlNode>();

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void putSql(String sqlId, SqlNode node) {
		mapper.put(sqlId, node);
	}

	public SqlNode getSql(String sqlId) {
		return mapper.get(sqlId);
	}

	public List<SqlNode> getSqls() {
		List<SqlNode> sqls = new ArrayList<SqlNode>();

		if (mapper.isEmpty()) {
			return sqls;
		}

		sqls.addAll(mapper.values());

		return sqls;
	}

}

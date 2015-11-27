package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer;

public interface SqlAnalyzer {

	public <T> String get(String namespace, String sqlId, T parameter);

}

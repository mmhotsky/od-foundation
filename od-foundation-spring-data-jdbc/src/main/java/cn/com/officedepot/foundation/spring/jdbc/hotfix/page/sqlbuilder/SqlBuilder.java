package cn.com.officedepot.foundation.spring.jdbc.hotfix.page.sqlbuilder;

public interface SqlBuilder {

	public String buildPageSql(String sql, int offset, int limit);

}

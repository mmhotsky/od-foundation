package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags;

public class SqlSourceBuilder {

	public String parse(String originalSql) {
		return new GenericTokenParser("${", "}").parse(originalSql);
	}

}

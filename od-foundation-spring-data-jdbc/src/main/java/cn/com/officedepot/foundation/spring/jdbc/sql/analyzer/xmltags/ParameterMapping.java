package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags;

public class ParameterMapping {

	private String property;
	private ParameterMode mode;
	private Class<?> javaType = Object.class;
	private JdbcType jdbcType;
	private Integer numericScale;
	private TypeHandler<?> typeHandler;
	private String resultMapId;
	private String jdbcTypeName;
	private String expression;

	public String getProperty() {
		return property;
	}

	public ParameterMode getMode() {
		return mode;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public Integer getNumericScale() {
		return numericScale;
	}

	public TypeHandler<?> getTypeHandler() {
		return typeHandler;
	}

	public String getResultMapId() {
		return resultMapId;
	}

	public String getJdbcTypeName() {
		return jdbcTypeName;
	}

	public String getExpression() {
		return expression;
	}

}

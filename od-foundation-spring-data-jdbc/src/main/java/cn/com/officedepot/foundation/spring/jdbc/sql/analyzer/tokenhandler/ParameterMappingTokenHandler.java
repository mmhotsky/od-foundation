package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.tokenhandler;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags.BaseBuilder;
import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags.ParameterExpressionMap;
import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags.ParameterMapping;

public class ParameterMappingTokenHandler extends BaseBuilder implements TokenHandler {

	private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
	private Object parameterObject;

	public ParameterMappingTokenHandler(Object parameterObject) {
		this.parameterObject = parameterObject;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	@Override
	public String handleToken(String content) {
		parameterMappings.add(this.buildParameterMapping(content));
		return "?";
	}

	protected ParameterMapping buildParameterMapping(String content) {
		Map<String, String> propertiesMap = parseParameterMapping(content);
		String property = propertiesMap.get("property");
		Class<?> propertyType;
		if (property != null) {
			Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
			Object value = new PropertyDescriptor(property, parameterType).getReadMethod().invoke(parameterObject);
			if (metaClass.hasGetter(property)) {
				propertyType = metaClass.getGetterType(property);
			} else {
				propertyType = Object.class;
			}
		} else {
			propertyType = Object.class;
		}
		ParameterMapping.Builder builder = new ParameterMapping.Builder(configuration, property, propertyType);
		Class<?> javaType = propertyType;
		String typeHandlerAlias = null;
		if (typeHandlerAlias != null) {
			builder.typeHandler(resolveTypeHandler(javaType, typeHandlerAlias));
		}
		return builder.build();
	}

	protected Map<String, String> parseParameterMapping(String content) {
		try {
			return new ParameterExpressionMap(content);

		} catch (RuntimeException ex) {
			throw ex;

		} catch (Exception ex) {
			throw new RuntimeException("Parsing error was found in mapping #{" + content + "}.  Check syntax #{property|(expression), var1=value1, var2=value2, ...} ", ex);
		}
	}

}

package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseBuilder {

	protected Boolean booleanValueOf(String value, Boolean defaultValue) {
		return value == null ? defaultValue : Boolean.valueOf(value);
	}

	protected Integer integerValueOf(String value, Integer defaultValue) {
		return value == null ? defaultValue : Integer.valueOf(value);
	}

	protected Set<String> stringSetValueOf(String value, String defaultValue) {
		value = (value == null ? defaultValue : value);
		return new HashSet<String>(Arrays.asList(value.split(",")));
	}

	protected JdbcType resolveJdbcType(String alias) {
		if (alias == null)
			return null;
		try {
			return JdbcType.valueOf(alias);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Error resolving JdbcType. Cause: " + e, e);
		}
	}

	protected ResultSetType resolveResultSetType(String alias) {
		if (alias == null)
			return null;
		try {
			return ResultSetType.valueOf(alias);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Error resolving ResultSetType. Cause: " + e, e);
		}
	}

	protected ParameterMode resolveParameterMode(String alias) {
		if (alias == null)
			return null;
		try {
			return ParameterMode.valueOf(alias);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Error resolving ParameterMode. Cause: " + e, e);
		}
	}

	protected Object createInstance(String alias) {
		Class<?> clazz = resolveClass(alias);
		if (clazz == null)
			return null;
		try {
			return resolveClass(alias).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error creating instance. Cause: " + e, e);
		}
	}

	protected Class<?> resolveClass(String alias) {
		if (alias == null)
			return null;
		try {
			return resolveAlias(alias);
		} catch (Exception e) {
			throw new RuntimeException("Error resolving class. Cause: " + e, e);
		}
	}

	protected TypeHandler<?> resolveTypeHandler(Class<?> javaType, String typeHandlerAlias) {
		if (typeHandlerAlias == null)
			return null;
		Class<?> type = resolveClass(typeHandlerAlias);
		if (type != null && !TypeHandler.class.isAssignableFrom(type)) {
			throw new RuntimeException("Type " + type.getName() + " is not a valid TypeHandler because it does not implement TypeHandler interface");
		}
		@SuppressWarnings("unchecked") // already verified it is a TypeHandler
		Class<? extends TypeHandler<?>> typeHandlerType = (Class<? extends TypeHandler<?>>) type;
		return resolveTypeHandler(javaType, typeHandlerType);
	}

	protected TypeHandler<?> resolveTypeHandler(Class<?> javaType, Class<? extends TypeHandler<?>> typeHandlerType) {
		if (typeHandlerType == null)
			return null;
		// javaType ignored for injected handlers see issue #746 for full detail
		TypeHandler<?> handler = typeHandlerRegistry.getMappingTypeHandler(typeHandlerType);
		if (handler == null) {
			// not in registry, create a new one
			handler = typeHandlerRegistry.getInstance(javaType, typeHandlerType);
		}
		return handler;
	}

	protected Class<?> resolveAlias(String alias) {
		return typeAliasRegistry.resolveAlias(alias);
	}
}

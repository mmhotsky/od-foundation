package cn.com.officedepot.foundation.spring.mybatis.hotfix.sql;

import java.sql.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class SqlformatInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(SqlformatInterceptor.class);

	private static final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.getDefault());

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		Configuration config = this.resolveConfiguration(statement);
		BoundSql boundSql = this.resolveBoundSql(invocation, statement);

		Object results = null;
		if ("true".equalsIgnoreCase(config.getVariables().getProperty("sql.show"))) {
			long startTime = System.currentTimeMillis();
			results = invocation.proceed();
			long costTime = (System.currentTimeMillis() - startTime);

			logger.debug("invoke method: {}", statement.getId());
			String sql = boundSql.getSql();
			if ("true".equalsIgnoreCase(config.getVariables().getProperty("sql.format"))) {
				sql = this.formatSql(config, boundSql);
			}
			logger.debug("SQL: {}", sql);
			logger.debug("SQL cost time: {} ms", costTime);
			logger.debug("SQL results: {}", results);

		} else {
			results = invocation.proceed();
		}

		return results;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

	protected Configuration resolveConfiguration(MappedStatement statement) {
		return statement.getConfiguration();
	}

	protected BoundSql resolveBoundSql(Invocation invocation, MappedStatement statement) {
		return statement.getBoundSql(invocation.getArgs().length > 1 ? invocation.getArgs()[1] : null);
	}

	protected String resolveParasValue(Object paras) {
		String results = null;

		if (paras instanceof String) {
			results = "'" + paras.toString() + "'";

		} else if (paras instanceof Date) {
			results = "'" + df.format(paras) + "'";

		} else if (paras != null) {
			results = paras.toString();

		} else {
			results = "";
		}

		return results;
	}

	protected String formatSql(Configuration config, BoundSql boundSql) {
		String sql = boundSql.getSql();

		sql = StringUtils.replacePattern(sql, "[\\s]+", " ");

		Object paras = boundSql.getParameterObject();
		List<ParameterMapping> mappings = boundSql.getParameterMappings();

		if (mappings.size() > 0 && paras != null) {
			TypeHandlerRegistry typeHandlerRegistry = config.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(paras.getClass())) {
				sql = sql.replaceFirst("\\?", resolveParasValue(paras));

			} else {
				MetaObject metaObject = config.newMetaObject(paras);
				for (ParameterMapping parameterMapping : mappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", resolveParasValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", resolveParasValue(obj));
					}
				}
			}
		}

		return sql;
	}

}

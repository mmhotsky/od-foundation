package cn.com.officedepot.foundation.spring.mybatis.hotfix.page;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.officedepot.foundation.spring.mybatis.hotfix.page.sqlbuilder.SqlBuilder;
import cn.com.officedepot.foundation.spring.mybatis.hotfix.page.sqlbuilder.SqlBuilderFactory;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagableInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(PagableInterceptor.class);

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();

	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	/*************************************************************/

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MetaObject handler = this.resolveMetaObject(invocation);
		Configuration config = this.resolveConfiguration(handler);
		BoundSql boundSql = this.resolveBoundSql(handler);
		RowBounds rowBounds = this.resolveRowBounds(handler);

		if (rowBounds != null && rowBounds != RowBounds.DEFAULT) {
			logger.debug("PagableInterceptor process pageable info");

			SqlBuilder builder = SqlBuilderFactory.create(config.getVariables().getProperty("dialect"));
			String sql = builder.buildPageSql(boundSql.getSql(), rowBounds.getOffset(), rowBounds.getLimit());

			handler.setValue("delegate.boundSql.sql", sql);
			handler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			handler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);

		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {

	}

	/*************************************************************/

	protected MetaObject resolveMetaObject(Invocation invocation) {
		MetaObject handler = MetaObject.forObject(invocation.getTarget(), DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

		while (handler.hasGetter("h")) {
			Object object = handler.getValue("h");
			handler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}

		while (handler.hasGetter("target")) {
			Object object = handler.getValue("target");
			handler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}

		return handler;
	}

	protected Configuration resolveConfiguration(MetaObject handler) {
		return (Configuration) handler.getValue("delegate.configuration");
	}

	protected BoundSql resolveBoundSql(MetaObject handler) {
		return (BoundSql) handler.getValue("delegate.boundSql");
	}

	protected RowBounds resolveRowBounds(MetaObject handler) {
		return (RowBounds) handler.getValue("delegate.rowBounds");
	}

}

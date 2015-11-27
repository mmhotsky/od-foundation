package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.tokenhandler;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.context.DynamicContext;
import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags.OgnlCache;

public class BindingTokenParser implements TokenHandler {

	private DynamicContext context;

	public BindingTokenParser(DynamicContext context) {
		this.context = context;
	}

	@Override
	public String handleToken(String content) {
		Object parameter = context.getBindings().get(DynamicContext.PARAMETER_OBJECT_KEY);

		if (parameter == null) {
			context.getBindings().put("value", null);

		} else if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
			context.getBindings().put("value", parameter);
		}

		Object value = OgnlCache.getValue(content, context.getBindings());

		return (value == null ? "" : String.valueOf(value));
	}

}

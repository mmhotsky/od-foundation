package cn.com.officedepot.foundation.spring.mybatis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("cn.com.officedepot.foundation.spring.mybatis.Bootstrap")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Bootstrap implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

	protected ApplicationContext applicationContext = null;

	@PostConstruct
	public void onStartup() throws Exception {
		Assert.notNull(applicationContext);
		this.init();
	}

	@PreDestroy
	public void onShutdown() {
		applicationContext = null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	protected void init() {
		BeanDefinition beanDefinition = ((GenericApplicationContext) applicationContext).getBeanFactory().getBeanDefinition("dataSource");
		String description = beanDefinition.getDescription();
		PropertyValue pv = beanDefinition.getPropertyValues().getPropertyValue("url");
		String url = ((TypedStringValue) pv.getValue()).getValue();
		logger.info("you are now using {}, url: {}", description, url);
	}

	public Object resolveValueIfNecessary(Object propertyName, Object value) {
		// if (value instanceof TypedStringValue) {
		// TypedStringValue typedStringValue = (TypedStringValue) value;
		// try {
		// Class resolvedTargetType = resolveTargetType(typedStringValue);
		// if (resolvedTargetType != null) {
		// return this.typeConverter.convertIfNecessary(typedStringValue.getValue(), resolvedTargetType);
		// } else {
		// // No target type specified - no conversion necessary...
		// return typedStringValue.getValue();
		// }
		// } catch (Throwable ex) {
		// // Improve the message by showing the context.
		// throw new BeanCreationException(this.beanDefinition.getResourceDescription(), this.beanName, "Error converting typed String value for " + argName, ex);
		// }
		// }
		return null;
	}

}

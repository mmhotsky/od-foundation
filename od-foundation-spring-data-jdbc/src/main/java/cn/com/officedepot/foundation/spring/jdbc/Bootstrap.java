package cn.com.officedepot.foundation.spring.jdbc;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.com.officedepot.foundation.spring.context.support.MessageSourceUtils;

@Component("cn.com.officedepot.foundation.spring.jdbc.Bootstrap")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Bootstrap implements ApplicationContextAware, MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

	protected ApplicationContext applicationContext = null;

	protected MessageSource message = null;

	@PostConstruct
	public void onStartup() throws Exception {
		Assert.notNull(applicationContext);
		Assert.notNull(message);

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

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.message = messageSource;
	}

	protected void init() {
		logger.info("{}: {}", this.resolveMessageKey(), this.resolveDataSourceDescription());
	}

	protected Object resolveMessageKey() {
		return message.getMessage(MessageSourceUtils.resolveClassMessageKeyPrefix(this.getClass()) + "nowusing", null, Locale.getDefault());
	}

	protected String resolveDataSourceDescription() {
		BeanDefinition beanDefinition = ((GenericApplicationContext) applicationContext).getBeanFactory().getBeanDefinition("dataSource");
		return beanDefinition.getDescription();
	}

}

package cn.com.officedepot.foundation.spring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import uk.org.lidalia.sysoutslf4j.context.LogLevel;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

@Component("cn.com.officedepot.foundation.spring.Bootstrap")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Bootstrap implements ApplicationContextAware, EnvironmentAware, MessageSourceAware {

	protected ApplicationContext applicationContext = null;

	protected Environment env = null;

	protected MessageSource message = null;

	@PostConstruct
	public void onStartup() throws Exception {
		Assert.notNull(applicationContext);
		Assert.notNull(env);
		Assert.notNull(message);

		SysOutOverSLF4J.sendSystemOutAndErrToSLF4J(LogLevel.DEBUG, LogLevel.ERROR);
	}

	@PreDestroy
	public void onShutdown() {
		applicationContext = null;
		env = null;
		message = null;

		SysOutOverSLF4J.stopSendingSystemOutAndErrToSLF4J();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.env = environment;
	}

	@Override
	public void setMessageSource(final MessageSource messageSource) {
		this.message = messageSource;
	}

}

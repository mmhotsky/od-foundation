package cn.com.officedepot.foundation.spring;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.util.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ ServletTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath*:/conf/beans.xml" })
@FixMethodOrder(MethodSorters.DEFAULT)
public class SpringTest implements ApplicationContextAware, EnvironmentAware, MessageSourceAware {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected ApplicationContext applicationContext = null;

	protected Environment env = null;

	protected MessageSource message = null;

	@Before
	public void onStartup() throws Exception {
		Assert.assertNotNull(applicationContext);
		Assert.assertNotNull(env);
		Assert.assertNotNull(message);
	}

	@After
	public void onShutdown() throws Exception {
		applicationContext = null;
		env = null;
		message = null;
	}

	public <T> T getBean(Class<T> clazz) {
		return this.applicationContext.getBean(clazz);
	}

	public <T> T getBean(String beanName, Class<T> clazz) {
		return this.applicationContext.getBean(beanName, clazz);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
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

	@Test
	public void test0() {
		Assert.assertNotNull(applicationContext);
		Assert.assertNotNull(env);
		Assert.assertNotNull(message);

		for (Map.Entry<String, String> e : System.getenv().entrySet()) {
			logger.debug("[{}] {}: {}", message.getMessage("EnvionmentVariables", null, Locale.getDefault()), e.getKey(), e.getValue());
		}

		for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
			logger.debug("[{}] {}: {}", message.getMessage("SystemProperties", null, Locale.getDefault()), e.getKey(), e.getValue());
		}

		String[] beanNames = StringUtils.sortStringArray(this.applicationContext.getBeanDefinitionNames());
		if (ArrayUtils.isEmpty(beanNames)) {
			beanNames = new String[0];
		}
		for (String beanName : beanNames) {
			logger.debug("[Spring Bean] {}", beanName);
		}
	}

}

package cn.com.officedepot.foundation.spring;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import cn.com.officedepot.foundation.spring.context.support.MessageSourceUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ ServletTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath*:/conf/beans.xml" })
@FixMethodOrder(MethodSorters.DEFAULT)
public class SpringMessageSourceTest implements MessageSourceAware {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected MessageSource message = null;

	@Before
	public void onStartup() throws Exception {
		Assert.assertNotNull(message);
	}

	@After
	public void onShutdown() throws Exception {
		message = null;
	}

	@Override
	public void setMessageSource(final MessageSource messageSource) {
		this.message = messageSource;
	}

	@Test
	public void test0() {
		if (Locale.SIMPLIFIED_CHINESE.equals(Locale.getDefault())) {
			Assert.assertEquals("测试", message.getMessage(MessageSourceUtils.resolveClassMessageKeyPrefix(this.getClass()) + "test", null, Locale.getDefault()));

		} else if (Locale.US.equals(Locale.getDefault())) {
			Assert.assertEquals("test", message.getMessage(MessageSourceUtils.resolveClassMessageKeyPrefix(this.getClass()) + "test", null, Locale.getDefault()));

		} else {
			Assert.fail();
		}
	}

	@Test
	public void test1() {
		if (Locale.SIMPLIFIED_CHINESE.equals(Locale.getDefault())) {
			Assert.assertEquals("环境变量", message.getMessage(MessageSourceUtils.resolveEnvionmentVariablesMessageKey(), null, Locale.getDefault()));

		} else if (Locale.US.equals(Locale.getDefault())) {
			Assert.assertEquals("Envionment Variables", message.getMessage(MessageSourceUtils.resolveEnvionmentVariablesMessageKey(), null, Locale.getDefault()));

		} else {
			Assert.fail();
		}
	}

}

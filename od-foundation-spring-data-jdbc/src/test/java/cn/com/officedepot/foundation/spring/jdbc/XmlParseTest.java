package cn.com.officedepot.foundation.spring.jdbc;

import java.io.File;
import java.util.Iterator;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.SqlAnalyzer;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, SqlScriptsTestExecutionListener.class, ServletTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class, DirtiesContextBeforeModesTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath*:/conf/beans.xml" })
@FixMethodOrder(MethodSorters.DEFAULT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class XmlParseTest {

	@Resource(name = "sqlAnalyzer")
	protected SqlAnalyzer sqlAnalyzer = null;

	@Before
	public void onStartup() throws Exception {
		Assert.assertNotNull(sqlAnalyzer);
	}

	@After
	public void onShutdown() throws Exception {
		sqlAnalyzer = null;
	}

	@Test
	public void test1() throws Exception {
		SAXReader sax = new SAXReader();
		for (File file : sqlAnalyzer.getPackages()) {
			Document xmlDoc = sax.read(file);
			Element root = xmlDoc.getRootElement(); // 根节点
			Iterator<?> it = root.elementIterator("sql");
			while (it.hasNext()) {
				Element sqlElement = (Element) it.next();
			}
		}
	}

}

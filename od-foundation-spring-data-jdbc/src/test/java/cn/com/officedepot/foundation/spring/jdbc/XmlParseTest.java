package cn.com.officedepot.foundation.spring.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

	@Resource(name = "jdbcTemplate")
	protected JdbcTemplate jdbcTemplate = null;

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
		Integer paras = 1;
		jdbcTemplate.query(sqlAnalyzer.get("", "", paras), new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return null;
			}

		}, paras);
	}

}

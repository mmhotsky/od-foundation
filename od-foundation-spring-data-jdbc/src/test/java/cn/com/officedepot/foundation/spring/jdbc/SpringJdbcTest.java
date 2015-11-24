package cn.com.officedepot.foundation.spring.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.officedepot.model.Model;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, SqlScriptsTestExecutionListener.class, ServletTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class, DirtiesContextBeforeModesTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath*:/conf/beans.xml" })
@FixMethodOrder(MethodSorters.DEFAULT)
@Rollback(value = true)
@Transactional(transactionManager = "transactionManager", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringJdbcTest {

	@Resource(name = "jdbcTemplate")
	protected JdbcTemplate jdbcTemplate = null;

	@Resource(name = "testsql")
	protected Properties testSql = null;

	@Before
	public void onStartup() throws Exception {
		Assert.assertNotNull(jdbcTemplate);
		Assert.assertNotNull(testSql);
	}

	@After
	public void onShutdown() throws Exception {
		jdbcTemplate = null;
		testSql = null;
	}

	/**
	 * 测试jdbc插入
	 */
	@Test
	@Transactional(readOnly = false)
	public void test1() {
		String sql = null;
		Integer obj = null;

		// 插入
		sql = testSql.getProperty("test1", "");
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setInt(i++, 1);
				ps.setString(i++, "str");
			}
		});

		// 校验
		sql = testSql.getProperty("check", "");
		obj = jdbcTemplate.queryForObject(sql, new Object[] { 1, "str" }, Integer.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);
	}

	/**
	 * 测试jdbc修改
	 */
	@Test
	@Transactional(readOnly = false)
	public void test2() {
		String sql = null;
		Integer obj = null;

		// 插入
		sql = testSql.getProperty("test1", "");
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setInt(i++, 1);
				ps.setString(i++, "str");
			}
		});

		// 校验
		sql = testSql.getProperty("check", "");
		obj = jdbcTemplate.queryForObject(sql, new Object[] { 1, "str" }, Integer.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);

		// 修改
		sql = testSql.getProperty("test2", "");
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setString(i++, "str update");
				ps.setInt(i++, 1);
			}
		});

		// 校验
		sql = testSql.getProperty("check", "");
		obj = jdbcTemplate.queryForObject(sql, new Object[] { 1, "str update" }, Integer.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);
	}

	/**
	 * 测试jdbc删除
	 */
	@Test
	@Transactional(readOnly = false)
	public void test3() {
		String sql = null;
		Integer obj = null;

		// 插入
		sql = testSql.getProperty("test1", "");
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setInt(i++, 1);
				ps.setString(i++, "str");
			}
		});

		// 校验
		sql = testSql.getProperty("check", "");
		obj = jdbcTemplate.queryForObject(sql, new Object[] { 1, "str" }, Integer.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);

		// 删除
		sql = testSql.getProperty("test3", "");
		sql = StringUtils.replace(sql, "#{id}", "1");
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setInt(i++, 1);
			}
		});

		// 校验
		sql = testSql.getProperty("check", "");
		obj = jdbcTemplate.queryForObject(sql, new Object[] { 1, "str" }, Integer.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(0), obj);
	}

	/**
	 * 测试jdbc回滚
	 */
	@Test
	@Transactional(readOnly = false)
	public void test4() {
		String sql = null;
		Integer obj = null;

		// 插入
		sql = testSql.getProperty("test1", "");
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setInt(i++, 1);
				ps.setString(i++, "str");
			}
		});

		// 校验
		sql = testSql.getProperty("check", "");
		obj = jdbcTemplate.queryForObject(sql, new Object[] { 1, "str" }, Integer.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);

		// 回滚
		TestTransaction.flagForRollback();
		TestTransaction.end();
		TestTransaction.start();

		// 校验
		sql = testSql.getProperty("check", "");
		obj = jdbcTemplate.queryForObject(sql, new Object[] { 1, "str" }, Integer.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(0), obj);
	}

	/**
	 * 测试jdbc分页查询
	 */
	@Test
	@Transactional(readOnly = false)
	public void test6() {
		String sql = null;
		Integer obj = null;
		final int ROW_COUNT = 59;

		// 插入
		final List<Model> paras = new ArrayList<Model>();
		for (int i = 1; i <= ROW_COUNT; i++) {
			paras.add(new Model(i, "str" + i));
		}

		sql = testSql.getProperty("test1", "");
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int j = 1;
				ps.setInt(j++, paras.get(i).getId());
				ps.setString(j++, paras.get(i).getStr());
			}

			@Override
			public int getBatchSize() {
				return paras.size();
			}
		});

		// 校验
		sql = testSql.getProperty("checkAll", "");
		obj = jdbcTemplate.queryForObject(sql, new Object[0], Integer.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(ROW_COUNT), obj);

		List<Model> results = null;
		sql = testSql.getProperty("test5", "");

		// 查询
		results = jdbcTemplate.query(sql, new Object[] { 0, ROW_COUNT }, rowMapper);
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == ROW_COUNT);

		// 查询
		results = jdbcTemplate.query(sql, new Object[] { 0, 7 }, rowMapper);
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == 7);

		// 查询
		results = jdbcTemplate.query(sql, new Object[] { 1, 7 }, rowMapper);
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == 7);
	}

	private RowMapper<Model> rowMapper = new RowMapper<Model>() {

		@Override
		public Model mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Model(rs.getInt(1), rs.getString(2));
		}

	};

}

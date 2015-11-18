package cn.com.officedepot.foundation.spring.mybatis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mybatis.spring.SqlSessionTemplate;
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
public class SpringMybatisTest {

	private static final String DEFAULT_NAMESPACE = "mybatis.dbmapper.test.";

	@Resource(name = "sqlSessionTemplate")
	protected SqlSessionTemplate sqlTemplate = null;

	@Before
	public void onStartup() throws Exception {
		Assert.assertNotNull(sqlTemplate);
	}

	@After
	public void onShutdown() throws Exception {
		sqlTemplate = null;
	}

	/**
	 * 测试mybatis插入
	 */
	@Test
	@Transactional(readOnly = false)
	public void test1() {
		// 插入
		Model paras = new Model(1, "str");
		sqlTemplate.insert(DEFAULT_NAMESPACE + "test1", paras);

		// 校验
		Integer obj = sqlTemplate.selectOne(DEFAULT_NAMESPACE + "check", paras);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);
	}

	/**
	 * 测试mybatis修改
	 */
	@Test
	@Transactional(readOnly = false)
	public void test2() {
		Model paras = null;
		Integer obj = null;

		// 插入
		paras = new Model(1, "str");
		sqlTemplate.insert(DEFAULT_NAMESPACE + "test1", paras);

		// 校验
		obj = sqlTemplate.selectOne(DEFAULT_NAMESPACE + "check", paras);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);

		// 修改
		paras.setStr("str update");
		sqlTemplate.update(DEFAULT_NAMESPACE + "test2", paras);

		// 校验
		obj = sqlTemplate.selectOne(DEFAULT_NAMESPACE + "check", paras);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);
	}

	/**
	 * 测试mybatis删除
	 */
	@Test
	@Transactional(readOnly = false)
	public void test3() {
		Model paras = null;
		Integer obj = null;

		// 插入
		paras = new Model(1, "str");
		sqlTemplate.insert(DEFAULT_NAMESPACE + "test1", paras);

		// 校验
		obj = sqlTemplate.selectOne(DEFAULT_NAMESPACE + "check", paras);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);

		// 删除
		sqlTemplate.delete(DEFAULT_NAMESPACE + "test3", paras);

		// 校验
		obj = sqlTemplate.selectOne(DEFAULT_NAMESPACE + "check", paras);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(0), obj);
	}

	/**
	 * 测试mybatis回滚
	 * 
	 * @throws SQLException
	 */
	@Test
	@Transactional(readOnly = false)
	public void test4() {
		Model paras = null;
		Integer obj = null;

		// 插入
		paras = new Model(1, "str");
		sqlTemplate.insert(DEFAULT_NAMESPACE + "test1", paras);

		// 校验
		obj = sqlTemplate.selectOne(DEFAULT_NAMESPACE + "check", paras);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);

		// 回滚
		TestTransaction.flagForRollback();
		TestTransaction.end();
		TestTransaction.start();

		// 校验
		paras = new Model(1, "str");
		obj = sqlTemplate.selectOne(DEFAULT_NAMESPACE + "check", paras);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(0), obj);
	}

	public void test5() {
		throw new RuntimeException("rollback");
	}

	/**
	 * 测试mybatis分页查询
	 */
	@Test
	@Transactional(readOnly = false)
	public void test6() {
		final int ROW_COUNT = 59;

		// 插入
		List<Model> paras = new ArrayList<Model>();
		for (int i = 1; i <= ROW_COUNT; i++) {
			paras.add(new Model(i, "str" + i));
		}
		sqlTemplate.insert(DEFAULT_NAMESPACE + "insert-batch", paras);

		// 校验
		Integer obj = sqlTemplate.selectOne(DEFAULT_NAMESPACE + "check", null);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(ROW_COUNT), obj);

		List<Model> results = null;

		// 查询
		results = sqlTemplate.selectList(DEFAULT_NAMESPACE + "test5");
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == ROW_COUNT);

		// 查询
		results = sqlTemplate.selectList(DEFAULT_NAMESPACE + "test5", null);
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == ROW_COUNT);

		// 查询
		results = sqlTemplate.selectList(DEFAULT_NAMESPACE + "test5", new Model(1));
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == 1);

		// 查询
		results = sqlTemplate.selectList(DEFAULT_NAMESPACE + "test5", new Model(1, "str1"));
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == 1);

		// 查询
		results = sqlTemplate.selectList(DEFAULT_NAMESPACE + "test5", new Model(null, null));
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == ROW_COUNT);

		// 查询
		results = sqlTemplate.selectList(DEFAULT_NAMESPACE + "test5", null, new RowBounds(0, ROW_COUNT));
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == ROW_COUNT);

		// 查询
		results = sqlTemplate.selectList(DEFAULT_NAMESPACE + "test5", null, new RowBounds(0, 7));
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == 7);

		// 查询
		results = sqlTemplate.selectList(DEFAULT_NAMESPACE + "test5", null, new RowBounds(1, 7));
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == 7);

		// 查询
		results = sqlTemplate.selectList(DEFAULT_NAMESPACE + "test5", new Model(2), new RowBounds(0, 7));
		// 校验
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == 1);
	}

}

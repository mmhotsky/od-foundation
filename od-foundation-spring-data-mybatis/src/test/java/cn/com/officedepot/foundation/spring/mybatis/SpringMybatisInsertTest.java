package cn.com.officedepot.foundation.spring.mybatis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.executor.BatchResult;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
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
public class SpringMybatisInsertTest {

	private static final String DEFAULT_TEST_NAMESPACE = MybatisUtils.resolveMybatisMapperPrefix() + "test.insert.";

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
	 * 测试mybatis单行插入时返回自增主键
	 */
	@Test
	@Transactional(readOnly = false)
	public void test1() {
		// 插入
		Model paras = new Model(null, "str");
		sqlTemplate.insert(DEFAULT_TEST_NAMESPACE + "test1", paras); // insert方法不再返回主键，只返回影响的行数
		sqlTemplate.flushStatements();
		Integer pk = paras.getId();

		// 校验
		Integer obj = sqlTemplate.selectOne(DEFAULT_TEST_NAMESPACE + "check", paras);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);
		Assert.assertNotNull(pk);
		Assert.assertTrue(pk > 0);
	}

	/**
	 * 测试mybatis批量插入时返回自增主键
	 */
	@Test
	@Transactional(readOnly = false)
	@Rollback(false)
	public void test2() {
		final int ROW_COUNT = 3;

		// 插入
		List<Model> paras = new ArrayList<Model>();
		for (int i = 1; i <= ROW_COUNT; i++) {
			paras.add(new Model(null, "str" + i));
		}
		sqlTemplate.insert(DEFAULT_TEST_NAMESPACE + "test2", paras);
		sqlTemplate.flushStatements();

		// 校验
		Integer obj = sqlTemplate.selectOne(DEFAULT_TEST_NAMESPACE + "check", null);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(ROW_COUNT), obj);
		for (int i = 0; i < ROW_COUNT; i++) {
			Integer pk = paras.get(i).getId();
			Assert.assertNotNull(pk);
			Assert.assertTrue(pk > 0);
		}
	}

	/**
	 * 测试mybatis单行插入时使用Sequence生成主键
	 */
	@Test
	@Transactional(readOnly = false)
	@IfProfileValue(name = "dialect", values = { "oracle" })
	public void test3() {
		// Assert.fail();
	}

	/**
	 * 测试mybatis单行插入时返回受影响的行数
	 */
	@Test
	@Transactional(readOnly = false)
	@Ignore
	public void test4() {
		// 插入
		Model paras = new Model(1, "str");
		sqlTemplate.insert(DEFAULT_TEST_NAMESPACE + "test1", paras);
		List<BatchResult> affected = sqlTemplate.flushStatements();

		// 校验
		Integer obj = sqlTemplate.selectOne(DEFAULT_TEST_NAMESPACE + "check", paras);
		Assert.assertNotNull(obj);
		Assert.assertEquals(Integer.valueOf(1), obj);

		int execCounts = affected.size();
		int affectedRows = 0;
		for (BatchResult r : affected) {
			for (int a : r.getUpdateCounts()) {
				affectedRows += a;
			}
		}
		Assert.assertEquals(1, execCounts);
		Assert.assertEquals(1, affectedRows);
	}

	/**
	 * 测试mybatis批量插入时返回受影响的行数
	 */
	@Test
	@Transactional(readOnly = false)
	public void test5() {

	}

	/**
	 * 测试mybatis单行插入时使用组合主键
	 */
	@Test
	@Transactional(readOnly = false)
	public void test6() {

	}

	/**
	 * 测试mybatis批量插入时使用组合主键
	 */
	@Test
	@Transactional(readOnly = false)
	public void test7() {

	}

	/**
	 * 测试mybatis插入时的异常处理
	 */
	@Test
	@Transactional(readOnly = false)
	public void test8() {

	}

	/**
	 * 测试mybatis插入时的自动提交
	 */
	@Test
	@Transactional(readOnly = false)
	public void test9() {

	}

	/**
	 * 测试mybatis插入时的手工提交
	 */
	@Test
	@Transactional(readOnly = false)
	public void test10() {

	}

}

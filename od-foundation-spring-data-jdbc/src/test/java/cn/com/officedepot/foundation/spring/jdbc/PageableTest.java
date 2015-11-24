package cn.com.officedepot.foundation.spring.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.officedepot.foundation.spring.jdbc.hotfix.page.Pageable;

@RunWith(BlockJUnit4ClassRunner.class)
public class PageableTest {

	private static final Logger logger = LoggerFactory.getLogger(PageableTest.class);

	@Before
	public void onStartup() throws Exception {

	}

	@After
	public void onShutdown() throws Exception {

	}

	@Test
	public void test1() {
		int total = 0;

		Pageable<String> results = new Pageable<String>(total, 1, 7);

		logger.debug(results.toString());
	}

	@Test
	public void test2() {
		int total = -1;

		Pageable<String> results = new Pageable<String>(total, 1, 7);

		logger.debug(results.toString());
	}

	@Test
	public void test3() {
		int total = 50;

		Pageable<String> results = new Pageable<String>(total, 1, 7);

		logger.debug(results.toString());
	}

	@Test
	public void test4() {
		int total = 50;

		Pageable<String> results = new Pageable<String>(total, 8, 7);

		logger.debug(results.toString());
	}

	@Test
	public void test5() {
		int total = 50;

		Pageable<String> results = new Pageable<String>(total, 8, 7);

		logger.debug(results.toString());
	}

	@Test
	public void test6() {
		int total = 50;

		Pageable<String> results = new Pageable<String>(total, 8, 0);

		logger.debug(results.toString());
	}

}

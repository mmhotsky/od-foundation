package cn.com.officedepot.foundation.spring.mybatis.hotfix.generator;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;

public class Jdbc3KeyGeneratorFixed extends Jdbc3KeyGenerator {

	@Override
	public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
		// List<Object> parameters = new ArrayList<Object>();
		// parameters.add(parameter);
		// processBatch(ms, stmt, parameters);
		processBatch(ms, stmt, getParameters(parameter));
	}

	@SuppressWarnings("unchecked")
	protected List<Object> getParameters(Object parameter) {
		List<Object> parameters = null;
		if (parameter instanceof List) {
			parameters = (List) parameter;

		} else if (parameter instanceof Map) {
			Map parameterMap = (Map) parameter;
			if (parameterMap.containsKey("list")) {
				parameters = (List) parameterMap.get("list");

			} else if (parameterMap.containsKey("array")) {
				parameters = Arrays.asList((Object[]) parameterMap.get("array"));
			}
		}

		if (parameters == null) {
			parameters = new ArrayList<Object>();
			parameters.add(parameter);
		}

		return parameters;
	}

}

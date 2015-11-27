package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.tokenhandler;

public class DynamicCheckerTokenParser implements TokenHandler {

	private boolean isDynamic;

	public boolean isDynamic() {
		return isDynamic;
	}

	@Override
	public String handleToken(String content) {
		this.isDynamic = true;
		return null;
	}

}

package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags;

import org.apache.commons.lang3.StringUtils;

public class GenericTokenParser implements TokenParser {

	protected static final char PLACEHOLDER = '?';
	private String open = "${";
	private String close = "}";

	public GenericTokenParser() {
		super();
	}

	public GenericTokenParser(String open, String close) {
		super();

		if (StringUtils.isBlank(open)) {
			throw new IllegalArgumentException("open tag must be not empty");
		}

		if (StringUtils.isBlank(close)) {
			throw new IllegalArgumentException("close tag must be not empty");
		}

		this.open = open;
		this.close = close;
	}

	@Override
	public String parse(String originalSql) {
		StringBuilder builder = new StringBuilder();

		if (StringUtils.isNoneBlank(originalSql)) {
			char[] cs = originalSql.toCharArray();
			int offset = 0;

			while (true) {
				int start = originalSql.indexOf(open, offset);
				int end = start;

				if (start > -1) {
					end = originalSql.indexOf(close, start + open.length());
					builder.append(cs, offset, start - offset);

					if (end < 0) {
						break;
					}

					builder.append(PLACEHOLDER);
					offset = end + close.length();

				} else {
					end = originalSql.indexOf(close, offset);

					if (end < 0) {
						end = cs.length;
					}

					builder.append(cs, offset, end - offset);

					break;
				}
			}
		}

		return builder.toString();
	}

}

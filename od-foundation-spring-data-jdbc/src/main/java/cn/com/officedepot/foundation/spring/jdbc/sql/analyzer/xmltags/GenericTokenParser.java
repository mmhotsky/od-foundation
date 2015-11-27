package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags;

public class GenericTokenParser {

	private final String open;
	private final String close;

	public GenericTokenParser(String open, String close) {
		this.open = open;
		this.close = close;
	}

	public String parse(String text) {
		StringBuilder builder = new StringBuilder();

		if (text != null && text.length() > 0) {
			char[] src = text.toCharArray();
			int offset = 0;
			int start = text.indexOf(open, offset);

			while (start > -1) {
				int end = text.indexOf(close, start);
				if (end == -1) {
					builder.append(src, offset, start - offset);
					builder.append("?");
					offset = src.length;

				} else {
					builder.append(src, offset, start - offset);
					builder.append("?");
					offset = end + close.length();
				}

				start = text.indexOf(open, offset);
			}

			if (offset < src.length) {
				builder.append(src, offset, src.length - offset);
			}
		}

		return builder.toString();
	}

}

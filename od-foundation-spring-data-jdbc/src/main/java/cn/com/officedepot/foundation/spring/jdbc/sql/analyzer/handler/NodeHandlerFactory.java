package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.handler;

public class NodeHandlerFactory {

	public static final NodeHandler buildNodeHandler(NodeType type) {
		switch (type) {
		case SQLGROUP:
			return new SqlGroupNodeHandler();
		case IMPORT:
			return new SqlGroupNodeHandler();
		case SQL:
			return new SqlGroupNodeHandler();
		case WHERE:
			return new SqlGroupNodeHandler();
		case IF:
			return new SqlGroupNodeHandler();
		case CHOOSE:
			return new SqlGroupNodeHandler();
		case WHEN:
			return new SqlGroupNodeHandler();
		case OTHERWISE:
			return new SqlGroupNodeHandler();
		case FOREACH:
			return new SqlGroupNodeHandler();
		default:
			throw new IllegalArgumentException();
		}
	}

}

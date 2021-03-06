package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.handler;

import java.util.List;

import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node.SqlNode;
import cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.node.XNode;

public interface NodeHandler {

	public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents);

}

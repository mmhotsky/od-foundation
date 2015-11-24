package cn.com.officedepot.foundation.spring.context.support;

import org.springframework.util.ClassUtils;

public class MessageSourceUtils {

	private static String messageKeySplit = ".";

	public static String resolveClassMessageKeyPrefix(Class<?> clazz) {
		StringBuffer buffer = new StringBuffer();

		buffer.append(ClassUtils.getPackageName(clazz));
		buffer.append(messageKeySplit);
		buffer.append(ClassUtils.getShortName(clazz));
		buffer.append(messageKeySplit);
		buffer.append("message");
		buffer.append(messageKeySplit);

		return buffer.toString();
	}

	public static String resolveEnvionmentVariablesMessageKey() {
		return "envionment.variables";
	}

	public static String resolveSystemPropertiesMessageKey() {
		return "system.properties";
	}

}

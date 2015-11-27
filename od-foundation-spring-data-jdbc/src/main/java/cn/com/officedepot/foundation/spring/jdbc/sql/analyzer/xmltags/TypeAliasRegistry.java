package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer.xmltags;

public class TypeAliasRegistry {

	Class<?> classForName(String name, ClassLoader[] classLoader) throws ClassNotFoundException {
		for (ClassLoader cl : classLoader) {
			if (null != cl) {
				try {
					Class<?> c = Class.forName(name, true, cl);
					if (null != c)
						return c;

				} catch (ClassNotFoundException e) {
				}
			}
		}

		throw new ClassNotFoundException("Cannot find class: " + name);
	}

	ClassLoader[] getClassLoaders(ClassLoader classLoader) {
		return new ClassLoader[] { classLoader, Thread.currentThread().getContextClassLoader(), getClass().getClassLoader(), ClassLoader.getSystemClassLoader() };
	}

}

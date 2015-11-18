package cn.com.officedepot.foundation.spring.context.support;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class MultipleMergeMessageSource extends ReloadableResourceBundleMessageSource {

	private static final String PROPERTIES_SUFFIX = ".properties";
	private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	@Override
	protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
		if (filename.startsWith(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
			return this.refreshClassPathProperties(filename, propHolder);

		} else {
			return super.refreshProperties(filename, propHolder);
		}
	}

	protected PropertiesHolder refreshClassPathProperties(String filename, PropertiesHolder holder) {
		long lastModified = -1;

		Properties properties = new Properties();
		try {
			Resource[] resources = resolver.getResources(filename + PROPERTIES_SUFFIX);

			for (Resource resource : resources) {
				String path = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
				properties.putAll(super.refreshProperties(path, holder).getProperties());

				if (lastModified < resource.lastModified()) {
					lastModified = resource.lastModified();
				}
			}

		} catch (IOException ignored) {

		}

		return new PropertiesHolder(properties, lastModified);
	}

}

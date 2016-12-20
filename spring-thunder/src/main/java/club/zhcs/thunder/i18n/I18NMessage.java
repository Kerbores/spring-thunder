package club.zhcs.thunder.i18n;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.google.common.collect.Maps;

import club.zhcs.thunder.ext.spring.SpringBeans;

public class I18NMessage {

	private Map<Locale, Properties> messageMap = Maps.newConcurrentMap();;

	private String basePath;

	public I18NMessage(String basePath) {
		this.basePath = basePath;
	}

	public String getMessage(String key) {
		Properties properties = messageMap.get(SpringBeans.getrResponse().getLocale());
		if (properties == null) {
			return null;
		}
		return properties.getProperty(key);
	}

	@PostConstruct
	public void init() throws IOException {
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = patternResolver.getResources(basePath);
		Locale[] locales = Locale.getAvailableLocales();

		for (Resource resource : resources) {
			for (Locale locale : locales) {
				if (!StringUtils.isBlank(locale.toString()) && StringUtils.containsIgnoreCase(resource.getURI().toString(), locale.toString() + ".properties")) {// 找到
					Properties properties = new Properties();
					properties.load(resource.getInputStream());
					if (messageMap.keySet().contains(locale)) {// 已有
						messageMap.get(locale).putAll(properties);
					} else {// 未添加
						messageMap.put(locale, properties);
					}

				}
			}
		}
	}

}

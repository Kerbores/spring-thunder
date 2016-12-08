package club.zhcs.thunder.ext.mybatis.utils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 王贵源
 *
 *         create at 2014年9月28日 下午1:37:44
 */
public class Classs {

	public static Set<Field> getAllfFields(Class<?> clazz) {
		Set<Field> target = new HashSet<Field>();

		for (Field field : clazz.getDeclaredFields()) {
			target.add(field);
		}

		while (!StringUtils.equals(clazz.getName(), "java.lang.Object")) {
			for (Field field : clazz.getDeclaredFields()) {
				target.add(field);
			}
			clazz = clazz.getSuperclass();
		}
		return target;
	}

}

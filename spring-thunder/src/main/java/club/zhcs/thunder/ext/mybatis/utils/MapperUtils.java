package club.zhcs.thunder.ext.mybatis.utils;

import java.lang.reflect.Field;

import club.zhcs.thunder.ext.mybatis.anno.Column;
import club.zhcs.thunder.ext.mybatis.anno.Id;

/**
 * @author 王贵源
 * 
 *         create at 2014年10月9日 下午3:12:08<br>
 *         mapper生成工具
 */
public class MapperUtils {

	public static String genMapper(Class clazz) {
		String target = "";
		for (Field f : Classs.getAllfFields(clazz)) {
			String col = getColumn(f);
			if (col != null) {
				target += " <result property=\"" + f.getName() + "\" column=\"" + col + "\" javaType=\"" + f.getType().getName() + "\" jdbcType=\""
						+ decode(f.getType().getName()) + "\"/>\n";
			}
		}
		return target;
	}

	/**
	 * @param name
	 * @return
	 */
	private static String decode(String name) {
		switch (name) {
		case "java.lang.Long":
			return "BIGINT";
		case "java.util.Date":
			return "TIMESTAMP";
		case "java.lang.String":
			return "VARCHAR";
		case "java.lang.Integer":
			return "BIGINT";
		case "int":
			return "BIGINT";
		case "long":
			return "BIGINT";
		case "boolean":
			return "TINYINT";
		case "java.lang.Boolean":
			return "TINYINT";
		default:
			break;
		}
		return null;
	}

	/**
	 * @param f
	 * @return
	 */
	private static String getColumn(Field f) {
		if (f.getAnnotation(Column.class) != null) {
			return f.getAnnotation(Column.class).value();
		}
		if (f.getAnnotation(Id.class) != null) {
			return f.getName();
		}
		return null;
	}

}

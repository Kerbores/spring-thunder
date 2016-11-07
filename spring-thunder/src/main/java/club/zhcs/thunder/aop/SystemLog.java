package club.zhcs.thunder.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author kerbores
 *
 * @email kerbores@gmail.com
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

	String description() default ""; //

	String methods() default ""; // 新增用户

	String module() default ""; // 模块名称 系统管理-用户管理－列表页面

}

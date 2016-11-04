package club.zhcs.thunder.ext.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.nutz.lang.Lang;

import club.zhcs.thunder.Thunder.SessionKeys;
import club.zhcs.thunder.ext.spring.SpringBeans;

/**
 * 
 * @author kerbores
 *
 * @email kerbores@gmail.com
 *
 */
public class LoginUserFun implements Function {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.Function#call(java.lang.Object[],
	 * org.beetl.core.Context)
	 */
	@Override
	public Object call(Object[] paras, Context ctx) {
		if (paras != null && paras.length > 0) {
			String key = paras[0].toString();
			Object obj = SpringBeans.getRequest().getSession().getAttribute(SessionKeys.USER_KEY);
			return obj == null ? null : Lang.obj2nutmap(obj).get(key);
		}
		return SpringBeans.getRequest().getSession().getAttribute(SessionKeys.USER_KEY);
	}

}

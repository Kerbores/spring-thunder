package club.zhcs.thunder.ext.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;

import club.zhcs.thunder.ext.spring.SpringBeans;
import club.zhcs.thunder.i18n.I18NMessage;

public class MessageFunction implements Function {

	@Override
	public Object call(Object[] arg0, Context context) {
		I18NMessage message = SpringBeans.getBean(I18NMessage.class);
		if (arg0 == null || arg0.length == 0) {
			return null;
		}
		if (message == null) {
			return null;
		}
		return message.getMessage(arg0[0].toString());
	}

}

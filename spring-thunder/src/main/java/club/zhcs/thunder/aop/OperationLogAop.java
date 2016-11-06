package club.zhcs.thunder.aop;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.nutz.castor.Castors;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import club.zhcs.thunder.biz.log.OperationLogService;
import club.zhcs.thunder.domain.log.OperationLog;
import club.zhcs.thunder.ext.spring.SpringBeans;
import club.zhcs.titans.utils.db.Result;

/**
 * @author kerbores
 *
 * @email kerbores@gmail.com
 *
 */
@Aspect
@Component
public class OperationLogAop {

	@Resource
	OperationLogService operationLogService;

	@Pointcut("@annotation(club.zhcs.thunder.aop.SystemLog)")
	public void cutSystemLog() {

	}

	public SystemLog getSystemLog(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		SystemLog target = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					target = method.getAnnotation(SystemLog.class);
					break;
				}
			}
		}
		return target;
	}

	@Around("cutSystemLog()")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

		OperationLog operationLog = new OperationLog();

		String ip = Lang.getIP(SpringBeans.getRequest());
		String user = SecurityUtils.getSubject().getPrincipal().toString();
		SystemLog log = getSystemLog(point);

		operationLog.setAccount(user);
		operationLog.setMethodMeta(point.getSignature().getName());
		operationLog.setParameters(getParameter(point));
		operationLog.setAction(log.methods());
		operationLog.setIp(ip);
		operationLog.setModule(log.module());

		Stopwatch stopwatch = Stopwatch.begin();
		Object obj = point.proceed();
		stopwatch.stop();

		Object rObj = getMethodReturnObject(point, obj);// 把业务的返回值取回来

		operationLog.setMethodReturn(Json.toJson(rObj, JsonFormat.compact()));

		if (rObj instanceof Result) {
			operationLog.setDescription(Castors.me().castTo(obj, Result.class).isSuccess() ? "操作成功" : "操作失败");
		}

		if (Strings.isBlank(operationLog.getDescription())) {
			operationLog.setDescription(log.description());
		}

		operationLog.setOperationTime(stopwatch.getDuration());
		operationLogService.save(operationLog);
		return obj;
	}

	/**
	 * @param point
	 * @return
	 */
	private Object getMethodReturnObject(ProceedingJoinPoint point, Object obj) {
		// 如果是 ajax 请求,返回方法的返回值
		if (point.getSignature().getDeclaringType().getAnnotation(ResponseBody.class) != null) {
			return obj;
		}
		// 如果不是获取 Model 中的属性
		for (Object o : point.getArgs()) {
			if (o instanceof Model) {
				Model m = (Model) o;
				return m.asMap();
			}
		}
		return null;// void 方法返回空
	}

	/**
	 * @param point
	 * @return
	 */
	private String getParameter(ProceedingJoinPoint point) {
		List<Object> target = Lists.newArrayList();

		Lang.each(point.getArgs(), new Each<Object>() {

			@Override
			public void invoke(int arg0, Object obj, int arg2) throws ExitLoop, ContinueLoop, LoopException {
				if (obj instanceof ServletRequest) {
					target.add(((ServletRequest) obj).getParameterMap());
				} else if (obj instanceof ServletResponse || obj instanceof HttpSession || obj instanceof Model) { // response/session/model

				} else {
					target.add(obj);
				}
			}
		});

		return Json.toJson(target, JsonFormat.compact());
	}
}

package club.zhcs.thunder.domain.log;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Times;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * 
 * @author kerbores
 *
 * @email kerbores@gmail.com
 *
 */
@Table("t_opt_log")
public class OperationLog extends Entity {

	@Column("opt_account")
	@Comment("操作人员账户")
	private String account;

	@Column("opt_ip")
	@Comment("操作人员 ip 地址")
	private String ip;

	@Column("opt_module")
	@Comment("操作功能模块")
	private String module;

	@Column("opt_action")
	@Comment("操作的具体功能")
	private String action;

	@Column("opt_description")
	@Comment("功能描述")
	private String description;

	@Column("opt_method_meta")
	@Comment("方法描述")
	@ColDefine(width = 200)
	private String methodMeta;

	@Column("opt_parameters")
	@Comment("入参信息")
	@ColDefine(width = 6000)
	private String parameters;

	@Column("opt_method_return")
	@Comment("响应信息")
	@ColDefine(width = 6000)
	private String methodReturn;

	@Column("opt_action_time")
	@Comment("操作时间")
	private Date actionTime = Times.now();

	@Column("opt_execution_time")
	@Comment("方法执行时间")
	private long operationTime;

	public String getAccount() {
		return account;
	}

	/**
	 * @return the methodMeta
	 */
	public String getMethodMeta() {
		return methodMeta;
	}

	/**
	 * @param methodMeta
	 *            the methodMeta to set
	 */
	public void setMethodMeta(String methodMeta) {
		this.methodMeta = methodMeta;
	}

	/**
	 * @return the parameters
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the methodReturn
	 */
	public String getMethodReturn() {
		return methodReturn;
	}

	/**
	 * @param methodReturn
	 *            the methodReturn to set
	 */
	public void setMethodReturn(String methodReturn) {
		this.methodReturn = methodReturn;
	}

	public String getAction() {
		return action;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public String getDescription() {
		return description;
	}

	public String getIp() {
		return ip;
	}

	public String getModule() {
		return module;
	}

	public long getOperationTime() {
		return operationTime;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setOperationTime(long operationTime) {
		this.operationTime = operationTime;
	}

}

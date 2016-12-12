package club.zhcs.thunder.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.nutz.lang.Times;


@Table(name ="t_user")
public class User {
	
	public static enum Status {
		ACTIVED("正常"), DISABLED("禁用");
		/**
		 * 中文描述,主要用于页面展示
		 */
		private String name;

		private Status(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	/**
	 * 用户类型
	 * 
	 * @author 王贵源<kerbores>
	 *
	 *         create at 2016年1月11日 下午3:55:01
	 */
	public static enum Type {
		PLATFORM, MERCHANTS, CUSTOMER
	}
	
	
	@Id
	@GeneratedValue(generator = "JDBC")
	private int id;

	@Column(name  ="u_name")
	private String name;

	@Column(name ="u_real_name")
	private String realName;

	@Column(name ="u_nick_name")
	private String nickName;

	@Column(name ="u_pwd")
	private String password;

	@Column(name ="u_phone")
	private String phone;

	@Column(name ="u_email")
	private String email;

	@Column(name ="u_head_key")
	private String headKey;


	@Column(name ="u_create_time")
	private Date createTime = Times.now();

	@Column(name ="u_status")
	private Status status = Status.ACTIVED;

	@Column(name ="u_type")
	private Type userType = Type.PLATFORM;

	@Column(name ="u_openid")
	private String openid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadKey() {
		return headKey;
	}

	public void setHeadKey(String headKey) {
		this.headKey = headKey;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Type getUserType() {
		return userType;
	}

	public void setUserType(Type userType) {
		this.userType = userType;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	

}

package club.zhcs.thunder.domain;

import club.zhcs.thunder.domain.base.Entity;
import org.nutz.dao.entity.annotation.*;
import org.nutz.lang.Times;

import java.util.Date;

/**
 * @author 王贵源
 * @email kerbores@kerbores.com
 * @description 用户实体类
 * @copyright 内部代码, 禁止转发
 * @time 2016年1月26日 下午2:18:45
 */
@Table("t_user")
@Comment("用户表")
public class User extends Entity {

    @Column("u_name")
    @Name
    @Comment("用户登录名")
    private String name;
    @Column("u_real_name")
    @Comment("用户真实姓名")
    private String realName;
    @Column("u_nick_name")
    @Comment("用户昵称")
    private String nickName;
    @Column("u_pwd")
    @Comment("用户登录密码")
    private String password;
    @Column("u_phone")
    @Comment("用户手机号")
    private String phone;
    @Column("u_email")
    @Comment("用户邮箱")
    private String email;
    @Column("u_head_key")
    @Comment("用户头像")
    @ColDefine(width = 200)
    private String headKey;
    private String headUrl;
    @Column("u_create_time")
    @Comment("用户创建时间")
    private Date createTime = Times.now();
    @Column("u_status")
    @Comment("用户状态")
    private Status status = Status.ACTIVED;
    @Column("u_type")
    @Comment("用户类型")
    private Type userType = Type.PLATFORM;
    @Column("u_openid")
    @Comment("用户微信 openid")
    private String openid;

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (getId() != other.getId())
            return false;
        if (userType != other.userType)
            return false;
        return true;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getHeadUrl() {
        return headUrl;
    }

    protected void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName the nickName to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the userType
     */
    public Type getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    protected void setUserType(Type userType) {
        this.userType = userType;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + getId();
        result = prime * result + ((userType == null) ? 0 : userType.hashCode());
        return result;
    }

    public boolean isAvailable() {
        return status == Status.ACTIVED;
    }

    /**
     * @author 王贵源
     * @email kerbores@kerbores.com
     * @description 用户状态枚举
     * @copyright 内部代码, 禁止转发
     * @time 2016年1月26日 下午2:18:59
     */
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
     *         <p>
     *         create at 2016年1月11日 下午3:55:01
     */
    public static enum Type {
        PLATFORM, MERCHANTS, CUSTOMER
    }

}
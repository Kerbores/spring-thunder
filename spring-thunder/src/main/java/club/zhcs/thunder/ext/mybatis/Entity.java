package club.zhcs.thunder.ext.mybatis;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import club.zhcs.thunder.ext.mybatis.anno.Column;
import club.zhcs.thunder.ext.mybatis.anno.Id;
import club.zhcs.thunder.ext.mybatis.anno.Table;
import club.zhcs.thunder.ext.mybatis.utils.Classs;

/**
 * @author 王贵源
 *
 *         create at 2014年9月29日 下午2:20:31
 */
public class Entity implements Serializable {

	/**
	 * 创建时间
	 */
	@Column("create_time")
	private Date createTime = new Date();// 默认当前
	/**
	 * 更新时间
	 */
	@Column("update_time")
	private Date updateTime = new Date();// 默认当前

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取POJO对应的表名 需要POJO中的属性定义@Table()
	 * 
	 * @return
	 */
	public String tablename() {
		return tablename(this.getClass());
	}

	/**
	 * 递归获取父类的表名
	 * 
	 * @param clazz
	 * @return
	 */
	public String tablename(Class clazz) {
		Table table = (Table) clazz.getAnnotation(Table.class);
		if (table != null)
			return table.value();
		else if (!StringUtils.equals(clazz.getSuperclass().getName(), "java.lang.Object")) {
			return tablename(clazz.getSuperclass());
		} else {
			throw new RuntimeException("没有找到表名");
		}
	}

	/**
	 * 获取POJO对应的主键名称 需要POJO中的属性定义@Id
	 * 
	 * @return
	 */
	public String id() {
		for (Field field : Classs.getAllfFields(this.getClass())) {
			if (field.isAnnotationPresent(Id.class)) {
				String id = field.getAnnotation(Id.class).value();
				return StringUtils.equals("", id) ? field.getName() : id;
			}
		}
		return "id";

	}
}

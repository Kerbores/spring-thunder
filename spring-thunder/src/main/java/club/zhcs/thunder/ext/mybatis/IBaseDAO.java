package club.zhcs.thunder.ext.mybatis;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import club.zhcs.thunder.ext.mybatis.utils.Condition;
import club.zhcs.thunder.ext.mybatis.utils.SqlGener;

/**
 * @author 王贵源
 * 
 *         create at 2014年9月29日 下午2:22:35
 */
public interface IBaseDAO<T extends Entity> {
	/**
	 * 删除
	 * 
	 * @param t
	 *            实体对象,只要有主键属性即可(在目前项目中约定为long类型属性名称为id注解@Id的字段)
	 */
	@DeleteProvider(type = SqlGener.class, method = "delete")
	public int delete(T t);

	/**
	 * 新增
	 * 
	 * @param t
	 * @return 
	 *         影响的记录条数,如果需要获取id从实体上去获得,同时为了这个自增id的实现我们需要规范所有实体的主键属性为id类型为long数据库字段为id
	 */
	@InsertProvider(type = SqlGener.class, method = "insert")
	@SelectKey(resultType = Long.class, before = false, keyProperty = "id", keyColumn = "id", statement = { "SELECT LAST_INSERT_ID() AS ID  " })
	public long save(T t);

	/**
	 * 更新
	 * 
	 * @param t
	 * @return 影响的记录条数
	 */
	@UpdateProvider(type = SqlGener.class, method = "update")
	public int update(T t);

	/**
	 * 更新对象的非空字段,对象一定有id
	 * 
	 * @param t
	 *            待更新对象
	 * @return 影响的记录条数
	 */
	@UpdateProvider(type = SqlGener.class, method = "updateNotNull")
	public int updateNotNull(T t);

	/**
	 * 更新指定字段
	 * 
	 * @param t
	 *            待更新对象
	 * @param fields
	 *            待更新字段
	 * @return 影响的记录条数
	 */
	@UpdateProvider(type = SqlGener.class, method = "updateFields")
	public int updateFields(@Param("entity") T t, @Param("fields") String... fields);

	/**
	 * 计数
	 * 
	 * @param clazzOfT
	 * @return
	 */
	@SelectProvider(type = SqlGener.class, method = "count")
	public int count(Class<? extends Entity> clazzOfT);

	/**
	 * 
	 * 根据条件计数
	 * 
	 * @param clazz
	 * @param cnd
	 * @return
	 */
	@SelectProvider(type = SqlGener.class, method = "countByCnd")
	public int countByCondition(@Param("clazz") Class<? extends Entity> clazz, @Param("cnd") Condition cnd);

}

package club.zhcs.thunder.biz.base;

import java.util.List;

import club.zhcs.thunder.bean.BaseEntity;

/**
 * @author SK.Loda
 * @description 业务基类
 * @date 2016年1月12日 下午3:40:22
 */
public interface IBaseService<T extends BaseEntity> {

	// ============C==========

	int save(T obj);

	int saveIgnoreNull(T obj);

	int saveList(List<T> list);

	// ============R==========

	T findById(Integer id);

	List<T> listAll();

	List<T> searchByExample(T example);

	// ============U==========

	int update(T obj);

	int updateIgnoreNull(T obj);

	// ============D==========

	int deleteById(Integer id);

}

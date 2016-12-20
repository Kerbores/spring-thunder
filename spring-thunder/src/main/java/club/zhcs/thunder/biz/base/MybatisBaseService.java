package club.zhcs.thunder.biz.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;

import club.zhcs.thunder.bean.BaseEntity;
import club.zhcs.thunder.mapper.BaseMapper;

/**
 * 业务基类，实体业务类只需继承即可拥有基础crud方法
 * 
 * @author JiangKun
 * @date 2016年7月19日
 */
public abstract class MybatisBaseService<T extends BaseEntity> implements IBaseService<T> {

	// Spring4.x 泛型限定注入
	@Autowired
	protected BaseMapper<T> mapper;

	protected Class<T> klass;

	protected Field idField;

	protected Class<T> getEntityClass() {
		return klass;
	}

	@PostConstruct
	private void init() {
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<T> entityClass = (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
		this.klass = entityClass;
		for (Field field : klass.getFields()) {
			if (field.getAnnotation(Id.class) != null) {
				idField = field;
				break;
			}
		}
	}

	@Override
	public int save(T obj) {
		return mapper.insert(obj);
	}

	@Override
	public int saveList(List<T> list) {
		return mapper.insertList(list);
	}

	@Override
	public int saveIgnoreNull(T obj) {
		return mapper.insertSelective(obj);
	}

	@Override
	public T findById(Integer id) {
		@SuppressWarnings("unchecked")
		T entity = (T) ReflectUtils.newInstance(klass);
		try {
			idField.setAccessible(true);
			idField.set(entity, id);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return mapper.selectByPrimaryKey(entity);
	}

	@Override
	public List<T> listAll() {
		return mapper.selectAll();
	}

	@Override
	public List<T> searchByExample(T example) {
		return mapper.selectByExample(example);
	}

	@Override
	public int update(T obj) {
		return mapper.updateByPrimaryKey(obj);
	}

	@Override
	public int updateIgnoreNull(T obj) {
		return mapper.updateByPrimaryKeySelective(obj);
	}

	@Override
	public int deleteById(Integer id) {
		@SuppressWarnings("unchecked")
		T entity = (T) ReflectUtils.newInstance(klass);
		try {
			idField.setAccessible(true);
			idField.set(entity, id);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return mapper.deleteByPrimaryKey(entity);
	}

}

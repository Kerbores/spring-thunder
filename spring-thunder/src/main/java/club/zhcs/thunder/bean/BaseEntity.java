package club.zhcs.thunder.bean;

import javax.persistence.Transient;

import com.google.gson.Gson;

/**
 * 实体基类
 * 
 * @author JiangKun
 * @date 2016年7月19日
 */
public class BaseEntity {

	@Transient
	private transient Integer page = 1;

	@Transient
	private transient Integer rows = 10;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}

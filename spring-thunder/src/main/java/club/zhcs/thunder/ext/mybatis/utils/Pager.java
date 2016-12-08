package club.zhcs.thunder.ext.mybatis.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * @author 王贵源
 *
 *         create at 2014年9月30日 上午11:21:12
 */
public class Pager<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pager [pageSize=" + pageSize + ", page=" + page + ", count=" + count + ", entities=" + entities + ", url=" + url + ", paras=" + paras + ", maxLength=" + maxLength
				+ ", size=" + size + "]";
	}

	/**
	 * 默认分页大小
	 */
	private int pageSize = 15;
	/**
	 * 默认第一页
	 */
	private int page = 1;
	/**
	 * 默认数据记录数
	 */
	private int count = 0;
	/**
	 * 分页内容列表
	 */
	private List<T> entities;
	/**
	 * 简单分页
	 */
	private boolean isSimplePager = true;
	/**
	 * 分页url
	 */
	private String url;
	/**
	 * 分页参数(带有一堆参数的分页)
	 */
	private Map<String, Object> paras;
	/**
	 * 分页条最大长度
	 */
	private int maxLength = 10;
	/**
	 * 分页条尺寸
	 */
	private String size;

	/**
	 * @return the pages
	 */
	public int getPages() {
		return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
	}

	/**
	 * 简单分页
	 * 
	 * @return
	 */
	public boolean isSimplePager() {
		return isSimplePager && (paras == null || paras.size() == 0);
	}

	/**
	 * @param isSimplePager
	 *            the isSimplePager to set
	 */
	public void setSimplePager(boolean isSimplePager) {
		this.isSimplePager = isSimplePager;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set 设置分页路径,建议绝对路径
	 *            server:port/contextPath/nameSpace/method
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the paras
	 */
	public Map<String, Object> getParas() {
		return paras;
	}

	/**
	 * @param paras
	 *            the paras to set 设置分页查询参数一些查询的筛选条件 按照mvc参数key-value的形式
	 */
	public void setParas(Map<String, Object> paras) {
		this.paras = paras;
	}

	public Pager<T> addParam(String key, Object value) {
		if (this.paras == null) {
			this.paras = Maps.newHashMap();
		}
		this.paras.put(key, value);
		return this;
	}

	/**
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength
	 *            the maxLength to set 分页条的最大长度
	 *            默认10也就是说页数大于10的时候最多显示10个分页节点(显示当前页码的前后X页,尽量让当前页在中间部位)
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * 获取数据库的数据分页起始偏移量
	 * 
	 * @return
	 */
	public int getOffset() {
		return (this.page - 1) * pageSize;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set 页面大小,默认15
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		adjustPage();
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set 当前页码
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set 记录条数,将根据记录条数生成数据页数,用count()从数据库查询
	 */
	public void setCount(int count) {
		this.count = count;
		adjustPage();
	}

	private void adjustPage() {
		if (getPages() < page) {// 如果传入页码大于页数则
			setPage(getPage());
		}
	}

	/**
	 * @return the entities
	 */
	public List<T> getEntities() {
		return entities;
	}

	/**
	 * @param entities
	 *            the entities to set 数据列表,根据条件从数据库分页查出
	 */
	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	/**
	 * 
	 * @param pageSize
	 *            分页大小
	 * @param page
	 *            当前页
	 */
	public Pager(int pageSize, int page) {
		super();
		this.pageSize = pageSize;
		this.page = page;
	}

	/**
	 * @param pageSize
	 *            页面大小
	 * @param page
	 *            页码
	 * @param count
	 *            总记录数
	 * @param entities
	 *            实体列表
	 */
	public Pager(int pageSize, int page, int count, List<T> entities) {
		super();
		this.pageSize = pageSize;
		this.page = page;
		this.count = count;
		this.entities = entities;
	}

	/**
	 * 获取分页条
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getPagerBar() throws UnsupportedEncodingException {
		if (getPages() == 0) {
			return "";
		}
		if (getPages() < maxLength) {
			maxLength = getPages();
		}

		if (page <= 0) {
			page = 1;
		}
		if (page > getPages()) {
			page = getPages();
		}

		if (StringUtils.isNotBlank(url)) {
			if (!isSimplePager()) {
				// 非简单分页,组装分页参数
				url = url.indexOf('?') > 0 ? url : url + "?";
				if (paras != null) {
					Iterator it = paras.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next().toString();
						String val = paras.get(key).toString();
						url += key + "=" + URLEncoder.encode(val, "UTF-8") + "&";
					}
				}
				url += "page=";
			} else {
				url = url.charAt(url.length() - 1) == '/' ? url : url + "/";// 判断传入的url是否加了'/'
			}
		}

		// 处理url结束
		String bar = "<div class='row'>";
		bar += "<ul class='pagination col-md-7 " + (size == null ? "" : "pagination-" + size) + "'>";
		if (page <= 1) {
			bar += "<li class='disabled'><a href='#' onclick='return false'>&laquo;</a>";
		} else {
			bar += "<li><a href='" + url + (page - 1) + "'>&laquo;</a></li>";
		}
		// 页码小于分页条的一半的时候，从第一页开始显示到barLength页//page 1 barLength 2
		if (page <= maxLength / 2) {
			bar += genPagerBarNode(url, 1, maxLength, page);
		}
		// 页码大于页数减去分页长度的一半的时候 显示 pages - maxLength到pages页
		else if (page >= getPages() - maxLength / 2) {
			bar += genPagerBarNode(url, getPages() - maxLength == 0 ? 1 : getPages() - maxLength, getPages(), page);
		}
		// 中间情况 显示 page - maxLength/2到page+maxLength/2页
		else {
			bar += genPagerBarNode(url, page - maxLength / 2, page + maxLength / 2, page);
		}
		// 处理结尾
		if (page >= getPages()) {
			bar += "<li class='disabled'><a href='#' onclick='return false'>&raquo;</a></li>";
		} else {
			bar += "<li><a href='" + url + (page + 1) + "'>&raquo;</a></li>";
		}
		bar += "</ul>";
		bar += "<div class='col-md-5' style='text-align: right;'>当前第 " + page + " / " + getPages() + " 页，每页 " + pageSize + " 条，总量 " + count + " 条</div>";
		bar += "</div>";
		return bar;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * 
	 * 生成分页节点
	 * 
	 * @param url
	 *            分页URL
	 * @param start
	 *            开始节点index
	 * @param end
	 *            结束节点index
	 * @param page
	 *            当前页
	 * @return
	 */
	private String genPagerBarNode(String url, int start, int end, int page) {
		String target = "";
		for (int i = start; i <= end; i++) {
			target += "<li " + (page == i ? "class='active'" : "") + "><a href='" + url + i + "'>" + i + (page == i ? "<span class='sr-only'>(current)</span>" : "") + "</a></li>";
		}
		return target;
	}

	/**
	 * 默认构造
	 */
	public Pager() {
	}

}

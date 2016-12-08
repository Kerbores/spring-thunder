package club.zhcs.thunder.ext.mybatis.utils;

/**
 * @author 王贵源
 *
 *         create at 2014年9月30日 下午12:33:57<br>
 *         查询排序构造
 */
public class OrderBy {
	/**
	 * 
	 */
	public OrderBy() {
		super();
	}

	/**
	 * @param col
	 * @param order
	 */
	public OrderBy(String col, Order order) {
		super();
		this.col = col;
		this.order = order;
	}

	public static enum Order {
		DESC, ASC
	}

	private String col;
	private Order order;

	/**
	 * @return the col
	 */
	public String getCol() {
		return col;
	}

	/**
	 * @param col
	 *            the col to set
	 */
	public void setCol(String col) {
		this.col = col;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderBy [col=" + col + ", order=" + order + "]";
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * desc排序
	 * 
	 * @param column
	 * @return
	 */
	public static OrderBy DESC(String column) {
		return new OrderBy(column, Order.DESC);
	}

	/**
	 * ASC排序
	 * 
	 * @param column
	 * @return
	 */
	public static OrderBy ASC(String column) {
		return new OrderBy(column, Order.ASC);
	}

}

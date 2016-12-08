package club.zhcs.thunder.ext.mybatis.utils;

/**
 * @author 王贵源
 *
 *         create at 2014年10月8日 上午10:23:33
 */
public class ConditionNode {

	private String key;

	private String operator;

	private Object value;

	/**
	 * @param key
	 * @param operator
	 * @param value
	 */
	public ConditionNode(String key, String operator, Object value) {
		super();
		this.key = key;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}

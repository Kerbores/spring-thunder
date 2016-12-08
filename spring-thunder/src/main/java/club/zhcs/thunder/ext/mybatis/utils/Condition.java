package club.zhcs.thunder.ext.mybatis.utils;

import java.util.ArrayList;

/**
 * @author 王贵源
 *
 *         create at 2014年9月30日 下午1:15:00<br>
 *         sql条件应该是由一组节点组成//TODO 可能存在不稳定的情况,慎用
 */
public class Condition extends ArrayList<ConditionNode> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 空条件
	 * 
	 * @return
	 */
	public static Condition me() {
		return new Condition();
	}

	/**
	 * where 条件
	 * 
	 * @param key
	 * @param operator
	 * @param val
	 * @return
	 */
	public static Condition where(String key, String operator, Object val) {
		Condition con = me();
		con.add(new ConditionNode(key, operator, val));
		return con;
	}

	/**
	 * and
	 * 
	 * @param key
	 * @param operator
	 * @param val
	 * @return
	 */
	public Condition and(String key, String operator, Object val) {
		this.add(new ConditionNode(key, operator, val));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
	public String toString() {
		if (this.size() == 0) {
			return "1 =1";
		}
		return super.toString();
	}
}

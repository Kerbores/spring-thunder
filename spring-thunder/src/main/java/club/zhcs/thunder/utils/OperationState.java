package club.zhcs.thunder.utils;

/**
 * @author Kerbores(kerbores@gmail.com)
 * @project spring-thunder
 * @file OperationState.java
 * @description 操作状态枚举
 * @time 2016年9月8日 上午11:24:53
 */
public enum OperationState {
    /**
     * 成功
     */
    SUCCESS,
    /**
     * 失败
     */
    FAIL,
    /**
     * 默认
     */
    DEFAULT,
    /**
     * 异常
     */
    EXCEPTION,
    /**
     * 未登录
     */
    UNLOGINED
}

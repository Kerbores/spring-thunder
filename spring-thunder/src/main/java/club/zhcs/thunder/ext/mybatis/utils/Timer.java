package club.zhcs.thunder.ext.mybatis.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计时工具
 * 
 * @author kipy
 * 
 */
public class Timer {
	/**
	 * 开始时间
	 */
	private static long begin;
	private static long nbegin;
	/**
	 * 结束时间
	 */
	private static long end;
	private static long nend;
	private static SimpleDateFormat s = new SimpleDateFormat("yyyy年MM月dd日 kk:mm:ss.SS ");

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(Timer.class);

	/**
	 * 计时结束 在需要结束的地方调用此方法
	 */
	public static void end() {
		end = System.currentTimeMillis();
		nend = System.nanoTime();
		Date date = new Date(end);
		System.err.println("计时结束,结束时间:" + s.format(date) + ",耗时:" + (end - begin) + "--" + (nend - nbegin) + "纳秒");
		initTimer();
	}

	private static void initTimer() {
		begin = 0;
		end = 0;
		nbegin = 0;
		nend = 0;
	}

	public static long getBegin() {
		return begin;
	}

	public static SimpleDateFormat getDateFormat() {
		return s;
	}

	public static long getEnd() {
		return end;
	}

	public static void setBegin(long begin) {
		Timer.begin = begin;
	}

	public static void setDateFormat(SimpleDateFormat s) {
		Timer.s = s;
	}

	public static void setEnd(long end) {
		Timer.end = end;
	}

	/**
	 * 计时开始 在需要开始计时的地方调用此方法
	 */
	public static void start() {
		begin = System.currentTimeMillis();
		nbegin = System.nanoTime();
		Date date = new Date(begin);
		System.err.println("计时开始,开始时间:" + s.format(date));
	}
}

package com.xhs.ems.bean;

/**
 * @category 24小时出诊强度
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午1:21:50
 */
public class OutCallSpan {
	/**
	 * 时段
	 */
	private String span;
	/**
	 * 次数
	 */
	private String times;
	/**
	 * 接回
	 */
	private String takeBacks;
	/**
	 * 平均反应时间
	 */
	private String averageResponseTime;
	/**
	 * 耗时合计
	 */
	private String outCallTotal;
	/**
	 * 平均耗时
	 */
	private String averageTime;

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getTakeBacks() {
		return takeBacks;
	}

	public void setTakeBacks(String takeBacks) {
		this.takeBacks = takeBacks;
	}

	public String getAverageResponseTime() {
		return averageResponseTime;
	}

	public void setAverageResponseTime(String averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}

	public String getOutCallTotal() {
		return outCallTotal;
	}

	public void setOutCallTotal(String outCallTotal) {
		this.outCallTotal = outCallTotal;
	}

	public String getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(String averageTime) {
		this.averageTime = averageTime;
	}

	public OutCallSpan(String span, String times, String takeBacks,
			String averageResponseTime, String outCallTotal, String averageTime) {
		this.span = span;
		this.times = times;
		this.takeBacks = takeBacks;
		this.averageResponseTime = averageResponseTime;
		this.outCallTotal = outCallTotal;
		this.averageTime = averageTime;
	}

}

package com.xhs.ems.bean;

/**
 * @category 司机出诊表
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午2:15:07
 */
public class DriverOutCall {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 出诊数
	 */
	private String outCalls;
	/**
	 * 接回数
	 */
	private String takeBacks;
	/**
	 * 里程
	 */
	private String distance;
	/**
	 * 平均反应时间
	 */
	private String averageResponseTime;
	/**
	 * 出诊用时合计
	 */
	private String outCallTimeTotal;
	/**
	 * 平均耗时
	 */
	private String averageTime;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutCalls() {
		return outCalls;
	}

	public void setOutCalls(String outCalls) {
		this.outCalls = outCalls;
	}

	public String getTakeBacks() {
		return takeBacks;
	}

	public void setTakeBacks(String takeBacks) {
		this.takeBacks = takeBacks;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getAverageResponseTime() {
		return averageResponseTime;
	}

	public void setAverageResponseTime(String averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}

	public String getOutCallTimeTotal() {
		return outCallTimeTotal;
	}

	public void setOutCallTimeTotal(String outCallTimeTotal) {
		this.outCallTimeTotal = outCallTimeTotal;
	}

	public String getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(String averageTime) {
		this.averageTime = averageTime;
	}

	public DriverOutCall(String name, String outCalls, String takeBacks,
			String distance, String averageResponseTime,
			String outCallTimeTotal, String averageTime) {
		this.name = name;
		this.outCalls = outCalls;
		this.takeBacks = takeBacks;
		this.distance = distance;
		this.averageResponseTime = averageResponseTime;
		this.outCallTimeTotal = outCallTimeTotal;
		this.averageTime = averageTime;
	}

}

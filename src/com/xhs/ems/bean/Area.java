package com.xhs.ems.bean;

/**
 * @category 区域统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午1:07:17
 */
public class Area {
	/**
	 * 区域
	 */
	private String area;
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
	 * 出诊耗时
	 */
	private String outCallTime; 
	/**
	 * 平均耗时
	 */
	private String averageTime;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getOutCallTime() {
		return outCallTime;
	}

	public void setOutCallTime(String outCallTime) {
		this.outCallTime = outCallTime;
	}

	public String getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(String averageTime) {
		this.averageTime = averageTime;
	}

	public Area(String area, String outCalls, String takeBacks,
			String distance, String outCallTime, String averageTime) {
		this.area = area;
		this.outCalls = outCalls;
		this.takeBacks = takeBacks;
		this.distance = distance;
		this.outCallTime = outCallTime;
		this.averageTime = averageTime;
	}

}

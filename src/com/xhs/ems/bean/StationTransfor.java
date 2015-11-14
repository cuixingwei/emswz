package com.xhs.ems.bean;

/**
 * @category 医院转诊统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午1:48:48
 */
public class StationTransfor {
	/**
	 * 区域
	 */
	private String area;
	/**
	 * 医院名称
	 */
	private String station;
	/**
	 * 出诊数
	 */
	private String outCalls;
	/**
	 * 接回数
	 */
	private String takeBacks;
	/**
	 * 里程合计
	 */
	private String distance;
	/**
	 * 耗时
	 */
	private String time;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public StationTransfor() {
	}

}

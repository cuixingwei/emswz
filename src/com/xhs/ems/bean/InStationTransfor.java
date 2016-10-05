package com.xhs.ems.bean;

/**
 * @category 院内转运统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午2:05:02
 */
public class InStationTransfor {
	/**
	 * 分站名称
	 */
	private String station;
	/**
	 * 转运次数
	 */
	private String transforTimes;
	/**
	 * 里程
	 */
	private String distance;
	/**
	 * 转运耗时
	 */
	private String transforTime;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTransforTimes() {
		return transforTimes;
	}

	public void setTransforTimes(String transforTimes) {
		this.transforTimes = transforTimes;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTransforTime() {
		return transforTime;
	}

	public void setTransforTime(String transforTime) {
		this.transforTime = transforTime;
	}

	public InStationTransfor(String station, String transforTimes,
			String distance, String transforTime) {
		this.station = station;
		this.transforTimes = transforTimes;
		this.distance = distance;
		this.transforTime = transforTime;
	}

}

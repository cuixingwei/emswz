package com.xhs.ems.bean;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月30日 下午5:12:23
 */
public class Station {
	/**
	 * 分站编码
	 */
	private String stationCode;
	/**
	 * 分站名称
	 */
	private String stationName;
	/**
	 * 显示顺序
	 */
	private String order;
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Station(String stationCode, String stationName, String order) {
		this.stationCode = stationCode;
		this.stationName = stationName;
		this.order = order;
	}
	
}

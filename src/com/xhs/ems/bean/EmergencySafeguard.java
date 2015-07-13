package com.xhs.ems.bean;

/**
 * @category 急救保障明细
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午3:42:16
 */
public class EmergencySafeguard {
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 性质
	 */
	private String nature;
	/**
	 * 事件
	 */
	private String event;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 里程
	 */
	private String distance;
	/**
	 * 医生
	 */
	private String doctor;
	/**
	 * 护士
	 */
	private String nurse;
	/**
	 * 司机
	 */
	private String driver;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public EmergencySafeguard(String date, String nature, String event,
			String address, String distance, String doctor, String nurse,
			String driver) {
		this.date = date;
		this.nature = nature;
		this.event = event;
		this.address = address;
		this.distance = distance;
		this.doctor = doctor;
		this.nurse = nurse;
		this.driver = driver;
	}

}

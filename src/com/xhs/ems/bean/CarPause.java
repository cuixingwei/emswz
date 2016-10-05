package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午4:02:00
 */
public class CarPause {
	/**
	 * 车辆
	 */
	private String carCode;
	/**
	 * 司机
	 */
	private String driver;
	/**
	 * 暂停时长
	 */
	private String pauseTimes;
	/**
	 * 暂停时刻
	 */
	private String pauseTime;
	/**
	 * 结束时刻
	 */
	private String endTime;
	/**
	 * 操作人
	 */
	private String dispatcher;
	/**
	 * 暂停原因
	 */
	private String pauseReason;
	
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getPauseTimes() {
		return pauseTimes;
	}
	public void setPauseTimes(String pauseTimes) {
		this.pauseTimes = pauseTimes;
	}
	public String getPauseTime() {
		return pauseTime;
	}
	public void setPauseTime(String pauseTime) {
		this.pauseTime = pauseTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getPauseReason() {
		return pauseReason;
	}
	public void setPauseReason(String pauseReason) {
		this.pauseReason = pauseReason;
	}
	public CarPause(String carCode, String driver, String pauseTimes,
			String pauseTime, String endTime, String dispatcher,
			String pauseReason) {
		this.carCode = carCode;
		this.driver = driver;
		this.pauseTimes = pauseTimes;
		this.pauseTime = pauseTime;
		this.endTime = endTime;
		this.dispatcher = dispatcher;
		this.pauseReason = pauseReason;
	}
	
	
}

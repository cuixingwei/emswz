package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 下午7:11:17
 */
public class SubstationLateVisit {
	/**
	 * 现场地址
	 */
	private String siteAddress;
	/**
	 * 事件类型
	 */
	private String eventType;
	/**
	 * 车辆标识
	 */
	private String carCode;
	/**
	 * 受理时刻
	 */
	private String acceptTime;
	/**
	 * 接收命令时刻
	 */
	private String acceptTaskTime;
	/**
	 * 出车时刻
	 */
	private String outCarTime;
	/**
	 * 出车时长
	 */
	private String outCarTimes;
	/**
	 * 出车结果
	 */
	private String taskResult;
	/**
	 * 任务备注
	 */
	private String remark;
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 医生
	 */
	private String docter;
	/**
	 * 护士
	 */
	private String nurse;
	/**
	 * 司机
	 */
	private String driver;
	/**
	 * 分站
	 */
	private String station;

	public String getAcceptTaskTime() {
		return acceptTaskTime;
	}

	public void setAcceptTaskTime(String acceptTaskTime) {
		this.acceptTaskTime = acceptTaskTime;
	}

	public String getDocter() {
		return docter;
	}

	public void setDocter(String docter) {
		this.docter = docter;
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

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getOutCarTime() {
		return outCarTime;
	}

	public void setOutCarTime(String outCarTime) {
		this.outCarTime = outCarTime;
	}

	public String getOutCarTimes() {
		return outCarTimes;
	}

	public void setOutCarTimes(String outCarTimes) {
		this.outCarTimes = outCarTimes;
	}

	public String getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public SubstationLateVisit() {
	}

}

package com.xhs.ems.bean;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午1:39:08
 */
public class StopTask {
	/**
	 * 受理时间
	 */
	private String acceptTime;
	/**
	 * 患者地址
	 */
	private String sickAddress;
	/**
	 * 呼救电话
	 */
	private String phone;
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 车辆编码
	 */
	private String carCode;
	/**
	 * 出车时间
	 */
	private String drivingTime;
	/**
	 * 空跑时长	
	 */
	private String emptyRunTime;
	/**
	 * 分站
	 */
	private String staion;
	/**
	 * 中止原因
	 */
	private String stopReason;
	/**
	 * 备注说明
	 */
	private String remark;
	
	public String getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getAddress() {
		return sickAddress;
	}
	public void setAddress(String address) {
		this.sickAddress = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getDrivingTime() {
		return drivingTime;
	}
	public void setDrivingTime(String drivingTime) {
		this.drivingTime = drivingTime;
	}
	public String getEmptyRunTime() {
		return emptyRunTime;
	}
	public void setEmptyRunTime(String emptyRunTime) {
		this.emptyRunTime = emptyRunTime;
	}
	public String getStaion() {
		return staion;
	}
	public void setStaion(String staion) {
		this.staion = staion;
	}
	public String getStopReason() {
		return stopReason;
	}
	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}
	
	public String getSickAddress() {
		return sickAddress;
	}
	public void setSickAddress(String sickAddress) {
		this.sickAddress = sickAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public StopTask(String acceptTime, String sickAddress, String phone,
			String dispatcher, String carCode, String drivingTime,
			String emptyRunTime, String staion, String stopReason, String remark) {
		this.acceptTime = acceptTime;
		this.sickAddress = sickAddress;
		this.phone = phone;
		this.dispatcher = dispatcher;
		this.carCode = carCode;
		this.drivingTime = drivingTime;
		this.emptyRunTime = emptyRunTime;
		this.staion = staion;
		this.stopReason = stopReason;
		this.remark = remark;
	}

	
}

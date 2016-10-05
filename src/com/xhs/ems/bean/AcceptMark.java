package com.xhs.ems.bean;

/**
 * 受理备注查询实体
 * @author CUIXINGWEI
 *
 */
public class AcceptMark {
	/**
	 * 受理时间
	 */
	private String acceptTime;
	/**
	 * 呼救电话
	 */
	private String callPhone;
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 现场地址
	 */
	private String spotAddress;
	/**
	 * 任务备注
	 */
	private String taskRemark;
	/**
	 * 受理备注
	 */
	private String acceptRemark;
	public String getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getCallPhone() {
		return callPhone;
	}
	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getSpotAddress() {
		return spotAddress;
	}
	public void setSpotAddress(String spotAddress) {
		this.spotAddress = spotAddress;
	}
	public String getTaskRemark() {
		return taskRemark;
	}
	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}
	public String getAcceptRemark() {
		return acceptRemark;
	}
	public void setAcceptRemark(String acceptRemark) {
		this.acceptRemark = acceptRemark;
	}
	public AcceptMark(String acceptTime, String callPhone, String dispatcher,
			String spotAddress, String taskRemark, String acceptRemark) {
		this.acceptTime = acceptTime;
		this.callPhone = callPhone;
		this.dispatcher = dispatcher;
		this.spotAddress = spotAddress;
		this.taskRemark = taskRemark;
		this.acceptRemark = acceptRemark;
	}
	
	
}

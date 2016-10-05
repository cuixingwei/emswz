package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年5月7日 下午3:01:07
 * @category 开始受理到派车大于X秒
 */
public class AcceptSendCar {
	/**
	 * 受理表ID
	 */
	private String id;
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 开始受理时刻
	 */
	private String startAcceptTime;
	/**
	 * 派车时刻
	 */
	private String sendCarTime;
	/**
	 * 受理类型
	 */
	private String acceptType;
	/**
	 * 呼救号码
	 */
	private String ringPhone;
	/**
	 * 派车时长
	 */
	private String sendCarTimes;
	/**
	 * 受理备注
	 */
	private String remark;

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getStartAcceptTime() {
		return startAcceptTime;
	}

	public void setStartAcceptTime(String startAcceptTime) {
		this.startAcceptTime = startAcceptTime;
	}

	public String getSendCarTime() {
		return sendCarTime;
	}

	public void setSendCarTime(String sendCarTime) {
		this.sendCarTime = sendCarTime;
	}

	public String getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}

	public String getRingPhone() {
		return ringPhone;
	}

	public void setRingPhone(String ringPhone) {
		this.ringPhone = ringPhone;
	}

	public String getSendCarTimes() {
		return sendCarTimes;
	}

	public void setSendCarTimes(String sendCarTimes) {
		this.sendCarTimes = sendCarTimes;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AcceptSendCar(String id, String dispatcher, String startAcceptTime,
			String sendCarTime, String acceptType, String ringPhone,
			String sendCarTimes, String remark) {
		this.id = id;
		this.dispatcher = dispatcher;
		this.startAcceptTime = startAcceptTime;
		this.sendCarTime = sendCarTime;
		this.acceptType = acceptType;
		this.ringPhone = ringPhone;
		this.sendCarTimes = sendCarTimes;
		this.remark = remark;
	}

}

package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午8:44:01
 */
public class EmptyCar {
	/**
	 * 受理时间
	 */
	private String acceptTime;
	/**
	 * 患者地址
	 */
	private String sickAddress;
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 空跑时间
	 */
	private String emptyRunTimes;
	/**
	 * 空炮原因
	 */
	private String emptyReason;

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getSickAddress() {
		return sickAddress;
	}

	public void setSickAddress(String sickAddress) {
		this.sickAddress = sickAddress;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getEmptyRunTimes() {
		return emptyRunTimes;
	}

	public void setEmptyRunTimes(String emptyRunTimes) {
		this.emptyRunTimes = emptyRunTimes;
	}

	public String getEmptyReason() {
		return emptyReason;
	}

	public void setEmptyReason(String emptyReason) {
		this.emptyReason = emptyReason;
	}

	public EmptyCar(String acceptTime, String sickAddress, String dispatcher,
			String emptyRunTimes, String emptyReason) {
		this.acceptTime = acceptTime;
		this.sickAddress = sickAddress;
		this.dispatcher = dispatcher;
		this.emptyRunTimes = emptyRunTimes;
		this.emptyReason = emptyReason;
	}

}

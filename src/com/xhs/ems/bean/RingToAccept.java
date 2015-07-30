package com.xhs.ems.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RingToAccept {
	/**
	 * 调度员姓名
	 */
	private String dispatcher;
	/**
	 * 电话振铃时刻
	 */
	private String ringTime;
	/**
	 * 通话时刻
	 */
	private String callTime;
	/**
	 * 响铃时长（秒）
	 */
	private String ringDuration;
	/**
	 * 受理台号
	 */
	private String acceptCode;
	/**
	 * 受理备注
	 */
	private String acceptRemark;

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getRingTime() {
		return ringTime;
	}

	public void setRingTime(String ringTime) {
		this.ringTime = ringTime;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getRingDuration() {
		return ringDuration;
	}

	public void setRingDuration(String ringDuration) {
		this.ringDuration = ringDuration;
	}

	public String getAcceptCode() {
		return acceptCode;
	}

	public void setAcceptCode(String acceptCode) {
		this.acceptCode = acceptCode;
	}

	public String getAcceptRemark() {
		return acceptRemark;
	}

	public void setAcceptRemark(String acceptRemark) {
		this.acceptRemark = acceptRemark;
	}

	public RingToAccept(String dispatcher, String ringTime, String callTime,
			String ringDuration, String acceptCode, String acceptRemark) {
		this.dispatcher = dispatcher;
		this.ringTime = ringTime;
		this.callTime = callTime;
		this.ringDuration = ringDuration;
		this.acceptCode = acceptCode;
		this.acceptRemark = acceptRemark;
	}

}

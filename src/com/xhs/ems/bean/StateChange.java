package com.xhs.ems.bean;
/**
 * 
 * @author CUIXINGWEI
 * @date 2015年3月30日
 */
public class StateChange {
	/**
	 * 座席号
	 */
	private String seatCode;
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 座席状态
	 */
	private String seatState;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	public String getSeatCode() {
		return seatCode;
	}
	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getSeatState() {
		return seatState;
	}
	public void setSeatState(String seatState) {
		this.seatState = seatState;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public StateChange(String seatCode, String dispatcher, String seatState,
			String startTime, String endTime) {
		this.seatCode = seatCode;
		this.dispatcher = dispatcher;
		this.seatState = seatState;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	
}

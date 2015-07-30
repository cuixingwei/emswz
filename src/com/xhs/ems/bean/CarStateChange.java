package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午7:34:55
 */
public class CarStateChange {
	private String eventName;  //事件名称
	private String carCode;  //车辆
	private String carState;  //车辆状态
	private String recordTime;  //记录时刻
	private String recordClass;  //记录类型
	private String seatCode;  //坐席号
	private String dispatcher;  //操作人
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getCarState() {
		return carState;
	}
	public void setCarState(String carState) {
		this.carState = carState;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public String getRecordClass() {
		return recordClass;
	}
	public void setRecordClass(String recordClass) {
		this.recordClass = recordClass;
	}
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
	public CarStateChange(String eventName, String carCode, String carState,
			String recordTime, String recordClass, String seatCode,
			String dispatcher) {
		this.eventName = eventName;
		this.carCode = carCode;
		this.carState = carState;
		this.recordTime = recordTime;
		this.recordClass = recordClass;
		this.seatCode = seatCode;
		this.dispatcher = dispatcher;
	}
	
}

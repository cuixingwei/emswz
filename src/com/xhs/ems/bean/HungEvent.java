package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 下午9:03:44
 */
public class HungEvent {
	/**
	 * 事件名称
	 */
	private String eventName;
	/**
	 * 受理类型
	 */
	private String acceptType;
	/**
	 * 挂起时刻
	 */
	private String hungTime;
	/**
	 * 挂起原因
	 */
	private String hungReason;
	/**
	 * 操作人
	 */
	private String dispatcher;
	/**
	 * 结束时刻
	 */
	private String endTime;
	/**
	 * 时长
	 */
	private String hungtimes;
	/**
	 * 分诊调度医院
	 */
	private String station;
	/**
	 * 区域
	 */
	private String area;
	/**
	 * 出诊类型
	 */
	private String eventType;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}

	public String getHungTime() {
		return hungTime;
	}

	public void setHungTime(String hungTime) {
		this.hungTime = hungTime;
	}

	public String getHungReason() {
		return hungReason;
	}

	public void setHungReason(String hungReason) {
		this.hungReason = hungReason;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHungtimes() {
		return hungtimes;
	}

	public void setHungtimes(String hungtimes) {
		this.hungtimes = hungtimes;
	}

	public HungEvent(String eventName, String acceptType, String hungTime,
			String hungReason, String dispatcher, String endTime,
			String hungtimes, String station, String area, String eventType) {
		super();
		this.eventName = eventName;
		this.acceptType = acceptType;
		this.hungTime = hungTime;
		this.hungReason = hungReason;
		this.dispatcher = dispatcher;
		this.endTime = endTime;
		this.hungtimes = hungtimes;
		this.station = station;
		this.area = area;
		this.eventType = eventType;
	}

}

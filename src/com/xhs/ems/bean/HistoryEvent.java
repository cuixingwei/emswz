package com.xhs.ems.bean;

/**
 * 历史事件统计
 * @datetime 2016年7月27日 下午3:10:08
 * @author 崔兴伟
 */
public class HistoryEvent {
	private String eventName; //事件名称
	private String alarmPhone; //呼救电话
	private String acceptTime; //受理时刻
	private String eventType; //事件类型
	private String eventSource; //联动来源
	private String acceptNumbers; //受理次数
	private String taskNumbers; //任务次数
	private String caseNumbers; //病历个数
	private String dispatcher; //调度员
	
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getAlarmPhone() {
		return alarmPhone;
	}
	public void setAlarmPhone(String alarmPhone) {
		this.alarmPhone = alarmPhone;
	}
	public String getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventSource() {
		return eventSource;
	}
	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}
	public String getAcceptNumbers() {
		return acceptNumbers;
	}
	public void setAcceptNumbers(String acceptNumbers) {
		this.acceptNumbers = acceptNumbers;
	}
	public String getTaskNumbers() {
		return taskNumbers;
	}
	public void setTaskNumbers(String taskNumbers) {
		this.taskNumbers = taskNumbers;
	}
	public String getCaseNumbers() {
		return caseNumbers;
	}
	public void setCaseNumbers(String caseNumbers) {
		this.caseNumbers = caseNumbers;
	}
	public HistoryEvent() {
	}
	
	
}

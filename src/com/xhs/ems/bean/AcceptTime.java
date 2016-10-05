package com.xhs.ems.bean;

/**
 * 受理时间统计实体
 * @author CUIXINGWEI
 *
 */
public class AcceptTime {
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 平均摘机时长
	 */
	private String averageOffhookTime;
	/**
	 * 平均派车时长
	 */
	private String averageOffSendCar;
	/**
	 * 平均受理时长
	 */
	private String averageAccept;
	/**
	 * 就绪时长
	 */
	private String readyTime;
	/**
	 * 离席时长
	 */
	private String leaveTime;
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getAverageOffhookTime() {
		return averageOffhookTime;
	}
	public void setAverageOffhookTime(String averageOffhookTime) {
		this.averageOffhookTime = averageOffhookTime;
	}
	public String getAverageOffSendCar() {
		return averageOffSendCar;
	}
	public void setAverageOffSendCar(String averageOffSendCar) {
		this.averageOffSendCar = averageOffSendCar;
	}
	public String getAverageAccept() {
		return averageAccept;
	}
	public void setAverageAccept(String averageAccept) {
		this.averageAccept = averageAccept;
	}
	public String getReadyTime() {
		return readyTime;
	}
	public void setReadyTime(String readyTime) {
		this.readyTime = readyTime;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public AcceptTime(String dispatcher, String averageOffhookTime,
			String averageOffSendCar, String averageAccept, String readyTime,
			String leaveTime) {
		this.dispatcher = dispatcher;
		this.averageOffhookTime = averageOffhookTime;
		this.averageOffSendCar = averageOffSendCar;
		this.averageAccept = averageAccept;
		this.readyTime = readyTime;
		this.leaveTime = leaveTime;
	}
	
}

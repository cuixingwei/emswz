package com.xhs.ems.bean;

/**
 * @author CUIXINGWEI
 * @date 2015年3月30日
 */
public class CenterTask {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 患者地址
	 */
	private String sickAddress;
	/**
	 * 主诉
	 */
	private String sickDescription;
	/**
	 * 呼救电话
	 */
	private String phone;
	/**
	 * 受理时间
	 */
	private String acceptTime;
	/**
	 * 派车时间
	 */
	private String sendCarTime;
	/**
	 * 出车时间
	 */
	private String drivingTime;
	/**
	 * 到达时间
	 */
	private String arrivalTime;
	/**
	 * 返院时间
	 */
	private String returnHospitalTime;
	/**
	 * 送住地点
	 */
	private String toAddress;
	/**
	 * 车辆
	 */
	private String carCode;
	/**
	 * 医生
	 */
	private String doctor;
	/**
	 * 护士
	 */
	private String nurse;
	/**
	 * 司机
	 */
	private String driver;
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 任务结果
	 */
	private String taskResult;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSickAddress() {
		return sickAddress;
	}

	public void setSickAddress(String sickAddress) {
		this.sickAddress = sickAddress;
	}

	public String getSickDescription() {
		return sickDescription;
	}

	public void setSickDescription(String sickDescription) {
		this.sickDescription = sickDescription;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getSendCarTime() {
		return sendCarTime;
	}

	public void setSendCarTime(String sendCarTime) {
		this.sendCarTime = sendCarTime;
	}

	public String getDrivingTime() {
		return drivingTime;
	}

	public void setDrivingTime(String drivingTime) {
		this.drivingTime = drivingTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getReturnHospitalTime() {
		return returnHospitalTime;
	}

	public void setReturnHospitalTime(String returnHospitalTime) {
		this.returnHospitalTime = returnHospitalTime;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}

	public CenterTask(String name, String sickAddress, String sickDescription,
			String phone, String acceptTime, String sendCarTime,
			String drivingTime, String arrivalTime, String returnHospitalTime,
			String toAddress, String carCode, String doctor, String nurse,
			String driver, String dispatcher, String taskResult) {
		this.name = name;
		this.sickAddress = sickAddress;
		this.sickDescription = sickDescription;
		this.phone = phone;
		this.acceptTime = acceptTime;
		this.sendCarTime = sendCarTime;
		this.drivingTime = drivingTime;
		this.arrivalTime = arrivalTime;
		this.returnHospitalTime = returnHospitalTime;
		this.toAddress = toAddress;
		this.carCode = carCode;
		this.doctor = doctor;
		this.nurse = nurse;
		this.driver = driver;
		this.dispatcher = dispatcher;
		this.taskResult = taskResult;
	}


}

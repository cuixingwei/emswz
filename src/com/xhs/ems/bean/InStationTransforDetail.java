package com.xhs.ems.bean;

/**
 * @category 院内转运明细
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午2:02:06
 */
public class InStationTransforDetail {
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 病人姓名
	 */
	private String patientName;
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 诊断
	 */
	private String diagnose;
	/**
	 * 出诊地址
	 */
	private String outCallAddress;
	/**
	 * 送达科室
	 */
	private String sendClass;
	/**
	 * 现场地址
	 */
	private String spot;
	/**
	 * 距离
	 */
	private String distance;
	/**
	 * 耗时
	 */
	private String time;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getOutCallAddress() {
		return outCallAddress;
	}

	public void setOutCallAddress(String outCallAddress) {
		this.outCallAddress = outCallAddress;
	}

	public String getSendClass() {
		return sendClass;
	}

	public void setSendClass(String sendClass) {
		this.sendClass = sendClass;
	}

	public String getSpot() {
		return spot;
	}

	public void setSpot(String spot) {
		this.spot = spot;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public InStationTransforDetail() {

	}

}

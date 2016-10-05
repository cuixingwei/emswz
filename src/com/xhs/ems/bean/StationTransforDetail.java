package com.xhs.ems.bean;

/**
 * @category 医院转诊明细
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午1:53:03
 */
public class StationTransforDetail {
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
	 * 出诊分站
	 */
	private String outCallAddress;
	/**
	 * 送达科室
	 */
	private String sendClass;
	/**
	 * 现场地址
	 */
	private String spotAddress;
	/**
	 * 里程
	 */
	private String distance;
	/**
	 * 出诊结果
	 */
	private String outResult;

	
	public String getSpotAddress() {
		return spotAddress;
	}

	public void setSpotAddress(String spotAddress) {
		this.spotAddress = spotAddress;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getOutResult() {
		return outResult;
	}

	public void setOutResult(String outResult) {
		this.outResult = outResult;
	}

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

	public StationTransforDetail() {
		super();
	}

}

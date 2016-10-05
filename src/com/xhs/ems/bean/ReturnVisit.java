package com.xhs.ems.bean;

/**
 * @category 回访统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午3:49:33
 */
public class ReturnVisit {
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 初步诊断
	 */
	private String PreDisgnose;
	/**
	 * 收费
	 */
	private String cost;
	/**
	 * 出诊医生
	 */
	private String doctor;
	/**
	 * 出诊护士
	 */
	private String nurse;
	/**
	 * 出诊司机
	 */
	private String driver;
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 满意计数
	 */
	private String satisfyCount;
	/**
	 * 一般计数
	 */
	private String commonCount;
	/**
	 * 不满意计数
	 */
	private String unsatisfyCount;
	/**
	 * 评分合计
	 */
	private String totalScore;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPreDisgnose() {
		return PreDisgnose;
	}

	public void setPreDisgnose(String preDisgnose) {
		PreDisgnose = preDisgnose;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
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

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getSatisfyCount() {
		return satisfyCount;
	}

	public void setSatisfyCount(String satisfyCount) {
		this.satisfyCount = satisfyCount;
	}

	public String getCommonCount() {
		return commonCount;
	}

	public void setCommonCount(String commonCount) {
		this.commonCount = commonCount;
	}

	public String getUnsatisfyCount() {
		return unsatisfyCount;
	}

	public void setUnsatisfyCount(String unsatisfyCount) {
		this.unsatisfyCount = unsatisfyCount;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public ReturnVisit(String date, String name, String phone, String address,
			String preDisgnose, String cost, String doctor, String nurse,
			String driver, String dispatcher, String satisfyCount,
			String commonCount, String unsatisfyCount, String totalScore) {
		this.date = date;
		this.name = name;
		this.phone = phone;
		this.address = address;
		PreDisgnose = preDisgnose;
		this.cost = cost;
		this.doctor = doctor;
		this.nurse = nurse;
		this.driver = driver;
		this.dispatcher = dispatcher;
		this.satisfyCount = satisfyCount;
		this.commonCount = commonCount;
		this.unsatisfyCount = unsatisfyCount;
		this.totalScore = totalScore;
	}

}

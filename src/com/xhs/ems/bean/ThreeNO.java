package com.xhs.ems.bean;

/**
 * @category 三无统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 上午11:19:05
 */
public class ThreeNO {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 诊断
	 */
	private String diagnose;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 费用
	 */
	private String cost;
	/**
	 * 送往地点
	 */
	private String toAddress;
	/**
	 * 距离
	 */
	private String distance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public ThreeNO(String name, String gender, String age, String diagnose,
			String address, String cost, String toAddress, String distance) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.diagnose = diagnose;
		this.address = address;
		this.cost = cost;
		this.toAddress = toAddress;
		this.distance = distance;
	}

}

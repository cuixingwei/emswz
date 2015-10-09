package com.xhs.ems.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class User {
	/**
	 * 工号
	 */
	private String employeeId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 有效标志
	 */
	private Integer isValid;
	/**
	 * 分站名称
	 */
	private String stationName;

	public User(String employeeId, String name) {
		this.employeeId = employeeId;
		this.name = name;
	}

	public User(String employeeId, String name, String password, Integer isValid) {
		this.employeeId = employeeId;
		this.name = name;
		this.password = password;
		this.isValid = isValid;
	}

	public User() {

	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

}

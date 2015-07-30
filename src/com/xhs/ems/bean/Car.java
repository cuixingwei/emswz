package com.xhs.ems.bean;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:19:25
 */
public class Car {
	/**
	 * 车辆编码
	 */
	private String carCode;
	/**
	 * 车辆标识
	 */
	private String carIdentification;
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getCarIdentification() {
		return carIdentification;
	}
	public void setCarIdentification(String carIdentification) {
		this.carIdentification = carIdentification;
	}
	public Car(String carCode, String carIdentification) {
		this.carCode = carCode;
		this.carIdentification = carIdentification;
	}
	
}

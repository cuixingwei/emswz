package com.xhs.ems.bean;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:50:00
 */
public class StopReason {
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	public StopReason(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

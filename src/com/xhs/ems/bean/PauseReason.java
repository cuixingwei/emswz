package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午5:08:30
 */
public class PauseReason {
	private String code;
	private String name;
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
	public PauseReason(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
}

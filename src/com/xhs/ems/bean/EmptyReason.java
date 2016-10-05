package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午11:10:22
 *  放空车原因列表
 */
public class EmptyReason {
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

	public EmptyReason(String code, String name) {
		this.code = code;
		this.name = name;
	}

}

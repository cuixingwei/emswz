package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:47:17
 */
public class HungReason {
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;

	public HungReason(String code, String name) {
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

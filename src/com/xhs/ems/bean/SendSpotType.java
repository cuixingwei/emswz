package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午3:43:46
 * @category 送往地点类型实体
 */
public class SendSpotType {
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 次数
	 */
	private String times;
	/**
	 * 比率
	 */
	private String rate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public SendSpotType(String name, String times, String rate) {
		this.name = name;
		this.times = times;
		this.rate = rate;
	}

}

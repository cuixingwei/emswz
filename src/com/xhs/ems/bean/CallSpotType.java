package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @category 呼救现场地点类型统计
 * @datetime 2015年4月15日 下午3:00:52
 */
public class CallSpotType {
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

	public CallSpotType(String name, String times, String rate) {
		this.name = name;
		this.times = times;
		this.rate = rate;
	}

}

package com.xhs.ems.bean;

/**
 * @category 病因统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午1:32:13
 */
public class DiseaseReason {
	/**
	 * 病因类型
	 */
	private String name;
	/**
	 * 次数
	 */
	private String times;
	/**
	 * 占比
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

	public DiseaseReason(String name, String times, String rate) {
		this.name = name;
		this.times = times;
		this.rate = rate;
	}

}

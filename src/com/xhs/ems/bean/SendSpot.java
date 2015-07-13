package com.xhs.ems.bean;

/**
 * @category 送往地点统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午1:34:57
 */
public class SendSpot {
	/**
	 * 送达地点
	 */
	private String address;
	/**
	 * 次数
	 */
	private String times;
	/**
	 * 比率
	 */
	private String rate;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public SendSpot(String address, String times, String rate) {
		this.address = address;
		this.times = times;
		this.rate = rate;
	}

}

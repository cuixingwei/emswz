package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 下午1:44:22
 */
public class EmptyCarReason {
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 次数
	 */
	private String times;
	/**
	 * 比率
	 */
	private String rate;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public EmptyCarReason(String reason, String times, String rate) {
		this.reason = reason;
		this.times = times;
		this.rate = rate;
	}

}

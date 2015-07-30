package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午2:06:41
 */
public class HungEventReason {
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

	public HungEventReason(String reason, String times, String rate) {
		this.reason = reason;
		this.times = times;
		this.rate = rate;
	}

}

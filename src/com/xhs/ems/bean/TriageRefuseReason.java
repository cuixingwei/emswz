package com.xhs.ems.bean;

/**
 * @datetime 2015年11月14日 下午2:02:06
 * @author 崔兴伟
 */
public class TriageRefuseReason {
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

	public TriageRefuseReason() {
	}

}

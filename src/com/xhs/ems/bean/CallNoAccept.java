package com.xhs.ems.bean;

/**
 * @category 呼救未受理
 * @author 崔兴伟
 * @datetime 2015年5月21日 上午11:33:53
 */
public class CallNoAccept {
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 次数
	 */
	private String times;
	/**
	 * 占比
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

	public CallNoAccept(String reason, String times, String rate) {
		this.reason = reason;
		this.times = times;
		this.rate = rate;
	}

}

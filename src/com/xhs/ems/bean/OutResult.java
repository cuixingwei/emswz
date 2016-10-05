package com.xhs.ems.bean;

/**
 * @datetime 2015年11月14日 下午1:24:53
 * @author 崔兴伟
 */
public class OutResult {
	/**
	 * 转归结果
	 */
	private String resultName;
	/**
	 * 次数
	 */
	private String times;
	/**
	 * 比率
	 */
	private String rate;

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
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

	public OutResult() {
	}

}

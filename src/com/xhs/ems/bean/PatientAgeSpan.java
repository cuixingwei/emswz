package com.xhs.ems.bean;

/**
 * @category 病人年龄分段统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午2:58:52
 */
public class PatientAgeSpan {
	/**
	 * 年龄段
	 */
	private String span;
	/**
	 * 人数
	 */
	private String times;
	/**
	 * 比率
	 */
	private String rate;

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
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

	public PatientAgeSpan(String span, String times, String rate) {
		this.span = span;
		this.times = times;
		this.rate = rate;
	}

}

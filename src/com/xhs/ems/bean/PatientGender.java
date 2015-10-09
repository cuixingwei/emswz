package com.xhs.ems.bean;

/**
 * @category 病人性别统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午2:56:21
 */
public class PatientGender {
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 人数
	 */
	private String times;
	/**
	 * 比率
	 */
	private String rate;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public PatientGender(String gender, String times, String rate) {
		this.gender = gender;
		this.times = times;
		this.rate = rate;
	}

}

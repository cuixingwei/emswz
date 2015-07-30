package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午2:02:02
 * @category 疾病回填率
 */
public class PatientCaseFillCount {
	/**
	 * 分站名称
	 */
	private String station;
	/**
	 * 120派诊数
	 */
	private String sendNumbers;
	/**
	 * 回填数
	 */
	private String fillNumbers;
	/**
	 * 回填率
	 */
	private String rate;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getSendNumbers() {
		return sendNumbers;
	}

	public void setSendNumbers(String sendNumbers) {
		this.sendNumbers = sendNumbers;
	}

	public String getFillNumbers() {
		return fillNumbers;
	}

	public void setFillNumbers(String fillNumbers) {
		this.fillNumbers = fillNumbers;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public PatientCaseFillCount(String station, String sendNumbers,
			String fillNumbers, String rate) {
		this.station = station;
		this.sendNumbers = sendNumbers;
		this.fillNumbers = fillNumbers;
		this.rate = rate;
	}

}

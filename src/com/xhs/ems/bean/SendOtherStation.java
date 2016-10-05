package com.xhs.ems.bean;

/**
 * @category 其他医院调度分诊
 * @author 崔兴伟
 * @datetime 2015年5月21日 上午11:38:51
 */
public class SendOtherStation {
	/**
	 * 医院名称
	 */
	private String station;
	/**
	 * 受理次数
	 */
	private String acceptTimes;
	/**
	 * 接回
	 */
	private String takeBacks;
	/**
	 * 未受理次数
	 */
	private String noAcceptTimes;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getAcceptTimes() {
		return acceptTimes;
	}

	public void setAcceptTimes(String acceptTimes) {
		this.acceptTimes = acceptTimes;
	}

	public String getTakeBacks() {
		return takeBacks;
	}

	public void setTakeBacks(String takeBacks) {
		this.takeBacks = takeBacks;
	}

	public String getNoAcceptTimes() {
		return noAcceptTimes;
	}

	public void setNoAcceptTimes(String noAcceptTimes) {
		this.noAcceptTimes = noAcceptTimes;
	}

	public SendOtherStation(String station, String acceptTimes,
			String takeBacks, String noAcceptTimes) {
		this.station = station;
		this.acceptTimes = acceptTimes;
		this.takeBacks = takeBacks;
		this.noAcceptTimes = noAcceptTimes;
	}

}

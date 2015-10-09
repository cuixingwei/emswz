package com.xhs.ems.bean;

/**
 * @category 分诊他院拒绝受理原因统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午3:05:40
 */
public class RefuseAcceptReason {
	/**
	 * 医院名称
	 */
	private String station;
	/**
	 * 分诊总数
	 */
	private String totals;
	/**
	 * 拒绝受理原因
	 */
	private String reason;
	/**
	 * 次数
	 */
	private String times;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTotals() {
		return totals;
	}

	public void setTotals(String totals) {
		this.totals = totals;
	}

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

	public RefuseAcceptReason(String station, String totals, String reason,
			String times) {
		this.station = station;
		this.totals = totals;
		this.reason = reason;
		this.times = times;
	}

}

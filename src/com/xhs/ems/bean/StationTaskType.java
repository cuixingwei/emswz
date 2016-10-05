package com.xhs.ems.bean;

/**
 * @category 站点任务类型统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 上午11:59:32
 */
public class StationTaskType {
	/**
	 * 站点名称
	 */
	private String station;
	/**
	 * 总次数_次数
	 */
	private String ztimes;
	/**
	 * 总次数_里程
	 */
	private String zdistance;
	/**
	 * 总次数_平均反应时间
	 */
	private String zaverageResponseTime;
	/**
	 * 总次数_平均耗时
	 */
	private String zaverageTime;
	/**
	 * 现场急救_次数
	 */
	private String xctimes;
	/**
	 * 现场急救_里程
	 */
	private String xcdistance;
	/**
	 * 现场急救_平均反应时间
	 */
	private String xcaverageResponseTime;
	/**
	 * 现场急救_平均耗时
	 */
	private String xcaverageTime;
	/**
	 * 医院转诊_次数
	 */
	private String yytimes;
	/**
	 * 医院转诊_里程
	 */
	private String yydistance;
	/**
	 * 医院转诊_平均反应时间
	 */
	private String yyaverageResponseTime;
	/**
	 * 医院转诊_平均耗时
	 */
	private String yyaverageTime;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getZtimes() {
		return ztimes;
	}

	public void setZtimes(String ztimes) {
		this.ztimes = ztimes;
	}

	public String getZdistance() {
		return zdistance;
	}

	public void setZdistance(String zdistance) {
		this.zdistance = zdistance;
	}

	public String getZaverageResponseTime() {
		return zaverageResponseTime;
	}

	public void setZaverageResponseTime(String zaverageResponseTime) {
		this.zaverageResponseTime = zaverageResponseTime;
	}

	public String getZaverageTime() {
		return zaverageTime;
	}

	public void setZaverageTime(String zaverageTime) {
		this.zaverageTime = zaverageTime;
	}

	public String getXctimes() {
		return xctimes;
	}

	public void setXctimes(String xctimes) {
		this.xctimes = xctimes;
	}

	public String getXcdistance() {
		return xcdistance;
	}

	public void setXcdistance(String xcdistance) {
		this.xcdistance = xcdistance;
	}

	public String getXcaverageResponseTime() {
		return xcaverageResponseTime;
	}

	public void setXcaverageResponseTime(String xcaverageResponseTime) {
		this.xcaverageResponseTime = xcaverageResponseTime;
	}

	public String getXcaverageTime() {
		return xcaverageTime;
	}

	public void setXcaverageTime(String xcaverageTime) {
		this.xcaverageTime = xcaverageTime;
	}

	public String getYytimes() {
		return yytimes;
	}

	public void setYytimes(String yytimes) {
		this.yytimes = yytimes;
	}

	public String getYydistance() {
		return yydistance;
	}

	public void setYydistance(String yydistance) {
		this.yydistance = yydistance;
	}

	public String getYyaverageResponseTime() {
		return yyaverageResponseTime;
	}

	public void setYyaverageResponseTime(String yyaverageResponseTime) {
		this.yyaverageResponseTime = yyaverageResponseTime;
	}

	public String getYyaverageTime() {
		return yyaverageTime;
	}

	public void setYyaverageTime(String yyaverageTime) {
		this.yyaverageTime = yyaverageTime;
	}

	public StationTaskType(String station, String ztimes, String zdistance,
			String zaverageResponseTime, String zaverageTime, String xctimes,
			String xcdistance, String xcaverageResponseTime,
			String xcaverageTime, String yytimes, String yydistance,
			String yyaverageResponseTime, String yyaverageTime) {
		this.station = station;
		this.ztimes = ztimes;
		this.zdistance = zdistance;
		this.zaverageResponseTime = zaverageResponseTime;
		this.zaverageTime = zaverageTime;
		this.xctimes = xctimes;
		this.xcdistance = xcdistance;
		this.xcaverageResponseTime = xcaverageResponseTime;
		this.xcaverageTime = xcaverageTime;
		this.yytimes = yytimes;
		this.yydistance = yydistance;
		this.yyaverageResponseTime = yyaverageResponseTime;
		this.yyaverageTime = yyaverageTime;
	}

}

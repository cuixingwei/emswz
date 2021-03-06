package com.xhs.ems.bean;

/**
 * @category 任务性质统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 上午11:24:52
 */
public class TaskNature {
	/**
	 * 出诊类型
	 */
	private String outCallType;
	/**
	 * 次数
	 */
	private String times;
	/**
	 * 接回数
	 */
	private String takeBacks;
	/**
	 * 接回率
	 */
	private String takeBackRate;
	/**
	 * 里程
	 */
	private String distance;
	/**
	 * 平均反应时间
	 */
	private String averageResponseTime;
	/**
	 * 平均用时
	 */
	private String averageTime;
	/**
	 * 空车
	 */
	private String emptyCars;
	/**
	 * 拒绝入院
	 */
	private String refuseToHospitals;
	/**
	 * 死亡
	 */
	private String deaths;
	/**
	 * 市区
	 */
	private String shiqu;
	/**
	 * 万州
	 */
	private String wanzhou;
	/**
	 * 其他
	 */
	private String others;

	public String getShiqu() {
		return shiqu;
	}

	public void setShiqu(String shiqu) {
		this.shiqu = shiqu;
	}

	public String getWanzhou() {
		return wanzhou;
	}

	public void setWanzhou(String wanzhou) {
		this.wanzhou = wanzhou;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getOutCallType() {
		return outCallType;
	}

	public void setOutCallType(String outCallType) {
		this.outCallType = outCallType;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getTakeBacks() {
		return takeBacks;
	}

	public void setTakeBacks(String takeBacks) {
		this.takeBacks = takeBacks;
	}

	public String getTakeBackRate() {
		return takeBackRate;
	}

	public void setTakeBackRate(String takeBackRate) {
		this.takeBackRate = takeBackRate;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getAverageResponseTime() {
		return averageResponseTime;
	}

	public void setAverageResponseTime(String averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}

	public String getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(String averageTime) {
		this.averageTime = averageTime;
	}

	public String getEmptyCars() {
		return emptyCars;
	}

	public void setEmptyCars(String emptyCars) {
		this.emptyCars = emptyCars;
	}

	public String getRefuseToHospitals() {
		return refuseToHospitals;
	}

	public void setRefuseToHospitals(String refuseToHospitals) {
		this.refuseToHospitals = refuseToHospitals;
	}

	public String getDeaths() {
		return deaths;
	}

	public void setDeaths(String deaths) {
		this.deaths = deaths;
	}

	public TaskNature() {
	}

}

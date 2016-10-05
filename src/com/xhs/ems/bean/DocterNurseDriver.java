package com.xhs.ems.bean;

/**
 * @category 医护、司机工作统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 上午11:01:13
 */
public class DocterNurseDriver {
	/**
	 * 站点
	 */
	private String station;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 出诊数
	 */
	private String outCalls;
	/**
	 * 接回数
	 */
	private String takeBacks;
	/**
	 * 空车
	 */
	private String emptyCars;
	/**
	 * 拒绝入院
	 */
	private String refuseHospitals;
	/**
	 * 现场死亡
	 */
	private String spotDeaths;
	/**
	 * 救后死亡
	 */
	private String afterDeaths;
	/**
	 * 其他
	 */
	private String others;
	/**
	 * 里程合计
	 */
	private String distanceTotal;
	/**
	 * 收费合计
	 */
	private String costToal;
	/**
	 * 平均反应时间
	 */
	private String averageResponseTime;
	/**
	 * 出诊用时合计
	 */
	private String outCallTimeTotal;
	/**
	 * 治疗数统计
	 */
	private String cureNumbers;
	/**
	 * 安全送出
	 */
	private String safeOut;
	/**
	 * 非急救任务完成
	 */
	private String noAmbulance;
	/**
	 * 平均出车时间
	 */
	private String averageSendTime;

	public String getSafeOut() {
		return safeOut;
	}

	public void setSafeOut(String safeOut) {
		this.safeOut = safeOut;
	}

	public String getNoAmbulance() {
		return noAmbulance;
	}

	public void setNoAmbulance(String noAmbulance) {
		this.noAmbulance = noAmbulance;
	}

	public String getAverageSendTime() {
		return averageSendTime;
	}

	public void setAverageSendTime(String averageSendTime) {
		this.averageSendTime = averageSendTime;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutCalls() {
		return outCalls;
	}

	public void setOutCalls(String outCalls) {
		this.outCalls = outCalls;
	}

	public String getTakeBacks() {
		return takeBacks;
	}

	public void setTakeBacks(String takeBacks) {
		this.takeBacks = takeBacks;
	}

	public String getEmptyCars() {
		return emptyCars;
	}

	public void setEmptyCars(String emptyCars) {
		this.emptyCars = emptyCars;
	}

	public String getRefuseHospitals() {
		return refuseHospitals;
	}

	public void setRefuseHospitals(String refuseHospitals) {
		this.refuseHospitals = refuseHospitals;
	}

	public String getSpotDeaths() {
		return spotDeaths;
	}

	public void setSpotDeaths(String spotDeaths) {
		this.spotDeaths = spotDeaths;
	}

	public String getAfterDeaths() {
		return afterDeaths;
	}

	public void setAfterDeaths(String afterDeaths) {
		this.afterDeaths = afterDeaths;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getDistanceTotal() {
		return distanceTotal;
	}

	public void setDistanceTotal(String distanceTotal) {
		this.distanceTotal = distanceTotal;
	}

	public String getCostToal() {
		return costToal;
	}

	public void setCostToal(String costToal) {
		this.costToal = costToal;
	}

	public String getAverageResponseTime() {
		return averageResponseTime;
	}

	public void setAverageResponseTime(String averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}

	public String getOutCallTimeTotal() {
		return outCallTimeTotal;
	}

	public void setOutCallTimeTotal(String outCallTimeTotal) {
		this.outCallTimeTotal = outCallTimeTotal;
	}

	public String getCureNumbers() {
		return cureNumbers;
	}

	public void setCureNumbers(String cureNumbers) {
		this.cureNumbers = cureNumbers;
	}

	public DocterNurseDriver() {

	}

}

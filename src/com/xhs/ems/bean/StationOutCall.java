package com.xhs.ems.bean;

/**
 * @category 站点出诊情况
 * @author 崔兴伟
 * @datetime 2015年5月21日 上午11:42:53
 */
public class StationOutCall {
	/**
	 * 站点名称
	 */
	private String station;
	/**
	 * 现场急救
	 */
	private String spotFirstAid;
	/**
	 * 医院转诊
	 */
	private String stationTransfer;
	/**
	 * 院内转运
	 */
	private String inHospitalTransfer;
	/**
	 * 送出病人
	 */
	private String sendOutPatient;
	/**
	 * 保障
	 */
	private String safeguard;
	/**
	 * 行政用车(administrative use vehicle)
	 */
	private String auv;
	/**
	 * 义诊
	 */
	private String volunteer;
	/**
	 * 培训
	 */
	private String train;
	/**
	 * 演习
	 */
	private String practice;
	/**
	 * 其他
	 */
	private String other;
	/**
	 * 出诊数合计
	 */
	private String outCallTotal;
	/**
	 * 接回数合计
	 */
	private String tackBackTotal;
	/**
	 * 里程
	 */
	private String distance;
	/**
	 * 空车
	 */
	private String emptyCars;
	/**
	 * 拒绝
	 */
	private String refuses;
	/**
	 * 死亡
	 */
	private String death;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getSpotFirstAid() {
		return spotFirstAid;
	}

	public void setSpotFirstAid(String spotFirstAid) {
		this.spotFirstAid = spotFirstAid;
	}

	public String getStationTransfer() {
		return stationTransfer;
	}

	public void setStationTransfer(String stationTransfer) {
		this.stationTransfer = stationTransfer;
	}

	public String getInHospitalTransfer() {
		return inHospitalTransfer;
	}

	public void setInHospitalTransfer(String inHospitalTransfer) {
		this.inHospitalTransfer = inHospitalTransfer;
	}

	public String getSendOutPatient() {
		return sendOutPatient;
	}

	public void setSendOutPatient(String sendOutPatient) {
		this.sendOutPatient = sendOutPatient;
	}

	public String getSafeguard() {
		return safeguard;
	}

	public void setSafeguard(String safeguard) {
		this.safeguard = safeguard;
	}

	public String getAuv() {
		return auv;
	}

	public void setAuv(String auv) {
		this.auv = auv;
	}

	public String getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(String volunteer) {
		this.volunteer = volunteer;
	}

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}

	public String getPractice() {
		return practice;
	}

	public void setPractice(String practice) {
		this.practice = practice;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getOutCallTotal() {
		return outCallTotal;
	}

	public void setOutCallTotal(String outCallTotal) {
		this.outCallTotal = outCallTotal;
	}

	public String getTackBackTotal() {
		return tackBackTotal;
	}

	public void setTackBackTotal(String tackBackTotal) {
		this.tackBackTotal = tackBackTotal;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getEmptyCars() {
		return emptyCars;
	}

	public void setEmptyCars(String emptyCars) {
		this.emptyCars = emptyCars;
	}

	public String getRefuses() {
		return refuses;
	}

	public void setRefuses(String refuses) {
		this.refuses = refuses;
	}

	public String getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = death;
	}

	public StationOutCall(String station, String spotFirstAid,
			String stationTransfer, String inHospitalTransfer,
			String sendOutPatient, String safeguard, String auv,
			String volunteer, String train, String practice, String other,
			String outCallTotal, String tackBackTotal, String distance,
			String emptyCars, String refuses, String death) {
		this.station = station;
		this.spotFirstAid = spotFirstAid;
		this.stationTransfer = stationTransfer;
		this.inHospitalTransfer = inHospitalTransfer;
		this.sendOutPatient = sendOutPatient;
		this.safeguard = safeguard;
		this.auv = auv;
		this.volunteer = volunteer;
		this.train = train;
		this.practice = practice;
		this.other = other;
		this.outCallTotal = outCallTotal;
		this.tackBackTotal = tackBackTotal;
		this.distance = distance;
		this.emptyCars = emptyCars;
		this.refuses = refuses;
		this.death = death;
	}

}

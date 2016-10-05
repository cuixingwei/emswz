package com.xhs.ems.bean;


/**
 * @datetime 2015年11月12日 下午12:13:10
 * @author 崔兴伟
 */
public class CenterBusiness {
	/**
	 * 呼入电话数
	 */
	private String inPhone;
	/**
	 * 呼出电话数
	 */
	private String outPhone;
	/**
	 * 合计
	 */
	private String totalPhone;
	/**
	 * 呼救电话数
	 */
	private String helpPhone;
	/**
	 * 110来电
	 */
	private String phoneOf110;
	/**
	 * 119来电
	 */
	private String phoneOf119;
	/**
	 * 其他
	 */
	private String phoneOfOther;
	/**
	 * 现场急救派车
	 */
	private String spotFirstAid;
	/**
	 * 医院转诊派车
	 */
	private String stationTransfer;
	/**
	 * 转运派车
	 */
	private String inHospitalTransfer;
	/**
	 * 保障
	 */
	private String safeguard;
	/**
	 * 非急救用车
	 */
	private String noFirstAid;
	/**
	 * 急救受理时长
	 */
	private String firstaidAcceptTime;
	/**
	 * 转诊受理时长
	 */
	private String referralAcceptTime;
	/**
	 * 中心派车
	 */
	private String centerSendCar;
	/**
	 * 分诊派车
	 */
	private String referralSendCar;
	/**
	 * 挂起事件计数
	 */
	private String hungNumbers;
	/**
	 * 中止任务计数
	 */
	private String stopStaskNumbers;

	public String getInPhone() {
		return inPhone;
	}

	public void setInPhone(String inPhone) {
		this.inPhone = inPhone;
	}

	public String getOutPhone() {
		return outPhone;
	}

	public void setOutPhone(String outPhone) {
		this.outPhone = outPhone;
	}

	public String getTotalPhone() {
		return totalPhone;
	}

	public void setTotalPhone(String totalPhone) {
		this.totalPhone = totalPhone;
	}

	public String getHelpPhone() {
		return helpPhone;
	}

	public void setHelpPhone(String helpPhone) {
		this.helpPhone = helpPhone;
	}

	public String getPhoneOf110() {
		return phoneOf110;
	}

	public void setPhoneOf110(String phoneOf110) {
		this.phoneOf110 = phoneOf110;
	}

	public String getPhoneOf119() {
		return phoneOf119;
	}

	public void setPhoneOf119(String phoneOf119) {
		this.phoneOf119 = phoneOf119;
	}

	public String getPhoneOfOther() {
		return phoneOfOther;
	}

	public void setPhoneOfOther(String phoneOfOther) {
		this.phoneOfOther = phoneOfOther;
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

	public String getSafeguard() {
		return safeguard;
	}

	public void setSafeguard(String safeguard) {
		this.safeguard = safeguard;
	}

	public String getNoFirstAid() {
		return noFirstAid;
	}

	public void setNoFirstAid(String noFirstAid) {
		this.noFirstAid = noFirstAid;
	}

	public String getFirstaidAcceptTime() {
		return firstaidAcceptTime;
	}

	public void setFirstaidAcceptTime(String firstaidAcceptTime) {
		this.firstaidAcceptTime = firstaidAcceptTime;
	}

	public String getReferralAcceptTime() {
		return referralAcceptTime;
	}

	public void setReferralAcceptTime(String referralAcceptTime) {
		this.referralAcceptTime = referralAcceptTime;
	}

	public String getCenterSendCar() {
		return centerSendCar;
	}

	public void setCenterSendCar(String centerSendCar) {
		this.centerSendCar = centerSendCar;
	}

	public String getReferralSendCar() {
		return referralSendCar;
	}

	public void setReferralSendCar(String referralSendCar) {
		this.referralSendCar = referralSendCar;
	}

	public String getHungNumbers() {
		return hungNumbers;
	}

	public void setHungNumbers(String hungNumbers) {
		this.hungNumbers = hungNumbers;
	}

	public String getStopStaskNumbers() {
		return stopStaskNumbers;
	}

	public void setStopStaskNumbers(String stopStaskNumbers) {
		this.stopStaskNumbers = stopStaskNumbers;
	}

	public CenterBusiness() {
	}

}

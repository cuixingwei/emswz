package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午7:41:42
 */
public class AnswerAlarm {
	/**
	 * 受理ID
	 */
	private String id;
	/**
	 * 接诊时间
	 */
	private String answerAlarmTime;
	/**
	 * 报警电话
	 */
	private String alarmPhone;
	/**
	 * 相关电话
	 */
	private String relatedPhone;
	/**
	 * 报警地址
	 */
	private String siteAddress;
	/**
	 * 电话判断
	 */
	private String judgementOnPhone;
	/**
	 * 出车分站
	 */
	private String station;
	/**
	 * 派车时间
	 */
	private String sendCarTime;
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 接回数
	 */
	private String takeBacks;
	/**
	 * 出诊结果
	 */
	private String outResult;
	/**
	 * 分诊医院
	 */
	private String triageStation;

	public String getTriageStation() {
		return triageStation;
	}

	public void setTriageStation(String triageStation) {
		this.triageStation = triageStation;
	}

	public String getTakeBacks() {
		return takeBacks;
	}

	public void setTakeBacks(String takeBacks) {
		this.takeBacks = takeBacks;
	}

	public String getOutResult() {
		return outResult;
	}

	public void setOutResult(String outResult) {
		this.outResult = outResult;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnswerAlarmTime() {
		return answerAlarmTime;
	}

	public void setAnswerAlarmTime(String answerAlarmTime) {
		this.answerAlarmTime = answerAlarmTime;
	}

	public String getAlarmPhone() {
		return alarmPhone;
	}

	public void setAlarmPhone(String alarmPhone) {
		this.alarmPhone = alarmPhone;
	}

	public String getRelatedPhone() {
		return relatedPhone;
	}

	public void setRelatedPhone(String relatedPhone) {
		this.relatedPhone = relatedPhone;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public String getJudgementOnPhone() {
		return judgementOnPhone;
	}

	public void setJudgementOnPhone(String judgementOnPhone) {
		this.judgementOnPhone = judgementOnPhone;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getSendCarTime() {
		return sendCarTime;
	}

	public void setSendCarTime(String sendCarTime) {
		this.sendCarTime = sendCarTime;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public AnswerAlarm() {
	}

}

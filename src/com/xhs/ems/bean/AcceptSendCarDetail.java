package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年5月9日 上午10:20:13
 * @category 事件详情
 */
public class AcceptSendCarDetail {
	/**
	 * 事件表,受理
	 */
	private String eventType; // 事件类型
	private String eventNature; // 事件性质
	private String callPhone; // 主叫号码
	private String callAddress; // 呼救地点
	private String patientNeed; // 病人需求
	private String preJudgment; // 初步判断
	private String sickCondition; // 病情
	private String specialNeeds; // 特殊需求
	private String humanNumbers; // 人数
	private String sickName; // 患者姓名
	private String gender; // 性别
	private String age; // 年龄
	private String identity; // 身份
	private String contactMan; // 联系人
	private String contactPhone; // 联系电话
	private String extension; // 分机
	private String thisDispatcher; // 本次调度员
	private String remark; // 备注
	private String isOrNoLitter; // 是否担架
	private String acceptNumber; // 受理序号

	/**
	 * 受理表
	 */
	private String acceptStartTime; // 开始时刻
	private String acceptType; // 受理类型
	private String toBeSentReason; // 待派原因
	private String endAcceptTime; // 结束受理时刻
	private String cancelReason; // 撤销原因
	private String sendCarTime; // 派车时刻
	private String sendStation; // 出车医院
	/**
	 * 任务表
	 */
	private String carIndentiy; // 车辆标识
	private String state; // 状态
	private String receiveOrderTime; // 接受命令时刻
	private String taskResult; // 出车结果
	private String reason; // 原因
	private String outCarTime; // 出车时刻
	private String taskRemark; // 任务备注
	private String arriveSpotTime; // 到达现场时刻
	private String takeHumanNumbers; // 接回人数
	private String toHospitalNumbers; // 入院人数
	private String leaveSpotTime; // 离开现场时刻
	private String deathNumbers; // 死亡人数
	private String stayHospitalNumbers; // 留院人数
	private String backHospitalNumbers; // 返院（转院）人数
	private String completeTime; // 完成时刻
	private String stationDispatcher; // 分站调度员
	private String outCarNumbers; // 出车次数

	private String record; // 录音文件名

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventNature() {
		return eventNature;
	}

	public void setEventNature(String eventNature) {
		this.eventNature = eventNature;
	}

	public String getCallPhone() {
		return callPhone;
	}

	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}

	public String getCallAddress() {
		return callAddress;
	}

	public void setCallAddress(String callAddress) {
		this.callAddress = callAddress;
	}

	public String getPatientNeed() {
		return patientNeed;
	}

	public void setPatientNeed(String patientNeed) {
		this.patientNeed = patientNeed;
	}

	public String getPreJudgment() {
		return preJudgment;
	}

	public void setPreJudgment(String preJudgment) {
		this.preJudgment = preJudgment;
	}

	public String getSickCondition() {
		return sickCondition;
	}

	public void setSickCondition(String sickCondition) {
		this.sickCondition = sickCondition;
	}

	public String getSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public String getHumanNumbers() {
		return humanNumbers;
	}

	public void setHumanNumbers(String humanNumbers) {
		this.humanNumbers = humanNumbers;
	}

	public String getSickName() {
		return sickName;
	}

	public void setSickName(String sickName) {
		this.sickName = sickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getThisDispatcher() {
		return thisDispatcher;
	}

	public void setThisDispatcher(String thisDispatcher) {
		this.thisDispatcher = thisDispatcher;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsOrNoLitter() {
		return isOrNoLitter;
	}

	public void setIsOrNoLitter(String isOrNoLitter) {
		this.isOrNoLitter = isOrNoLitter;
	}

	public String getAcceptNumber() {
		return acceptNumber;
	}

	public void setAcceptNumber(String acceptNumber) {
		this.acceptNumber = acceptNumber;
	}

	public String getAcceptStartTime() {
		return acceptStartTime;
	}

	public void setAcceptStartTime(String acceptStartTime) {
		this.acceptStartTime = acceptStartTime;
	}

	public String getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}

	public String getToBeSentReason() {
		return toBeSentReason;
	}

	public void setToBeSentReason(String toBeSentReason) {
		this.toBeSentReason = toBeSentReason;
	}

	public String getEndAcceptTime() {
		return endAcceptTime;
	}

	public void setEndAcceptTime(String endAcceptTime) {
		this.endAcceptTime = endAcceptTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getSendCarTime() {
		return sendCarTime;
	}

	public void setSendCarTime(String sendCarTime) {
		this.sendCarTime = sendCarTime;
	}

	public String getSendStation() {
		return sendStation;
	}

	public void setSendStation(String sendStation) {
		this.sendStation = sendStation;
	}

	public String getCarIndentiy() {
		return carIndentiy;
	}

	public void setCarIndentiy(String carIndentiy) {
		this.carIndentiy = carIndentiy;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReceiveOrderTime() {
		return receiveOrderTime;
	}

	public void setReceiveOrderTime(String receiveOrderTime) {
		this.receiveOrderTime = receiveOrderTime;
	}

	public String getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOutCarTime() {
		return outCarTime;
	}

	public void setOutCarTime(String outCarTime) {
		this.outCarTime = outCarTime;
	}

	public String getTaskRemark() {
		return taskRemark;
	}

	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}

	public String getArriveSpotTime() {
		return arriveSpotTime;
	}

	public void setArriveSpotTime(String arriveSpotTime) {
		this.arriveSpotTime = arriveSpotTime;
	}

	public String getTakeHumanNumbers() {
		return takeHumanNumbers;
	}

	public void setTakeHumanNumbers(String takeHumanNumbers) {
		this.takeHumanNumbers = takeHumanNumbers;
	}

	public String getToHospitalNumbers() {
		return toHospitalNumbers;
	}

	public void setToHospitalNumbers(String toHospitalNumbers) {
		this.toHospitalNumbers = toHospitalNumbers;
	}

	public String getLeaveSpotTime() {
		return leaveSpotTime;
	}

	public void setLeaveSpotTime(String leaveSpotTime) {
		this.leaveSpotTime = leaveSpotTime;
	}

	public String getDeathNumbers() {
		return deathNumbers;
	}

	public void setDeathNumbers(String deathNumbers) {
		this.deathNumbers = deathNumbers;
	}

	public String getStayHospitalNumbers() {
		return stayHospitalNumbers;
	}

	public void setStayHospitalNumbers(String stayHospitalNumbers) {
		this.stayHospitalNumbers = stayHospitalNumbers;
	}

	public String getBackHospitalNumbers() {
		return backHospitalNumbers;
	}

	public void setBackHospitalNumbers(String backHospitalNumbers) {
		this.backHospitalNumbers = backHospitalNumbers;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getStationDispatcher() {
		return stationDispatcher;
	}

	public void setStationDispatcher(String stationDispatcher) {
		this.stationDispatcher = stationDispatcher;
	}

	public String getOutCarNumbers() {
		return outCarNumbers;
	}

	public void setOutCarNumbers(String outCarNumbers) {
		this.outCarNumbers = outCarNumbers;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public AcceptSendCarDetail(String eventType, String eventNature,
			String callPhone, String callAddress, String patientNeed,
			String preJudgment, String sickCondition, String specialNeeds,
			String humanNumbers, String sickName, String gender, String age,
			String identity, String contactMan, String contactPhone,
			String extension, String thisDispatcher, String remark,
			String isOrNoLitter, String acceptNumber, String acceptStartTime,
			String acceptType, String toBeSentReason, String endAcceptTime,
			String cancelReason, String sendCarTime, String sendStation,
			String carIndentiy, String state, String receiveOrderTime,
			String taskResult, String reason, String outCarTime,
			String taskRemark, String arriveSpotTime, String takeHumanNumbers,
			String toHospitalNumbers, String leaveSpotTime,
			String deathNumbers, String stayHospitalNumbers,
			String backHospitalNumbers, String completeTime,
			String stationDispatcher, String outCarNumbers, String record) {
		this.eventType = eventType;
		this.eventNature = eventNature;
		this.callPhone = callPhone;
		this.callAddress = callAddress;
		this.patientNeed = patientNeed;
		this.preJudgment = preJudgment;
		this.sickCondition = sickCondition;
		this.specialNeeds = specialNeeds;
		this.humanNumbers = humanNumbers;
		this.sickName = sickName;
		this.gender = gender;
		this.age = age;
		this.identity = identity;
		this.contactMan = contactMan;
		this.contactPhone = contactPhone;
		this.extension = extension;
		this.thisDispatcher = thisDispatcher;
		this.remark = remark;
		this.isOrNoLitter = isOrNoLitter;
		this.acceptNumber = acceptNumber;
		this.acceptStartTime = acceptStartTime;
		this.acceptType = acceptType;
		this.toBeSentReason = toBeSentReason;
		this.endAcceptTime = endAcceptTime;
		this.cancelReason = cancelReason;
		this.sendCarTime = sendCarTime;
		this.sendStation = sendStation;
		this.carIndentiy = carIndentiy;
		this.state = state;
		this.receiveOrderTime = receiveOrderTime;
		this.taskResult = taskResult;
		this.reason = reason;
		this.outCarTime = outCarTime;
		this.taskRemark = taskRemark;
		this.arriveSpotTime = arriveSpotTime;
		this.takeHumanNumbers = takeHumanNumbers;
		this.toHospitalNumbers = toHospitalNumbers;
		this.leaveSpotTime = leaveSpotTime;
		this.deathNumbers = deathNumbers;
		this.stayHospitalNumbers = stayHospitalNumbers;
		this.backHospitalNumbers = backHospitalNumbers;
		this.completeTime = completeTime;
		this.stationDispatcher = stationDispatcher;
		this.outCarNumbers = outCarNumbers;
		this.record = record;
	}

}

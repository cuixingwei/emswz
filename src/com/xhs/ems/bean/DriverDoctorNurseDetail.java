package com.xhs.ems.bean;

/**
 * 驾驶员，医生，护士出诊明细
 * 
 * @author 崔兴伟
 * @datetime 2015年10月8日 上午11:09:28
 */
public class DriverDoctorNurseDetail {
	private String dateTime; // 日期时间
	private String patientName; // 病人姓名
	private String address; // 现场地址
	private String outStation; // 出诊分站
	private String outResult; // 出诊结果
	private String driver; // 出诊司机
	private String doctor; // 医生
	private String nurse; // 护士
	private String distance; // 里程
	private String sex; // 性别
	private String age; // 年龄
	private String diagnose; // 诊断
	private String diseaseDepartment; // 疾病科别
	private String classState; // 分类统计
	private String diseaseDegree; // 病情程度
	private String treatmentEffet; // 救治效果
	private String area; // 区域
	private String sendAddress; // 送到地点
	private String eventType; // 出诊类型
	private String carCode; // 车辆编码
	private String poorTime; // 出车差时
	private String userTime; // 出诊耗时
	private String cureMeasure; // 救治措施
	private String dispatcher; //调度员

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public DriverDoctorNurseDetail() {
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getPoorTime() {
		return poorTime;
	}

	public void setPoorTime(String poorTime) {
		this.poorTime = poorTime;
	}

	public String getUserTime() {
		return userTime;
	}

	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}

	public String getCureMeasure() {
		return cureMeasure;
	}

	public void setCureMeasure(String cureMeasure) {
		this.cureMeasure = cureMeasure;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getDiseaseDepartment() {
		return diseaseDepartment;
	}

	public void setDiseaseDepartment(String diseaseDepartment) {
		this.diseaseDepartment = diseaseDepartment;
	}

	public String getClassState() {
		return classState;
	}

	public void setClassState(String classState) {
		this.classState = classState;
	}

	public String getDiseaseDegree() {
		return diseaseDegree;
	}

	public void setDiseaseDegree(String diseaseDegree) {
		this.diseaseDegree = diseaseDegree;
	}

	public String getTreatmentEffet() {
		return treatmentEffet;
	}

	public void setTreatmentEffet(String treatmentEffet) {
		this.treatmentEffet = treatmentEffet;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOutStation() {
		return outStation;
	}

	public void setOutStation(String outStation) {
		this.outStation = outStation;
	}

	public String getOutResult() {
		return outResult;
	}

	public void setOutResult(String outResult) {
		this.outResult = outResult;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

}

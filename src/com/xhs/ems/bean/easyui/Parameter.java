package com.xhs.ems.bean.easyui;

public class Parameter {
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 调试员
	 */
	private String dispatcher;
	/**
	 * datagrid 参数 页码
	 */
	private int page;
	/**
	 * datagrid 参数 每页的行数
	 */
	private int rows;
	/**
	 * datagrid 参数 排序字段
	 */
	private String sort;
	/**
	 * datagrid 参数 排序类型desc , asc
	 */
	/**
	 * 通讯录查询（姓名）
	 */
	private String name;
	/**
	 * 中心任务信息统计（分站）
	 */
	private String station;
	/**
	 * 中止任务信息（中止任务原因编码）
	 */
	private String stopReason;
	/**
	 * 中止任务信息（车辆编码）
	 */
	private String carCode;
	/**
	 * 车辆暂停调用情况(暂停调用原因)
	 */
	private String pauseReason;
	/**
	 * 车辆状态变化（事件名称）
	 */
	private String eventName;
	/**
	 * 放空车统计（空车原因）
	 */
	private String emptyReason;
	/**
	 * 挂起事件流水统计(挂起原因)
	 */
	private String hungReason;
	/**
	 * 中心接警情况统计(报警电话)
	 */
	private String alarmPhone;
	/**
	 * 中心接警情况统计(报警地点)
	 */
	private String siteAddress;
	/**
	 * 医生护士工作统计（1代表医生，2代表护士,3代表司机）
	 */
	private String doctorOrNurseOrDriver;
	/**
	 * 通讯录查询（电话）
	 */
	private String phone;
	/**
	 * 急救站晚出诊统计(出诊时长最小值)
	 */
	private String outCarTimesMin;
	/**
	 * 急救站晚出诊统计(出诊时长最大值)
	 */
	private String outCarTimesMax;
	/**
	 * 响铃到接听大于X秒（超时时长）
	 */
	private String overtimes;
	/**
	 * 中止任务信息（空跑时间）
	 */
	private String emptyRunTime;
	/**
	 * 事件详情查询（受理表的ID）
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmptyRunTime() {
		return emptyRunTime;
	}

	public void setEmptyRunTime(String emptyRunTime) {
		this.emptyRunTime = emptyRunTime;
	}

	public String getOvertimes() {
		return overtimes;
	}

	public void setOvertimes(String overtimes) {
		this.overtimes = overtimes;
	}

	public String getOutCarTimesMin() {
		return outCarTimesMin;
	}

	public void setOutCarTimesMin(String outCarTimesMin) {
		this.outCarTimesMin = outCarTimesMin;
	}

	public String getOutCarTimesMax() {
		return outCarTimesMax;
	}

	public void setOutCarTimesMax(String outCarTimesMax) {
		this.outCarTimesMax = outCarTimesMax;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDoctorOrNurseOrDriver() {
		return doctorOrNurseOrDriver;
	}

	public void setDoctorOrNurseOrDriver(String doctorOrNurseOrDriver) {
		this.doctorOrNurseOrDriver = doctorOrNurseOrDriver;
	}

	public void setPauseReason(String pauseReason) {
		this.pauseReason = pauseReason;
	}

	public String getAlarmPhone() {
		return alarmPhone;
	}

	public void setAlarmPhone(String alarmPhone) {
		this.alarmPhone = alarmPhone;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public String getHungReason() {
		return hungReason;
	}

	public void setHungReason(String hungReason) {
		this.hungReason = hungReason;
	}

	public String getEmptyReason() {
		return emptyReason;
	}

	public void setEmptyReason(String emptyReason) {
		this.emptyReason = emptyReason;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getPauseReason() {
		return pauseReason;
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

	private String order;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

}

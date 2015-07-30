package com.xhs.ems.bean;

/**
 * 事件类型统计实体
 * @author CUIXINGWEI
 *
 */
public class AcceptEventType {
	/**
	 * 调度员
	 */
	private String dispatcher;
	/**
	 * 接电话数
	 */
	private String numbersOfPhone;
	/**
	 * 派车数
	 */
	private String numbersOfSendCar;
	/**
	 * 正常派车
	 */
	private String numbersOfNormalSendCar;
	/**
	 * 正常挂起
	 */
	private String numbersOfNormalHangUp;
	/**
	 * 增援派车
	 */
	private String numbersOfReinforceSendCar;
	/**
	 * 增援挂起
	 */
	private String numbersOfReinforceHangUp;
	/**
	 * 中止任务
	 */
	private String numbersOfStopTask;
	/**
	 * 特殊事件
	 */
	private String specialEvent;
	/**
	 * 欲派无车
	 */
	private String noCar;
	/**
	 * 转分中心
	 */
	private String transmitCenter;
	/**
	 * 拒绝出车
	 */
	private String refuseSendCar;
	/**
	 * 唤醒待派
	 */
	private String wakeSendCar;
	/**
	 * 出车结果（中止任务）
	 */
	private String stopTask;
	/**
	 * 中止任务比率
	 */
	private String ratioStopTask;
	/**
	 * 放空车
	 */
	private String emptyCar;
	/**
	 * 空车比率
	 */
	private String ratioEmptyCar;
	/**
	 * 正常完成
	 */
	private String nomalComplete;
	/**
	 * 正常完成比率
	 */
	private String ratioComplete;
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getNumbersOfPhone() {
		return numbersOfPhone;
	}
	public void setNumbersOfPhone(String numbersOfPhone) {
		this.numbersOfPhone = numbersOfPhone;
	}
	public String getNumbersOfSendCar() {
		return numbersOfSendCar;
	}
	public void setNumbersOfSendCar(String numbersOfSendCar) {
		this.numbersOfSendCar = numbersOfSendCar;
	}
	public String getNumbersOfNormalSendCar() {
		return numbersOfNormalSendCar;
	}
	public void setNumbersOfNormalSendCar(String numbersOfNormalSendCar) {
		this.numbersOfNormalSendCar = numbersOfNormalSendCar;
	}
	public String getNumbersOfNormalHangUp() {
		return numbersOfNormalHangUp;
	}
	public void setNumbersOfNormalHangUp(String numbersOfNormalHangUp) {
		this.numbersOfNormalHangUp = numbersOfNormalHangUp;
	}
	public String getNumbersOfReinforceSendCar() {
		return numbersOfReinforceSendCar;
	}
	public void setNumbersOfReinforceSendCar(String numbersOfReinforceSendCar) {
		this.numbersOfReinforceSendCar = numbersOfReinforceSendCar;
	}
	public String getNumbersOfReinforceHangUp() {
		return numbersOfReinforceHangUp;
	}
	public void setNumbersOfReinforceHangUp(String numbersOfReinforceHangUp) {
		this.numbersOfReinforceHangUp = numbersOfReinforceHangUp;
	}
	public String getNumbersOfStopTask() {
		return numbersOfStopTask;
	}
	public void setNumbersOfStopTask(String numbersOfStopTask) {
		this.numbersOfStopTask = numbersOfStopTask;
	}
	public String getSpecialEvent() {
		return specialEvent;
	}
	public void setSpecialEvent(String specialEvent) {
		this.specialEvent = specialEvent;
	}
	public String getNoCar() {
		return noCar;
	}
	public void setNoCar(String noCar) {
		this.noCar = noCar;
	}
	public String getTransmitCenter() {
		return transmitCenter;
	}
	public void setTransmitCenter(String transmitCenter) {
		this.transmitCenter = transmitCenter;
	}
	public String getRefuseSendCar() {
		return refuseSendCar;
	}
	public void setRefuseSendCar(String refuseSendCar) {
		this.refuseSendCar = refuseSendCar;
	}
	public String getWakeSendCar() {
		return wakeSendCar;
	}
	public void setWakeSendCar(String wakeSendCar) {
		this.wakeSendCar = wakeSendCar;
	}
	public String getStopTask() {
		return stopTask;
	}
	public void setStopTask(String stopTask) {
		this.stopTask = stopTask;
	}
	public String getRatioStopTask() {
		return ratioStopTask;
	}
	public void setRatioStopTask(String ratioStopTask) {
		this.ratioStopTask = ratioStopTask;
	}
	public String getEmptyCar() {
		return emptyCar;
	}
	public void setEmptyCar(String emptyCar) {
		this.emptyCar = emptyCar;
	}
	public String getRatioEmptyCar() {
		return ratioEmptyCar;
	}
	public void setRatioEmptyCar(String ratioEmptyCar) {
		this.ratioEmptyCar = ratioEmptyCar;
	}
	public String getNomalComplete() {
		return nomalComplete;
	}
	public void setNomalComplete(String nomalComplete) {
		this.nomalComplete = nomalComplete;
	}
	public String getRatioComplete() {
		return ratioComplete;
	}
	public void setRatioComplete(String ratioComplete) {
		this.ratioComplete = ratioComplete;
	}
	public AcceptEventType(String dispatcher, String numbersOfPhone,
			String numbersOfSendCar, String numbersOfNormalSendCar,
			String numbersOfNormalHangUp, String numbersOfReinforceSendCar,
			String numbersOfReinforceHangUp, String numbersOfStopTask,
			String specialEvent, String noCar, String transmitCenter,
			String refuseSendCar, String wakeSendCar, String stopTask,
			String ratioStopTask, String emptyCar, String ratioEmptyCar,
			String nomalComplete, String ratioComplete) {
		this.dispatcher = dispatcher;
		this.numbersOfPhone = numbersOfPhone;
		this.numbersOfSendCar = numbersOfSendCar;
		this.numbersOfNormalSendCar = numbersOfNormalSendCar;
		this.numbersOfNormalHangUp = numbersOfNormalHangUp;
		this.numbersOfReinforceSendCar = numbersOfReinforceSendCar;
		this.numbersOfReinforceHangUp = numbersOfReinforceHangUp;
		this.numbersOfStopTask = numbersOfStopTask;
		this.specialEvent = specialEvent;
		this.noCar = noCar;
		this.transmitCenter = transmitCenter;
		this.refuseSendCar = refuseSendCar;
		this.wakeSendCar = wakeSendCar;
		this.stopTask = stopTask;
		this.ratioStopTask = ratioStopTask;
		this.emptyCar = emptyCar;
		this.ratioEmptyCar = ratioEmptyCar;
		this.nomalComplete = nomalComplete;
		this.ratioComplete = ratioComplete;
	}
	
}

package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年8月18日 上午9:35:07
 */
public class DispatcherWorkload {
	private String dispatcher; // 调度员
	private String numbersOfPhone; // 电话总数
	private String inOfPhone; // 接入电话
	private String outOfPhone; // 打出电话
	private String numbersOfSendCar; // 有效出车
	private String numbersOfNormalSendCar; // 正常完成
	private String emptyCar; // 空车
	private String numbersOfStopTask; // 中止任务
	private String refuseCar; // 拒绝出车
	private String takeBacks; // 接回病人数
	private String triageNumber; // 分诊数

	public DispatcherWorkload() {

	}

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

	public String getInOfPhone() {
		return inOfPhone;
	}

	public void setInOfPhone(String inOfPhone) {
		this.inOfPhone = inOfPhone;
	}

	public String getOutOfPhone() {
		return outOfPhone;
	}

	public void setOutOfPhone(String outOfPhone) {
		this.outOfPhone = outOfPhone;
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

	public String getEmptyCar() {
		return emptyCar;
	}

	public void setEmptyCar(String emptyCar) {
		this.emptyCar = emptyCar;
	}

	public String getNumbersOfStopTask() {
		return numbersOfStopTask;
	}

	public void setNumbersOfStopTask(String numbersOfStopTask) {
		this.numbersOfStopTask = numbersOfStopTask;
	}

	public String getRefuseCar() {
		return refuseCar;
	}

	public void setRefuseCar(String refuseCar) {
		this.refuseCar = refuseCar;
	}

	public String getTakeBacks() {
		return takeBacks;
	}

	public void setTakeBacks(String takeBacks) {
		this.takeBacks = takeBacks;
	}

	public String getTriageNumber() {
		return triageNumber;
	}

	public void setTriageNumber(String triageNumber) {
		this.triageNumber = triageNumber;
	}

}

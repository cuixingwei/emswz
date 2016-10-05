package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午12:13:47
 * @category 车辆工作情况
 */
public class CarWork {
	/**
	 * 车辆
	 */
	private String carCode;
	/**
	 * 出车次数
	 */
	private String outCarNumbers;
	/**
	 * 平均出车时长
	 */
	private String averageOutCarTimes;
	/**
	 * 到达现场次数
	 */
	private String arriveSpotNumbers;
	/**
	 * 平均到达现场时长
	 */
	private String averageArriveSpotTimes;
	/**
	 * 暂停次数
	 */
	private String pauseNumbers;
	/**
	 * 出诊里程
	 */
	private String outDistance;
	/**
	 * 里程统计(GPS)
	 */
	private String distance;

	public String getOutDistance() {
		return outDistance;
	}

	public void setOutDistance(String outDistance) {
		this.outDistance = outDistance;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getOutCarNumbers() {
		return outCarNumbers;
	}

	public void setOutCarNumbers(String outCarNumbers) {
		this.outCarNumbers = outCarNumbers;
	}

	public String getAverageOutCarTimes() {
		return averageOutCarTimes;
	}

	public void setAverageOutCarTimes(String averageOutCarTimes) {
		this.averageOutCarTimes = averageOutCarTimes;
	}

	public String getArriveSpotNumbers() {
		return arriveSpotNumbers;
	}

	public void setArriveSpotNumbers(String arriveSpotNumbers) {
		this.arriveSpotNumbers = arriveSpotNumbers;
	}

	public String getAverageArriveSpotTimes() {
		return averageArriveSpotTimes;
	}

	public void setAverageArriveSpotTimes(String averageArriveSpotTimes) {
		this.averageArriveSpotTimes = averageArriveSpotTimes;
	}

	public String getPauseNumbers() {
		return pauseNumbers;
	}

	public void setPauseNumbers(String pauseNumbers) {
		this.pauseNumbers = pauseNumbers;
	}

	public CarWork() {
		super();
	}

}

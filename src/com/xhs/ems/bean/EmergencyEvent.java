package com.xhs.ems.bean;

/**
 * @category 突发时间统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午2:28:31
 */
public class EmergencyEvent {
	/**
	 * 事件类型
	 */
	private String eventType;
	/**
	 * 次数
	 */
	private String times;
	/**
	 * 伤亡人数
	 */
	private String casualties;
	/**
	 * 轻
	 */
	private String light;
	/**
	 * 中
	 */
	private String middle;
	/**
	 * 重
	 */
	private String heavy;
	/**
	 * 死亡
	 */
	private String death;
	/**
	 * 里程
	 */
	private String distance;
	/**
	 * 反应时间
	 */
	private String responseTime;
	/**
	 * 总耗时
	 */
	private String timeTotal;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getCasualties() {
		return casualties;
	}

	public void setCasualties(String casualties) {
		this.casualties = casualties;
	}

	public String getLight() {
		return light;
	}

	public void setLight(String light) {
		this.light = light;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getHeavy() {
		return heavy;
	}

	public void setHeavy(String heavy) {
		this.heavy = heavy;
	}

	public String getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = death;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getTimeTotal() {
		return timeTotal;
	}

	public void setTimeTotal(String timeTotal) {
		this.timeTotal = timeTotal;
	}

	public EmergencyEvent(String eventType, String times, String casualties,
			String light, String middle, String heavy, String death,
			String distance, String responseTime, String timeTotal) {
		super();
		this.eventType = eventType;
		this.times = times;
		this.casualties = casualties;
		this.light = light;
		this.middle = middle;
		this.heavy = heavy;
		this.death = death;
		this.distance = distance;
		this.responseTime = responseTime;
		this.timeTotal = timeTotal;
	}

}

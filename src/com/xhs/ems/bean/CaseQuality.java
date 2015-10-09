package com.xhs.ems.bean;

/**
 * @category 病历质量统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午2:43:57
 */
public class CaseQuality {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 完整性
	 */
	private String wholeness;
	/**
	 * 及时性
	 */
	private String timely;
	/**
	 * 召回次数
	 */
	private String recallTimes;
	/**
	 * 得分统计
	 */
	private String score;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWholeness() {
		return wholeness;
	}

	public void setWholeness(String wholeness) {
		this.wholeness = wholeness;
	}

	public String getTimely() {
		return timely;
	}

	public void setTimely(String timely) {
		this.timely = timely;
	}

	public String getRecallTimes() {
		return recallTimes;
	}

	public void setRecallTimes(String recallTimes) {
		this.recallTimes = recallTimes;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public CaseQuality(String name, String wholeness, String timely,
			String recallTimes, String score) {
		this.name = name;
		this.wholeness = wholeness;
		this.timely = timely;
		this.recallTimes = recallTimes;
		this.score = score;
	}

}

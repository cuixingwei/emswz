package com.xhs.ems.bean;

/**
 * @category 非急救用车统计
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午3:17:01
 */
public class NonEmergencyVehicles {
	/**
	 * 院内转运_次数
	 */
	private String hospital_times;
	/**
	 * 院内转运_里程
	 */
	private String hospital_distance;
	/**
	 * 保障_次数
	 */
	private String safeguard_times;
	/**
	 * 保障_里程
	 */
	private String safeguard_distance;
	/**
	 * 义诊会诊_次数
	 */
	private String clinic_times;
	/**
	 * 义诊会诊_里程
	 * 
	 */
	private String clinic_distance;
	/**
	 * 演习培训_次数
	 */
	private String practice_times;
	/**
	 * 演习培训_里程
	 */
	private String practice_distance;
	/**
	 * 本院内_次数
	 */
	private String inHospital_times;
	/**
	 * 本院内_里程
	 */
	private String inHospital_distance;
	/**
	 * 行政用车_次数
	 */
	private String xh_times;
	/**
	 * 行政用车_里程
	 */
	private String xh_distance;
	/**
	 * 其他_次数
	 */
	private String other_times;
	/**
	 * 其他_里程
	 */
	private String other_distance;

	public String getHospital_times() {
		return hospital_times;
	}

	public void setHospital_times(String hospital_times) {
		this.hospital_times = hospital_times;
	}

	public String getHospital_distance() {
		return hospital_distance;
	}

	public void setHospital_distance(String hospital_distance) {
		this.hospital_distance = hospital_distance;
	}

	public String getSafeguard_times() {
		return safeguard_times;
	}

	public void setSafeguard_times(String safeguard_times) {
		this.safeguard_times = safeguard_times;
	}

	public String getSafeguard_distance() {
		return safeguard_distance;
	}

	public void setSafeguard_distance(String safeguard_distance) {
		this.safeguard_distance = safeguard_distance;
	}

	public String getClinic_times() {
		return clinic_times;
	}

	public void setClinic_times(String clinic_times) {
		this.clinic_times = clinic_times;
	}

	public String getClinic_distance() {
		return clinic_distance;
	}

	public void setClinic_distance(String clinic_distance) {
		this.clinic_distance = clinic_distance;
	}

	public String getPractice_times() {
		return practice_times;
	}

	public void setPractice_times(String practice_times) {
		this.practice_times = practice_times;
	}

	public String getPractice_distance() {
		return practice_distance;
	}

	public void setPractice_distance(String practice_distance) {
		this.practice_distance = practice_distance;
	}

	public String getInHospital_times() {
		return inHospital_times;
	}

	public void setInHospital_times(String inHospital_times) {
		this.inHospital_times = inHospital_times;
	}

	public String getInHospital_distance() {
		return inHospital_distance;
	}

	public void setInHospital_distance(String inHospital_distance) {
		this.inHospital_distance = inHospital_distance;
	}

	public String getXh_times() {
		return xh_times;
	}

	public void setXh_times(String xh_times) {
		this.xh_times = xh_times;
	}

	public String getXh_distance() {
		return xh_distance;
	}

	public void setXh_distance(String xh_distance) {
		this.xh_distance = xh_distance;
	}

	public String getOther_times() {
		return other_times;
	}

	public void setOther_times(String other_times) {
		this.other_times = other_times;
	}

	public String getOther_distance() {
		return other_distance;
	}

	public void setOther_distance(String other_distance) {
		this.other_distance = other_distance;
	}

	public NonEmergencyVehicles(String hospital_times,
			String hospital_distance, String safeguard_times,
			String safeguard_distance, String clinic_times,
			String clinic_distance, String practice_times,
			String practice_distance, String inHospital_times,
			String inHospital_distance, String xh_times, String xh_distance,
			String other_times, String other_distance) {
		this.hospital_times = hospital_times;
		this.hospital_distance = hospital_distance;
		this.safeguard_times = safeguard_times;
		this.safeguard_distance = safeguard_distance;
		this.clinic_times = clinic_times;
		this.clinic_distance = clinic_distance;
		this.practice_times = practice_times;
		this.practice_distance = practice_distance;
		this.inHospital_times = inHospital_times;
		this.inHospital_distance = inHospital_distance;
		this.xh_times = xh_times;
		this.xh_distance = xh_distance;
		this.other_times = other_times;
		this.other_distance = other_distance;
	}

}

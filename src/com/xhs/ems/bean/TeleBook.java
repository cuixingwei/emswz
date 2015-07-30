package com.xhs.ems.bean;

/**
 * 通讯录实体
 * @author CUIXINGWEI
 *
 */
public class TeleBook {
	/**
	 * 单位名称
	 */
	private String department;
	/**
	 * 机主姓名
	 */
	private String ownerName;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	/**
	 * 固定电话
	 */
	private String fixedPhone;
	/**
	 * 分机
	 */
	private String extension;
	/**
	 * 移动电话
	 */
	private String mobilePhone;
	/**
	 * 小灵通
	 */
	private String littleSmart;
	/**
	 * 备注 
	 */
	private String remark;
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getFixedPhone() {
		return fixedPhone;
	}
	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getLittleSmart() {
		return littleSmart;
	}
	public void setLittleSmart(String littleSmart) {
		this.littleSmart = littleSmart;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TeleBook(String department, String ownerName, String contactPhone,
			String fixedPhone, String extension, String mobilePhone,
			String littleSmart, String remark) {
		this.department = department;
		this.ownerName = ownerName;
		this.contactPhone = contactPhone;
		this.fixedPhone = fixedPhone;
		this.extension = extension;
		this.mobilePhone = mobilePhone;
		this.littleSmart = littleSmart;
		this.remark = remark;
	}
	
}

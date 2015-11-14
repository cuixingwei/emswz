package com.xhs.ems.bean;

/**
 * 出诊时间大于3分钟统计表
 * 
 * @datetime 2015年11月14日 下午2:32:54
 * @author 崔兴伟
 */
public class OutOfThree {
	/**
	 * 出诊类型
	 */
	private String outType;
	/**
	 * 标准出诊计数
	 */
	private String normalNumbers;
	private String rate1;
	/**
	 * 超过3分钟出诊
	 */
	private String lateNumbers;
	private String rate2;
	/**
	 * 总出车数
	 */
	private String total;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getNormalNumbers() {
		return normalNumbers;
	}

	public void setNormalNumbers(String normalNumbers) {
		this.normalNumbers = normalNumbers;
	}

	public String getRate1() {
		return rate1;
	}

	public void setRate1(String rate1) {
		this.rate1 = rate1;
	}

	public String getLateNumbers() {
		return lateNumbers;
	}

	public void setLateNumbers(String lateNumbers) {
		this.lateNumbers = lateNumbers;
	}

	public String getRate2() {
		return rate2;
	}

	public void setRate2(String rate2) {
		this.rate2 = rate2;
	}

	public OutOfThree() {
	}

}

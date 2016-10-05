package com.xhs.ems.dao;

import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 上午9:14:13
 */
public interface DriverOutCallDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月22日 上午9:14:30
	 * @param parameter
	 * @return 司机出诊表
	 */
	public Grid getData(Parameter parameter);

	/**
	 * 司机出诊明细表
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年10月8日 上午11:03:38
	 * @param parameter
	 * @return
	 */
	public Grid getDriverDetail(Parameter parameter);

	/**
	 * 医生护士出诊明细表
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年10月9日 上午8:50:54
	 * @param parameter
	 * @return
	 */
	public Grid getDoctorNurseDetail(Parameter parameter);

	/**
	 * 三峡中心医院出诊明细表
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年10月9日 上午9:36:26
	 * @param parameter
	 * @return
	 */
	public Grid getCenterHospitalOutDetail(Parameter parameter);
}

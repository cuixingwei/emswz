package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.Car;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CarDAO;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:30:07
 */
@Repository
public class CarDAOImpl implements CarDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.dao.CarDAO#getData()
	 * @datetime 2015年4月10日 下午2:30:07
	 */
	@Override
	public List<Car> getData(String stationID) {
		String sql = "select * from AuSp120.tb_Ambulance  where 有效标志=1";
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!CommonUtil.isNullOrEmpty(stationID)) {
			sql = sql + " and 分站编码= :stationID";
			paramMap.put("stationID", stationID);
		}
		final List<Car> results = new ArrayList<Car>();
		this.npJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				Car car = new Car(rs.getString("车辆编码"), rs.getString("车牌号码"));
				results.add(car);
			}
		});
		return results;
	}

}

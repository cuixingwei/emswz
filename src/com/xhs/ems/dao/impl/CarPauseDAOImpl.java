package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.CarPause;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CarPauseDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午4:27:14
 */
@Repository
public class CarPauseDAOImpl implements CarPauseDAO {
	private static final Logger logger = Logger
			.getLogger(CarPauseDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午4:27:14
	 * @see com.xhs.ems.dao.CarPauseDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select am.实际标识 carCode,rpr.司机 driver,isnull(DATEDIFF(Second,rpr.操作时刻,rpr.结束时刻),0) pauseTimes,convert(varchar(20),rpr.操作时刻,120) pauseTime,"
				+ "convert(varchar(20),rpr.结束时刻,120) endTime,m.姓名 dispatcher,rpr.暂停调用原因 pauseReason	"
				+ "from AuSp120.tb_RecordPauseReason rpr	"
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=rpr.车辆编码	"
				+ "left outer join AuSp120.tb_MrUser m on rpr.调度员编码=m.工号  "
				+ "left outer join AuSp120.tb_DPauseReason dpr on dpr.NameM=rpr.暂停调用原因	"
				+ "where rpr.操作时刻 between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + "and rpr.调度员编码= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and am.分站编码=:station ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql = sql + " and rpr.车辆编码=:carCode ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getPauseReason())) {
			sql = sql + " and dpr.Code=:pauseReason ";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("carCode", parameter.getCarCode());
		paramMap.put("pauseReason", parameter.getPauseReason());

		List<CarPause> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CarPause>() {
					@Override
					public CarPause mapRow(ResultSet rs, int index)
							throws SQLException {
						return new CarPause(rs.getString("carCode"), rs
								.getString("driver"), rs
								.getString("pauseTimes"), rs
								.getString("pauseTime"), rs
								.getString("endTime"), rs
								.getString("dispatcher"), rs
								.getString("pauseReason"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (CarPause result : results) {
			result.setPauseTimes(CommonUtil.formatSecond(result.getPauseTimes()));
		}
		Grid grid = new Grid();
		if ((int) parameter.getPage() > 0) {
			int page = (int) parameter.getPage();
			int rows = (int) parameter.getRows();

			int fromIndex = (page - 1) * rows;
			int toIndex = (results.size() <= page * rows && results.size() >= (page - 1)
					* rows) ? results.size() : page * rows;
			grid.setRows(results.subList(fromIndex, toIndex));
			grid.setTotal(results.size());

		} else {
			grid.setRows(results);
		}
		return grid;
	}

}

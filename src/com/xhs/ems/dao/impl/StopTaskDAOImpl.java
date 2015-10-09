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

import com.xhs.ems.bean.StopTask;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.StopTaskDAO;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午3:20:46
 */
@Repository
public class StopTaskDAOImpl implements StopTaskDAO {
	private static final Logger logger = Logger
			.getLogger(StopTaskDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.dao.StopTaskDAO#getData(com.xhs.ems.bean.Parameter)
	 * @datetime 2015年4月10日 下午3:20:46
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select  convert(varchar(20),e.受理时刻,120) acceptTime,a.现场地址 sickAddress,a.呼救电话 phone,"
				+ "m.姓名 dispatcher,am.实际标识 carCode,convert(varchar(20),t.出车时刻,120) drivingTime,	datediff(Second,t.途中待命时刻,t.完成时刻) emptyRunTime,"
				+ "s.分站名称 staion,dsr.NameM stopReason,t.备注 remark 	"
				+ "from AuSp120.tb_AcceptDescriptV a left outer join  AuSp120.tb_TaskV t on a.事件编码=t.事件编码  and a.受理序号=t.受理序号 "
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码     "
				+ "left outer join AuSp120.tb_MrUser m on t.调度员编码=m.工号   "
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码  "
				+ "left outer join AuSp120.tb_DStopReason dsr on dsr.Code=t.中止任务原因编码   "
				+ "left outer join AuSp120.tb_Station s on s.分站编码=t.分站编码	"
				+ "where e.事件性质编码=1 and t.结果编码=2 and e.受理时刻  between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and t.调度员编码= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getStopReason())) {
			sql = sql + " and t.中止任务原因编码= :stopReason ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and t.分站编码=:station ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql = sql + " and t.车辆编码=:carCode ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getEmptyRunTime())) {
			sql = sql + " and datediff(M,t.途中待命时刻,t.完成时刻)>:emptyRunTime ";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("station", parameter.getStation());
		paramMap.put("stopReason", parameter.getStopReason());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("carCode", parameter.getCarCode());

		List<StopTask> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<StopTask>() {
					@Override
					public StopTask mapRow(ResultSet rs, int index)
							throws SQLException {

						return new StopTask(rs.getString("acceptTime"), rs
								.getString("sickAddress"), rs
								.getString("phone"),
								rs.getString("dispatcher"), rs
										.getString("carCode"), rs
										.getString("drivingTime"), rs
										.getString("emptyRunTime"), rs
										.getString("staion"), rs
										.getString("stopReason"), rs
										.getString("remark"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (StopTask result : results) {
			result.setEmptyRunTime(CommonUtil.formatSecond(result
					.getEmptyRunTime()));
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

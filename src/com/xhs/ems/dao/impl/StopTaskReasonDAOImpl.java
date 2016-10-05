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

import com.xhs.ems.bean.StopTaskReason;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.StopTaskReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午2:58:06
 */
@Repository
public class StopTaskReasonDAOImpl implements StopTaskReasonDAO {
	private static final Logger logger = Logger
			.getLogger(StopTaskReasonDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午2:58:06
	 * @see com.xhs.ems.dao.StopTaskReasonDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select dsr.NameM reason,COUNT(t.任务编码) times,'' rate "
				+ "	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_DStopReason dsr on dsr.Code=t.中止任务原因编码  "
				+ " where e.事件性质编码=1 and t.结果编码=2 and t.生成任务时刻  between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + "and t.分站编码=:station ";
		}
		sql = sql + " group by dsr.NameM";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());

		List<StopTaskReason> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<StopTaskReason>() {
					@Override
					public StopTaskReason mapRow(ResultSet rs, int index)
							throws SQLException {
						return new StopTaskReason(rs.getString("reason"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		int totaltimes = 0;
		// 计算总终止次数
		for (StopTaskReason result : results) {
			totaltimes += Integer.parseInt(result.getTimes());
		}
		// 计算比率
		for (StopTaskReason result : results) {
			result.setRate(CommonUtil.calculateRate(totaltimes,
					Integer.parseInt(result.getTimes())));
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

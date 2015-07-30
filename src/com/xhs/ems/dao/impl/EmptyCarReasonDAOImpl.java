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

import com.xhs.ems.bean.EmptyCarReason;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.EmptyCarReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 下午1:47:00
 */
@Repository
public class EmptyCarReasonDAOImpl implements EmptyCarReasonDAO {

	private static final Logger logger = Logger
			.getLogger(EmptyCarReasonDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 下午1:47:00
	 * @see com.xhs.ems.dao.EmptyCarReasonDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select der.NameM reason,COUNT(t.任务编码) times,'' rate from AuSp120.tb_AcceptDescriptV a	"
				+ "left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DEmptyReason der on der.Code=t.放空车原因编码 	"
				+ "where t.结果编码=3 and e.事件性质编码=1	and t.放空车原因编码 is not null "
				+ "and a.开始受理时刻 between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and t.分站编码=:station ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getEmptyReason())) {
			sql += " and t.放空车原因编码=:emptyReason";
		}
		sql += " group by der.NameM";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		paramMap.put("emptyReason", parameter.getEmptyReason());

		List<EmptyCarReason> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<EmptyCarReason>() {
					@Override
					public EmptyCarReason mapRow(ResultSet rs, int index)
							throws SQLException {
						return new EmptyCarReason(rs.getString("reason"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		int totaltimes = 0;
		// 计算总终止次数
		for (EmptyCarReason result : results) {
			totaltimes += Integer.parseInt(result.getTimes());
		}
		// 计算比率
		for (EmptyCarReason result : results) {
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

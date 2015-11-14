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

import com.xhs.ems.bean.EmptyCar;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.EmptyCarDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午8:55:42
 */
@Repository
public class EmptyCarDAOImpl implements EmptyCarDAO {
	private static final Logger logger = Logger
			.getLogger(EmptyCarDAOImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void SetDataSourceTag(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午8:55:42
	 * @see com.xhs.ems.dao.EmptyCarDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select convert(varchar(20),a.开始受理时刻,120) acceptTime,a.现场地址 sickAddress,"
				+ "m.姓名 dispatcher,	isnull(DATEDIFF(Second,t.出车时刻,t.途中待命时刻),0) emptyRunTimes,der.NameM emptyReason,et.NameM eventType	 	"
				+ "from AuSp120.tb_AcceptDescriptV a	"
				+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码	 "
				+ "left outer join AuSp120.tb_MrUser m on t.调度员编码=m.工号	"
				+ "left outer join AuSp120.tb_DEmptyReason der on der.Code=t.放空车原因编码  	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and t.结果编码=3 and t.放空车原因编码 is not null and a.开始受理时刻 between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql += " and t.调度员编码=:dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getEmptyReason())) {
			sql += " and t.放空车原因编码=:emptyCarReason ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql += " and t.分站编码=:station ";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		paramMap.put("emptyCarReason", parameter.getEmptyReason());

		List<EmptyCar> results = this.namedParameterJdbcTemplate.query(sql,
				paramMap, new RowMapper<EmptyCar>() {
					@Override
					public EmptyCar mapRow(ResultSet rs, int index)
							throws SQLException {

						return new EmptyCar(rs.getString("acceptTime"), rs
								.getString("sickAddress"), rs
								.getString("dispatcher"), rs
								.getString("emptyRunTimes"), rs
								.getString("emptyReason"),rs.getString("eventType"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (EmptyCar result : results) {
			result.setEmptyRunTimes(CommonUtil.formatSecond(result
					.getEmptyRunTimes()));
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

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

import com.xhs.ems.bean.HungEventReason;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.HungEventReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午2:09:40
 */
@Repository
public class HungEventReasonDAOImpl implements HungEventReasonDAO {
	private static final Logger logger = Logger
			.getLogger(HungEventReasonDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午2:09:40
	 * @see com.xhs.ems.dao.HungEventReasonDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select dhr.NameM reason,COUNT(a.开始受理时刻) times,'' rate	"
				+ "from AuSp120.tb_AcceptDescriptV a	"
				+ "left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DHangReason dhr on dhr.Code=a.挂起原因编码	"
				+ "where e.事件性质编码=1 and a.开始受理时刻 between :startTime and :endTime	"
				+ "and a.挂起原因编码 is not null	";
		if (!CommonUtil.isNullOrEmpty(parameter.getHungReason())) {
			sql += " and a.挂起原因编码=:hungReason";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql += " and e.调度员编码=:dispatcher";
		}
		sql += " group by dhr.NameM";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("hungReason", parameter.getHungReason());
		paramMap.put("dispatcher", parameter.getDispatcher());

		List<HungEventReason> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<HungEventReason>() {
					@Override
					public HungEventReason mapRow(ResultSet rs, int index)
							throws SQLException {
						return new HungEventReason(rs.getString("reason"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		int totaltimes = 0;
		// 计算总终止次数
		for (HungEventReason result : results) {
			totaltimes += Integer.parseInt(result.getTimes());
		}
		// 计算比率
		for (HungEventReason result : results) {
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

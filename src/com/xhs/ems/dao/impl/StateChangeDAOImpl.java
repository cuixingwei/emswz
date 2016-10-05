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

import com.xhs.ems.bean.StateChange;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.StateChangeDAO;

/**
 * @author CUIXINGWEI
 * @date 2015年3月30日
 */
@Repository
public class StateChangeDAOImpl implements StateChangeDAO {
	private static final Logger logger = Logger
			.getLogger(StateChangeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.dao.StateChangeDAO#getData(com.xhs.ems.bean.Parameter)
	 * @date 2015年3月30日
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select sl.座席号 seatCode,m.姓名 dispatcher,sl.座席状态 seatState,sl.开始时刻 startTime,sl.结束时刻 endTime	"
				+ "from AuSp120.tb_SlinoLog sl	left outer join AuSp120.tb_MrUser m on sl.调度员编码=m.工号 "
				+ "where sl.开始时刻 between :startTime and :endTime and m.人员类型=0";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and sl.调度员编码=:dispatcher ";
		}
		sql = sql + " order by sl.调度员编码";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<StateChange> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<StateChange>() {
					@Override
					public StateChange mapRow(ResultSet rs, int index)
							throws SQLException {

						return new StateChange(rs.getString("seatCode"), rs
								.getString("dispatcher"), rs
								.getString("seatState"), rs
								.getString("startTime"), rs
								.getString("endTime"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

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

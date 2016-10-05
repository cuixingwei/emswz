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

import com.xhs.ems.bean.ReturnVisit;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.ReturnVisitDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:21:35
 */
@Repository
public class ReturnVisitDAOImpl implements ReturnVisitDAO {

	private static final Logger logger = Logger
			.getLogger(ReturnVisitDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = " ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<ReturnVisit> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<ReturnVisit>() {
					@Override
					public ReturnVisit mapRow(ResultSet rs, int index)
							throws SQLException {

						return new ReturnVisit(rs.getString("date"), rs
								.getString("name"), rs.getString("phone"), rs
								.getString("address"), rs
								.getString("PreDisgnose"),
								rs.getString("cost"), rs.getString("doctor"),
								rs.getString("nurse"), rs.getString("driver"),
								rs.getString("dispatcher"), rs
										.getString("satisfyCount"), rs
										.getString("commonCount"), rs
										.getString("unsatisfyCount"), rs
										.getString("totalScore"));
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
